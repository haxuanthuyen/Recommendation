package com.hust.soict.hxt.recommendation.dao;

import com.hust.soict.hxt.recommendation.bo.ItemHistory;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class ItemDAOTest extends TestCase {

    public void testLoadDataByDate() throws Exception {
        ItemDAO itemDAO = new ItemDAO();
        HashMap<String, List<ItemHistory>> lst = itemDAO.loadDataByDate("2016-03-09");
        System.out.println(lst.size());
    }

    public void testLoadDataByGuid() throws Exception {
        ItemDAO itemDAO = new ItemDAO();
        List<ItemHistory> res = itemDAO.loadDataByGuid("2016-03-07", "1445411930246467641");
        itemDAO.dispose();
        System.out.println(res.size());
    }
}