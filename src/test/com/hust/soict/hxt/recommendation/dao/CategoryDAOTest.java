package com.hust.soict.hxt.recommendation.dao;

import junit.framework.TestCase;

import java.util.HashMap;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class CategoryDAOTest extends TestCase {

    public void testLoadAllItemByCat() throws Exception {
        CategoryDAO categoryDAO = new CategoryDAO();
        HashMap<Integer, Integer> catCache = categoryDAO.loadAllItemByCat();
        System.out.println(catCache.size());
    }
}