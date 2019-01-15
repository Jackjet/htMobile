package com.kwchina.work.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.security.dao.SecurityCategoryDAO;
import com.kwchina.work.security.entity.SecurityCategory;
import com.kwchina.work.security.service.SecurityCategoryManager;

@Service("securityCategoryManager")
public class SecurityCategoryManagerImpl extends BasicManagerImpl<SecurityCategory> implements SecurityCategoryManager{

	private SecurityCategoryDAO securityCategoryDAO;
	

	@Autowired
	public void setSystemDocumentCategoryDAO(SecurityCategoryDAO securityCategoryDAO) {
		this.securityCategoryDAO = securityCategoryDAO;
		super.setDao(securityCategoryDAO);
	}
	

		//获取某个层次的文档分类
		public List getLayerCategorys(int layer) {
			return this.securityCategoryDAO.getLayerCategorys(layer);
		}


		//得到某层下的所有子分类
		public List<SecurityCategory> getAllSubCategories(int categoryId){
			List<SecurityCategory> list = new ArrayList<SecurityCategory>();
			SecurityCategory category = this.get(categoryId);
			addSubCategory(list, category);
			
			return list;
			
		}
		
		private void addSubCategory(List<SecurityCategory> list,SecurityCategory category){
			String hql = " from SecurityCategory category where category.pid = " + category.getCategoryId() + " order by category.displayNo";
			List<SecurityCategory> tmpList = this.securityCategoryDAO.getResultByQueryString(hql);
			if(tmpList != null && tmpList.size() > 0){
				for(SecurityCategory tmpCategory : tmpList){
					list.add(tmpCategory);
					addSubCategory(list, tmpCategory);
				}
			}
		}
		
		//得到某层下的直属子分类
		public List<SecurityCategory> getSubCategories(int categoryId){
			String hql = " from SecurityCategory category where category.pid = " + categoryId + " order by category.displayNo";
			List<SecurityCategory> tmpList = this.securityCategoryDAO.getResultByQueryString(hql);
			
			return tmpList;
		}


		@Override
		public SecurityCategory getByCategoryId(int categoryId) {
			String hql="from SecurityCategory category where category.categoryId=" + categoryId;
			List<SecurityCategory> resultByQueryString = this.securityCategoryDAO.getResultByQueryString(hql);
			return resultByQueryString.get(0);
		}


}
