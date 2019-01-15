package com.kwchina.core.base.controller;

import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.base.vo.UserVo;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.common.page.PageList;
import com.kwchina.core.common.page.Pages;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.Encpy;
import com.kwchina.core.util.HttpHelper;
import com.kwchina.core.util.json.JSONConvert;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.sys.LoginConfirm;
import com.kwchina.work.sys.SysCommonMethod;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/core/user.htm")
public class UserController extends BasicController {
	@Resource
	private UserManager userManager;
	
	
	@Resource
	private DepartmentManager departmentManager;
	
	/**
	 * 分页显示所有系统用户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="method=listUsers")
	public void listUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//构造查询语句
		
		String[] queryString = new String[2];
		queryString[0] = "from User user where 1=1 and valid = 1 ";
		queryString[1] = "select count(xId) from User user where 1=1 and valid = 1 ";

		queryString = this.userManager.generateQueryString(queryString, getSearchParams(request));
		
		String page = request.getParameter("page");
		String rowsNum = request.getParameter("rows");
		Pages pages = new Pages(request);
		pages.setPage(Integer.valueOf(page));
		pages.setPerPageNum(Integer.valueOf(rowsNum));
		
		PageList pl = this.userManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
		List list = pl.getObjectList();
		List voList=new ArrayList();
		for(Iterator it=list.iterator();it.hasNext();){
			User user= (User) it.next();
			voList.add(userManager.transferToVo(user));
		}
		
		//定义返回的数据类型：json，使用了json-lib
        JSONObject jsonObj = new JSONObject();
                  
        //定义rows，存放数据
        JSONArray rows = new JSONArray();
        jsonObj.put("page", pl.getPages().getCurrPage());   //当前页(名称必须为page)
        jsonObj.put("total", pl.getPages().getTotalPage()); //总页数(名称必须为total)
        jsonObj.put("records", pl.getPages().getTotals());	//总记录数(名称必须为records)        
        
		JSONConvert convert = new JSONConvert();
		//通知Convert，哪些关联对象需要获取
		List awareObject = new ArrayList();
		rows = convert.modelCollect2JSONArray(voList, awareObject);
		jsonObj.put("rows", rows);							//返回到前台每页显示的数据(名称必须为rows)
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);

	}
	
	
	/**
	 * 得到有效用户		
	 * @param request
	 * @param response
	 * @param vo
	 * @throws Exception
	 */
	@RequestMapping(params="method=getUsers")
	@ResponseBody
	public void getUsers(HttpServletRequest request, HttpServletResponse response,UserVo vo) throws Exception{
		JSONObject jsonObj = new JSONObject();
		JSONConvert convert = new JSONConvert();
		List<User> users = new ArrayList<User>();
		
		try {
			
			Integer departmentId = vo.getDepartmentId();
			if(departmentId!=null&&departmentId>0){
				String hql = "from User user where user.valid = 1 and user.departmentId = '" + departmentId + "'";
				users = this.userManager.getResultByQueryString(hql);
			}else {
				users = this.userManager.getAllValid();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jsonObj.put("_Users", users);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);		
	}
	
	
	/**
	 * 得到有效用户（app端通讯录）
	 * @param request
	 * @param response
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(params="method=getUsers_m")
	@ResponseBody
	public void getUsers_m(HttpServletRequest request, HttpServletResponse response) throws Exception{
		boolean success = false;
		String message = "操作成功";
		JSONObject jsonObj = new JSONObject();
		
		List<UserVo> vos = new ArrayList<UserVo>();
		
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		try {
			if(user != null){
			
//				List<User> users = this.userManager.getAllValid();
				//先根据排序号得到部门
				List<Department> depts = this.departmentManager.getDepartments();
				Comparator<?> mycmp = ComparableComparator.getInstance();
		        mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null
//		        if (!asc) {
//		            mycmp = ComparatorUtils.reversedComparator(mycmp); // 逆序
//		        }
		        Collections.sort(depts, new BeanComparator("orderNo", mycmp));
		        
		        List<User> users = new ArrayList<User>();
		        for(Department tmpDept : depts){
		        	users.addAll(this.userManager.getUsersByDepartment(String.valueOf(tmpDept.getDepartmentId())));
		        }
				
				if(users != null && users.size() > 0){
					for(User tmpUser : users){
						UserVo vo = new UserVo();
						vo = this.userManager.transferToVo(tmpUser);
						
						vos.add(vo);
					}
				}
				success = true;
			}else {
				success = false;
				message = "请登录！";
			}
		} catch (Exception e) {
			success = false;
			message = "后台出错，请重试！  ";
			e.printStackTrace();
		}
		
		//返回值
		jsonObj.put("success", success);
		jsonObj.put("message", message);
		jsonObj.put("users", vos);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);	
	}
	
	
	
	/**
	 * 编辑人员
	 * @param request
	 * @param response
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, UserVo vo, Model model) throws Exception {

		Integer xId = vo.getxId();
		User user;
		
		
		if (xId != null && xId.intValue() > 0) {
			//编辑
			user = this.userManager.getUser(xId);
			
			//属性从model到vo
			vo = this.userManager.transferToVo(user);	
			
		}
		model.addAttribute("user",vo);
		return "/user/editUser";
	}
	
	/**
	 * 保存人员
	 * @param request
	 * @param response
	 * @param vo
	 * @throws Exception
	 */
	@RequestMapping(params="method=save")
	public String save(HttpServletRequest request, HttpServletResponse response, UserVo vo) {
		try {
			User user;
			if(vo.getxId() != null && vo.getxId() > 0){
				user = this.userManager.getUser(vo.getxId().intValue());
				String oldPwd = user.getPassword();
				BeanUtils.copyProperties(vo, user);
				//设置密码，如果新增时为空，则默认123456，修改时为空，则不修改
				if(StringUtil.isNotEmpty(vo.getPassword())&&!vo.getPassword().equals(oldPwd)){
					user.setPassword(Encpy.md5(vo.getPassword()));
				}else {
					user.setPassword(oldPwd);
				}

			}else {
				user = new User();
				BeanUtils.copyProperties(vo,user);
				user.setPassword(Encpy.md5(vo.getPassword()));
				//设置密码，如果新增时为空，则默认123456，修改时为空，则不修改
				if(!StringUtil.isNotEmpty(vo.getPassword())){
					user.setPassword(Encpy.md5("123456"));
				}

				//设置userId
				Integer userId = this.userManager.getMaxId("userId");
				user.setUserId(userId);
			}
			//设置valid
			user.setValid(true);
			//设置生日
			if(vo.getBirthdayStr() != null && !vo.getBirthdayStr().equals("")){
				user.setBirthday(Date.valueOf(vo.getBirthdayStr()));
			}
			this.userManager.save(user);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	//删除人员信息
	@RequestMapping(params="method=delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String rowIds = request.getParameter("rowIds");
		if (rowIds != null && rowIds.length() > 0) {
			
			String[] detleteIds = rowIds.split(",");
			if (detleteIds.length > 0) {
				for (int i=0;i<detleteIds.length;i++) {
					Integer xId = Integer.valueOf(detleteIds[i]);
					User tmpUser = this.userManager.getUser(xId);
					tmpUser.setValid(true);
					this.userManager.save(tmpUser);
				}
			}
		}
		
	}
	
	
	
	/**
	 * 重置密码
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=resetPwd")
	public void resetPwd(HttpServletRequest request,HttpServletResponse response,UserVo vo) {
		int msg = 1;
		try {
			User user;
			
			if(vo.getxId() != null && vo.getxId() > 0){
				user = this.userManager.getUser(vo.getxId().intValue());
				
				//重置密码为123456
				user.setPassword(Encpy.md5("123456"));
				
				this.userManager.save(user);
			}else {
				msg = 0;
			}
			
		} catch (Exception e) {
			msg = 0;
			e.printStackTrace();
		}
		
		HttpHelper.output(response, String.valueOf(msg));
	}
	
	/**
	 * 修改保存密码
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=editPwd")
	public void editPwd(HttpServletRequest request,HttpServletResponse response,UserVo vo) {
		int msg = 1;
		String newPwd = request.getParameter("newPwd");
		try {
			User user = SysCommonMethod.getSystemUser(request);
			
			if(user != null){
				
				//保存新密码
				user.setPassword(Encpy.md5(newPwd));
				
				this.userManager.save(user);
			}else {
				msg = 0;
			}
			
		} catch (Exception e) {
			msg = 0;
			e.printStackTrace();
		}
		
		HttpHelper.output(response, String.valueOf(msg));
	}
	
	/**
	 * app端修改密码
	 * @param request
	 * @param response
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(params="method=changePwd")
	public void changePwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean success = false;
		String message = "操作成功";
		JSONObject jsonObj = new JSONObject();
		
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		
		try {
			if(user != null){
				if(!StringUtil.isNotEmpty(oldPwd)){
					success = false;
					message = "旧密码不匹配！";
				}else{
					if(Encpy.md5(oldPwd).equals(user.getPassword())){
						if(StringUtil.isNotEmpty(newPwd)){
							String newPwdMd5 = Encpy.md5(newPwd);
							user.setPassword(newPwdMd5);
							this.userManager.save(user);
							success = true;
						}else{
							success = false;
							message = "新密码不能为空！ ";
						}
					}else{
						success = false;
						message = "旧密码不匹配！";
					}
				}
				
			}else {
				success = false;
				message = "请登录！";
			}
		} catch (Exception e) {
			success = false;
			message = "后台出错，请重试！  ";
			e.printStackTrace();
		}
		
		//返回值
		jsonObj.put("success", success);
		jsonObj.put("message", message);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);	
        
	}   
	
	
	/**
	 * app端上传头像
	 * @param request
	 * @param response
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(params="method=uploadAvatar")
	public void uploadAvatar(HttpServletRequest request, HttpServletResponse response, DefaultMultipartHttpServletRequest multipartRequest) throws Exception {
		boolean success = false;
		String message = "操作成功";
		JSONObject jsonObj = new JSONObject();
		
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		try {
			if(user != null){
			
				String avatar = this.uploadAttachment(multipartRequest, "avatar");
				user.setAvatar(avatar);
				this.userManager.save(user);
				
				success = true;
			}else {
				success = false;
				message = "请登录！";
			}
		} catch (Exception e) {
			success = false;
			message = "后台出错，请重试！  ";
			e.printStackTrace();
		}
		
		//返回值
		jsonObj.put("success", success);
		jsonObj.put("message", message);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);	
        
	}

	/**注销用户******/
	@RequestMapping(params="method=logout")
	public void logout(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("rowId");
		if(StringUtil.isNotEmpty(id)){
			User user=this.userManager.getUser(Integer.parseInt(id));
			user.setValid(false);
			log.info(new Timestamp((System.currentTimeMillis()))+": 用户【"+user.getName()+"】被注销");
			this.userManager.save(user);
		}
	}
}
