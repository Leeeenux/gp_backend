package com.demo.admin;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.Attendance;
import com.demo.common.model.Relation;
import com.demo.common.model.Student2;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;

public class ClassController extends Controller {
	public void getclass() {
		String studentId = this.getAttr("username");
		HashMap<String, Object> res = new HashMap<>();
		Relation relation = new Relation();
		List<Relation> relations = relation.dao.findAllCourses(studentId);
		Relation className = Relation.dao.findClassName(studentId);
		res.put("className", className);
		res.put("courses", relations);
		renderJson(res);
	}
	public void currentclass() {
		String studentId = this.getAttr("username");
		HashMap<String, Object> res = new HashMap<>();
		List<Attendance> className = Attendance.dao.findAllAtendance(studentId);
		renderJson(className);
	}
	
	public void array() {
		String json = HttpKit.readData(getRequest());
//		String str = JSONArray.toJSONString(json);
		System.out.println(json);
		JSONArray jsonArray = JSON.parseArray(json);
		Student2 student2 = new Student2();
		HashMap<String, Object> res = new HashMap<>();
		for (Object obj:jsonArray) {
			JSONObject o = JSON.parseObject(obj.toString());
			try {
				student2.set("studentId", o.getString("num"))
				.set("studentName", o.getString("name"))
				.set("classId", o.getString("classid"))
				.save();
			} catch (Exception e) {
				System.out.println(o.getString("name")+"已存在");
			}
			
        }
		res.put("msg", "hello!");
		renderJson(res);
	}

}
