package com.hust.soict.hxt.recommendation.global;

import com.hust.soict.hxt.recommendation.bo.ItemRate;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class GlobalObject {
    public static HashMap<Integer, AbstractSequenceClassifier<CoreLabel>> modelMap;
    public static HashMap<Integer, Integer> catCache;
    public static HashMap<String, List<ItemRate>> itemCache;
}
