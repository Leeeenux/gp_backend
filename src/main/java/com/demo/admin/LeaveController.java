package com.demo.admin;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.Leave;
import com.demo.common.model.Notice;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;

public class LeaveController extends Controller {
	public void create() {
		String studentId = this.getAttr("username");
		String json = HttpKit.readData(getRequest());
		JSONObject leaveInfo = JSONObject.parseObject(json);
		System.out.println(leaveInfo.getJSONArray("time").get(0));
		Leave leave = new Leave();
		boolean status = leave.set("content", leaveInfo.getString("content"))
		.set("type", leaveInfo.getString("type"))
		.set("studentId", studentId)
		.set("classId", leaveInfo.getString("classId"))
		.set("startTime",leaveInfo.getJSONArray("time").get(0))
		.set("endTime",leaveInfo.getJSONArray("time").get(1))
		.set("status", "未审核")
		.save();
		renderJson("success",status);
	}
	public void list() {
		String studentId = this.getAttr("username");
		Leave leave = new Leave();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", true);
		res.put("list",leave.findByStudentId(studentId));
		renderJson(res);
	}
	public void listc() {
		String classId = getPara("classId");
		Leave leave = new Leave();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", true);
		res.put("list",leave.findByClassId(classId));
		renderJson(res);
	}
	public void agree() {
		int leaveId = getParaToInt("leaveId");
		Leave leave = new Leave();
		boolean status = leave.set("leaveId", leaveId)
				.set("status", "批准")
				.update();
		renderJson("success",status);
	}
	public void disagree() {
		int leaveId = getParaToInt("leaveId");
		Leave leave = new Leave();
		boolean status = leave.set("leaveId", leaveId)
				.set("status", "不批准")
				.update();
		renderJson("success",status);
	}
	public void delete() {
		int leaveId = getParaToInt("leaveId");
		Leave leave = new Leave();
		boolean status = leave.deleteById(leaveId);
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", status);
		renderJson(res);
	}
	public void update() {
		String json = HttpKit.readData(getRequest());
		JSONObject leaveInfo = JSONObject.parseObject(json);
		Leave leave = new Leave();
		boolean status =  leave.set("leaveId", leaveInfo.getString("leaveId"))
				.set("startTime", leaveInfo.getString("startTime"))
				.set("endTime", leaveInfo.getString("endTime"))
		.set("content", leaveInfo.getString("content"))
		.set("type", leaveInfo.getString("type"))
		.update();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", status);
		renderJson(res);
	}
}
