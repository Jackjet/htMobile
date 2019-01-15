package com.kwchina.core.base.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.base.dao.DepartmentDAO;
import com.kwchina.core.base.dao.UserDAO;
import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.base.vo.UserVo;
import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.core.util.DateHelper;
import com.kwchina.core.util.Encpy;
import com.kwchina.core.util.string.StringUtil;

@Service("userManager")
public class UserManagerImpl extends BasicManagerImpl<User> implements UserManager{

	private UserDAO userDAO;

	private DepartmentDAO departmentDAO;

	@Autowired
	private DepartmentManager departmentManager;


	@Autowired
	public void setSystemUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
		super.setDao(userDAO);
	}

	@Autowired
	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
		super.setDao(departmentDAO);
	}

	@Override
	public User getUser(Integer xId){
		return this.userDAO.get(xId);
	}


	/**
	 * 用户登录时验证
	 */
	@Override
	public User checkUser(String userName, String password)
			throws Exception {

		//判断用户名
		User user = getUserByUserName(userName);
		if (user == null) {
			throw new ServiceException("该用户名不存在!");
		}

		//判断用户是否注销
		if (!user.isValid()) {
			throw new ServiceException("该用户已注销!");
		}

		//判断用户名,密码
		if (!user.getPassword().equals(Encpy.md5(password))) {
			throw new ServiceException("密码不正确!");
		}

		return this.findUser(userName, Encpy.md5(password));
	}

	//通过用户名和密码
	@Override
	public User findUser(String userName, String password){

		String sql = "from User user where user.userName = '" + userName + "' and user.password = '" + password + "'";
		List list = this.userDAO.getResultByQueryString(sql);

		if (list != null && list.size() > 0 && list.get(0) != null) {
			return (User) list.get(0);
		} else {
			return null;
		}
	}
	/**
	 * 根据userId得到user
	 */
	@Override
	public User getUserByUserId(Integer userId){
		User user = new User();
		String sql = "from User user where  user.userId='" + userId + "'";//user.valid = 1 and
		List<User> list = this.userDAO.getResultByQueryString(sql);

		if(list != null && list.size() > 0){
			user = list.get(0);
		}

		return user;
	}

	/**
	 * 获取全部未注销用户
	 */
	@Override
	public List<User> getAllValid(){

		String sql = "from User user where user.valid = 1  order by user.name";
		List list = this.userDAO.getResultByQueryString(sql);

		return list;
	}


	/**
	 * 根据部门得到其下的所有有效用户
	 */
	@Override
	public List<User> getUsersByDepartment(String departmentId){
		List<User> users = new ArrayList<User>();
		String sql = "from User user where user.valid = 1 and user.departmentId = '" + departmentId + "' order by user.name";
		users = this.userDAO.getResultByQueryString(sql);

		return users;
	}

	/**
	 * 根据用户名得到用户
	 */
	@Override
	public User getUserByUserName(String userName){
		User user = new User();
		String sql = "from User user where  user.userName='" + userName + "'";//user.valid = 1 and
		List<User> list = this.userDAO.getResultByQueryString(sql);

		if(list != null && list.size() > 0){
			user = list.get(0);
		}

		return user;
	}


	/**
	 * 保存user
	 */
	@Override
	public void saveUser(UserVo vo){
		User user;
		try {
			if (vo.getCommandType().equals("Add")) {
//				int departmentId = vo.getDepartmentId().intValue();
//				String departmentId = vo.getDepartmentId();

				if(vo.getxId() != null && vo.getxId() > 0){
					user = this.userDAO.get(vo.getxId().intValue());
				}else {
					user = new User();
				}

				BeanUtils.copyProperties(vo, user);

				//设置部门
//				if(departmentId > 0){
//					Department department = this.departmentDAO.get(departmentId);
//					user.setDepartment(department);
//				}

				//设置valid
				user.setValid(true);

				//设置生日
				if(vo.getBirthdayStr() != null && !vo.getBirthdayStr().equals("")){
					user.setBirthday(Date.valueOf(vo.getBirthdayStr()));
				}

				//上传头像
//				String attachment = this.uploadAttachment(multipartRequest, "person");

				this.userDAO.save(user);
			} else if (vo.getCommandType().equals("Delete")) {  //注销
				if (null != vo.getIds() && !vo.getIds().equals("")) {
					String[] ids = vo.getIds().split(",");
					for(String xId : ids){
						User tmpUser = this.userDAO.get(Integer.valueOf(xId));
						tmpUser.setValid(false);
						this.userDAO.save(tmpUser);
					}
				}
			}else if (vo.getCommandType().equals("Revalid")) {  //还原
				if (null != vo.getIds() && !vo.getIds().equals("")) {
					String[] ids = vo.getIds().split(",");
					for(String xId : ids){
						User tmpUser = this.userDAO.get(Integer.valueOf(xId));
						tmpUser.setValid(true);
						this.userDAO.save(tmpUser);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserVo transferToVo (User user){

		UserVo vo = new UserVo();
		try {
			BeanUtils.copyProperties(user, vo);
			Integer departmentId = user.getDepartmentId();
			if(departmentId != null && departmentId.intValue() > 0){
				Department department = this.departmentManager.getDepartmentByDeparmentId(departmentId);
				vo.setDepartmentName(department.getDepartmentName());
			}
			if(user.getBirthday() != null){
				vo.setBirthdayStr(DateHelper.getString(user.getBirthday()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vo;
	}

	/**
	 * 得到新的userId，即在现在最大的基础上+1
	 */
	@Override
	public String getNewUserId (){
		String userId = "";
//
//		String sql = "select max(userId) from x_user order by userId desc";
//		this.userDAO.getMaxId(idName)
		userId = String.valueOf(this.userDAO.getNewUserId());

		return userId;
	}


}
