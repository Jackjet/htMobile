package com.kwchina.work.security.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kwchina.core.common.dao.BasicDaoImpl;
import com.kwchina.work.security.dao.DocumentCategoryDAO;
import com.kwchina.work.security.entity.DocumentCategory;

@Repository
public class DocumentCategoryDAOImpl extends BasicDaoImpl<DocumentCategory> implements DocumentCategoryDAO{

	//获取某个层次的文档分类
	public List getLayerCategorys(int layer){
		String sql = "from DocumentCategory  category where category.valid=1 and  category.layer = " + layer + " order by displayNo asc";
		return getResultByQueryString(sql);
	}

	
	//获取所有父分类
	public List getParentCategory(){
		String sql = "from DocumentCategory category where category.valid=1 and category.leaf = 0 and category.categoryId <> 1 order by displayNo";
		return getResultByQueryString(sql);
	}


	public List<DocumentCategory> getAllValid(){
		List<DocumentCategory> list = new ArrayList<DocumentCategory>();
		String hql = " from DocumentCategory category whrer category.valid = 1 order by category.layer,category.displayNo";
		return list;
	}

}
