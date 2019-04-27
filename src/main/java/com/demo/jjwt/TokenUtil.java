package com.demo.jjwt;

import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.jfinal.kit.Base64Kit;



public class TokenUtil {
	static Key KEY = new SecretKeySpec("lee".getBytes(),SignatureAlgorithm.HS512.getJcaName());
	
	public String generateToken(String id) {
		long currentTime = System.currentTimeMillis() + 30 * 60 * 1000;
		
		Map<String, Object> stringObjectMap = new HashMap<>();
		stringObjectMap.put("type", "1");
		String compactJws = Jwts.builder()
				.setHeader(stringObjectMap)
				.claim("id", id) 
				.signWith(SignatureAlgorithm.HS512, KEY)
				.setExpiration(new Date(currentTime))
				.compact();
		
//		String compactJws = Jwts.builder().setHeader(stringObjectMap)
//				.setPayload(payload).signWith(SignatureAlgorithm.HS512, KEY).compact();
//		System.out.println("jwt key:" + new String(KEY.getEncoded()));
//		System.out.println("jwt payload:" + payload);
//		System.out.println("jwt encoded:" + compactJws);
		return compactJws;
	}
	
	public Claims token(String token) {
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
		JwsHeader header = claimsJws.getHeader();
		Claims body = claimsJws.getBody();
		return body;
//		System.out.println("jwt header:" + header);
//		System.out.println("jwt body:" + body);
//		System.out.println("jwt body user-id:" + body.get("user_id", String.class));
	}
	
	public Claims tokenParse2(String token) {
//		JwsHeader header = claimsJws.getHeader();
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
			Claims body = claimsJws.getBody();
			return body;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	public String tokenParse(String token) {
//		JwsHeader header = claimsJws.getHeader();
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
			Claims body = claimsJws.getBody();
			return body.get("id", String.class);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
}
