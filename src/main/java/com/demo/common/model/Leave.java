package com.demo.common.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Leave extends Model<Leave>{
	public static final Leave dao = new Leave();
	
	public List<Leave> findByClassId(String classId){
		return find("SELECT gp_leave.leaveId,gp_leave.status,gp_leave.classId,gp_student.studentName,gp_class.className," + 
	"gp_leave.type,gp_leave.content,gp_leave.startTime,gp_leave.endTime FROM gp_leave INNER JOIN gp_class " + 
				"ON gp_leave.classId = gp_class.classId INNER JOIN gp_student ON gp_leave.studentId = gp_student.studentId " +
	"WHERE gp_leave.classId = " + classId);
		}
	public List<Leave> findByStudentId(String studentId){
		return find("SELECT gp_leave.leaveId,gp_leave.status,gp_leave.classId,gp_student.studentName,gp_class.className," + 
	"gp_leave.type,gp_leave.content,gp_leave.startTime,gp_leave.endTime FROM gp_leave INNER JOIN gp_class " + 
				"ON gp_leave.classId = gp_class.classId INNER JOIN gp_student ON gp_leave.studentId = gp_student.studentId " +
	"WHERE gp_leave.studentId = " + studentId);
	}
	public List<Leave> findAllClass(){
		return find("SELECT gp_notice_info.noticeId,gp_notice_info.classId,gp_class.className,gp_notice_info.type," + 
					"gp_notice_info.title,gp_notice_info.content,gp_notice_info.createTime FROM gp_notice_info INNER JOIN gp_class " + 
				"ON gp_notice_info.classId = gp_class.classId");
	}
}
