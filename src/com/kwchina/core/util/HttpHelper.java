package com.kwchina.core.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class HttpHelper {
	
	public static void output(HttpServletResponse response, String result) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(result);
			response.flushBuffer();
		} catch (IOException e) {
		}
	}
	
	public static void outputJackson(HttpServletResponse response, String result) {
		try {
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(result);
			response.flushBuffer();
		} catch (IOException e) {
		}
	}
	
//	public static void output(HttpServletResponse response, ErrorDTO error) {
//		try {
//			response.setContentType("text/html;charset=utf-8");
//			response.getWriter().print(JacksonHelper.getJson(error));
//			response.flushBuffer();
//		} catch (IOException e) {
//		}
//	}
}
