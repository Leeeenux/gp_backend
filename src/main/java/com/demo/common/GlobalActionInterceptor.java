package com.demo.common;

import java.util.logging.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class GlobalActionInterceptor implements Interceptor{
	static Logger logger = Logger.getLogger("GlobalActionInterceptor");// 一般为当前的类名
	 
	public void intercept(Invocation inv) {
 
		Controller controller = inv.getController();
		controller.getResponse().addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8080");
		controller.getResponse().addHeader("Access-Control-Allow-Headers", "X-Requested-With");
		controller.getResponse().addHeader("Access-Control-Allow-Headers", "content-type");
		controller.getResponse().addHeader("Access-Control-Allow-Headers", "token");
		controller.getResponse().addHeader("Access-Control-Allow-Credentials","true");
		
		
		inv.invoke();
	}

}
