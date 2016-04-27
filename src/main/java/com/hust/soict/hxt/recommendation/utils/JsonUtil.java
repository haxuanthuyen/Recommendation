package com.hust.soict.hxt.recommendation.utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thuyenhx on 4/27/16.
 */
public class JsonUtil {
    public static void main(String[] args) throws Exception{
        JsonUtil.getJsonData();
    }
    private static ObjectMapper mapper = new ObjectMapper();
    public static void getJsonData() throws Exception{
        String res = "";
        Map<String,String> payload = new HashMap<>();
        payload.put("key1","value1");
        payload.put("key2","value2");

        String json = new ObjectMapper().writeValueAsString(payload);
        System.out.println(json);
    }
}
