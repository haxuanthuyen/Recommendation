package com.hust.soict.hxt.recommendation.services;

import com.hust.soict.hxt.recommendation.bo.ItemRate;
import com.hust.soict.hxt.recommendation.global.GlobalObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class HistorySuggestion {

    private static Logger logger = LoggerFactory.getLogger("suggestLog");

    public HistorySuggestion() {}

    public void buildListSuggest(String guid) {
        List<ItemRate> list = GlobalObject.itemCache.get(guid);
        System.out.println(list.size());
    }
}
