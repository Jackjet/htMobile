package com.kwchina.work.util;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kwchina.work.sys.LoginConfirm;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.UserManager;

@SuppressWarnings("serial")
public class AutoTokenServlet extends HttpServlet {
 	public void init() throws ServletException {
// 		ServletContext context = request.getSession().getServletContext();	
 		ServletContext context = this.getServletContext();
    	System.out.println("================================");
    	//存放“用户名：token”键值对
    	LoginConfirm.tokenMap.put("test", "test");

    	//存放“token:User”键值对
    	User user = new User();
    	
    	WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
		UserManager userManager = (UserManager)applicationContext.getBean("userManager");
    	user = userManager.getUserByUserName("test");
    	
    	LoginConfirm.loginUserMap.put("test", user);
    }
    
    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    
    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }

    
    //Clean up resources
    public void destroy() {
    }

}
