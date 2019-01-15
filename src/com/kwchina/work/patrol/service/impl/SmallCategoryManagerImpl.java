package com.kwchina.work.patrol.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.patrol.dao.SmallCategoryDAO;
import com.kwchina.work.patrol.entity.SmallCategory;
import com.kwchina.work.patrol.service.SmallCategoryManager;

@Service("smallCategoryManager")
public class SmallCategoryManagerImpl extends BasicManagerImpl<SmallCategory> implements SmallCategoryManager{

	private SmallCategoryDAO smallCategoryDAO;

	@Autowired
	public void setSystemSmallCategoryDAO(SmallCategoryDAO smallCategoryDAO) {
		this.smallCategoryDAO = smallCategoryDAO;
		super.setDao(smallCategoryDAO);
	}
	
	
	public SmallCategory getSmallCategoryBySmallId(Integer smallId){
		SmallCategory category = new SmallCategory();
		String sql = "from SmallCategory category where category.smallId='" + smallId + "'";
		List<SmallCategory> list = this.smallCategoryDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			category = list.get(0);
		}
		
		return category;
		
	}
	public List<SmallCategory> getSmallCategoriesByBigId(Integer bigId){
		List<SmallCategory> list = new ArrayList<SmallCategory>();
		String hql = "from SmallCategory category where valid=1 and category.bigId = '" + bigId + "'";
		list = this.smallCategoryDAO.getResultByQueryString(hql);
		return list;
	}
	
}
