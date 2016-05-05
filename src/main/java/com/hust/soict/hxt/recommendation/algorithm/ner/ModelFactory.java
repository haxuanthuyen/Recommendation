package com.hust.soict.hxt.recommendation.algorithm.ner;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class ModelFactory {
    public static String getModel(int catId) {
        if(catId == 0) {
            return "data/dulich/model.dulich.gz";
        }else if(catId == 1) {
            return "data/amthucnhahang/model.amthucnhahang.gz";
        }else if(catId == 2) {
            return "data/thoitrangnu/model.thoitrangnu.gz";
        } else if(catId == 3) {
            return "data/spavalamdep/model.spavalamdep.gz";
        } else if(catId == 4) {
            return "data/mevabe/model.mevabe.gz";
        } else if(catId == 5) {
            return "data/giadungnoithat/model.giadungnoithat.gz";
        } else if(catId == 6) {
            return "data/thucpham/model.thucpham.gz";
        } else if(catId == 7) {
            return "data/phukienmypham/model.phukienmypham.gz";
        } else if(catId == 8) {
            return "data/thoitrangnam/model.thoitrangnam.gz";
        } else if(catId == 9) {
            return "data/dientucongnghe/model.dientucongnghe.gz";
        } else if(catId == 10) {
            return "data/giadungnoithat/model.giadungnoithat.gz";
        } else if(catId == 11) {
            return null;
        } else if(catId == 12) {
            return "data/giadungnoithat/model.giadungnoithat.gz";
        } else {
            return null;
        }
    }
}
