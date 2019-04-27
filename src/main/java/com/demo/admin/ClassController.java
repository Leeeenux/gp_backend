package com.demo.admin;

import java.util.HashMap;
import java.util.List;

import com.demo.common.model.Attendance;
import com.demo.common.model.Relation;
import com.jfinal.core.Controller;

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
	
	public void xingqi() {
		
	}

}
