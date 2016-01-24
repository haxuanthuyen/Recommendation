package com.hust.soict.hxt.recommendation.services;

import com.hust.soict.hxt.recommendation.algorithm.ner.ModelFactory;
import com.hust.soict.hxt.recommendation.global.GlobalObject;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class GlobalResourceInit {

    private static Logger logger = LoggerFactory.getLogger("GlobalResourceInit");

    public static void initModelMap() {

        if (GlobalObject.modelMap == null) {
            GlobalObject.modelMap = new HashMap<>();
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
                GlobalObject.modelMap.put(i, classifier);
                logger.info("finish load model " + modelPath);
            }
        }
        logger.info("finish load all model.");
    }
}
