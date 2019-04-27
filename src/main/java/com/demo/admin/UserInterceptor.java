package com.demo.admin;

import java.util.logging.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class UserInterceptor implements Interceptor{
	static Logger logger = Logger.getLogger("UserInterceptor");
	
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		controller.getResponse().addHeader("Access-Control-Allow-Origin", "*");
		controller.getResponse().addHeader("Access-Control-Allow-Headers", "*");
	       System.out.println("Before method invoking");
	       inv.invoke();
	       System.out.println("After method invoking");
	}

}
