package com.hust.soict.hxt.recommendation.bo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by thuyenhx on 3/22/16.
 */
public class ItemGroup {
    private List<ItemHistory> duplicateList;
    private ItemHistory itemReplace;
    private HashMap<String,String> totalLabel = new HashMap<>();
    private double score;
    private double totalScore;

    public ItemGroup() {
        duplicateList = new ArrayList<>();
    }

    public List<ItemHistory> getDuplicateList() {
        return duplicateList;
    }

    public void setDuplicateList(List<ItemHistory> duplicateList) {
        this.duplicateList = duplicateList;
    }

    public ItemHistory getItemReplace() {
        return itemReplace;
    }

    public void setItemReplace(ItemHistory itemReplace) {
        this.itemReplace = itemReplace;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double score) {
        this.totalScore += score;
    }

    public HashMap<String, String> getTotalLabel() {
        return totalLabel;
    }

    public void setTotalLabel(HashMap<String, String> label) {
        HashMap<String, String> result = new HashMap<>();
        Set a = new HashSet<>();
        a.addAll(totalLabel.keySet());
        a.addAll(label.keySet());
        a.forEach(x -> {
            String valueTotal = totalLabel.get(x);
            String valueLabel = label.get(x);
            List<String> tmp = new ArrayList<String>();

            if (valueTotal != null) {
                tmp.addAll(Arrays.asList(valueTotal.split(",")));
            }
            if (valueLabel != null) {
                tmp.addAll(Arrays.asList(valueLabel.split(",")));
            }

            String value = tmp.stream().distinct().collect(Collectors.joining(","));
            result.put((String) x, value);
        });
        this.totalLabel = result;
    }
}
