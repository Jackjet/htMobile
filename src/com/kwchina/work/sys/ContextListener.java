package com.kwchina.work.sys;

import java.util.Calendar;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {    

	public ContextListener(){
		super();
    }

    private java.util.Timer timer = null;
    private java.util.Timer fixStateTimer = null;
    
    private java.util.Timer checkTimer = null;
    private java.util.Timer fixCheckTimer = null;
   
    public void contextInitialized(ServletContextEvent event){
        System.out.println("================定时器开启中...=================");
    	//起始时间
//        Calendar theTaskCalendar = Calendar.getInstance();
//        theTaskCalendar.add(Calendar.DAY_OF_YEAR, 0); //今日23点开始运行
//        theTaskCalendar.set(Calendar.HOUR_OF_DAY, 23);
//        theTaskCalendar.set(Calendar.MINUTE, 0);
//        theTaskCalendar.set(Calendar.SECOND, 0);
//
//        //初始化任务
////        CountRecordTask theTask = CountRecordTask.getCheckLicensidTask();
//        UpdateDirTask theTask = UpdateDirTask.getCheckLicensidTask();
//        theTask.setContext(event.getServletContext());
//       
//        //设置定时器，每隔30天运行一次
//        timer = new java.util.Timer(true);
//        //timer.schedule(theTask, theTaskCalendar.getTime(), 2 * 60 * 60 * 1000);
//        timer.schedule(theTask, theTaskCalendar.getTime(), 30 * 24 * 60 * 60 * 1000);//10秒后开始执行
//        System.out.println("================定时器开启成功！=================");
        
        
        
        //修复巡检巡更状态
        //起始时间
        Calendar fixStateTaskCalendar = Calendar.getInstance();
        fixStateTaskCalendar.add(Calendar.DAY_OF_YEAR, 0); //今日23点开始运行
        fixStateTaskCalendar.set(Calendar.HOUR_OF_DAY, 23);
        fixStateTaskCalendar.set(Calendar.MINUTE, 0);
        fixStateTaskCalendar.set(Calendar.SECOND, 0);

        //初始化任务
//        CountRecordTask theTask = CountRecordTask.getCheckLicensidTask();
        FixWorkStateTask fixStateTask = FixWorkStateTask.getCheckLicensidTask();
        fixStateTask.setContext(event.getServletContext());
       
        //设置定时器，每隔1小时运行一次
        fixStateTimer = new java.util.Timer(true);
        //timer.schedule(theTask, theTaskCalendar.getTime(), 2 * 60 * 60 * 1000);
        fixStateTimer.schedule(fixStateTask, 10 * 1000,  60 * 60 * 1000);//60秒后开始执行
        System.out.println("================定时器开启成功！=================");
    }

    public void contextDestroyed(ServletContextEvent event){
        timer.cancel();
        fixStateTimer.cancel();
        
        checkTimer.cancel();
        fixCheckTimer.cancel();
    }
} 


