package com.demo.admin;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.Attendance;
import com.demo.common.model.Notice;
import com.demo.common.model.Relation;
import com.demo.common.model.Student;
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
	public void notice() {
		String json = HttpKit.readData(getRequest());
		System.out.println(json);
		JSONObject noticeInfo = JSONObject.parseObject(json);
		Notice notice = new Notice();
		notice.set("title", noticeInfo.getString("title"))
		.set("content", noticeInfo.getString("content"))
		.set("type", noticeInfo.getString("type"))
		.set("classId", noticeInfo.getString("classId"))
		.save();
		
		HashMap<String, Object> res = new HashMap<>();
		res.put("msg", "hello!");
		renderJson(res);
	}
	
	public void array() {
		String json = HttpKit.readData(getRequest());
//		String str = JSONArray.toJSONString(json);
		System.out.println(json);
		JSONArray jsonArray = JSON.parseArray(json);
		Student student = new Student();
		HashMap<String, Object> res = new HashMap<>();
		for (Object obj:jsonArray) {
			JSONObject o = JSON.parseObject(obj.toString());
			try {
				student.set("studentId", o.getString("num"))
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
