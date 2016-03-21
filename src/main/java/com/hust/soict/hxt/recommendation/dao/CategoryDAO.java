package com.hust.soict.hxt.recommendation.dao;

import com.hust.soict.hxt.recommendation.dao.base.ConnectionBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class CategoryDAO extends ConnectionBase {

    protected static Logger logger = LoggerFactory.getLogger("warringLog");

    public CategoryDAO() throws SQLException {
    }

    public HashMap<Integer,Integer> loadAllItemByCat() {
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
}
