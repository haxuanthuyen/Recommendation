package com.hust.soict.hxt.recommendation.services;

import com.hust.soict.hxt.recommendation.algorithm.ner.NERProcess;
import com.hust.soict.hxt.recommendation.algorithm.similarity.JaccardCoefficient;
import com.hust.soict.hxt.recommendation.algorithm.similarity.WeightFactory;
import com.hust.soict.hxt.recommendation.bo.ItemCluster;
import com.hust.soict.hxt.recommendation.bo.ItemData;
import com.hust.soict.hxt.recommendation.bo.ItemGroup;
import com.hust.soict.hxt.recommendation.bo.ItemHistory;
import com.hust.soict.hxt.recommendation.dao.HistoryDao;
import com.hust.soict.hxt.recommendation.global.Resource;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class HistorySuggestion {

    private static Logger logger = LoggerFactory.getLogger("suggestLog");
    private NERProcess nerProcess;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private JaccardCoefficient jaccard = new JaccardCoefficient();

    public HistorySuggestion() {
        nerProcess = NERProcess.getInstance();
    }

    public List<ItemData> buildListSuggest(String guid, String startDate, String endDate) {
        List<ItemData> res = new ArrayList<>();
        List<ItemCluster> lstCluster = buildListHistory(guid, startDate, endDate);
        try {
            ItemCluster lstItem = (ItemCluster) lstCluster.get(0).clone();
            int catId = lstItem.getCatId();
            List<ItemData> lstCompare = Resource.itemDetailCache.get(catId);
            List<ItemHistory> lstHistory = lstItem.getHistoryList();
            for (ItemHistory hItem : lstHistory) {
                System.out.println(hItem.getTitle());
                for (ItemData compareItem : lstCompare) {
                    double sim = calSim(hItem.getLabel(), compareItem.getLabel(), catId);
                    if (sim >= 0.15 && compareItem.getItemId() != hItem.getItemId()) {
                        ItemData it = (ItemData) compareItem.clone();
                        it.setScore(hItem.getScore());
                        it.setSimilarity(sim);
                        res.add(it);
                        System.out.println("\t\t" + it.getTitle());
                    }
                }
            }
            Collections.sort(res);
        }catch (Exception e) {
            logger.error("error buid list suggest ",  e);
        }
        return res;
    }

    public double calSim(Map<String, String> labelA, Map<String, String> labelB, int catId) {
        double totalSim = 0.0;
        Map<String, Double> weightNer = WeightFactory.getWeightNer(catId);
        Set<String> lstLabel = new HashSet<>();
        lstLabel.addAll(labelA.keySet());
        lstLabel.addAll(labelB.keySet());
//        System.out.println(lstLabel);

        for (String label : lstLabel) {
            double weight = weightNer.get(label);

            String infoA = labelA.getOrDefault(label,"");
            String infoB = labelB.getOrDefault(label,"");
            List<String> lstA = Arrays.asList(infoA.split(","));
            List<String> lstB = Arrays.asList(infoB.split(","));
            double sim = jaccard.similarity(lstA, lstB);

            totalSim += sim*weight;
        }

        return totalSim;
    }

    public List<ItemCluster> buildListHistory(String guid, String startDate, String endDate) {
        List<ItemCluster> lstCluster = null;
        try {
            lstCluster = Resource.clusterCache.get(guid);
            if (lstCluster == null) {
                lstCluster = new ArrayList<>();
                HashMap<Integer, List<ItemHistory>> listHistory = getAllHistoryList(guid, startDate, endDate);
                Set<Integer> lstCat = listHistory.keySet();
                for (int catId : lstCat) {
                    List<ItemHistory> valueLst = listHistory.get(catId);
                    ItemCluster itemCluster = groupBySimilarityItem(valueLst, catId);
                    lstCluster.add(itemCluster);
                }
                Collections.sort(lstCluster);
            }
            Resource.clusterCache.put(guid, lstCluster);
        }catch (Exception e) {
            logger.error("error process guid: " + guid, e);
        }

        return lstCluster;
    }

    public HashMap<Integer, List<ItemHistory>> getAllHistoryList(String guid, String startDate, String endDate) throws Exception {

        HashMap<Integer, List<ItemHistory>> lstResult = new HashMap<>();
        HistoryDao itemDAO = new HistoryDao();
        List<ItemHistory> list = itemDAO.loadDataByGuid(endDate, guid);
        HashMap<String, Long> totalTimes = itemDAO.getTotalTime(guid, endDate);
        itemDAO.dispose();
        for (ItemHistory it : list) {
            String title = it.getTitle();
            int itemId = it.getItemId();
            String date = it.getDate();
            HashMap<String, String> label = null;
            int catId = 0;
            try {
                catId = Resource.catCache.get(itemId);
                label = nerProcess.tokenizeWithLabel(title, catId);
                it.setCatId(catId);
                it.setLabel(label);

                long timeLog = totalTimes.get(date);
                long timeEnd = sdf.parse(endDate).getTime();
                long itemTime = sdf.parse(date).getTime();
                long timeOnRead = it.getTimeOnRead();
                double score = WeightFactory.getScoreByHistory(timeEnd, itemTime, timeOnRead, timeLog);
                it.setScore(score);

                List<ItemHistory> lst = lstResult.get(it.getCatId());
                if (lst == null) {
                    lst = new ArrayList<>();
                }
                lst.add(it);
                lstResult.put(it.getCatId(), lst);
            }catch (Exception e) {
                logger.error("error process item: " + itemId);
            }
        }

        return lstResult;
    }


    /**
     * duyet qua cac item va cong don lai score cac
     *  san pham co do tuong dong lon hon 1 nguong la 0.6
     *  @return doi tuong ItemCluster gom catId,totalScore va danh sach cac item
     *  da duoc sap xep lai theo score
     */
    public ItemCluster groupBySimilarityItem(List<ItemHistory> lst, int catId) throws Exception {
        HashMap<String, ItemGroup> groupList = new HashMap<>();
        HashMap<String, ItemGroup> tmpGroupList;
        JaccardCoefficient jaccard = new JaccardCoefficient();
        boolean flag = false;

        for (ItemHistory item : lst) {
            if (item.getLabel() == null) continue;
            String pn = item.getProductName();
            ItemGroup itemGroup;
            if (!groupList.isEmpty() && groupList != null) {
                flag = false;
                Set<String> keys = groupList.keySet();
                tmpGroupList = new HashMap<>(groupList);
                for (String pnGroupKey : keys) {
                    double sim = jaccard.similarity(pn, pnGroupKey);
                    if (sim >= 0.6) {
                        itemGroup = groupList.get(pnGroupKey);
                        ItemHistory itemReplace = itemGroup.getItemReplace();
                        if (item.getScore() > itemGroup.getScore()) {
                            itemGroup.getDuplicateList().add(itemReplace);
                            itemGroup.setItemReplace(item);
                            itemGroup.setScore(item.getScore());
                            itemGroup.setTotalScore(item.getScore());
                            itemGroup.setTotalLabel(item.getLabel());
                            tmpGroupList.put(pn, itemGroup);
                            tmpGroupList.remove(pnGroupKey);
                        } else {
                            itemGroup.getDuplicateList().add(item);
                            itemGroup.setTotalScore(item.getScore());
                            itemGroup.setTotalLabel(item.getLabel());
                            tmpGroupList.put(pnGroupKey, itemGroup);
                        }
                        flag = true;
                    }
                }
                groupList =  tmpGroupList;
            }
            if (groupList.isEmpty() || !flag) {
                itemGroup = new ItemGroup();
                itemGroup.setItemReplace(item);
                itemGroup.setScore(item.getScore());
                itemGroup.setTotalScore(item.getScore());
                itemGroup.setTotalLabel(item.getLabel());
                groupList.put(pn, itemGroup);
            }
        }

        ItemCluster cluster = new ItemCluster();
        List<ItemHistory> historyList = new ArrayList<>();
        for (ItemGroup it : groupList.values()) {
            ItemHistory item = null;
            try {
                item = (ItemHistory) it.getItemReplace().clone();
            } catch (CloneNotSupportedException e) {
                logger.error("error clone item ", item.getItemId());
            }
            item.setScore(it.getTotalScore());
            item.setLabel(it.getTotalLabel());
            historyList.add(item);
            cluster.setTotalScore(item.getScore());
        }
        Collections.sort(historyList);
        int maxSize = historyList.size() > 10 ? 10 : historyList.size();
        historyList = historyList.subList(0, maxSize);
        cluster.setHistoryList(historyList);
        cluster.setCatId(catId);

        return cluster;
    }
}
