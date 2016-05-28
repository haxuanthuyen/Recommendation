package com.hust.soict.hxt.recommendation.bo;

/**
 * Created by thuyenhx on 5/28/16.
 */
public class ItemSimilarity {
    private int itemId;
    private int catId;
    private String itemSimilarity;
    private String dt;

    public ItemSimilarity(int itemId, int catId, String itemSimilarity, String dt) {
        this.itemId = itemId;
        this.catId = catId;
        this.itemSimilarity = itemSimilarity;
        this.dt = dt;
    }

    public ItemSimilarity(int itemId, int catId, String dt) {
        this.itemId = itemId;
        this.catId = catId;
        this.dt = dt;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getItemSimilarity() {
        return itemSimilarity;
    }

    public void setItemSimilarity(String itemSimilarity) {
        this.itemSimilarity = itemSimilarity;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }
}
