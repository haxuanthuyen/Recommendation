package com.hust.soict.hxt.recommendation.services;

import com.hust.soict.hxt.recommendation.algorithm.ner.NERProcess;
import com.hust.soict.hxt.recommendation.algorithm.similarity.JaccardCoefficient;
import com.hust.soict.hxt.recommendation.bo.ItemRate;
import com.hust.soict.hxt.recommendation.global.GlobalObject;
import edu.stanford.nlp.util.Execution;
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

        try {
            //lay du lieu item history theo guid va tokenize
            HashMap<Integer, List<ItemRate>> listHistory = getAllHistoryList(guid);
        }catch (Exception e) {
            logger.error("error process guid: " + guid, e);
        }

    }

    public HashMap<Integer, List<ItemRate>> getAllHistoryList(String guid) throws Exception {

        HashMap<Integer, List<ItemRate>> lstResult = new HashMap<>();
        List<ItemRate> list = GlobalObject.itemCache.get(guid);
        for (ItemRate it : list) {
            String title = it.getTitle();
            int itemId = it.getItemId();
            HashMap<String, String> label = null;
            int catId = 0;
            try {
                catId = GlobalObject.catCache.get(itemId);
                label = nerProcess.tokenizeWithLabel(title, catId);
                it.setCatId(catId);
                it.setLabel(label);

                List<ItemRate> lst = lstResult.get(it.getCatId());
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

    public void groupBySimilarityItem(List<ItemRate> lst) throws Exception {
        HashMap<String, List<ItemRate>> result = new HashMap<>();
        JaccardCoefficient jaccard = new JaccardCoefficient();
        for (ItemRate item : lst) {
            String pn = getProductName(item.getLabel(), item.getCatId());
            List<ItemRate> lstGroup;
            if (result.isEmpty()) {
                lstGroup = new ArrayList<>();
                lstGroup.add(item);
                result.put(pn, lstGroup);
            }else {
                Set<String> keys = result.keySet();
                boolean flag = false;
                for (String pnKey : keys) {
                    double sim = jaccard.similarity(pn, pnKey);
                    if (sim >= 0.6) {
                        lstGroup = result.get(pnKey);
                        lstGroup.add(item);
                        result.put(pnKey, lstGroup);
                        flag = true;
                    }
                }

                if (!flag) {
                    lstGroup = new ArrayList<>();
                    lstGroup.add(item);
                    result.put(pn, lstGroup);
                }
            }
        }
        System.out.println(result.size());
    }

    public String getProductName(HashMap<String, String> label, int catId) {
        if (catId == 1 || catId == 2 || catId == 4 || catId == 5 || catId == 6
                || catId == 7 || catId == 8 || catId == 9 || catId == 10 || catId == 11 || catId == 12) {
            return label.get("PN");
        }else if (catId == 3) {
            return label.get("SEV");
        }
        return null;
    }
}
