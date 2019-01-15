package com.kwchina.work.security.dao;

import java.util.List;

import com.kwchina.core.common.dao.BasicDao;
import com.kwchina.work.security.entity.SecurityCategory;

public interface SecurityCategoryDAO extends BasicDao<SecurityCategory> {
	//获取某个层次的文档分类
	public List getLayerCategorys(int layer);
	
    //获取所有父分类
//	public List getParentCategory();
	
	
}
