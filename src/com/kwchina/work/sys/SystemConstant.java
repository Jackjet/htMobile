package com.kwchina.work.sys;

import org.apache.struts.util.MessageResources;



public class SystemConstant {

	//message存储文件
	public static MessageResources MESSAGE = MessageResources.getMessageResources("messages_base");
	
	//系统用户session变量
	public static String Session_SystemUser = "_GLOBAL_USER";
	//APP平台session变量
	public static String Session_Platform = "_PLATFORM";
		
	//系统访问后缀
	public static String FILEPREFIX = ".htm";		
	
	//保存呈报文的路径
	public static String Submit_Path = "/uploadfiles/submit/";
	//保存文档大全文档的路径
	public static String Document_Path = "/uploadfiles/document/";
	//保存会议信息附件的路径
	public static String Meet_path = "/uploadfiles/meet/";
	//保存日常工作安排附件的路径
	public static String ScheduleJob_path = "/uploadfiles/scheduleJob/";
	//保存工作日志附件的路径
	public static String ScheduleJobLog_path = "/uploadfiles/scheduleJobLog/";
	//保存讯息附件的路径
	public static String Message_path = "/uploadfiles/message/";
	
	//保存用户论坛头像的路径
	public static String Photo_path = "/upload/photo/";
	//保存论坛图片附件的路径
	public static String Img_path = "/upload/img/";
	//保存论坛附件路径
	public static String Attch_path = "/upload/bbsAttch/";
	
	
	public static String Img_folder = "img";
	
	//用户类型
	public static int _User_Type_Admin = 1;
	
	//论坛版主
	public static int _User_Type_Admin_BBS = 1;
	
	//信息权限
	public static int _Right_View = 1;
	public static int _Right_Edit = 2;
	public static int _Right_Delete = 3;
	
	//车辆管理员
//	public static int Person_Control = 154;
	public final static int _Role_Department_Manager = 20; 		//部门经理以上人员
	//会议审核
	public static int Person_Meet = 1;
	
	
}
	