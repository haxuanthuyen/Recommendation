package com.hust.soict.hxt.recommendation.dao;

import com.hust.soict.hxt.recommendation.bo.ItemData;
import com.hust.soict.hxt.recommendation.global.Resource;
import com.hust.soict.hxt.recommendation.services.GlobalResourceInit;
import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

/**
 * Created by thuyenhx on 5/2/16.
 */
public class CategoryDaoTest extends TestCase {

    public void testGetAllItemByCat() throws Exception {
        CategoryDao  categoryDao = new CategoryDao();
        Map<Integer, List<ItemData>> map = categoryDao.getAllItemByCat();
        System.out.println(map.size());
    }

    public void testGetItemSimi() throws Exception {
        GlobalResourceInit.loadDataCache();
        CategoryDao  categoryDao = new CategoryDao();
        List<String> lst = categoryDao.getItemSimi(132021);
        int serial = 0;
        for (String id : lst) {
            ItemData itemData = (ItemData) Resource.itemCache.get(Integer.valueOf(id)).clone();
            itemData.setSimilarity(0.5 - 0.01*serial);
        }
    }
}