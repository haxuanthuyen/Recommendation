package com.hust.soict.hxt.recommendation.services;

import com.hust.soict.hxt.recommendation.algorithm.ner.NERProcess;
import com.hust.soict.hxt.recommendation.bo.ItemRate;
import com.hust.soict.hxt.recommendation.global.GlobalObject;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public void testGroupBySimilarityItem() throws Exception {
        GlobalResourceInit.initModelMap();
        GlobalResourceInit.loadDataCache();
        NERProcess nerProcess = NERProcess.getInstance();

        List<ItemRate> lst = new ArrayList<>();

        String title = "2 Bút cảm ứng dạng xoay";
        int itemId = 113699;
        int catId = GlobalObject.catCache.get(itemId);
        HashMap<String,String> label = nerProcess.tokenizeWithLabel(title, catId);
        ItemRate it1 = new ItemRate(title, itemId, catId, label);
        lst.add(it1);

        String title2 = "USB 16GB - Bảo hành 5 năm";
        int itemId2 = 123449;
        int catId2 = GlobalObject.catCache.get(itemId);
        HashMap<String,String> label2 = nerProcess.tokenizeWithLabel(title2, catId2);
        ItemRate it2 = new ItemRate(title2, itemId2, catId2, label2);
        lst.add(it2);

        String title3 = "USB Texet 16GB - Bảo hành 12 tháng";
        int itemId3 = 115035;
        int catId3 = GlobalObject.catCache.get(itemId);
        HashMap<String,String> label3 = nerProcess.tokenizeWithLabel(title3, catId3);
        ItemRate it3 = new ItemRate(title3, itemId3, catId3, label3);
        lst.add(it3);

        String title4 = "2 Bút cảm ứng dạng xoay";
        int itemId4 = 113699;
        int catId4 = GlobalObject.catCache.get(itemId);
        HashMap<String,String> label4 = nerProcess.tokenizeWithLabel(title4, catId4);
        ItemRate it4 = new ItemRate(title4, itemId4, catId4, label4);
        lst.add(it4);

        String title5 = "Bút USB 8GB đa năng 3 in 1";
        int itemId5 = 116033;
        int catId5 = GlobalObject.catCache.get(itemId);
        HashMap<String,String> label5 = nerProcess.tokenizeWithLabel(title5, catId5);
        ItemRate it5 = new ItemRate(title5, itemId5, catId5, label5);
        lst.add(it5);

        HistorySuggestion historySuggestion = new HistorySuggestion();
        historySuggestion.groupBySimilarityItem(lst);
    }
}