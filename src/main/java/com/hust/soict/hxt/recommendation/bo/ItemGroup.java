package com.hust.soict.hxt.recommendation.bo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyenhx on 3/22/16.
 */
public class ItemGroup {
    private List<Integer> groupItem;
    private HashMap<String,String> label;
    private double score;

    public ItemGroup() {}

    public List<Integer> getGroupItem() {
        return groupItem;
    }

    public void setGroupItem(List<Integer> groupItem) {
        this.groupItem = groupItem;
    }

    public HashMap<String, String> getLabel() {
        return label;
    }

    public void setLabel(HashMap<String, String> label) {
        this.label = label;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
