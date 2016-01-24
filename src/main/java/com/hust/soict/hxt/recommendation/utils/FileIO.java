package com.hust.soict.hxt.recommendation.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class FileIO {

    public static List<String> readFile(String path) {
        List<String> result = new ArrayList<>();
        try {
            FileInputStream ios = new FileInputStream(path);
            InputStreamReader is = new InputStreamReader(ios, "utf-8");
            BufferedReader br = new BufferedReader(is);

            String line="";
            while((line = br.readLine()) != null) {
                result.add(line.trim());
            }

            br.close();
            is.close();
            ios.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
