package com.demo.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("blog", "id", Blog.class);
		arp.addMapping("user", "uid", User.class);
		arp.addMapping("relation", "rid", Relation.class);
		arp.addMapping("student", "sid", Student.class);
		arp.addMapping("gp_attendance","attendanceId",Attendance.class);
		arp.addMapping("gp_student_copy","studentId",Student2.class);
	}
}

