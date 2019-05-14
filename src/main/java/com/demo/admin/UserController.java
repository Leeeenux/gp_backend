package com.demo.admin;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.demo.common.AuthInterceptor;
import com.demo.common.model.Student;
import com.demo.common.model.Teacher;
import com.demo.common.model.User;
import com.demo.jjwt.TokenUtil;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

public class UserController extends Controller {
	
//	private String Id = new UserController().getAttr("id");
	
	@Clear(AuthInterceptor.class)//登录不需要auth拦截
	public void login() {
		String username = this.getPara("username");
        String password = this.getPara("password");
        HttpSession session = getSession();
        session.setMaxInactiveInterval(3600);//会话过期时间，单位：秒
        HashMap<String, Object> res = new HashMap<>();
        List<Student> users = Student.dao.findByNameAndPwd(username, password);
        if(users.size() > 0){
        	String token =  new TokenUtil().generateToken(username.replaceAll("\r|\n", ""),"ss","101");
        	res.put("token", token);
        	res.put("success", true);
        	res.put("msg", "登陆成功");
        }else{
        	res.put("success", false);
        	res.put("msg", "登陆失败");
        }		
		renderJson(res);
	}
	public void info() {
		String username = this.getAttr("username");
		String role = this.getAttr("role");
		HashMap<String, Object> res = new HashMap<>();
		if (role.equals("student")) {
			Student StudentInfo = Student.dao.findStudentInfo(username);
			res.put("userInfo", StudentInfo);
		}else if(role.equals("teacher")){
			Teacher teacherInfo = Teacher.dao.findTeacherInfo(username);
			res.put("userInfo", teacherInfo);
		}else if (role.equals("admin")) {
			Student StudentInfo = Student.dao.findStudentInfo(username);
			res.put("userInfo", StudentInfo);
		}
		res.put("role", role);
		renderJson(res);
	}
	public void header() {
		HashMap<String, Object> res = new HashMap<>();
		res.put("token", "sss");
		renderJson(res);
	}
	public void tokeninfo() {
		String token = getPara("token");
		HashMap<String, Object> res = new HashMap<>();
		String s =  new TokenUtil().tokenParseUsername(token);
		res.put("id", s);
		renderJson(res);
	}
	public void test() {
		String Id = this.getAttr("id");
		renderJson("id",Id);
	}

}
