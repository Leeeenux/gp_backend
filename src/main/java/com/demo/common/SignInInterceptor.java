package com.demo.common;

import java.util.HashMap;
import java.util.logging.Logger;

import com.demo.common.model.Attendance;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class SignInInterceptor implements Interceptor{
	static Logger logger = Logger.getLogger("GlobalActionInterceptor");// 一般为当前的类名
	 
	public void intercept(Invocation inv) {
		HashMap<String, Object> res = new HashMap<>();
		Controller controller = inv.getController();
		String classId = controller.getPara("classId");
		Attendance atd = new Attendance();
		boolean IsAllow = atd.IsAllow(classId).getBoolean("allow");
		if (IsAllow) {
			inv.invoke();//开放签到 调用action
		}else{
			res.put("success", false);
			res.put("msg", "还未开放签到");
			controller.renderJson(res);
		}
	}

}
