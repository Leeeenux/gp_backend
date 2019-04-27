package com.demo.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.demo.common.AuthInterceptor;
import com.demo.common.model.Student;
import com.demo.util.VerifyFace;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

public class ApiController extends Controller {
	public void shenqing() {
		String Id = this.getAttr("id");
		String fromTime = getPara("time[0]");
		String toTime = getPara("time[0]");
		String desc = getPara("desc");
		String type = getPara("type");
		renderJson("asd","s");
	}
	@Clear(AuthInterceptor.class)
	public void face() {
		String base64 = getPara("base64");
		String studentId = getPara("studentid");
		System.out.println(studentId);
		VerifyFace verifyface = new VerifyFace();
		verifyface.verify(base64,studentId);
		renderJson("seuu","s");
	}
	@Clear(AuthInterceptor.class)
	public void create() {
		String base64 = getPara("base64");
		String studentId = getPara("studentid");
		String groupId = getPara("groupid");
		String gender = getPara("gender");
		String personName = getPara("name");
		System.out.println(studentId);
		VerifyFace verifyface = new VerifyFace();
		verifyface.createPerson(groupId, personName, studentId, base64, gender);
		renderJson("seuu","s");
	}
	
}
