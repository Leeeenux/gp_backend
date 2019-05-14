package com.demo.common.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Notice extends Model<Notice>{
	public static final Notice dao = new Notice();
	
	public List<Notice> findByClassId(String classId){
		return find("SELECT gp_notice_info.noticeId,gp_notice_info.classId,gp_class.className,gp_notice_info.type," + 
				"gp_notice_info.title,gp_notice_info.content FROM gp_notice_info INNER JOIN gp_class " + 
			"ON gp_notice_info.classId = gp_class.classId WHERE gp_class.classId = " + classId);
	}
	public List<Notice> findAllClass(){
		return find("SELECT gp_notice_info.noticeId,gp_notice_info.classId,gp_class.className,gp_notice_info.type," + 
					"gp_notice_info.title,gp_notice_info.content,gp_notice_info.createTime FROM gp_notice_info INNER JOIN gp_class " + 
				"ON gp_notice_info.classId = gp_class.classId");
	}
}
