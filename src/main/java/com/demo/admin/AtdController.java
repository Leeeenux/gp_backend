package com.demo.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.demo.common.AuthInterceptor;
import com.demo.common.model.Attendance;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

public class AtdController extends Controller {
	@Clear(AuthInterceptor.class)
	public void signin() {
		String studentId = getPara("studentId");
		String classId = getPara("classId");
		Attendance atd = new Attendance();
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
			renderJson("msg","签到成功");
		}else {
			renderJson("msg","还未开放签到");
		}
	}
	public void start() {
		Attendance atd = new Attendance();
		int res = atd.startAtendance("101","5");
		renderJson("res",res);
	}
	public void end() {
		Attendance atd = new Attendance();
		int res = atd.startAtendance("101","5");
		renderJson("res",res);
	}
	public void list() {
		String studentId = getPara("studentId");
		Attendance atd = Attendance.dao.findLastAtendanceId(studentId);
		renderJson("res",atd);
	}

}
