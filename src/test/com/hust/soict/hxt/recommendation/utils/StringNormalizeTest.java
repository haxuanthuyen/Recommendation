package com.hust.soict.hxt.recommendation.utils;

import com.hust.soict.hxt.recommendation.bo.Item;
import com.hust.soict.hxt.recommendation.dao.ItemDAO;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyenhx on 3/15/16.
 */
public class StringNormalizeTest extends TestCase {

    public void testReceiveItemId() throws Exception {
        String url = "https://plaza.muachung.vn/phu-kien-cong-nghe/d-4229/hop-dung-o-cung-hdd-box-2-5-orico-2599us3-hang-chinh-hang.html";
        System.out.println(StringNormalize.receiveItemId(url));

        List<Item> lst = new ArrayList<>();
        ItemDAO itemDAO = new ItemDAO();
        List<Item> list = itemDAO.getAllItems();
        for (Item it : list) {
            if (it.getItemId() == 0) {
                String itemId = StringNormalize.receiveItemId(it.getUrl());
                if (itemId != null) {
                    Item i = new Item(it.getUrl(), Integer.parseInt(itemId));
                    lst.add(i);
//                    System.out.println(i.getUrl() + " => " + i.getItemId());
                }
            }
        }
        itemDAO.updateHistoryLog(lst);

        itemDAO.dispose();

    }
}