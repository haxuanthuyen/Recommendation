package com.hust.soict.hxt.recommendation.bo;

import java.util.List;

/**
 * Created by thuyenhx on 4/14/16.
 */
public class ItemCluster implements Comparable<ItemCluster>, Cloneable{

    private int catId;
    private double totalScore;
    private List<ItemHistory> historyList;

    public ItemCluster() {}

    public ItemCluster(int catId, double totalScore, List<ItemHistory> historyList) {
        this.catId = catId;
        this.totalScore = totalScore;
        this.historyList = historyList;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore += totalScore;
    }

    public List<ItemHistory> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<ItemHistory> historyList) {
        this.historyList = historyList;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(ItemCluster o) {
        if (this.getTotalScore() < o.getTotalScore()) return 1;
        else if (this.getTotalScore() == o.getTotalScore()) return 0;
        else return -1;
    }
}
