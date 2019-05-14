package com.demo.admin;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.AuthInterceptor;
import com.demo.common.model.Attendance;
import com.demo.common.model.Leave;
import com.demo.common.model.Notice;
import com.demo.common.model.Student;
import com.demo.common.model.User;
import com.demo.jjwt.TokenUtil;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;

public class StudentController extends Controller {
	@Clear(AuthInterceptor.class)
	public void login() {
		String username = this.getPara("username");
        String password = this.getPara("password");	
        HashMap<String, Object> res = new HashMap<>();
        List<Student> users = Student.dao.findByNameAndPwd(username, password);
        if(users.size() > 0){
        	String token =  new TokenUtil().generateToken(username.replaceAll("\r|\n", ""),"student","101");
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
		String classId = getPara("classId");
		List<Student> students = Student.dao.findByClassId(classId);
		renderJson(students);
	}
	public void info() {
		String studentId = this.getAttr("username");
		HashMap<String, Object> res = new HashMap<>();
		Student studentInfo = Student.dao.findStudentInfo(studentId);
		res.put("userInfo", studentInfo);
		res.put("role", "student");
		renderJson(res);
	}
	public void update() {
		String json = HttpKit.readData(getRequest());
		JSONObject noticeInfo = JSONObject.parseObject(json);
		Student student = new Student();
		boolean status =  student.set("studentId", noticeInfo.getString("studentId"))
		.set("studentName", noticeInfo.getString("studentName"))
		.set("phone", noticeInfo.getString("phone"))
		.set("duty", noticeInfo.getString("duty"))
		.update();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", status);
		renderJson(res);
	}
	public void record() {
		String studentId = this.getAttr("username");
		Attendance atd = new Attendance();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", true);
		res.put("list",atd.findAllAtendance(studentId));
		renderJson(res);
	}
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
				.set("studentName", o.getString("studentName"))
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
