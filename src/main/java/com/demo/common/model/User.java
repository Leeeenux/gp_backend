package com.demo.common.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User>{
	public static final User dao = new User();
	
	public List<User> findByNameAndPwd(String username, String password){
		return find("SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "'");
	}
	public User findByUserName(String username){
		return findFirst("SELECT * FROM user WHERE username = " + username );
	}
}
