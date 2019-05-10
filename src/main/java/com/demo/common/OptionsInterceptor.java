package com.demo.common;

import java.util.HashMap;
import java.util.logging.Logger;

import com.demo.jjwt.TokenUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class OptionsInterceptor implements Interceptor{
	static Logger logger = Logger.getLogger("OptionsInterceptor");// 一般为当前的类名
	 
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		if (controller.getRequest().getMethod()=="OPTIONS"){//OPTIONS统一返回
			HashMap<String, Object> res = new HashMap<>();
			System.out.println("OPTIONS拦截");
			res.put("msg", "hello!");
			controller.renderJson(res);
		}
	}

}
