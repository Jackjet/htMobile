package com.kwchina.work.reform.service.impl;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.reform.dao.ChildCategoryDAO;
import com.kwchina.work.reform.entity.ChildCategory;
import com.kwchina.work.reform.service.ChildCategoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("childCategoryManager")
public class ChildCategoryManagerImpl extends BasicManagerImpl<ChildCategory> implements ChildCategoryManager {

	private ChildCategoryDAO childCategoryDAO;

	@Autowired
	public void setSystemChildCategoryDAO(ChildCategoryDAO childCategoryDAO) {
		this.childCategoryDAO = childCategoryDAO;
		super.setDao(childCategoryDAO);
	}
	
	
	public ChildCategory getChildCategoryByCategoryId(Integer categoryId){
		ChildCategory category = new ChildCategory();
		String sql = "from ChildCategory category where category.categoryId='" + categoryId + "'";
		List<ChildCategory> list = this.childCategoryDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			category = list.get(0);
		}
		
		return category;
		
	}


	public List<ChildCategory> getChildCategoriesByParentId(Integer parentId){
		List<ChildCategory> list;
		String hql = "from ChildCategory category where valid=1 and category.parentId = '" + parentId+ "'";
		list = this.childCategoryDAO.getResultByQueryString(hql);
		return list;
	}
	
}
