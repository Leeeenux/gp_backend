package com.demo.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.AuthInterceptor;
import com.demo.common.model.Attendance;
import com.demo.common.model.Leave;
import com.demo.common.model.Relation;
import com.demo.common.model.Student;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
@Clear(AuthInterceptor.class)
public class WechatController extends Controller {
	public void info() {
		String studentId = getPara("studentId");
		HashMap<String, Object> res = new HashMap<>();
		Student studentInfo = Student.dao.findInfo(studentId);
		renderJson(studentInfo);
	}
	public void currentsubject() {
		String studentId = getPara("studentId");
		HashMap<String, Object> res = new HashMap<>();
		Relation relation = new Relation();
		renderJson(relation.findCurrentSubjectBys(studentId));
	}
	public void create() {
		
	}
	public void signin() {
		String studentId = getPara("studentId");
		String classId = getPara("classId");
		Attendance atd = new Attendance();
		HashMap<String, Object> res = new HashMap<>();
		String LastAtdId = atd.findLastAtendanceId(studentId).getStr("attendanceId");
		boolean IsAllow = atd.IsAllow(classId).getBoolean("allow");
		System.out.println(IsAllow);
		if (IsAllow) {
			HashMap<String, Object> params = new HashMap<>();
			Date currentTime = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String current = sdf.format(currentTime);
			params.put("attendanceId", LastAtdId);
			params.put("signInStatus", "准点");
			params.put("signInTime", current);
			atd._setAttrs(params);
			atd.update();
			res.put("success", true);
			res.put("msg", "签到成功");
		}else {
			res.put("success", true);
			res.put("msg","还未开放签到");
		}
		renderJson(res);
	}
	public void leave() {
		String studentId = getPara("studentId");
		String classId = getPara("classId");
		String time = getPara("time");
		String content = getPara("content");
		Leave leave = new Leave();
		boolean status = leave.set("content", content)
		.set("type", "请假")
		.set("studentId", studentId)
		.set("classId", classId)
		.set("startTime",time)
		.set("endTime",time)
		.set("status", "未审核")
		.save();
		renderJson("success",status);
	}
	public void record(){
		Attendance atd = new Attendance();
		HashMap<String, Object> res = new HashMap<>();
		res.put("list",atd.findAllAtendance("1001"));
		renderJson(res);
	}
}
