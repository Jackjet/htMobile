package com.kwchina.core.base.vo;

import lombok.Data;

@Data
public class UserListVo{

	private Integer xId;          //主键
	private Integer userId;        //用来被关联
	private int userType;         //用户类型   0-普通  1-管理员
	private String userName;      //登录账号
	private String password;      //登录密码
	private String name;          //姓名
	private String nickName;      //昵称
	private String mobile;        //手机 
	private String email;         //邮箱
	private String tel;           //固定电话 ccc
	private int sex;              //性别  0-男 1-女
	private String address;       //地址 
	private String birthdayStr;   //生日
	private String departmentId;  //所在部门
	private String departmentName;//所在部门名称
	private boolean valid;        //是否  0-无效  1-有效
	private boolean docDelete;
	private boolean docAdd;
	private boolean webLogic;
	private boolean reformFinder;
}


