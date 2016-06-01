package com.hust.soict.hxt.recommendation.services;

import junit.framework.TestCase;

/**
 * Created by thuyenhx on 5/28/16.
 */
public class ItemCFSuggestionTest extends TestCase {

    public void testBuildListSimilarity() throws Exception {
        //init load model and data
        GlobalResourceInit.initModelMap();
        GlobalResourceInit.loadCategoryCache();
//        GlobalResourceInit.loadDataCache();

        ItemCFSuggestion itemCFSuggestion = new ItemCFSuggestion();
        itemCFSuggestion.buildListSimilarity(1);
    }
}