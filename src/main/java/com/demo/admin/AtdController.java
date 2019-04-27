package com.demo.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.demo.common.model.Attendance;
import com.jfinal.core.Controller;

public class AtdController extends Controller {
	public void signin() {
		Attendance atd = new Attendance();
		String LastAtdId = atd.findLastAtendanceId("1001").getStr("attendanceId");
		boolean IsAllow = atd.IsAllow("101").getBoolean("allow");
		System.out.println(atd.IsAllow("101").getBoolean("allow"));
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
	public void find() {
		Attendance atd = Attendance.dao.findLastAtendanceId("1001");
		renderJson("res",atd);
	}

}
