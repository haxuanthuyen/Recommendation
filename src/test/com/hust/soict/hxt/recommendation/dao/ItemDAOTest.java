package com.hust.soict.hxt.recommendation.dao;

import com.hust.soict.hxt.recommendation.bo.ItemRate;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class ItemDAOTest extends TestCase {

    public void testLoadDataByDate() throws Exception {
        ItemDAO itemDAO = new ItemDAO();
        HashMap<String, List<ItemRate>> lst = itemDAO.loadDataByDate("2016-03-09");
        System.out.println(lst.size());
    }
}