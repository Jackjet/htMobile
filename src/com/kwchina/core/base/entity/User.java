package com.kwchina.core.base.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

@Entity
@Table(name = "x_core_user")
@ObjectId(id="xId")
public class User implements JSONNotAware , Serializable {
	public static int _Type_Admin = 1;
	public static int _Type_Normal = 0;

	private Integer xId;          //主键
	private Integer userId;        //用来被关联
	private int userType;         //用户类型   1-5 -- 普通执行人员，   6-10 -- 部门管理人员，10以上的为领导层，99为系统管理员
	private String userName;      //登录账号
	private String workNo;        //人员编号 
	private String password;      //登录密码
	private String name;          //姓名
	private String nickName;      //昵称
	private String mobile;        //手机 
	private String email;         //邮箱
	private String tel;           //固定电话 
	private int sex;
	private String address;       //地址 
	private Date birthday;        //生日
	private String avatar;        //头像
	private String extra;         //其他扩展属性
	private String memo;          //备注
	private boolean valid;        //是否  0-无效  1-有效
	private String parentId;      //直属领导
	private boolean reformFinder; //上报隐患权限
	private Integer departmentId;  //所在部门
	private boolean docDelete;
	private boolean docAdd;
	private boolean webLogin;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getxId() {
		return xId;
	}
	public void setxId(Integer xId) {
		this.xId = xId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	
	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getWorkNo() {
		return workNo;
	}
	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}
	
	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@JsonIgnore
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public boolean isReformFinder() {
		return reformFinder;
	}

	public void setReformFinder(boolean reformFinder) {
		this.reformFinder = reformFinder;
	}

	public boolean isDocDelete() {
		return docDelete;
	}

	public void setDocDelete(boolean docDelete) {
		this.docDelete = docDelete;
	}

	public boolean isDocAdd() {
		return docAdd;
	}

	public void setDocAdd(boolean docAdd) {
		this.docAdd = docAdd;
	}

	public boolean isWebLogin() {
		return webLogin;
	}

	public void setWebLogin(boolean webLogin) {
		this.webLogin = webLogin;
	}
}


