package com.hust.soict.hxt.recommendation.services;

import junit.framework.TestCase;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class HistorySuggestionTest extends TestCase {

    public void testBuildListSuggest() throws Exception {
        GlobalResourceInit.initModelMap();
        GlobalResourceInit.loadDataCache();

        String guid = "1435715810206524383";
        HistorySuggestion historySuggestion = new HistorySuggestion();
        historySuggestion.buildListSuggest(guid);
    }
}