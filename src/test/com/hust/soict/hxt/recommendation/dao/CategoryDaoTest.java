package com.hust.soict.hxt.recommendation.dao;

import com.hust.soict.hxt.recommendation.bo.ItemData;
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
}