package com.kwchina.work.security.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.security.dao.DocumentCategoryDAO;
import com.kwchina.work.security.dao.SecurityDocumentDAO;
import com.kwchina.work.security.entity.SecurityDocument;
import com.kwchina.work.security.service.SecurityDocumentManager;

@Service("securityDocumentManager")
public class SecurityDocumentManagerImpl extends BasicManagerImpl<SecurityDocument> implements SecurityDocumentManager{

	private SecurityDocumentDAO securityDocumentDAO;
			
	@Resource
	private DocumentCategoryDAO documentCategoryDAO;

	@Autowired
	public void setSystemSecurityDocumentDAO(SecurityDocumentDAO securityDocumentDAO) {
		this.securityDocumentDAO = securityDocumentDAO;
		super.setDao(securityDocumentDAO);
	}
	
	/**
	 * 获取某个分类下的文档
	 */
	public List<SecurityDocument> getAllDocuments(int categoryId) {
		List<SecurityDocument> list = this.securityDocumentDAO.getAllDocuments(categoryId);
		return list;
	}

	
}
