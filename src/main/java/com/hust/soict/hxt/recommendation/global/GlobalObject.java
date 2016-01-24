package com.hust.soict.hxt.recommendation.global;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.HashMap;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class GlobalObject {
    public static HashMap<Integer, AbstractSequenceClassifier<CoreLabel>> modelMap;

}
