package com.kwchina.work.security.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kwchina.core.common.dao.BasicDaoImpl;
import com.kwchina.work.security.dao.SecurityCategoryDAO;
import com.kwchina.work.security.entity.SecurityCategory;

@Repository
public class SecurityCategoryDAOImpl extends BasicDaoImpl<SecurityCategory> implements SecurityCategoryDAO{

	//获取某个层次的文档分类
	public List getLayerCategorys(int layer){
		Date now = new Date();
		SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
		String yesrStr=sf_y.format(now);
		String sql = "from SecurityCategory  category where category.layer = " + layer + " and categoryName <= "+yesrStr+" order by displayNo asc,categoryName";
		return getResultByQueryString(sql);
	}

	
	//获取所有父分类
//	public List getParentCategory(){
//		String sql = "from DocumentCategory category where category.valid=1 and category.leaf = 0 and category.categoryId <> 1 order by displayNo";
//		return getResultByQueryString(sql);
//	}



}
