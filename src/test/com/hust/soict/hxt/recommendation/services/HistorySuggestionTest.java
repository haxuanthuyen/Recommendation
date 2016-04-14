package com.hust.soict.hxt.recommendation.services;

import com.hust.soict.hxt.recommendation.algorithm.ner.NERProcess;
import com.hust.soict.hxt.recommendation.bo.ItemHistory;
import com.hust.soict.hxt.recommendation.global.GlobalObject;
import junit.framework.TestCase;

import java.util.*;
import java.util.stream.Collectors;

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

        List<ItemHistory> lst = new ArrayList<>();

        String title = "2 Bút cảm ứng dạng xoay";
        int itemId = 113699;
        int catId = GlobalObject.catCache.get(itemId);
        HashMap<String,String> label = nerProcess.tokenizeWithLabel(title, catId);
        ItemHistory it1 = new ItemHistory(title, itemId, catId, label);
        lst.add(it1);

        String title2 = "USB 16GB - Bảo hành 5 năm";
        int itemId2 = 123449;
        int catId2 = GlobalObject.catCache.get(itemId);
        HashMap<String,String> label2 = nerProcess.tokenizeWithLabel(title2, catId2);
        ItemHistory it2 = new ItemHistory(title2, itemId2, catId2, label2);
        lst.add(it2);

        String title3 = "USB Texet 16GB - Bảo hành 12 tháng";
        int itemId3 = 115035;
        int catId3 = GlobalObject.catCache.get(itemId);
        HashMap<String,String> label3 = nerProcess.tokenizeWithLabel(title3, catId3);
        ItemHistory it3 = new ItemHistory(title3, itemId3, catId3, label3);
        lst.add(it3);

        String title4 = "2 Bút cảm ứng dạng xoay";
        int itemId4 = 113699;
        int catId4 = GlobalObject.catCache.get(itemId);
        HashMap<String,String> label4 = nerProcess.tokenizeWithLabel(title4, catId4);
        ItemHistory it4 = new ItemHistory(title4, itemId4, catId4, label4);
        lst.add(it4);

        String title5 = "Bút USB 8GB đa năng 3 in 1";
        int itemId5 = 116033;
        int catId5 = GlobalObject.catCache.get(itemId);
        HashMap<String,String> label5 = nerProcess.tokenizeWithLabel(title5, catId5);
        ItemHistory it5 = new ItemHistory(title5, itemId5, catId5, label5);
        lst.add(it5);

        HistorySuggestion historySuggestion = new HistorySuggestion();
        List<ItemHistory> list = historySuggestion.groupBySimilarityItem(lst);
        System.out.println(list.size());
    }

    public void testPutLabelToken() throws Exception{
        HashMap<String, String> listMap = new HashMap<>();
        listMap.put("PN", "abc,cde");
        listMap.put("PROP", "uio,wer");

        HashMap<String, String> listMap2 = new HashMap<>();
        listMap2.put("PN", "efg");
        listMap2.put("PROP", "uio,iop");

        HashMap<String, String> result = new HashMap<>();
        Set a = new HashSet<>();
        a.addAll(listMap.keySet());
        a.addAll(listMap2.keySet());
        a.forEach(x -> {
            String value1 = listMap.get(x);
            String value2 = listMap2.get(x);
            List<String> tmp = new ArrayList<String>();

            if (value1 != null) {
                tmp.addAll(Arrays.asList(value1.split(",")));
            }
            if (value2 != null) {
                tmp.addAll(Arrays.asList(value2.split(",")));
            }

            String value = tmp.stream().distinct().collect(Collectors.joining(","));
            result.put((String) x, value);
        });

        System.out.println(result);
    }
}