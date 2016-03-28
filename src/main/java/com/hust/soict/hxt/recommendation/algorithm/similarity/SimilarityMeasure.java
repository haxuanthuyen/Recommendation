package com.hust.soict.hxt.recommendation.algorithm.similarity;

import java.util.List;

/**
 * Created by thuyenhx on 3/22/16.
 */
public interface SimilarityMeasure {
    public double similarity(List<String> a, List<String>  b);
}
