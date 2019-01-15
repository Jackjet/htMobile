package com.kwchina.work.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kwchina.core.util.DateHelper;
import com.kwchina.core.util.string.StringUtil;

/**
 * 生成令牌工具类
 * 
 * @description 描述： 用于生成进行身份认证的唯一令牌，存入到指定时间的对象中。<br/>
 * @author 
 * @version 1.0
 * @since jdk1.6+
 */
public class TokenUtil {
	private static String timeFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 生成令牌方法
	 * 
	 * @return 返回令牌字符串
	 */
	public static String buildToken() {
		String token = UUID.randomUUID().toString();
		return token.replace("-", "").toUpperCase();
	}

	/**
	 * MD5加密
	 * 
	 * @param sourceString
	 * @return
	 */
	public static String encodeByMD5(String sourceString) throws Exception {
		if (StringUtil.isNotEmpty(sourceString)) {
//			MessageDigest md5 = MessageDigest.getInstance("MD5");
//			BASE64Encoder base64en = new BASE64Encoder();
//
//			// 加密后的字符串
//			String newstr = base64en.encode(md5.digest(sourceString.getBytes("utf-8")));
			
			String newstr = Encpy.md5(sourceString);

			return newstr;
		}else {
			return "";
		}

	}

	/**
	 * 获取Token令牌，Map结构的数据，包含未加密的令牌、加密后的令牌、创建令牌时间
	 * 
	 * @return 返回生成的令牌数据
	 */
	public static Map<String, String> getToken() throws Exception{
		Map<String, String> tokenMap = new HashMap<String, String>(0);

		String token = buildToken();
		tokenMap.put("srcToken", token);
		// tokenMap.put("encryptToken", MD5.MD5Encode(token).toUpperCase());
		// tokenMap.put("dateString", DateOperationUtil.dateToString(new Date(),
		// timeFormat));
		tokenMap.put("encryptToken", encodeByMD5(token).toUpperCase());
		tokenMap.put("dateString", DateHelper.dateToString(new Date(), timeFormat));

		return tokenMap;
	}

	/**
	 * 验证Token令牌数据是否有效
	 * 
	 * @param request 请求对象
	 * @param tokenKey 用户登录名，作为令牌的Key
	 * @param inSrcToken 用户请求时的令牌字符串
	 * @param inEncrytpToken 用户请求时的加密令牌字符串
	 * @return 返回true或false
	 */
	@SuppressWarnings("unchecked")
	public static boolean validateToken(HttpServletRequest request,
			String tokenKey, String inSrcToken, String inEncrytpToken)throws Exception {
		HttpSession session = request.getSession();

		Map<String, String> tokenMap = new HashMap<String, String>(0);

		if (session.getAttribute(tokenKey) != null) {
			tokenMap = (Map<String, String>) session.getAttribute(tokenKey);
		} else {
			return false;
		}

		if (tokenMap.isEmpty()) {
			return false;
		}

		String srcToken = tokenMap.get("srcToken"); // 未加密的Token
		String encryptToken = tokenMap.get("encryptToken"); // 加密后的Token

		if (srcToken == null || "".equals(srcToken.trim())) {
			return false;
		}

		if (encryptToken == null || "".equals(encryptToken.trim())) {
			return false;
		}

		if (srcToken.equals(inSrcToken) && encryptToken.equals(inEncrytpToken)) {
			// long minutes =
			// DateOperationUtil.getTimeDifference(tokenMap.get("dateString"));
			SimpleDateFormat sf = new SimpleDateFormat(timeFormat);
			Date lastDate = sf.parse(tokenMap.get("dateString"));
			int hours = DateHelper.hoursBetween(lastDate, new Date());

			// if (minutes > WebConfig.MAX_TOKEN_TIME) {
			if (hours > 2) {
				return false;
			} else {
				return true;
			}
		}

		return false;
	}

}