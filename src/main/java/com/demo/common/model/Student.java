package com.demo.common.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Student extends Model<Student>{
	public static final Student dao = new Student();
	
	public Student findStudentInfo(String StudentId){
		return findFirst("SELECT gp_student.studentId AS id,gp_student.studentName AS name,gp_student.classId,gp_class.className FROM gp_student "+
	"INNER JOIN gp_class ON gp_student.classId = gp_class.classId WHERE gp_student.studentId = " + StudentId );
	}
	public List<Student> findByClassId(String classId){
		return find("SELECT gp_student.studentId,gp_student.studentName,gp_student.duty,gp_student.phone,gp_class.className " + 
	"FROM gp_student INNER JOIN gp_class ON gp_student.classId = gp_class.classId WHERE gp_class.classId = " + classId );
	}
	public List<Student> findByNameAndPwd(String username, String password){
		return find("SELECT * FROM gp_student WHERE studentId = '" + username + "' AND password = '" + password + "'");
	}
	public Student findInfo(String StudentId){
		return findFirst("SELECT gp_student.studentId,gp_student.studentName,gp_student.classId,gp_class.className FROM gp_student "+
	"INNER JOIN gp_class ON gp_student.classId = gp_class.classId WHERE gp_student.studentId = " + StudentId );
	}
}
