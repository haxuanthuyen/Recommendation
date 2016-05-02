package com.hust.soict.hxt.recommendation.dao;

import junit.framework.TestCase;

import java.util.HashMap;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class CategoryDAOTest extends TestCase {

    public void testLoadAllItemByCat() throws Exception {
        CategoryDao categoryDAO = new CategoryDao();
        HashMap<Integer, Integer> catCache = categoryDAO.mapAllItemByCat();
        System.out.println(catCache.size());
    }
}