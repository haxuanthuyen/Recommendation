package com.hust.soict.hxt.recommendation.algorithm.similarity;

import java.util.*;

/**
 * Created by thuyenhx on 3/22/16.
 */
public class JaccardCoefficient implements SimilarityMeasure {

    public JaccardCoefficient() {}

    @Override
    public double similarity(List<String> a, List<String> b) {
        if (a.size() == 0 || b.size() == 0) return 0.0;
        List<String> aTemp = new ArrayList<>();
        List<String> bTemp = new ArrayList<>();

        for (String s : a) {
            String[] tokens = s.split("_");
            aTemp.addAll(Arrays.asList(tokens));
        }

        for( String s : b) {
            String[] tokens = s.split("_");
            bTemp.addAll(Arrays.asList(tokens));
        }

        Set<String> unionLst = new HashSet<>(aTemp);
        unionLst.addAll(bTemp);

        Set<String> intersectionLst = new HashSet<>(aTemp);
        intersectionLst.retainAll(bTemp);

        return (double) intersectionLst.size() / unionLst.size();
    }

    public double similarity(String a, String b) {
        List<String> aTemp = new ArrayList<>();
        List<String> bTemp = new ArrayList<>();

        String[] tokensA = a.split("_");
        aTemp.addAll(Arrays.asList(tokensA));

        String[] tokensB = b.split("_");
        bTemp.addAll(Arrays.asList(tokensB));

        Set<String> unionLst = new HashSet<>(aTemp);
        unionLst.addAll(bTemp);

        Set<String> intersectionLst = new HashSet<>(aTemp);
        intersectionLst.retainAll(bTemp);

        return (double) intersectionLst.size() / unionLst.size();
    }
}
