package com.kwchina.work.patrol.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.patrol.dao.BigCategoryDAO;
import com.kwchina.work.patrol.entity.BigCategory;
import com.kwchina.work.patrol.service.BigCategoryManager;

@Service("bigCategoryManager")
public class BigCategoryManagerImpl extends BasicManagerImpl<BigCategory> implements BigCategoryManager{

	private BigCategoryDAO bigCategoryDAO;

	@Autowired
	public void setSystemBigCategoryDAO(BigCategoryDAO bigCategoryDAO) {
		this.bigCategoryDAO = bigCategoryDAO;
		super.setDao(bigCategoryDAO);
	}
	
	public BigCategory getBigCategoryByBigId(Integer bigId){
		BigCategory category = new BigCategory();
		String sql = "from BigCategory category where category.bigId='" + bigId + "'";
		List<BigCategory> list = this.bigCategoryDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			category = list.get(0);
		}
		
		return category;
	}
	
	public List<BigCategory> getAllValid(){
		List<BigCategory> list = new ArrayList<BigCategory>();
		String hql = "from BigCategory where valid = 1 order by orderNo,categoryName";
		list = this.bigCategoryDAO.getResultByQueryString(hql);
		return list;
	}
	
}
