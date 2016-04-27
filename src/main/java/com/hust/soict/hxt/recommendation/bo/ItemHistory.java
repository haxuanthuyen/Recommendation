package com.hust.soict.hxt.recommendation.bo;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.HashMap;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class ItemHistory implements Cloneable, Comparable<ItemHistory>{


    private double score;
    private HashMap<String,String> label = new HashMap<>();

    @JsonIgnore
    private String title;
    @JsonIgnore
    private int catId;
    @JsonIgnore
    private String guid;
    @JsonIgnore
    private int itemId;
    @JsonIgnore
    private long timeOnSite;
    @JsonIgnore
    private long timeOnRead;
    @JsonIgnore
    private String date;


    public ItemHistory(String guid, int itemId, String title, long timeOnSite, long timeOnRead, double score) {
        this.guid = guid;
        this.itemId = itemId;
        this.title = title;
        this.timeOnSite = timeOnSite;
        this.timeOnRead = timeOnRead;
        this.score = score;
    }

    public ItemHistory(String guid, int itemId, String title, long timeOnSite, long timeOnRead, String date) {
        this.guid = guid;
        this.itemId = itemId;
        this.title = title;
        this.timeOnSite = timeOnSite;
        this.timeOnRead = timeOnRead;
        this.date = date;
    }

    public ItemHistory(String title, int itemId, int catId, HashMap<String, String> label) {
        this.title = title;
        this.itemId = itemId;
        this.catId = catId;
        this.label = label;
    }

    public String getProductName() {
        if (label == null) return null;
        String productName = null;
        if ((productName = label.get("PN")) != null) {
            return productName;
        } else if ((productName = label.get("SEV")) != null) {
            return productName;
        } else if ((productName = label.get("TYPE")) != null && catId == 1) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(ItemHistory o) {
        if (this.getScore() < o.getScore()) return 1;
        else if (this.getScore() == o.getScore()) return 0;
        else return -1;
    }
}
