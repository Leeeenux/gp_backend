package com.demo.common.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Student extends Model<Student>{
	public static final Student dao = new Student();
	
	public Student findStudentInfo(String StudentId){
		return findFirst("SELECT student.sid As id,student.sname AS name ,class.className FROM student "+
	"INNER JOIN class ON student.sclassid = class.classId " + "WHERE student.sid = " + StudentId );
	}
}
