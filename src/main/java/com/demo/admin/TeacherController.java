package com.demo.admin;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.AuthInterceptor;
import com.demo.common.model.Attendance;
import com.demo.common.model.Relation;
import com.demo.common.model.Student;
import com.demo.common.model.Teacher;
import com.demo.jjwt.TokenUtil;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;

public class TeacherController extends Controller {
	@Clear(AuthInterceptor.class)
	public void login() {
		String username = this.getPara("username");
        String password = this.getPara("password");	
        HashMap<String, Object> res = new HashMap<>();
        List<Teacher> teachers = Teacher.dao.findByNameAndPwd(username, password);
        System.out.println(teachers.size());
        if(teachers.size() > 0){
        	String token =  new TokenUtil().generateToken(username.replaceAll("\r|\n", ""),"teacher",null);
        	res.put("token", token);
        	res.put("success", true);
        	res.put("msg", "登陆成功");
        }else{
        	res.put("success", false);
        	res.put("msg", "登陆失败");
        }		
		renderJson(res);
	}
	public void list() {
		List<Teacher> teachers = Teacher.dao.findAllTeacher();
		renderJson(teachers);
	}
	public void current() {
		String teacherId = this.getAttr("username");
		Relation relation = new Relation();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", true);
		res.put("subject", relation.findCurrentSubjectByt(teacherId));
		renderJson(res);
	}
	public void student() {
		String teacherId = this.getAttr("username");
		Attendance atd = new Attendance();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", true);
		res.put("list", atd.findCurrentAtendance("101", "5"));
		renderJson(res);
	}
	public void atdlist() {
		String classId = getPara("classId");
		String date = getPara("date");
		String subjectId = getPara("subjectId");
		Attendance atd = new Attendance();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", true);
		res.put("list", atd.findAtdBySubject(classId, date, subjectId));
		renderJson(res);
	}
	
	public void subjects() {
		String classId = getPara("classId");
		String date = getPara("date");
		Attendance atd = new Attendance();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", true);
		res.put("list", atd.findSubjects(classId, date));
		renderJson(res);
	}
	public void update() {
		String json = HttpKit.readData(getRequest());
		JSONObject noticeInfo = JSONObject.parseObject(json);
		Teacher teacher = new Teacher();
		boolean status =  teacher.set("teacherId", noticeInfo.getString("teacherId"))
		.set("teacherName", noticeInfo.getString("teacherName"))
		.set("phone", noticeInfo.getString("phone"))
		.set("duty", noticeInfo.getString("duty"))
		.update();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", status);
		renderJson(res);
	}
	public void upload() {
		String json = HttpKit.readData(getRequest());
		System.out.println(json);
		JSONArray jsonArray = JSON.parseArray(json);
		Teacher teacher = new Teacher();
		HashMap<String, Object> res = new HashMap<>();
		int num = 0;
		int sum = jsonArray.size();
		for (Object obj:jsonArray) {
			JSONObject o = JSON.parseObject(obj.toString());
			try {
				teacher.set("teacherId", o.getString("teacherId"))
				.set("teacherName", o.getString("teacherName"))
				.set("phone", o.getString("phone"))
				.set("duty", o.getString("duty"))
				.save();
			} catch (Exception e) {
				System.out.println(e);
				num ++;
			}
			
        }
		res.put("msg", "成功导入" + (sum-num) + "个教师");
		renderJson(res);
	}
}
