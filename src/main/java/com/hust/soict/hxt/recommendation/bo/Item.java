package com.hust.soict.hxt.recommendation.bo;

/**
 * Created by thuyenhx on 3/15/16.
 */
public class Item {

    private long id;
    private String guid;
    private int itemId;
    private String url;
    private String title;
    private long timeOnStite;
    private long timeOnRead;
    private String date;

    public Item() {}

    public Item(String url, int itemId) {
        this.url = url;
        this.itemId = itemId;
    }

    public Item(String guid, int itemId, String url, String title, long timeOnStite, long timeOnRead, String date) {
        this.guid = guid;
        this.itemId = itemId;
        this.url = url;
        this.title = title;
        this.timeOnStite = timeOnStite;
        this.timeOnRead = timeOnRead;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getTimeOnStite() {
        return timeOnStite;
    }

    public void setTimeOnStite(long timeOnStite) {
        this.timeOnStite = timeOnStite;
    }

    public long getTimeOnRead() {
        return timeOnRead;
    }

    public void setTimeOnRead(long timeOnRead) {
        this.timeOnRead = timeOnRead;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
