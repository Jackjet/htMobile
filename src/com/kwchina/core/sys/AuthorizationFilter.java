package com.kwchina.core.sys;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kwchina.core.base.entity.User;
import com.kwchina.work.sys.SysCommonMethod;

public class AuthorizationFilter extends HttpServlet implements Filter {

	private FilterConfig filterConfig;



	Logger logger = Logger.getLogger(Log4jHandlerAOP.class);
	 public static String getRequestURL(HttpServletRequest request) { 
    	if (request == null) { 
    		return ""; 
    	} 
    	String url = ""; 
    	url = request.getContextPath(); 
    	url = url + request.getServletPath(); 

    	java.util.Enumeration names = request.getParameterNames(); 
    	int i = 0; 
//    	if (!"".equals(request.getQueryString()) || request.getQueryString() != null) { 
//    		url = url + "?" + request.getQueryString(); 
//    	} 

    	if (names != null) { 
	    	while (names.hasMoreElements()) { 
		    	String name = (String) names.nextElement(); 
		    	if (i == 0) { 
		    		url = url + "?"; 
		    	} else { 
		    		url = url + "&"; 
		    	} 
		    	i++; 
		
		    	String value = request.getParameter(name); 
		    	if (value == null) { 
		    		value = ""; 
		    	} 
		
		    	url = url + name + "=" + value; 
		    	try { 
		    		// java.net.URLEncoder.encode(url, "ISO-8859"); 
		    	} catch (Exception e) { 
		    		e.printStackTrace(); 
		    	} 
	    	} 
    	} 
    	try { 
    	// String enUrl = java.net.URLEncoder.encode(url, "utf-8"); 
    	} catch (Exception ex) { 
    		ex.printStackTrace(); 
    	} 

    	return url; 
    }  
	
	//初始化
	public void init(FilterConfig config) {
		this.filterConfig = config;

		ServletContext context = filterConfig.getServletContext();
		WebApplicationContext webContext = WebApplicationContextUtils.getWebApplicationContext(context);
//		this.resourceManager = (VirtualResourceManager) webContext.getBean("virtualResourceManagerImpl");
	}

	//过滤
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String contextPath = httpRequest.getContextPath();

		
		
		try {
			httpRequest.setCharacterEncoding(CoreConstant.ENCODING);
			String pathURI = httpRequest.getRequestURI();
			pathURI = pathURI.replaceAll(contextPath, "");
			User systemUser = SysCommonMethod.getSystemUser(httpRequest);

			boolean fromMobile = false;
			String source = request.getParameter("source");
			if(source != null && !source.equals("")){
				fromMobile = true;
			}
			
			if (systemUser == null && !fromMobile) {
				//用户若没有登陆,则返回到登陆页面
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendRedirect(contextPath + "/login.htm");

			}else{
				
				
				//用户已经登陆,则需要判断所访问的资源是否有访问设置
				if (pathURI.indexOf("login.jsp") >= 0 || pathURI.indexOf("images") >= 0 || pathURI.indexOf("js") >= 0 
						|| pathURI.indexOf("css") >= 0 || pathURI.indexOf("templates") >= 0 || pathURI.indexOf("inc") >= 0) {
					filterChain.doFilter(request, response);
				}else{
					

					//logger.info("用户【"+systemUser.getUserName()+"】 执行操作："+getRequestURL(httpRequest));
					
					filterChain.doFilter(request, response);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			filterConfig.getServletContext().log(ex.getMessage());
		}

	}

	

	//销毁
	public void destroy() {
		
	}

}
