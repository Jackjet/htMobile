package com.kwchina.core.sys;

public class CoreConstant {
	
	//用于分割字符串的符号
	public static final String SPLIT_SIGN = ";";
	//编码形式
	public static final String ENCODING = "utf-8";
	//字符编码
	public static final String CONTENT_TYPE = "text/html;charset=" + ENCODING;
	
	//OrganizeInfor层级定义
	public static int Core_Department_Level_parent = 0;	//部门
	public static int Core_Department_Level_Department = 1;	//部门
	public static int Core_Department_Level_donglei = 2;
	public static int Core_Department_Level_duichang= 3;

	
	//查询排序变量
	public static String Order_By_Asc = "ASC";
	public static String Order_By_Desc = "DESC";
	
	//组织结构,岗位,系统资源,资讯分类默认开始编号
	public static int Structure_Begin_Id = 1;
	public static int Department_Begin_Id = 1;
	public static int Resource_Begin_Id = 1;
	public static int InforCategory_Begin_Id = 1;
	
	//系统ContextPath
	public static String Context_Name = "";  //如"/oa"
	
	//context real path
	public static String Context_Real_Path = "";
	
	//附件存放路径
	public static String Attachment_Path = "upload/";
	
	/** 角色定义-角色：
	 *  */
	public static int Role_Flow_Checker = 24;  //所有流程的主办人
	public static int Role_Enterprise_Editor = 25; //投资企业修改人员
	
	public static int Role_Enterprise_Catalog_View = 28;  //投资企业信息目录查看人员
	public static int Role_Enterprise_Catalog_Edit_SGYL = 29;  //投资企业信息目录编辑人员--上港邮轮
	public static int Role_Enterprise_Catalog_Edit_MSC = 30;  //投资企业信息目录编辑人员--地中海
	public static int Role_Enterprise_Catalog_Edit_SGZM = 31;  //投资企业信息目录编辑人员--上港中免
	public static int Role_Enterprise_Catalog_Edit_ZCGS = 32;  //投资企业信息目录编辑人员--资产公司
	public static int Role_Enterprise_Catalog_Edit_ZYHG = 34;  //投资企业信息目录编辑人员--中意海歌
	public static int Role_Enterprise_Catalog_Edit_YDS = 37;  //投资企业信息目录编辑人员--尚九一滴水
	
	/*********合同管理部门所需角色*********/
	public static int Role_Contract_ContractAdmin = 38;//公司合同管理员
	public static int Role_Contract_DepAdmin = 3;//部门合同管理员
	public static int Role_Contract_Finance = 23;//财务部门操作员
	public static int Role_Contract_DepManager = 26;//部门经理
	public static int Role_Contract_Leader = 39;//公司领导
}
