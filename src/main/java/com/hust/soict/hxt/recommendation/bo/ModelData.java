package com.hust.soict.hxt.recommendation.bo;

/**
 * Created by thuyenhx on 5/22/16.
 */
public class ModelData {
    private String label;
    private String precision;
    private String recall;
    private String f1;
    private String tp;
    private String fp;
    private String fn;

    public ModelData(String label, String precision, String recall, String f1, String tp, String fp, String fn) {
        this.label = label;
        this.precision = precision;
        this.recall = recall;
        this.f1 = f1;
        this.tp = tp;
        this.fp = fp;
        this.fn = fn;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getRecall() {
        return recall;
    }

    public void setRecall(String recall) {
        this.recall = recall;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getFp() {
        return fp;
    }

    public void setFp(String fp) {
        this.fp = fp;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }
}
