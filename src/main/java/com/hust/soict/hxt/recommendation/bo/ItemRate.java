package com.hust.soict.hxt.recommendation.bo;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class ItemRate {

    private String guid;
    private int itemId;
    private String title;
    private long timeOnSite;
    private long timeOnRead;
    private long timeInMilis;

    public ItemRate(String guid, int itemId, String title, long timeOnSite, long timeOnRead, long timeInMilis) {
        this.guid = guid;
        this.itemId = itemId;
        this.title = title;
        this.timeOnSite = timeOnSite;
        this.timeOnRead = timeOnRead;
        this.timeInMilis = timeInMilis;
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

    public long getTimeInMilis() {
        return timeInMilis;
    }

    public void setTimeInMilis(long timeInMilis) {
        this.timeInMilis = timeInMilis;
    }
}
