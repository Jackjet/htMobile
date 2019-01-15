package com.kwchina.core.base.dao;

import java.util.List;

import com.kwchina.core.base.entity.User;
import com.kwchina.core.common.dao.BasicDao;

public interface UserDAO extends BasicDao<User> {
	
	//根据职级获取用户
	public List getUserByPosition(int positionTag);
	
	//获取职级大于一定值的用户
	public List getOtherPositionUser(int positionTag);
	
	public User getUserByUserId (String userId);
	
	public Integer getNewUserId ();
}
