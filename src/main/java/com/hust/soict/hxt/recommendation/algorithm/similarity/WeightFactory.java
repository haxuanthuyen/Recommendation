package com.hust.soict.hxt.recommendation.algorithm.similarity;

import com.hust.soict.hxt.recommendation.global.SystemInfo;
import org.apache.commons.configuration.Configuration;

import java.util.HashMap;

/**
 * Created by thuyenhx on 3/30/16.
 */
public class WeightFactory {
    private static final Configuration conf = SystemInfo.getConfiguration();

    public static HashMap<String, Double> getWeightNer(int catId) {
        HashMap<String, Double> res = new HashMap<>();
        if (catId == 0) {//du lich
            res.put("TYPE", conf.getDouble("weight.dulich.type", 0.2));
            res.put("NAME", conf.getDouble("weight.dulich.name", 0.1));
            res.put("LOC", conf.getDouble("weight.dulich.loc", 0.3));
            res.put("TIME", conf.getDouble("weight.dulich.time", 0.2));
            res.put("HPROP", conf.getDouble("weight.dulich.hprop", 0.1));
            res.put("TPROP", conf.getDouble("weight.dulich.tprop", 0.1));
        }else if (catId == 1) {// am thuc va nha hang
            res.put("PN", conf.getDouble("weight.amthuc.pn", 0.3));
            res.put("TYPE", conf.getDouble("weight.amthuc.type", 0.2));
            res.put("LOC", conf.getDouble("weight.amthuc.loc", 0.2));
            res.put("PROP", conf.getDouble("weight.amthuc.prop", 0.2));
            res.put("OB", conf.getDouble("weight.amthuc.ob", 0.1));
        } else if (catId == 2) {//thoi trang nu
            res.put("PN", conf.getDouble("weight.thoitrangnu.pn", 0.2));
            res.put("STYLE", conf.getDouble("weight.thoitrangnu.styte", 0.3));
            res.put("PROP", conf.getDouble("weight.thoitrangnu.prop", 0.2));
            res.put("BR", conf.getDouble("weight.thoitrangnu.br", 0.1));
            res.put("OB", conf.getDouble("weight.thoitrangnu.ob", 0.1));
            res.put("FUNC", conf.getDouble("weight.thoitrangnu.func", 0.1));
        } else if (catId == 3) {// spa va lam dep
            res.put("SEV", conf.getDouble("weight.spa.sev", 0.6));
            res.put("BR", conf.getDouble("weight.spa.br", 0.2));
            res.put("PROP", conf.getDouble("weight.spa.prop", 0.2));
        } else if (catId == 4) {// me va be
            res.put("PN", conf.getDouble("weight.mevabe.pn", 0.25));
            res.put("STYLE", conf.getDouble("weight.mevabe.style", 0.4));
            res.put("PROP", conf.getDouble("weight.mevabe.prop", 0.2));
            res.put("OB", conf.getDouble("weight.mevabe.ob", 0.15));
        } else if (catId == 5) {// do dung gia dinh
            res.put("PN", conf.getDouble("weight.dodunggiadinh.pn", 0.4));
            res.put("FUNC", conf.getDouble("weight.dodunggiadinh.func", 0.3));
            res.put("PROP", conf.getDouble("weight.dodunggiadinh.prop", 0.2));
            res.put("OB", conf.getDouble("weight.dodunggiadinh.ob", 0.1));
        } else if (catId == 6) {// thuc pham
            res.put("PN", conf.getDouble("weight.thucpham.pn", 0.3));
            res.put("FUNC", conf.getDouble("weight.thucpham.func", 0.2));
            res.put("PROP", conf.getDouble("weight.thucpham.prop", 0.3));
            res.put("OB", conf.getDouble("weight.thucpham.ob", 0.1));
            res.put("BR", conf.getDouble("weight.thucpham.br", 0.1));
        } else if (catId == 7) {// phu kien va my pham
            res.put("PN", conf.getDouble("weight.phukienmypham.pn", 0.25));
            res.put("PROP", conf.getDouble("weight.phukienmypham.prop", 0.15));
            res.put("FUNC", conf.getDouble("weight.phukienmypham.func", 0.25));
            res.put("BR", conf.getDouble("weight.phukienmypham.br", 0.15));
            res.put("STYLE", conf.getDouble("weight.phukienmypham.style", 0.2));
        } else if (catId == 8) {// thoi trang nam
            res.put("PN", conf.getDouble("weight.thoitrangnam.pn", 0.3));
            res.put("STYLE", conf.getDouble("weight.thoitrangnam.style", 0.2));
            res.put("PROP", conf.getDouble("weight.thoitrangnam.prop", 0.2));
            res.put("FUNC", conf.getDouble("weight.thoitrangnam.func", 0.2));
            res.put("BR", conf.getDouble("weight.thoitrangnam.br", 0.1));
        } else if (catId == 9) {// dien tu cong nghe
            res.put("PN", conf.getDouble("weight.dientucongnghe.pn", 0.25));
            res.put("PROP", conf.getDouble("weight.dientucongnghe.prop", 0.2));
            res.put("FUNC", conf.getDouble("weight.dientucongnghe.func", 0.25));
            res.put("BR", conf.getDouble("weight.dientucongnghe.br", 0.15));
            res.put("OB", conf.getDouble("weight.dientucongnghe.ob", 0.15));
        } else if (catId == 10) {// nha bep
            res.put("PN", conf.getDouble("weight.nhabep.pn", 0.4));
            res.put("FUNC", conf.getDouble("weight.nhabep.func", 0.4));
            res.put("PROP", conf.getDouble("weight.nhabep.prop", 0.1));
            res.put("BR", conf.getDouble("weight.nhabep.br", 0.1));
        } else if (catId == 12) {// gia dung noi that
            res.put("PN", conf.getDouble("weight.giadungnoithat.pn", 0.3));
            res.put("STYLE", conf.getDouble("weight.giadungnoithat.style", 0.1));
            res.put("PN-OB", conf.getDouble("weight.giadungnoithat.pn-ob", 0.1));
            res.put("FUNC", conf.getDouble("weight.giadungnoithat.func", 0.1));
            res.put("FUNC-OB", conf.getDouble("weight.giadungnoithat.func-ob", 0.1));
            res.put("PROP", conf.getDouble("weight.giadungnoithat.prop", 0.1));
            res.put("BR", conf.getDouble("weight.giadungnoithat.br", 0.1));
            res.put("OB", conf.getDouble("weight.giadungnoithat.ob", 0.1));
        }

        return res;
    }

    public static double getScoreByHistory(long timeEnd, long timeDate, long timeOnRead, long totalTime) {
        int day = (int) ((timeDate - timeEnd)/(86400*1000));
        double scoreByDate = 0.0;
        double scoreByTime = (double) timeOnRead/totalTime;
        if (day == 0) scoreByDate =  conf.getDouble("score.zero", 0.1);
        if (day == 1) scoreByDate = conf.getDouble("score.one", 0.125);
        if (day == 2) scoreByDate = conf.getDouble("score.two", 0.25);
        if (day == 3) scoreByDate = conf.getDouble("score.three", 0.5);
        if (day == 4) scoreByDate = conf.getDouble("score.four", 1.0);

        //kiem tra khoang thoi gian nao thi khach hang co kha nang mua cao nhat => dua ra trong so
        //mac dinh score la 1.0

        return scoreByTime * 0.2 + scoreByDate * 0.8;
    }
}
