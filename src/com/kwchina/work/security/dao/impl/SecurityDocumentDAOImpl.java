package com.kwchina.work.security.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kwchina.core.common.dao.BasicDaoImpl;
import com.kwchina.work.security.dao.SecurityDocumentDAO;
import com.kwchina.work.security.entity.SecurityDocument;

@Repository
public class SecurityDocumentDAOImpl extends BasicDaoImpl<SecurityDocument> implements SecurityDocumentDAO{
	/**
     * 获取某个分类下的文档  
     */
    public List<SecurityDocument> getAllDocuments(int categoryId){
    	String sql ="from SecurityDocument document  where document.categoryId = " + categoryId;		
		List<SecurityDocument> list = getResultByQueryString(sql);

		return list;
    }
}
