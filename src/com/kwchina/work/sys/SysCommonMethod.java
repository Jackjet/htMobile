package com.kwchina.work.sys;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;

import com.kwchina.core.base.entity.User;

public class SysCommonMethod {

	public static User getSystemUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sessionUser = SystemConstant.Session_SystemUser;
		User systemUser = (User) session.getAttribute(sessionUser);

		return systemUser;
	}
	
	public static String getPlatform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sessionPlatform = SystemConstant.Session_Platform;
		String platform = (String) session.getAttribute(sessionPlatform);
//		List<String> platformList = (List<String>)session.getAttribute(sessionPlatform);
//		String platform = "";
//		if(platformList != null && platformList.size() > 0){
//			platform = platformList.get(0);
//		}

		return platform;
	}
	
	
	public static void getSortedCollection(List objects,String compareProperty,boolean reversed){
		 Comparator mycmp = ComparableComparator.getInstance();       
	     mycmp = ComparatorUtils.nullLowComparator(mycmp);  //允许null       
	     //逆序
	     if(reversed)
	    	 mycmp = ComparatorUtils.reversedComparator(mycmp);        
	     Comparator cmp = new BeanComparator(compareProperty, mycmp);    
	     Collections.sort(objects, cmp); 	  
	}
}