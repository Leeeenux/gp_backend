package com.demo.common;

import com.demo.admin.ApiController;
import com.demo.admin.AtdController;
import com.demo.admin.ClassController;
import com.demo.admin.FaceController;
import com.demo.admin.LeaveController;
import com.demo.admin.NoticeController;
import com.demo.admin.PersonController;
import com.demo.admin.StudentController;
import com.demo.admin.TeacherController;
import com.demo.admin.UserController;
import com.demo.admin.WechatController;
import com.demo.blog.BlogController;
import com.demo.common.model._MappingKit;
import com.demo.index.IndexController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * API 引导式配置
 */
public class DemoConfig extends JFinalConfig {
	
	static Prop p;
	
	/**
	 * 启动入口，运行此 main 方法可以启动项目，此 main 方法可以放置在任意的 Class 类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		UndertowServer.start(DemoConfig.class);
	}
	
	/**
	 * 先加载开发环境配置，然后尝试加载生产环境配置，生产环境配置不存在时不会抛异常
	 * 在生产环境部署时后动创建 demo-config-pro.txt，添加的配置项可以覆盖掉
	 * demo-config-dev.txt 中的配置项
	 */
	static void loadConfig() {
		if (p == null) {
			p = PropKit.use("demo-config-dev.txt").appendIfExists("demo-config-pro.txt");
		}
	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		loadConfig();
		
		me.setDevMode(p.getBoolean("devMode", false));
		
		// 支持 Controller、Interceptor 之中使用 @Inject 注入业务层，并且自动实现 AOP
		me.setInjectDependency(true);
		me.setEncoding("UTF-8");
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class, "/index");	// 第三个参数为该Controller的视图存放路径
		me.add("/blog", BlogController.class);			// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
		me.add("/user", UserController.class);
		me.add("/api", ApiController.class);
		me.add("/class", ClassController.class);
		me.add("/face", FaceController.class);
		me.add("/atd", AtdController.class);
		me.add("/notice", NoticeController.class);
		me.add("/person", PersonController.class);
		me.add("/student", StudentController.class);
		me.add("/teacher", TeacherController.class);
		me.add("/leave", LeaveController.class);
		me.add("/wechat", WechatController.class);
	}
	
	public void configEngine(Engine me) {
		me.addSharedFunction("/common/_layout.html");
		me.addSharedFunction("/common/_paginate.html");
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim());
		me.add(druidPlugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		me.add(arp);
	}
	
	public static DruidPlugin createDruidPlugin() {
		loadConfig();
		
		return new DruidPlugin(p.get("jdbcUrl"), p.get("user"), p.get("password").trim());
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new GlobalActionInterceptor());
		me.add(new AuthInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
}
