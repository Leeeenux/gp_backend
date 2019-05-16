package com.demo.common.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Relation extends Model<Relation>{
	public static final Relation dao = new Relation();
	
	public List<Relation> findByNameAndPwd(String username, String password){
		return find("SELECT * FROM user WHERE id = '" + username + "' AND password = '" + password + "'");
	}
	public Relation findCurrentCourse(int id){
		return findFirst("SELECT class.className,subject.subject,relation.rid AS relationId," +
				"arrangement.startTime,arrangement.endTime,arrangement.desc,relation.weekday "+
				"FROM student INNER JOIN class "+
				"ON student.sclassid = class.classId INNER JOIN relation "+
				"ON class.classId = relation.rclassid INNER JOIN subject "+
				"ON relation.rcourseid = subject.SId INNER JOIN arrangement "+
				"ON relation.arrangement = arrangement.tid "+
				"WHERE student.sid = 1022 "+
				"AND relation.weekday = '星期一' "+
				"AND arrangement.startTime <= '08:20:00' "+
				"AND arrangement.endTime >= '08:20:00'");
	}
	public Relation findCurrentSubjectBys(String studentId){
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		Date currentTime = new Date();
		cal.setTime(currentTime);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		String current = sdf.format(currentTime);
		System.out.println(current);
		return findFirst("SELECT gp_relation.relationId,gp_student.studentName,gp_class.className,gp_subject.subjectName,"+
				"gp_arrange.startTime,gp_arrange.endTime,gp_arrange.arrangeDesc,gp_relation.weekday " +
				"FROM gp_student INNER JOIN gp_class ON gp_student.classId = gp_class.classId INNER JOIN gp_relation " + 
				"ON gp_class.classId = gp_relation.classId INNER JOIN gp_subject " + 
				"ON gp_relation.subjectId = gp_subject.subjectId INNER JOIN gp_arrange " + 
				"ON gp_relation.arrangeId = gp_arrange.arrangeId " +
				"WHERE gp_student.studentId = " + studentId + 
				" AND gp_relation.weekday = '" + weekDays[w] +
				"' AND gp_arrange.startTime <= '" + current + 
				"' AND gp_arrange.endTime >= '" + current +"'");
	}
	public Relation findCurrentSubjectByt(String teacherId){
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		Date currentTime = new Date();
		cal.setTime(currentTime);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		String current = "10:20:22"; //sdf.format(currentTime);
		System.out.println(current);
		return findFirst("SELECT gp_relation.relationId,gp_class.classId,gp_teacher.teacherId,gp_teacher.teacherName," + 
		"gp_class.className,gp_subject.subjectName,gp_arrange.startTime,gp_arrange.endTime,gp_arrange.arrangeDesc," + 
				"gp_relation.weekday FROM gp_relation INNER JOIN gp_class ON gp_relation.classId = gp_class.classId " + 
		"INNER JOIN gp_subject ON gp_relation.subjectId = gp_subject.subjectId INNER JOIN gp_arrange ON " + 
				"gp_relation.arrangeId = gp_arrange.arrangeId INNER JOIN gp_teacher ON " + 
		"gp_relation.teacherId = gp_teacher.teacherId WHERE gp_relation.teacherId = " + teacherId + 
		" AND gp_relation.weekday = '" + weekDays[w] + "' AND gp_arrange.startTime <= '" + current + 
		"' AND gp_arrange.endTime >= '" + current + "'");
	}
	public Relation findCurrentSubjectByc(String studentId){
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		Date currentTime = new Date();
		cal.setTime(currentTime);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		String current = sdf.format(currentTime);
		System.out.println(current);
		return findFirst("SELECT gp_relation.relationId,gp_student.studentName,gp_class.className,gp_subject.subjectName,"+
				"gp_arrange.startTime,gp_arrange.endTime,gp_arrange.arrangeDesc,gp_relation.weekday " +
				"FROM gp_student INNER JOIN gp_class ON gp_student.classId = gp_class.classId INNER JOIN gp_relation " + 
				"ON gp_class.classId = gp_relation.classId INNER JOIN gp_subject " + 
				"ON gp_relation.subjectId = gp_subject.subjectId INNER JOIN gp_arrange " + 
				"ON gp_relation.arrangeId = gp_arrange.arrangeId " +
				"WHERE gp_relation.classId = " + 101 + 
				" AND gp_relation.weekday = '" + weekDays[w] +
				"' AND gp_arrange.startTime <= '" + current + 
				"' AND gp_arrange.endTime >= '" + current +"'");
	}
	public List<Relation> findAllCourses(String studentId){
		return find("SELECT subject.subject,arrangement.startTime,arrangement.endTime,arrangement.desc,relation.weekday,teacher.teacher_name AS teacher "+
				"FROM student INNER JOIN class "+
				"ON student.sclassid = class.classId INNER JOIN relation "+
				"ON class.classId = relation.rclassid INNER JOIN subject "+
				"ON relation.rcourseid = subject.SId INNER JOIN arrangement "+
				"ON relation.arrangement = arrangement.tid INNER JOIN teacher "+
				"ON relation.teacher = teacher.teacher_id " +
				"WHERE student.sid = " + studentId);
	}
	public Relation findClassName(String studentId) {
		return findFirst("SELECT class.className FROM student INNER JOIN class ON student.sclassid = class.classId WHERE student.sid = " + studentId);		
	}
	public List<Relation> findAllAtendance(String studentId) {
		return find("SELECT gp_relation.relationId,gp_attendance.attendanceId,gp_student.studentName," + 
					"gp_relation.weekday,gp_attendance.signInTime,gp_attendance.signInStatus,gp_subject.subjectName," + 
				    "gp_arrange.arrangeDesc FROM gp_attendance INNER JOIN gp_relation " + 
					"ON gp_relation.relationId = gp_attendance.relationId INNER JOIN gp_student " +
				    "ON gp_attendance.studentId = gp_student.studentId INNER JOIN gp_arrange " +
					"ON gp_relation.arrangeId = gp_arrange.arrangeId INNER JOIN gp_subject " + 
				    "ON gp_subject.subjectId = gp_relation.subjectId " + 
					"WHERE gp_student.studentId = " + studentId);
	}
}
