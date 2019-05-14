package com.demo.admin;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.Student;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;

public class PersonController extends Controller {
	public void upload() {
		String json = HttpKit.readData(getRequest());
		System.out.println(json);
		JSONArray jsonArray = JSON.parseArray(json);
		Student student = new Student();
		HashMap<String, Object> res = new HashMap<>();
		int num = 0;
		int sum = jsonArray.size();
		for (Object obj:jsonArray) {
			JSONObject o = JSON.parseObject(obj.toString());
			try {
				student.set("studentId", o.getString("studentId"))
				.set("studentName", o.getString("name"))
				.set("classId", o.getString("classId"))
				.set("phone", o.getString("phone"))
				.set("duty", o.getString("duty"))
				.save();
			} catch (Exception e) {
				System.out.println(e);
				num ++;
			}
			
        }
		res.put("msg", "成功导入" + (sum-num) + "个学生");
		renderJson(res);
	}
}
