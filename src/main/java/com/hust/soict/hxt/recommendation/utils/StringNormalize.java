package com.hust.soict.hxt.recommendation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class StringNormalize {

    private static final Logger logger = LoggerFactory.getLogger("warringLog");

    public static String normalize(String title) {

        String output = title.trim();
        try {
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
        }catch (Exception e) {
            logger.warn("error normal title: " + title);
        }

        return output;
    }

    public static String receiveItemId(String url) {
        String stringItem = null;
        String parseItem = null;
        String parseDomain = "//\\w+.(\\w+)*.\\w+/";
        Pattern pattern = Pattern.compile(parseDomain);
        Matcher matcher = pattern.matcher(url);

        try{
            if (matcher.find()) {
                if (matcher.group(0).equalsIgnoreCase("//muachung.vn/")) {
                    parseItem = "\\-\\d+.html";
                } else if (matcher.group(0).equalsIgnoreCase("//plaza.muachung.vn/")) {
                    parseItem = "/d\\-\\d+/";
                }
            }

            pattern = Pattern.compile(parseItem);
            matcher = pattern.matcher(url);
            if (matcher.find()) {
                pattern = Pattern.compile("\\d+");
                String segment = matcher.group(0);
                matcher = pattern.matcher(segment);
                if (matcher.find()) {
                    stringItem = matcher.group(0);
                }
            }
        }catch (Exception e) {
            logger.warn("error parsed url: " + url);
        }

        return stringItem;
    }
}
