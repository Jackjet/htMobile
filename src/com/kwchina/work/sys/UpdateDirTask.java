package com.kwchina.work.sys;

import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kwchina.work.security.util.SecurityDocUtil;


public class UpdateDirTask extends TimerTask { 
		 
	private UpdateDirTask(){}
	   
	private static UpdateDirTask _checkTask = new UpdateDirTask();
	public static UpdateDirTask getCheckLicensidTask(){
		return _checkTask;
	}
	   
	private boolean _isRunning  = false;
	private ServletContext context = null;
	
	public void setContext(ServletContext context){
		this.context = context;
	}

	public void run(){
		
		try{
		//if (!this._isRunning){
			//this._isRunning = true;
		
		    //从容器中得到注入的bean 
//			WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
//			DailyItemsCountInforManager dailyItemsCountInforManager = (DailyItemsCountInforManager)applicationContext.getBean("dailyItemsCountInforManager");
			
			SecurityDocUtil.updateDirs();
			
//			this._isRunning = false;
		//}else{
		//	context.log("上一次任务执行还未结束 ");
		//	this._isRunning = false;
		//}
		}catch(Exception e){
			//this._isRunning = false;
			e.printStackTrace();
		}

	}
	
	   
	   
	public void destroy(){
		//Just puts "destroy " string in log
		//Put your code here
	}

}