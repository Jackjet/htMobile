package com.kwchina.core.base.vo;

import com.kwchina.core.common.vo.BaseVo;




public class UserVo extends BaseVo {
	private Integer xId;          //主键
	private Integer userId;        //用来被关联
	private int userType;         //用户类型   0-普通  1-管理员
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
	private String birthdayStr;   //生日
	private String avatar;        //头像
	private String extra;         //其他扩展属性
	private String memo;          //备注
	private Integer parentId;      //直属领导
	private Integer departmentId;  //所在部门
	private String departmentName;//所在部门名称
	private boolean valid;        //是否  0-无效  1-有效
	private boolean docDelete;
	private boolean docAdd;
	private boolean webLogin;
	private boolean reformFinder;


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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}


	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

//	public boolean isZkRight() {
//		return zkRight;
//	}
//
//	public void setZkRight(boolean zkRight) {
//		this.zkRight = zkRight;
//	}


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

}


