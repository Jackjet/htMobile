package com.kwchina.core.base.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kwchina.core.base.dao.UserDAO;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.common.dao.BasicDaoImpl;

@Repository
public class UserDAOImpl extends BasicDaoImpl<User> implements UserDAO{

	//根据职级获取用户
	public List getUserByPosition(int positionTag) {
		String hql = "from SystemUserInfor user where user.invalidate = 0 and user.person.positionLayer <= " + positionTag + " order by user.person.positionLayer,user.person.personName";
		List userList = getResultByQueryString(hql);
		return userList;
	}
	
	//获取职级大于一定值的用户
	public List getOtherPositionUser(int positionTag) {
		String hql = "from SystemUserInfor user where user.invalidate = 0 and user.person.positionLayer > " + positionTag + " order by user.person.positionLayer,user.person.personName";
		List userList = getResultByQueryString(hql);
		return userList;
	}
	
	/**
	 * 根据userId得到user
	 */
	public User getUserByUserId(String userId){
		User user = new User();
		String sql = "from User user where  user.userId='" + userId + "'";//user.valid = 1 and
		List<User> list = getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			user = list.get(0);
		}
		
		return user;
	}
	
	@Override
	public Integer getNewUserId() {
		String sql = "select max(userId) from x_user order by userId desc";
		List list = this.getResultBySQLQuery(sql);
		
		return list != null ? (Integer.parseInt(list.get(0).toString()) + 1) : 1;
	}
	
}
