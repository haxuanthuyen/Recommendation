package com.hust.soict.hxt.recommendation.bo;

/**
 * Created by thuyenhx on 5/28/16.
 */
public class ItemCount implements Comparable<ItemCount>{
    private int itemId;
    private int count;

    public ItemCount(int itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }

    public void add(int num) { this.count += num;}

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public int compareTo(ItemCount o) {
        if (this.getCount() < o.getCount()) return 1;
        if (this.getCount() == o.getCount()) return 0;
        return -1;
    }
}
