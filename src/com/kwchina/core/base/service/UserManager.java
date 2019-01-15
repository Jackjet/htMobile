package com.kwchina.core.base.service;

import java.util.List;

import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.vo.UserVo;
import com.kwchina.core.common.service.BasicManager;

public interface UserManager extends BasicManager{
	public void saveUser(UserVo vo);
	
	public User getUser(Integer xId);
	
	//获取全部未注销用户
	public List<User> getAllValid();
	
	public User getUserByUserId (Integer userId);
	
	public UserVo transferToVo (User user);
	
	public String getNewUserId ();
	
	public List<User> getUsersByDepartment(String departmentId);
	
	public User getUserByUserName(String userName);
	
	 //通过用户名和密码
    public User findUser(String userName, String password);
	//用户登录时验证
	public User checkUser(String userName, String password) throws Exception;
}
