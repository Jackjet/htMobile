package com.kwchina.core.base.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.base.vo.DepartmentVo;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.json.JSONConvert;


@Controller
@RequestMapping("/core/department.htm")
public class DepartmentController extends BasicController {
	
	@Resource
	private DepartmentManager departmentManager;
	
	@Resource
	private UserManager userManager;
	
	
	/**
	 * 按照树状结构获取组织结构信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=list")
	public String listTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ArrayList returnArray = departmentManager.getDepartmentAsTree(CoreConstant.Department_Begin_Id);
		request.setAttribute("_DepartmentTree", returnArray);
		
		return "base/listDepartment";
	}
	
	/**
	 * 获取查询条件数据(部门信息)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="method=getDepartments")
	public void getDepartments(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject jsonObj = new JSONObject();
		JSONConvert convert = new JSONConvert();
		
		//部门信息
		JSONArray departmentArray = new JSONArray();
		List departments = this.departmentManager.getDepartments();
		departmentArray = convert.modelCollect2JSONArray(departments, new ArrayList());
		jsonObj.put("_Departments", departmentArray);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
        response.getWriter().print(jsonObj);		
	}
	
	
	/**
	 * 获取查询条件数据(班组信息)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
//	@RequestMapping(params="method=getGroups")
//	public void getGroups(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		JSONObject jsonObj = new JSONObject();
//		JSONConvert convert = new JSONConvert();
//
//		//班组信息
//		String xId = request.getParameter("xId");
//		JSONArray groupArray = new JSONArray();
//
//		List groups = new ArrayList();
//		if (xId != null && xId.length() > 0 && !("0").equals(xId)) {
//			//若xId不为空,则取该部门下的班组信息
//			Department department = (Department)this.departmentManager.get(Integer.valueOf(xId));
//			groups = new ArrayList(department.getChildren());
//		}else {
//			//若xId为空,则取该所有班组信息
//			groups = this.departmentManager.getGroups();
//		}
//		groupArray = convert.modelCollect2JSONArray(groups, new ArrayList());
//		jsonObj.put("_Groups", groupArray);
//
//		//设置字符编码
//        response.setContentType(CoreConstant.CONTENT_TYPE);
//        response.getWriter().print(jsonObj);
//	}
	

	
	
	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=edit")
	public String edit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String xId = request.getParameter("xId");
		
		//修改
		if (xId != null && xId.length() > 0) {
			Department department = (Department)this.departmentManager.get(Integer.valueOf(xId));
			request.setAttribute("_Department", department);
			
		}
		
		//所有部门班组信息
		List treeDepartments = this.departmentManager.getDepartmentAsTree(CoreConstant.Department_Begin_Id);
		request.setAttribute("_TreeDepartments", treeDepartments);
		
		//人员信息
		List persons = this.userManager.getAllValid();
		request.setAttribute("_Persons", persons);
		
		return "base/editDepartment";
	}
	
	
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @throws Exception
	 */
//	@RequestMapping(params="method=save")
//	public void save(HttpServletRequest request, HttpServletResponse response, DepartmentVo vo) throws Exception {
//
////		String xId = request.getParameter("xId");
////		String departmentName = request.getParameter("departmentName");
////		String shortName = request.getParameter("shortName");
////		String departmentNo = request.getParameter("departmentNo");
////		String orderNo = request.getParameter("orderNo");
////		String levelId = request.getParameter("levelId");
////		String parentId = request.getParameter("parentId");
////		String directorId = request.getParameter("directorId");
//		Department department = new Department();
//
//		//修改
//		if (vo.getXId()!= null && vo.getXId()> 0) {
//			department = (Department)this.departmentManager.get(vo.getXId());
//
//			BeanUtils.copyProperties(department, vo);
//
//
//			department.setLevelId(1);
//			department.setValid(true);
//		}else {
//
//			BeanUtils.copyProperties(department, vo);
//
//			Integer departmentId = this.departmentManager.getMaxId("departmentId");
//			department.setDepartmentId(departmentId);
//
//			if(vo.getParentId() != null && vo.getParentId() > 0){
//				Department parent= (Department)this.departmentManager.get(vo.getParentId());
//				department.setParent(parent);
//				department.setLayer(parent.getLayer() + 1);
//			}
//		}
//
////		if(StringUtil.isNotEmpty(vo.getUserId())){
////			User director = this.userManager.getUserByUserId(vo.getUserId());
////			department.set
////		}
//
//		this.departmentManager.save(department);
//
//	}
	
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="method=delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String xId = request.getParameter("xId");
		if (xId != null && xId.length() > 0) {
			Department department  = (Department)this.departmentManager.get(Integer.parseInt(xId));
			deleteChildren(department);
		}
	}
	
	private void deleteChildren(Department department){
		Set childs = department.getChildren();
		
		if(childs!=null && childs.size()>0){
			List tmpList = new ArrayList(childs);
			for(Iterator it = tmpList.iterator();it.hasNext();){
				Department tpS = (Department)it.next();
				
				//从父对象移除
				childs.remove(tpS);
				
				deleteChildren(tpS);
			}			
		}
		
		this.departmentManager.remove(department);
	}
	
	
//	/**
//	 * 获取部门名称(用于工作流自定义标签)
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping(params="method=getDepartmentName")
//	public void getDepartmentName(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		JSONObject jsonObj = new JSONObject();
//		
//		//部门名称
//		String xId = request.getParameter("xId");
//		String departmentName = "";
//		if (xId != null && xId.length() > 0) {
//			Department department = (Department)this.departmentManager.get(Integer.valueOf(xId));
//			if (department != null) {
//				departmentName = department.getDepartmentName();
//			}
//		}
//		jsonObj.put("_DepartmentName", departmentName);
//		
//		//设置字符编码
//       response.setContentType(CoreConstant.CONTENT_TYPE);
//       response.getWriter().print(jsonObj);		
//	}
	
//	/**
//	 * 获取部门名称(用于工作流自定义标签)
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping(params="method=getDepartmentNo")
//	public void getDepartmentNo(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		JSONObject jsonObj = new JSONObject();
//		
//		//部门名称
//		String xId = request.getParameter("xId");
//		String departmentNo = "";
//		if (xId != null && xId.length() > 0) {
//			Department department = (Department)this.departmentManager.get(Integer.valueOf(xId));
//			if (department != null) {
//				departmentNo = department.getDepartmentNo();
//			}
//		}
//		jsonObj.put("_DepartmentNo", departmentNo);
//		
//		//设置字符编码
//       response.setContentType(CoreConstant.CONTENT_TYPE);
//       response.getWriter().print(jsonObj);		
//	}
	
	
//	/**
//	 * 获取部门简称(用于公文生成发文号、收文号)
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping(params="method=getShortName")
//	public void getShortName(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		JSONObject jsonObj = new JSONObject();
//		
//		//部门名称
//		String xId = request.getParameter("xId");
//		String shortName = "";
//		if (xId != null && xId.length() > 0) {
//			Department department = (Department)this.departmentManager.get(Integer.valueOf(xId));
//			if (department != null) {
//				shortName = department.getShortName();
//			}
//		}
//		jsonObj.put("_ShortName", shortName);
//		
//		//设置字符编码
//       response.setContentType(CoreConstant.CONTENT_TYPE);
//       response.getWriter().print(jsonObj);		
//	}
}
