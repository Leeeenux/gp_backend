package com.demo.util;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;

public class Face {
	public static void main(String[] args) {
		HashMap<String, String> param = new HashMap<>();
        param.put("Image", "sss");
        param.put("personif", "100");
        String json = JSON.toJSONString(param);
        System.out.println(json);
	}
}
