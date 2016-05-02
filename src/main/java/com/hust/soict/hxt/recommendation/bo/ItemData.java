package com.hust.soict.hxt.recommendation.bo;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.HashMap;

/**
 * Created by thuyenhx on 5/2/16.
 */
public class ItemData implements Cloneable,Comparable<ItemData>{
    private int itemId;
    private int catId;
    private String url;
    private String title;
    private String imgUrl;
    private String price;
    private String sellPrice;

    @JsonIgnore
    private double score;
    @JsonIgnore
    private double similarity;
    @JsonIgnore
    private HashMap<String,String> label = new HashMap<>();

    public ItemData(int itemId, int catId, String url, String title, String imgUrl, String price, String sellPrice) {
        this.itemId = itemId;
        this.catId = catId;
        this.url = url;
        this.title = title;
        this.imgUrl = imgUrl;
        this.price = price;
        this.sellPrice = sellPrice;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public HashMap<String, String> getLabel() {
        return label;
    }

    public void setLabel(HashMap<String, String> label) {
        this.label = label;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(ItemData o) {
        if (this.getScore() < o.getScore()) return 1;
        if (this.getScore() == o.getScore()) {
            if (this.getSimilarity() < o.getSimilarity()) return 1;
            if (this.getSimilarity() == o.getSimilarity()) return 0;
            return -1;
        }
        return -1;
    }
}
