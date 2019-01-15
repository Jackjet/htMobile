package com.kwchina.work.security.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.security.entity.SecurityCategory;

public interface SecurityCategoryManager extends BasicManager{
		
	//获取某个层次的文档分类
	public List getLayerCategorys(int layer);
	
	//根据id获取单个分类
	public SecurityCategory getByCategoryId(int categoryId);
		
	//得到某层下的所有子分类
	public List<SecurityCategory> getAllSubCategories(int categoryId);
	
	//得到某层下的直属子分类
	public List<SecurityCategory> getSubCategories(int categoryId);

		
}
