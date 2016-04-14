package com.hust.soict.hxt.recommendation.services;

import com.hust.soict.hxt.recommendation.algorithm.ner.NERProcess;
import com.hust.soict.hxt.recommendation.algorithm.similarity.JaccardCoefficient;
import com.hust.soict.hxt.recommendation.bo.ItemCluster;
import com.hust.soict.hxt.recommendation.bo.ItemGroup;
import com.hust.soict.hxt.recommendation.bo.ItemHistory;
import com.hust.soict.hxt.recommendation.global.GlobalObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class HistorySuggestion {

    private static Logger logger = LoggerFactory.getLogger("suggestLog");
    private NERProcess nerProcess;

    public HistorySuggestion() {
        nerProcess = NERProcess.getInstance();
    }

    public void buildListSuggest(String guid) {
        List<ItemCluster> lstCluster = new ArrayList<>();
        try {
            HashMap<Integer, List<ItemHistory>> listHistory = getAllHistoryList(guid);
            Set<Integer> lstCat = listHistory.keySet();
            for (int catId : lstCat) {
                List<ItemHistory> valueLst = listHistory.get(catId);
                ItemCluster itemCluster = groupBySimilarityItem(valueLst, catId);
                lstCluster.add(itemCluster);
            }
            Collections.sort(lstCluster);
            System.out.println(lstCluster.size());
        }catch (Exception e) {
            logger.error("error process guid: " + guid, e);
        }

    }

    public HashMap<Integer, List<ItemHistory>> getAllHistoryList(String guid) throws Exception {

        HashMap<Integer, List<ItemHistory>> lstResult = new HashMap<>();
        List<ItemHistory> list = new ArrayList<>(GlobalObject.itemCache.get(guid));
        for (ItemHistory it : list) {
            String title = it.getTitle();
            int itemId = it.getItemId();
            HashMap<String, String> label = null;
            int catId = 0;
            try {
                catId = GlobalObject.catCache.get(itemId);
                label = nerProcess.tokenizeWithLabel(title, catId);
                it.setCatId(catId);
                it.setLabel(label);

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
        groupList.entrySet().forEach(x -> {
            ItemGroup itemGroup = x.getValue();
            ItemHistory item = null;
            try {
                item = (ItemHistory) itemGroup.getItemReplace().clone();
            } catch (CloneNotSupportedException e) {
                logger.error("error clone item ", item.getItemId());
            }
            item.setScore(itemGroup.getTotalScore());
            item.setLabel(itemGroup.getTotalLabel());
            historyList.add(item);
            cluster.setTotalScore(item.getScore());
        });
        Collections.sort(historyList);
        cluster.setHistoryList(historyList);
        cluster.setCatId(catId);

        return cluster;
    }
}
