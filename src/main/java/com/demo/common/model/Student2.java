package com.demo.common.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Student2 extends Model<Student2>{
	public static final Student2 dao = new Student2();
	
	public Student2 findStudentInfo(String StudentId){
		return findFirst("SELECT student.sid As id,student.sname AS name ,class.className FROM student "+
	"INNER JOIN class ON student.sclassid = class.classId " + "WHERE student.sid = " + StudentId );
	}
}
