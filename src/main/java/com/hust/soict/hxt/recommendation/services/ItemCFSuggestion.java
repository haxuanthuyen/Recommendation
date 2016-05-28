package com.hust.soict.hxt.recommendation.services;

import com.hust.soict.hxt.recommendation.algorithm.ner.NERProcess;
import com.hust.soict.hxt.recommendation.algorithm.similarity.JaccardCoefficient;
import com.hust.soict.hxt.recommendation.algorithm.similarity.WeightFactory;
import com.hust.soict.hxt.recommendation.bo.*;
import com.hust.soict.hxt.recommendation.dao.CategoryDao;
import com.hust.soict.hxt.recommendation.dao.HistoryDao;
import com.hust.soict.hxt.recommendation.global.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.swing.BakedArrayList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by thuyenhx on 5/28/16.
 */
public class ItemCFSuggestion {
    private static Logger logger = LoggerFactory.getLogger("suggestLog");
    private JaccardCoefficient jaccard = new JaccardCoefficient();
    private HistorySuggestion historySuggestion;
    private HistoryDao historyDao;
    private CategoryDao categoryDao;

    public ItemCFSuggestion() throws Exception {
        historySuggestion = new HistorySuggestion();
        historyDao = new HistoryDao();
        categoryDao = new CategoryDao();
    }


    public void buildListSimilarity(int catId) {

        String startDate = "2016-03-13";
        String endDate = "2016-03-08";

        try {
            //lay thong tin sp theo danh muc va gan nhan
            List<ItemData> lstCompare = categoryDao.getItemByCat(catId);
            NERProcess nerProcess = NERProcess.getInstance();
            for (ItemData item : lstCompare) {
                HashMap<String, String> label = nerProcess.tokenizeWithLabel(item.getTitle(), catId);
                item.setLabel(label);
            }

            for (ItemData itemD : lstCompare) {
                ItemSimilarity itemS = buildSimilarityByItem(itemD, catId, startDate, endDate);
                historyDao.updateItemSimilarity(itemS);
            }
        }catch (Exception e) {
            logger.error("error build list similarity for catId " + catId, e);
        }
    }

    public ItemSimilarity buildSimilarityByItem(ItemData itemD, int catId, String startDate, String endDate) throws Exception{

        List<ItemHistory> tmpHistory = new ArrayList<>();
        List<String> lstGuid = historyDao.getGuidViewItem(itemD.getItemId(), endDate);
        for (String guid : lstGuid) {
            List<ItemCluster> lstCluster = historySuggestion.buildListHistory(guid, startDate, endDate);
            for (ItemCluster itemC : lstCluster) {
                tmpHistory.addAll(itemC.getHistoryList());
            }
        }

        //tinh toan do tuong dong
        Map<Integer, ItemCount> mapCount = new HashMap<>();
        for (ItemHistory itemH : tmpHistory) {
            double sim = calSim(itemH.getLabel(), itemD.getLabel(), itemH.getCatId(), itemD.getCatId());
            if (sim >= 0.15 && itemD.getItemId() != itemH.getItemId()) {
                int id = itemH.getItemId();
                ItemCount itemCount = mapCount.getOrDefault(id, new ItemCount(id, 1));
                itemCount.add(1);
                mapCount.put(id, itemCount);
            }
        }

        //dua ra list sp tuong dong, uu tien theo so lan duoc xem nhieu nhat
        List<ItemCount> lstCount = mapCount.values().stream().sorted().collect(Collectors.toList());
        StringBuilder sb = new StringBuilder("");
        for (ItemCount itemCount : lstCount) {
            sb.append(itemCount.getItemId() + ",");
        }
        if (sb.length() > 0) sb.delete(sb.length() - 1, sb.length());
        String itemSimilarity = sb.toString();
        ItemSimilarity itemS = new ItemSimilarity(itemD.getItemId(), catId, itemSimilarity, startDate);

        return itemS;
    }

    public double calSim(Map<String, String> labelA, Map<String, String> labelB, int catIdA, int catIdB) {
        double totalSim = 0.0;
        Map<String, Double> weightNerA = WeightFactory.getWeightNer(catIdA);
        Map<String, Double> weightNerB = WeightFactory.getWeightNer(catIdB);
        Set<String> lstLabel = new HashSet<>();
        lstLabel.addAll(labelA.keySet());
        lstLabel.addAll(labelB.keySet());

        for (String label : lstLabel) {
            String lb = label;
            double fixWeight = 1.0;

            boolean flag = label.contains("-");
            if (flag) {
                lb = lb.split("-")[0];
                fixWeight = 0.5;
            }
            if (weightNerA.get(lb) == null || weightNerB.get(lb) == null) fixWeight = 0;
            double weight = weightNerA.getOrDefault(lb, 0.0)*fixWeight;

            String infoA = labelA.getOrDefault(label,"");
            String infoB = labelB.getOrDefault(label,"");
            List<String> lstA = Arrays.asList(infoA.split(","));
            List<String> lstB = Arrays.asList(infoB.split(","));
            double sim = jaccard.similarity(lstA, lstB);

            totalSim += sim*weight;
        }

        return totalSim;
    }
}
