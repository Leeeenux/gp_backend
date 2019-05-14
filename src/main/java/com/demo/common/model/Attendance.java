package com.demo.common.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

public class Attendance extends Model<Attendance>{
	public static final Attendance dao = new Attendance();
	
	public List<Attendance> findAllAtendance(String studentId) {
		return find("SELECT gp_relation.relationId,gp_attendance.attendanceId,gp_attendance.createTime,gp_student.studentName," + 
					"gp_relation.weekday,gp_attendance.signInTime,gp_attendance.signInStatus,gp_subject.subjectName," + 
				    "gp_arrange.arrangeDesc FROM gp_attendance INNER JOIN gp_relation " + 
					"ON gp_relation.relationId = gp_attendance.relationId INNER JOIN gp_student " +
				    "ON gp_attendance.studentId = gp_student.studentId INNER JOIN gp_arrange " +
					"ON gp_relation.arrangeId = gp_arrange.arrangeId INNER JOIN gp_subject " + 
				    "ON gp_subject.subjectId = gp_relation.subjectId " + 
					"WHERE gp_student.studentId = " + studentId);
	}
	public List<Attendance> findWeekAtendance(String studentId) {
		return find("SELECT gp_relation.relationId,gp_attendance.attendanceId,gp_attendance.createTime,gp_student.studentName," + 
					"gp_relation.weekday,gp_attendance.signInTime,gp_attendance.signInStatus,gp_subject.subjectName," + 
				    "gp_arrange.arrangeDesc FROM gp_attendance INNER JOIN gp_relation " + 
					"ON gp_relation.relationId = gp_attendance.relationId INNER JOIN gp_student " +
				    "ON gp_attendance.studentId = gp_student.studentId INNER JOIN gp_arrange " +
					"ON gp_relation.arrangeId = gp_arrange.arrangeId INNER JOIN gp_subject " + 
				    "ON gp_subject.subjectId = gp_relation.subjectId " + 
					"WHERE gp_student.studentId = " + studentId);
	}
	public List<Attendance> findMonthAtendance(String studentId) {
		return find("SELECT gp_relation.relationId,gp_attendance.attendanceId,gp_attendance.createTime,gp_student.studentName," + 
					"gp_relation.weekday,gp_attendance.signInTime,gp_attendance.signInStatus,gp_subject.subjectName," + 
				    "gp_arrange.arrangeDesc FROM gp_attendance INNER JOIN gp_relation " + 
					"ON gp_relation.relationId = gp_attendance.relationId INNER JOIN gp_student " +
				    "ON gp_attendance.studentId = gp_student.studentId INNER JOIN gp_arrange " +
					"ON gp_relation.arrangeId = gp_arrange.arrangeId INNER JOIN gp_subject " + 
				    "ON gp_subject.subjectId = gp_relation.subjectId " + 
					"WHERE gp_student.studentId = " + studentId);
	}
	public int startAtendance(String classId,String relationId) {
		
		return Db.update("INSERT INTO gp_attendance (studentId,relationId) " + 
					"SELECT gp_student.studentId,gp_relation.relationId FROM gp_student INNER JOIN gp_relation " + 
					"ON gp_student.classId = gp_relation.classId " + 
					"WHERE gp_student.classId = " + classId + " AND gp_relation.relationId = " + relationId);
	}
	public Attendance findLastAtendanceId(String studentId) {
		return findFirst("SELECT attendanceId FROM gp_attendance WHERE studentId = " + studentId + " ORDER BY attendanceId DESC LIMIT 1");
	}
	public int Allow(String classId) {
		return Db.update("UPDATE gp_attendance_allow SET allow=1 WHERE classId = " + classId);
	}
	public int NotAllow(String classId) {
		return Db.update("UPDATE gp_attendance_allow SET allow=0 WHERE classId = " + classId);
	}
	public Attendance IsAllow(String classId) {
		return findFirst("SELECT allow FROM gp_attendance_allow WHERE classId = " + classId);
	}
}
