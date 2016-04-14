package com.hust.soict.hxt.recommendation.bo;

import java.util.HashMap;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class ItemHistory implements Cloneable{

    private String guid;
    private int itemId;
    private int catId;
    private String title;
    private long timeOnSite;
    private long timeOnRead;
    private double score;
    private HashMap<String,String> label = new HashMap<>();

    public ItemHistory(String guid, int itemId, String title, long timeOnSite, long timeOnRead, double score) {
        this.guid = guid;
        this.itemId = itemId;
        this.title = title;
        this.timeOnSite = timeOnSite;
        this.timeOnRead = timeOnRead;
        this.score = score;
    }

    public ItemHistory(String title, int itemId, int catId, HashMap<String, String> label) {
        this.title = title;
        this.itemId = itemId;
        this.catId = catId;
        this.label = label;
    }

    public String getProductName() {
        String productName = null;
       if ((productName = label.get("PN")) != null) {
           return productName;
       }else if ((productName = label.get("SEV")) != null) {
           return productName;
       }else if ((productName = label.get("TYPE")) != null && catId == 1) {
           return productName;
       }
        return null;
    }

    public HashMap<String, String> getLabel() {
        return label;
    }

    public void setLabel(HashMap<String, String> label) {
        this.label = label;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimeOnSite() {
        return timeOnSite;
    }

    public void setTimeOnSite(long timeOnSite) {
        this.timeOnSite = timeOnSite;
    }

    public long getTimeOnRead() {
        return timeOnRead;
    }

    public void setTimeOnRead(long timeOnRead) {
        this.timeOnRead = timeOnRead;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
