package com.hust.soict.hxt.recommendation.dao;

import com.hust.soict.hxt.recommendation.bo.ItemData;
import com.hust.soict.hxt.recommendation.dao.base.ConnectionBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class CategoryDao extends ConnectionBase {

    protected static Logger logger = LoggerFactory.getLogger("warringLog");

    public CategoryDao() throws SQLException {
    }

    public HashMap<Integer,Integer> mapAllItemByCat() {
        HashMap<Integer, Integer> catCache = new HashMap<>();
        String sql = "SELECT id,cat_id " +
                "FROM item_info_date ";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int itemId = rs.getInt("id");
                int catId = rs.getInt("cat_id");
                catCache.put(itemId,catId);
            }

        }catch (Exception e) {
            logger.warn("error load all item by cat ");
        }finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return catCache;
    }

    public HashMap<Integer, List<ItemData>> getAllItemByCat() {
        HashMap<Integer, List<ItemData>> res = new HashMap<>();
        String sql = "SELECT id,cat_id,web_link,title,medium_image,original_price,price " +
                "FROM item_date ;";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int itemId = rs.getInt("id");
                int catId = rs.getInt("cat_id");
                String url = rs.getString("web_link");
                String title = rs.getNString("title");
                String imgUrl = rs.getString("medium_image");
                String originalPrice = rs.getString("original_price");
                String sellPrice = rs.getString("price");

                ItemData item = new ItemData(itemId,catId,url,title,imgUrl,originalPrice,sellPrice);
                List<ItemData> lst = res.get(catId);
                if (lst == null) {
                    lst = new ArrayList<>();
                    res.put(catId, lst);
                }
                lst.add(item);
            }
        }catch (Exception e) {
            logger.error("error get item detail by cat ", e);
        }
        return res;
    }

    public List<ItemData> getItemByCat(int id) {
        List<ItemData> res = new ArrayList<>();
        String sql = "SELECT id,cat_id,url,title,medium_image,format_original_price,price " +
                "FROM item_info_date " +
                "WHERE dt >= '2016-01-01' " +
                "AND cat_id=? ;";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int itemId = rs.getInt("id");
                int catId = rs.getInt("cat_id");
                String url = rs.getString("url");
                String title = rs.getNString("title");
                String imgUrl = rs.getString("medium_image");
                String originalPrice = rs.getString("format_original_price");
                String sellPrice = rs.getString("price");

                ItemData item = new ItemData(itemId,catId,url,title,imgUrl,originalPrice,sellPrice);
                res.add(item);
            }
        }catch (Exception e) {
            logger.error("error get item detail by cat ", e);
        }
        return res;
    }
}
