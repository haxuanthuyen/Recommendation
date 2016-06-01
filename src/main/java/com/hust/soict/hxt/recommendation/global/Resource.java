package com.hust.soict.hxt.recommendation.global;

import com.hust.soict.hxt.recommendation.bo.ItemCluster;
import com.hust.soict.hxt.recommendation.bo.ItemData;
import com.hust.soict.hxt.recommendation.bo.ItemHistory;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class Resource {
    public static HashMap<Integer, AbstractSequenceClassifier<CoreLabel>> modelMap;
    public static HashMap<Integer, Integer> catCache;
    public static HashMap<Integer, List<ItemData>> itemDetailCache;
    public static HashMap<Integer, ItemData> itemCache = new HashMap<>();
    public static HashMap<String, List<ItemCluster>> clusterCache = new HashMap<>();
}
