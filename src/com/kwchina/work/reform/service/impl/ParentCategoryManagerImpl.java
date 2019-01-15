package com.kwchina.work.reform.service.impl;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.reform.dao.ParentCategoryDAO;
import com.kwchina.work.reform.entity.ParentCategory;
import com.kwchina.work.reform.service.ParentCategoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("parentCategoryManager")
public class ParentCategoryManagerImpl extends BasicManagerImpl<ParentCategory> implements ParentCategoryManager {

	private ParentCategoryDAO parentCategoryDAO;

	@Autowired
	public void setSystemParentCategoryDAO(ParentCategoryDAO parentCategoryDAO) {
		this.parentCategoryDAO = parentCategoryDAO;
		super.setDao(parentCategoryDAO);
	}

	@Override
	public ParentCategory getParentCategoryByParentId(Integer parentId) {
		ParentCategory category = new ParentCategory();
		String sql = "from ParentCategory category where category.pId='" + parentId + "'";
		List<ParentCategory> list = this.parentCategoryDAO.getResultByQueryString(sql);

		if(list != null && list.size() > 0){
			category = list.get(0);
		}

		return category;
	}

	public List<ParentCategory> getAllValid(){
		List<ParentCategory> list;
		String hql = "from ParentCategory where valid = 1 order by orderNo,pName";
		list = this.parentCategoryDAO.getResultByQueryString(hql);
		return list;
	}
	
}
