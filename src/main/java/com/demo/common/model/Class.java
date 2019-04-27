package com.demo.common.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Class extends Model<Class>{
	public static final Class dao = new Class();
	
	public List<Class> findByNameAndPwd(String username, String password){
		return find("SELECT * FROM user WHERE id = '" + username + "' AND password = '" + password + "'");
	}
	public List<Class> findCurrentClass(int id){
		return find("SELECT gp_student.sname,class.className,subject.subject," +
				"arrangement.startTime,arrangement.endTime,arrangement.desc,relation.weekday"+
				"FROM student INNER JOIN class"+
				"ON student.sclassid = class.classId INNER JOIN relation"+
				"ON class.classId = relation.rclassid INNER JOIN subject"+
				"ON relation.rcourseid = subject.SId INNER JOIN arrangement"+
				"ON relation.arrangement = arrangement.tid"+
				"WHERE student.sid = 1022"+
				"AND relation.weekday = '星期一'"+
				"AND arrangement.startTime <= '08:20:00' "+
				"AND arrangement.endTime >= '08:20:00'");
	}
}
