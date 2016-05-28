package com.hust.soict.hxt.recommendation.services;

import com.hust.soict.hxt.recommendation.algorithm.ner.ModelFactory;
import com.hust.soict.hxt.recommendation.algorithm.ner.NERProcess;
import com.hust.soict.hxt.recommendation.bo.ItemData;
import com.hust.soict.hxt.recommendation.dao.CategoryDao;
import com.hust.soict.hxt.recommendation.global.Resource;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class GlobalResourceInit {

    private static Logger logger = LoggerFactory.getLogger(GlobalResourceInit.class);

    public static void initModelMap() {

        if (Resource.modelMap == null) {
            Resource.modelMap = new HashMap<>();
        }
        logger.info("start load model...");
        for (int i = 0; i <= 12; i++) {
            String modelPath = ModelFactory.getModel(i);
            if (modelPath != null) {
                AbstractSequenceClassifier<CoreLabel> classifier = null;
                try {
                    classifier = CRFClassifier.getClassifier(modelPath);
                } catch (Exception e) {
                    logger.error("error init model map of cat " + i);
                }
                Resource.modelMap.put(i, classifier);
                logger.info("finish load model " + modelPath);
            }
        }
        logger.info("finish load all model.");
    }

    public static void loadCategoryCache() throws SQLException {
        CategoryDao categoryDAO = new CategoryDao();
        if (Resource.catCache == null) {
            Resource.catCache = new HashMap<>();
            HashMap<Integer, Integer> itemMap = categoryDAO.mapAllItemByCat();
            Resource.catCache.putAll(itemMap);
            logger.info("finish load category caching ");
        }
        categoryDAO.dispose();
    }

    public static void loadDataCache() throws SQLException {
        CategoryDao categoryDAO = new CategoryDao();



        if (Resource.itemDetailCache == null) {
            Resource.itemDetailCache = new HashMap<>();
            HashMap<Integer, List<ItemData>> itemCache = categoryDAO.getAllItemByCat();
            NERProcess nerProcess = NERProcess.getInstance();
            for (Map.Entry entry : itemCache.entrySet()) {
                int catId = (int) entry.getKey();
                List<ItemData> lst = (List<ItemData>) entry.getValue();
                for (ItemData item : lst) {
                    HashMap<String, String> label = nerProcess.tokenizeWithLabel(item.getTitle(), catId);
                    item.setLabel(label);
                }
            }
            Resource.itemDetailCache.putAll(itemCache);
            logger.info("finish load all item details ");
        }

//        if (Resource.itemCache == null) {
//            Resource.itemCache = new HashMap<>();
//            HistoryDao itemDAO = null;
//            try{
//                String date = "2016-03-09";
//                itemDAO = new HistoryDao();
//                HashMap<String, List<ItemHistory>> itemLst = itemDAO.loadDataByDate(date);
//                Resource.itemCache.putAll(itemLst);
//                logger.info("finish load item cache");
//
//            }catch (Exception e) {
//                logger.warn("error load item cache ",e);
//            }finally {
//                itemDAO.dispose();
//            }
//        }
    }
}
