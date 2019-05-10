package com.demo.admin;

import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.bouncycastle.asn1.eac.Flags;

import com.alibaba.fastjson.JSONObject;
import com.demo.common.AuthInterceptor;
import com.demo.common.SignInInterceptor;
import com.demo.common.model.Attendance;
import com.demo.util.LocationUtil;
import com.demo.util.VerifyFace;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
@Clear(AuthInterceptor.class)
public class FaceController extends Controller {
	private JSONObject verify(String base64,String studentId) {//人脸识别接口
		VerifyFace verifyface = new VerifyFace();
		JSONObject res = verifyface.verify(base64,studentId);
		return res;
	}
	public void create() {//人脸录入接口
		String base64 = getPara("base64");
		String studentId = getPara("studentid");
		String groupId = getPara("groupid");
		String gender = getPara("gender");
		String personName = getPara("name");
		System.out.println(studentId);
		VerifyFace verifyface = new VerifyFace();
		String res = verifyface.createPerson(groupId, personName, studentId, base64, gender);
		renderJson(res);
	}
	
	@Before(SignInInterceptor.class)
	public void signin() {
		String base64 = getPara("base64");
		String studentId = getPara("studentId");
		String latitude = getPara("latitude");
		String longitude = getPara("longitude");
		HashMap<String, Object> res = new HashMap<>();
		Point2D StudentPoint = new Point2D.Double(Double.parseDouble(longitude),Double.parseDouble(latitude));
		System.out.println(StudentPoint);
		Point2D ClassPoint = new Point2D.Double(117.51759338378906,23.73352813720703);
		LocationUtil locationUtil = new LocationUtil();
		Double distance = locationUtil.getDistance(StudentPoint, ClassPoint);
		if (distance>20000) {
			res.put("success", false);
			res.put("msg", "请在教室里签到");
			renderJson(res);
		}else {
			JSONObject verifyResult = verify(base64, studentId);
			boolean IsMatch = (boolean) verifyResult.get("IsMatch");
			if (verifyResult.get("RequestId")!=null) {//有识别到人脸
				if (IsMatch) {
					if (sign(studentId)) {//签到成功
						res.put("success", true);
						res.put("score",verifyResult.get("Score"));
						res.put("msg", "人脸匹配成功");
					}else {
						res.put("success", false);
						res.put("score",verifyResult.get("Score"));
						res.put("msg", "未知错误请重试");
					}
					
				}else {
					res.put("success", false);
					res.put("score",verifyResult.get("Score"));
					res.put("msg", "人脸匹配失败");
				}
			}else {//没有识别到人脸
				res.put("success", false);
				res.put("score",-1);
				res.put("msg", "没有识别到人脸");
			}
			renderJson(res);
		}
	}
	private boolean sign(String studentId) {
		Attendance atd = new Attendance();
		String LastAtdId = atd.findLastAtendanceId(studentId).getStr("attendanceId");//获取考勤id
		System.out.println(atd.IsAllow("101").getBoolean("allow"));
		HashMap<String, Object> params = new HashMap<>();
		Date currentTime = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String current = sdf.format(currentTime);
		params.put("attendanceId", LastAtdId);
		params.put("signInStatus", "准点");
		params.put("signInTime", current);
		atd._setAttrs(params);
		return atd.update();
	}
}
