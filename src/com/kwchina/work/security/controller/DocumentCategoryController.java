package com.kwchina.work.security.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.work.security.entity.DocumentCategory;
import com.kwchina.work.security.service.DocumentCategoryManager;
import com.kwchina.work.security.service.DocumentInforManager;
import com.kwchina.work.security.util.DocumentConstant;
import com.kwchina.work.security.vo.DocumentCategoryVo;

@Controller
@RequestMapping("/security/documentCategory.htm")
public class DocumentCategoryController {

	@Resource
	private DocumentCategoryManager documentCategoryManager;

	@Resource
	private UserManager userManager;


	@Resource
	private DepartmentManager departmentManager;

	@Resource
	private DocumentInforManager documentInforManager;
	

	/**
	 * 浏览文档分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=list")
	public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//全部分类树状显示(需要判断分类的权限)
		ArrayList returnArray = this.documentCategoryManager.getCategoryAsTree(DocumentConstant._Root_Category_Id);
		
		Object[] categoryObjects = new Object[returnArray.size()];
		for(int i=0;i<returnArray.size();i++){
			categoryObjects[i] = returnArray.get(i);
		}
		
		request.setAttribute("_Categorys", categoryObjects);
		
//		每个模块中的操作角色
//		String[][] viewRights = new String[categoryObjects.length][];
//		String[][] editRights = new String[categoryObjects.length][];
//		String[][] deleteRights = new String[categoryObjects.length][];
		
		
//		for(int i=0;i<categoryObjects.length;i++){
//			DocumentCategory tmpCategory = (DocumentCategory)categoryObjects[i];
//			
//			Set<DocumentCategoryRight> categoryRights = tmpCategory.getRights();
//			
//			viewRights[i] = new String[categoryRights.size()];
//			editRights[i] = new String[categoryRights.size()];
//			deleteRights[i] = new String[categoryRights.size()];
//			int rightType = 0;
//			
//			int k = 0;
//			for (Iterator it = categoryRights.iterator(); it.hasNext();) {
//				DocumentCategoryRight right = (DocumentCategoryRight) it.next();
//				//if (right instanceof CategoryUserRight) {
//					rightType = 1;
//					/** 用户权限 */
//					//CategoryUserRight userRight = (CategoryUserRight)right;
////					int userId = userRight.getSystemUser().getPersonId().intValue();
//					String userName = right.getUser().getPerson().getPersonName();
//					
//					//编辑权限
//					if (this.documentCategoryRightManager.hasRight(right, DocumentCategoryRight._Right_Edit) || this.documentCategoryRightManager.hasRight(right, DocumentCategoryRight._Right_Create)) {
//						editRights[i][k] = userName;
//					}
//
//					//删除权限
//					if (this.documentCategoryRightManager.hasRight(right, DocumentCategoryRight._Right_Delete)) {
//						deleteRights[i][k] = userName;
//					}
//
//					//浏览权限
//					if (this.documentCategoryRightManager.hasRight(right, DocumentCategoryRight._Right_View)) {
//						viewRights[i][k] = userName;
//					}
//				//}
//				k += 1;
//			}
//		}
		
//		request.setAttribute("_ViewRights", viewRights);
//		request.setAttribute("_EditRights", editRights);
//		request.setAttribute("_DeleteRights", deleteRights);
		
		
		request.setAttribute("_TREE", returnArray);
		
		//getLeftCategory(form, request, 1);
		
		return "listCategory";
	}



	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, DocumentCategoryVo vo) throws Exception {

		Integer categoryId = vo.getCategoryId();

		//获取用户信息
		List users = this.userManager.getAll();
		
		DocumentCategory category = null;
		//修改
		if (categoryId != null && categoryId.intValue() > 0) {
			category = (DocumentCategory) this.documentCategoryManager.get(categoryId);

			BeanUtils.copyProperties(vo, category);
			
			//父分类
			if (category.getParent() != null) {
				vo.setParentId(category.getParent().getCategoryId().intValue());
			}
			//是否叶子分类
			if (category.isLeaf())
				vo.setLeaf(true);
			else
				vo.setLeaf(false);

		}

		//全部分类树状显示(需要判断分类的权限)
		ArrayList returnArray = this.documentCategoryManager.getCategoryAsTree(DocumentConstant._Root_Category_Id);
		request.setAttribute("_TREE", returnArray);
		
		//根据职级获取用户
//		List persons = this.userManager.getAllValid();	
//		request.setAttribute("_Persons", persons);
		

		//获取部门信息
//		List organizes = this.departmentManager.getDepartments();
//		request.setAttribute("_Organizes", organizes);
//		request.setAttribute("_Users", users);
		request.setAttribute("_Category", category);

		return "editCategory";
	}

	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param vo
	 * @throws Exception
	 */
	@RequestMapping(params = "method=save")
	public void save(HttpServletRequest request, HttpServletResponse response, DocumentCategoryVo vo) throws Exception {

		DocumentCategory category = new DocumentCategory();
		Integer categoryId = vo.getCategoryId();

		if (categoryId != null && categoryId.intValue() != 0) {
			category = (DocumentCategory) this.documentCategoryManager.get(categoryId.intValue());
		}
		
		if (vo.getParentId() != 0) {
			DocumentCategory parent = (DocumentCategory) this.documentCategoryManager.get(vo.getParentId());
			category.setParent(parent);
			vo.setLayer(parent.getLayer()+1);
		}
		//是否叶分类
		category.setLeaf(vo.isLeaf());
		category.setValid(true);
		
		
		
		BeanUtils.copyProperties(category, vo);
		this.documentCategoryManager.save(category);
//		this.documentCategoryManager.saveCategory(category, vo);

	}

	/**
	 * 删除分类操作
	 * @param request
	 * @param response
	 * @param vo
	 * @throws Exception
	 */
	@RequestMapping(params = "method=delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, DocumentCategoryVo vo) throws Exception {
		
		Integer categoryId = vo.getCategoryId();
		if (categoryId != null && categoryId.intValue() > 0) {
			DocumentCategory category = (DocumentCategory)this.documentCategoryManager.get(categoryId);
//			deleteChildren(category);
			category.setValid(false);
			this.documentCategoryManager.save(category);
			
		}
	}
//	private void deleteChildren(DocumentCategory category){
//		Set childs = category.getChilds();
//		
//		if(childs!=null && childs.size()>0){
//			List tmpList = new ArrayList(childs);
//			for(Iterator it = tmpList.iterator();it.hasNext();){
//				DocumentCategory tpS = (DocumentCategory)it.next();
//				
//				//从父对象移除
//				childs.remove(tpS);
//				
//				deleteChildren(tpS);
//			}			
//		}
//		this.documentCategoryManager.remove(category);
//	}
	
}
