package com.hust.soict.hxt.recommendation.dao;

import com.hust.soict.hxt.recommendation.algorithm.similarity.WeightFactory;
import com.hust.soict.hxt.recommendation.bo.Item;
import com.hust.soict.hxt.recommendation.bo.ItemHistory;
import com.hust.soict.hxt.recommendation.dao.base.ConnectionBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyenhx on 3/15/16.
 */
public class HistoryDao extends ConnectionBase {

    protected static Logger logger = LoggerFactory.getLogger("warringLog");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public HistoryDao() throws SQLException {
    }

    public void updateHistoryLog(List<Item> list) {
        String sql = "UPDATE content_history " +
                "SET item_id=? " +
                "WHERE url=? ";
        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement(sql);
            for (Item it : list) {
                ps.setInt(1, it.getItemId());
                ps.setString(2, it.getUrl());
                ps.addBatch();
            }
            ps.executeBatch();
        }catch (Exception e) {
            logger.warn("error update history log ", e);
        }
    }

    public List<Item> getAllItems() {
        List<Item> result = new ArrayList<>();
        String sql = "SELECT guid,item_id,url,content,timeonsite,timeonread,dt " +
                "FROM content_history " +
                "WHERE url LIKE '%muachung.vn%' " +
                "AND  url NOT LIKE '%plaza.muachung.vn%'";
        PreparedStatement ps = null;

        try{
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String guid = rs.getString("guid");
                int itemId = rs.getInt("item_id");
                String url = rs.getString("url");
                String title = rs.getNString("content");
                long timeOnSite = rs.getLong("timeonsite");
                long timeOnRead = rs.getLong("timeonread");
                String date = rs.getString("dt");

                Item it = new Item(guid, itemId, url, title, timeOnSite, timeOnRead, date);
                result.add(it);
            }

            return result;
        }catch (Exception e) {
            logger.warn("error get all data ", e);
        }

        return null;
    }

    public List<ItemHistory> loadDataByGuid(String dt, String guid) {
        List<ItemHistory> res = new ArrayList<>();
        String sql= "SELECT item_id,content,timeonsite,timeonread,dt " +
                "FROM content_history " +
                "WHERE dt >= ? " +
                "AND guid=? " +
                "AND item_id IS NOT NULL " +
                "AND url LIKE '%muachung.vn%' " +
                "AND  url NOT LIKE '%plaza.muachung.vn%'";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, dt);
            ps.setString(2, guid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String title = rs.getNString("content");
                long timeOnSite = rs.getLong("timeonsite");
                long timeOnRead = rs.getLong("timeonread");
                String date = rs.getString("dt");
                ItemHistory it = new ItemHistory(guid, itemId, title, timeOnSite, timeOnRead, date);
                res.add(it);
            }
        }catch (Exception e) {
            logger.error("error load data by guid ", e);
        }

        return res;
    }

    public HashMap<String, List<ItemHistory>> loadDataByDate(String dt) {
        HashMap<String, List<ItemHistory>> historyLst = new HashMap<>();
        String sql= "SELECT guid,item_id,content,timeonsite,timeonread,dt " +
                "FROM content_history " +
                "WHERE dt >= ? " +
                "AND item_id IS NOT NULL " +
                "AND url LIKE '%muachung.vn%' " +
                "AND  url NOT LIKE '%plaza.muachung.vn%'";
        PreparedStatement ps = null;

        try{
            long timeRequest = sdf.parse(dt).getTime();

            ps = conn.prepareStatement(sql);
            ps.setString(1, dt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String guid = rs.getString("guid");
                int itemId = rs.getInt("item_id");
                String title = rs.getNString("content");
                long timeOnSite = rs.getLong("timeonsite");
                long timeOnRead = rs.getLong("timeonread");
                String date = rs.getString("dt");
                long timeLog = sdf.parse(date).getTime();
                double scoreByDate = WeightFactory.getScoreByHistory(timeRequest, timeLog, timeOnRead, timeOnSite);

                ItemHistory it = new ItemHistory(guid, itemId, title, timeOnSite, timeOnRead, scoreByDate);
                List<ItemHistory> lst = historyLst.get(guid);
                if (lst == null) {
                    lst = new ArrayList<>();
                }
                lst.add(it);
                historyLst.put(guid,lst);
            }
        }catch (Exception e) {
            logger.warn("error load history list ", e);
        }

        return historyLst;
    }

    public HashMap<String, Long> getTotalTime(String guid, String dt) {
        HashMap<String, Long> totalTimeByDate = new HashMap<>();
        String sql = "SELECT guid,dt,SUM(timeonread) as total " +
                "FROM content_history " +
                "WHERE guid =? AND dt >= ? " +
                "AND url LIKE '%muachung.vn%' " +
                "AND url NOT LIKE '%plaza.muachung.vn%' " +
                "GROUP BY guid,dt ;";
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, guid);
            ps.setString(2, dt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String date = rs.getString("dt");
                long totalTime = rs.getLong("total");
                totalTimeByDate.put(date, totalTime);
            }
        }catch (Exception e) {
            logger.warn("error get total time for guid ", e);
        }

        return totalTimeByDate;
    }
}
