package com.hust.soict.hxt.recommendation.utils;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class StringNormalize {

    public static String normalize(String title) {

        String output = title;
        output = output.replace("-", " - ");
        output = output.replace(":", " : ");
        output = output.replace(",", " , ");
        output = output.replace(".", " . ");
        output = output.replace(";", " ; ");
        output = output.replace("(", " ");
        output = output.replace(")", " ");
        output = output.replace("!", " ");
        output = output.replace("+", " + ");
        output = output.replace("/", " / ");
        output = output.replace("\\", " \\ ");
        output = output.replace("&", " & ");
        output = output.replace("\"", " ");
        output = output.replace("\'", " ");
        output = output.replace("`", " ");
        output = output.replace("``", " ");
        output = output.replace("“", " ");
        output = output.replace("”", " ");
        output = output.replace("  ", " ");
        output = output.replace("  ", " ");

        return output;
    }
}
