package com.demo.common;

import java.util.HashMap;
import java.util.logging.Logger;

import com.demo.jjwt.TokenUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class AuthInterceptor implements Interceptor{
	static Logger logger = Logger.getLogger("AuthActionInterceptor");// 一般为当前的类名
	 
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		if (controller.getRequest().getMethod()=="OPTIONS"){//OPTIONS统一返回
			HashMap<String, Object> res = new HashMap<>();
			System.out.println("OPTIONS拦截");
			res.put("msg", "hello!");
			controller.renderJson(res);
		}
//		else if (controller.getRequest().getHeader("Referer")!=null) {
//			System.out.println("来自微信不拦截");
//			inv.invoke();
//		}
		else {//OPTIONS不做拦截
			String token = inv.getController().getHeader("token");
			if (token == null) {
				System.out.println("token不存在");
				inv.invoke();
				HashMap<String, Object> res = new HashMap<>();
				res.put("success", false);
				res.put("code", 401);
				res.put("msg", "token不存在");
				controller.renderJson(res);
			}else {
				String username = new TokenUtil().tokenParseUsername(token);
				String role = new TokenUtil().tokenParseRole(token);
				String classId = new TokenUtil().tokenParseClass(token);
				if (username == null) {
					inv.invoke();
					HashMap<String, Object> res = new HashMap<>();
					res.put("success", false);
					res.put("code", 401);
					res.put("msg", "登录超时");
					controller.renderJson(res);
				}else {
					controller.setAttr("username", username);//传参给controller
					controller.setAttr("role", role);
					controller.setAttr("classId", classId);
					inv.invoke();
				}
			}
			
			
		}
		
	}

}
