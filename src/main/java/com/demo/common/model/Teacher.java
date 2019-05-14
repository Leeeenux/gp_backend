package com.demo.common.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Teacher extends Model<Teacher>{
	public static final Teacher dao = new Teacher();
	
	public Teacher findTeacherInfo(String teacherId){
		return findFirst("SELECT gp_teacher.teacherId AS id,gp_teacher.teacherName AS name FROM gp_teacher "+
	"WHERE gp_teacher.teacherId = " + teacherId );
	}
	public List<Teacher> findAllTeacher(){
		return find("SELECT gp_teacher.teacherId,gp_teacher.teacherName,gp_teacher.duty,gp_teacher.phone " + 
	"FROM gp_teacher");
	}
	public List<Teacher> findByNameAndPwd(String username, String password){
		return find("SELECT * FROM gp_teacher WHERE teacherId = '" + username + "' AND password = '" + password + "'");
	}
}
