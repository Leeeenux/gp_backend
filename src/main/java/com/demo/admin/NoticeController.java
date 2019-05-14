package com.demo.admin;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.Notice;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;

public class NoticeController extends Controller {
	public void create() {
		String json = HttpKit.readData(getRequest());
		JSONObject noticeInfo = JSONObject.parseObject(json);
		Notice notice = new Notice();
		boolean status = notice.set("title", noticeInfo.getString("title"))
		.set("content", noticeInfo.getString("content"))
		.set("type", noticeInfo.getString("type"))
		.set("classId", noticeInfo.getString("classId"))
		.save();
		
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", status);
		renderJson(res);
		
	}
	public void list() {
		Notice notice = new Notice();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", true);
		res.put("list",notice.findAllClass());
		renderJson(res);
	}
	public void delete() {
		int noticeId = getParaToInt("noticeId");
		Notice notice = new Notice();
		System.out.println(noticeId);
		boolean status = notice.deleteById(noticeId);
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", status);
		renderJson(res);
	}
	public void update() {
		String json = HttpKit.readData(getRequest());
		JSONObject noticeInfo = JSONObject.parseObject(json);
		Notice notice = new Notice();
		boolean status =  notice.set("noticeId", noticeInfo.getString("noticeId"))
		.set("title", noticeInfo.getString("title"))
		.set("content", noticeInfo.getString("content"))
		.set("type", noticeInfo.getString("type"))
		.set("classId", noticeInfo.getString("classId"))
		.update();
		HashMap<String, Object> res = new HashMap<>();
		res.put("success", status);
		renderJson(res);
	}
}
