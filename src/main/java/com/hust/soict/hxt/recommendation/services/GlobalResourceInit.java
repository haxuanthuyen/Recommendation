package com.hust.soict.hxt.recommendation.services;

import com.hust.soict.hxt.recommendation.algorithm.ner.ModelFactory;
import com.hust.soict.hxt.recommendation.dao.CategoryDAO;
import com.hust.soict.hxt.recommendation.global.Resource;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;

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

    public static void loadDataCache() throws SQLException {
        if (Resource.catCache == null) {
            Resource.catCache = new HashMap<>();
            CategoryDAO categoryDAO = null;
            try {
                categoryDAO = new CategoryDAO();
                HashMap<Integer, Integer> itemMap = categoryDAO.loadAllItemByCat();
                Resource.catCache.putAll(itemMap);
                logger.info("finish load category caching ");

            }catch (Exception e) {
                logger.warn("error reload category caching ",e);
            }finally {
                categoryDAO.dispose();
            }
        }

//        if (Resource.itemCache == null) {
//            Resource.itemCache = new HashMap<>();
//            ItemDAO itemDAO = null;
//            try{
//                String date = "2016-03-09";
//                itemDAO = new ItemDAO();
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
