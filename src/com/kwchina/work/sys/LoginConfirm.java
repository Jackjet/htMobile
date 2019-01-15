package com.kwchina.work.sys;

import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.base.vo.DepartmentVo;
import com.kwchina.core.base.vo.UserVo;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.patrol.entity.BigCategory;
import com.kwchina.work.patrol.entity.ItemDetail;
import com.kwchina.work.patrol.service.BigCategoryManager;
import com.kwchina.work.patrol.service.ItemDetailManager;
import com.kwchina.work.reform.entity.ChildCategory;
import com.kwchina.work.reform.entity.ParentCategory;
import com.kwchina.work.reform.entity.ReformArea;
import com.kwchina.work.reform.entity.ReformDetail;
import com.kwchina.work.reform.service.AreaManage;
import com.kwchina.work.reform.service.ChildCategoryManager;
import com.kwchina.work.reform.service.ParentCategoryManager;
import com.kwchina.work.reform.service.ReformManager;
import com.kwchina.work.security.entity.SecurityCategory;
import com.kwchina.work.security.entity.SecurityCost;
import com.kwchina.work.security.service.DocumentCategoryManager;
import com.kwchina.work.security.service.SecurityCategoryManager;
import com.kwchina.work.security.service.SecurityCostManager;
import com.kwchina.work.train.entity.TrainCategory;
import com.kwchina.work.train.service.TrainCategoryManager;
import com.kwchina.work.util.TokenUtil;
import net.sf.json.JSONObject;
import org.apache.struts.actions.DispatchAction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class LoginConfirm extends DispatchAction {

	@Resource
	private UserManager userManager;
	
	@Resource
	private DocumentCategoryManager documentCategoryManager;
	@Resource
	private ReformManager reformManager;
	
	@Resource
	private SecurityCategoryManager  securityCategoryManager;
	
	@Resource
	private ItemDetailManager itemDetailManager;
	
	@Resource
	private SecurityCostManager securityCostManager;
	
	@Resource
	private DepartmentManager departmentManager;
	
	@Resource
	private BigCategoryManager bigCategoryManager;
	
	@Resource
	private ChildCategoryManager childCategoryManager;
	
	@Resource
	private TrainCategoryManager trainCategoryManager;
	@Resource
	private AreaManage areaManage;
	@Resource
	private ParentCategoryManager parentCategoryManager;


	//存放“用户名：token”键值对
	public static Map<String, String> tokenMap = new HashMap<String, String>();

	//存放“token:User”键值对
	public static Map<String, User> loginUserMap = new HashMap<String, User>();

	
	public String loginToken(User user, String userName, String password) {
//			System.out.println(userName + "-----" + password);
		/**
		 * 
		 * 判断是否登录成功
		 * 
		 * 1.登录成功
		 * 
		 * 1.1.成功生成对应的token并更新
		 * 
		 * 1.2.失败就抛异常
		 */

		String token = tokenMap.get(userName);

		try {
//				UserInfor user = null;

			if (StringUtil.isNotEmpty(token)) {
//					user = loginUserMap.get(token);
				loginUserMap.remove(token);
				System.out.println("更新用户登录token");
			} else {
//					user = new UserInfor();
//					user.setUserName(userName);
//					user.setPassword(password);
				System.out.println("新用户登录");
			}
			
			if(userName.equals("test")){
				token = "test";
			}else {
				token = TokenUtil.encodeByMD5(userName + password + new Date().getTime());
			}

			loginUserMap.put(token, user);
			tokenMap.put(userName, token);

			System.out.println("目前有" + tokenMap.size() + "个用户");
			for (User u : loginUserMap.values()) {
				if(u != null){
					System.out.println(u.getUserName() + ":" + u.getPassword() + " -- token:" + tokenMap.get(u.getUserName()));
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	
	
	@RequestMapping("/login.htm")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HttpSession session = request.getSession();		
		User user = SysCommonMethod.getSystemUser(request);
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		if(user == null) {
			//如果是第一次登陆系统			
			if(userName != null && userName.length() > 0 || password != null && password.length() > 0){			
				try{
					user = userManager.checkUser(userName, password);
				}catch (ServiceException ex){
					request.setAttribute("_Action_ErrorMessage", ex.getMessage());
					return "sessionProcess";
				}				
			}
		}

		if(user == null || !user.isValid()) {
			session.setAttribute("_From_Login", "this");
			return "sessionProcess";
		}else{
			try{
				request.getSession().setAttribute(SystemConstant.Session_SystemUser, user);
				
//				String photoAttachment = user.getAvatar();
//				String photoLocation = "";
//				if(photoAttachment != null && !photoAttachment.equals("")){
//					int first = photoAttachment.indexOf("|");
//					int second = photoAttachment.lastIndexOf("|");
//					String uPath = photoAttachment.substring(0,first);
//					String fileName = photoAttachment.substring(first+1,second);
//					String size = photoAttachment.substring(second+1);
//					
//					photoLocation = uPath + "/" + fileName;
//				}
//				request.getSession().setAttribute("_PERSON_IMG",photoLocation);
				
				//得到左边菜单
//				List<DocumentCategory> allMenus = this.documentCategoryManager.getLayerCategorys(1);
//				request.getSession().setAttribute("_Menus", allMenus);
				
				List<SecurityCategory> allMenus = this.securityCategoryManager.getLayerCategorys(1);
				request.getSession().setAttribute("_Menus", allMenus);
				
				Date now = new Date();
				SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
				
				int thisYear = Integer.valueOf(sf_y.format(now));
				request.getSession().setAttribute("_ThisYear", thisYear);
                Calendar cal = Calendar.getInstance();
                int mon = cal.get(Calendar.MONTH) + 1;
                String month=mon+"月";
                request.getSession().setAttribute("_ThisMonth",month);
				
				//本年总数量
				String yearBegin = thisYear + "-01-01 00:00:00";
				String yearEnd = thisYear + "-12-31 23:59:59";// and detail.opResult=-1
				String errorHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and endTime >= '" + yearBegin + "' and endTime <= '" + yearEnd + "'";
				List<ItemDetail> errorList = this.itemDetailManager.getResultByQueryString(errorHql);
				request.getSession().setAttribute("_YearCount", errorList.size());
				
				//本年隐患数量
				String reformHql="from ReformDetail detail where detail.valid=1 and findTime >= '" + yearBegin + "' and findTime <= '" + yearEnd + "'";
				List<ReformDetail> reformDetails=this.reformManager.getResultByQueryString(reformHql);
				request.getSession().setAttribute("_YearErrorCount", reformDetails.size());
				
				//安全投入
				double thisYearCostCount = 0;
				String securityCostHql = " from SecurityCost where dataYear = " + thisYear;
				List<SecurityCost> thisYearCosts = this.securityCostManager.getResultByQueryString(securityCostHql);
				if(thisYearCosts != null && thisYearCosts.size() > 0){
					for(SecurityCost tmpCost : thisYearCosts){
						thisYearCostCount += tmpCost.getRealCount();
					}
				}
				DecimalFormat df = new DecimalFormat("#.0");
				request.getSession().setAttribute("_ThisYearCost", df.format(thisYearCostCount));
				
				//判断是否是安监部人员
				boolean isAjb = false;
				Department department = this.departmentManager.getDepartmentByDeparmentId(user.getDepartmentId());
				if(department != null && department.getxId().intValue() > 0){
					if(department.getDepartmentName().contains("安监部")){
						isAjb = true;
					}
				}
				request.getSession().setAttribute("_IsAjb", isAjb);
				
				//类型
				List<ChildCategory> allCategory=this.childCategoryManager.getAll();
				request.getSession().setAttribute("_allCategory", allCategory);//类型
				List<ParentCategory> parentCategories=this.parentCategoryManager.getAllValid();
				request.getSession().setAttribute("_parentCategories", parentCategories);
				//首页报表中的检查项
				List<BigCategory> allBigs = this.bigCategoryManager.getAllValid();
				request.getSession().setAttribute("_CheckTypes", allBigs);
				//区域
				List<ReformArea> allArea = this.areaManage.getAll();
				request.getSession().setAttribute("_allArea",allArea);
				//部门
				List<Department> departments=this.departmentManager.getDepartments();
				request.getSession().setAttribute("_Departments",departments);
				List<Department> allDepartments=this.departmentManager.getAllDepartments();
				request.getSession().setAttribute("_AllDepartments",allDepartments);
				List<Department> unionDept=this.departmentManager.getUnionDepartments();
				request.getSession().setAttribute("_UnionDepartments",unionDept);
				
				//首页报表中的培训科目
				List<TrainCategory> allTCategory = this.trainCategoryManager.getAllCategories(1);
				request.getSession().setAttribute("_TrainCategories", allTCategory);
				
				return "home";
			}catch(Exception ex){
				ex.printStackTrace();
				return "sessionProcess";
			}
		}
	}
	
	/**
	 * 手机版登录入口
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/login_m.htm")
	public void login_m(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			JSONObject jsonObj = new JSONObject();
			boolean rtnFlag = true;
			String messsage = "";
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");

			User user = null;
			UserVo vo = new UserVo();
			String token = "";
			//重新验证登录
			if (userName != null && userName.length() > 0 && password != null && password.length() > 0) {
				try {
					//验证
					user = userManager.checkUser(userName, password);
				} catch (Exception ex) {
					rtnFlag = false;
					token = "";
				}
			}
            /*
             * 2、判断user是否为空，不为空时则重新生成token下发到app
             */
			if (user == null) {
				rtnFlag = false;
				token = "";
				messsage = "用户名或密码错误";
			} else {
				messsage = "";
				rtnFlag = true;
				token = loginToken(user, userName, password);

			}
			if (user != null && user.getxId() != null && user.getxId().intValue() > 0) {
				vo = this.userManager.transferToVo(user);
				//违章统计的违章类型
				List<ChildCategory> allCategory = this.childCategoryManager.getAll();
                List<DepartmentVo> p_dept= this.departmentManager.getGroups(0);
                List<DepartmentVo> ht_dept = this.departmentManager.getGroups(1);
                List<DepartmentVo> dl_dept = this.departmentManager.getGroups(2);
                List<DepartmentVo> dc_dept = this.departmentManager.getGroups(3);

				//部门
				jsonObj.put("areaDatas", this.areaManage.getAll());
                jsonObj.put("allCategory", allCategory);
                jsonObj.put("P_DEPT",p_dept);
                jsonObj.put("DL_DEPT",dl_dept);
                jsonObj.put("DC_DEPT",dc_dept);
                jsonObj.put("HT_DEPT",ht_dept);
			}
			jsonObj.put("_GLOBAL_USER", vo);
			jsonObj.put("token", token);
			jsonObj.put("success", rtnFlag);
			jsonObj.put("message", messsage);
			//设置字符编码
			response.setContentType(CoreConstant.CONTENT_TYPE);
			response.getWriter().print(jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//获取系统首页右边栏信息
	@RequestMapping("/mainInfor.htm")
	public String mainInfor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "mainInfor";
	}
	
}
