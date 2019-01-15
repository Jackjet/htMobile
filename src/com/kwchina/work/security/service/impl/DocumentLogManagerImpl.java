package com.kwchina.work.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.security.dao.DocumentLogDAO;
import com.kwchina.work.security.entity.DocumentLog;
import com.kwchina.work.security.service.DocumentLogManager;

@Service("documentLogManager")
public class DocumentLogManagerImpl extends BasicManagerImpl<DocumentLog> implements DocumentLogManager{

	private DocumentLogDAO documentLogDAO;

	@Autowired
	public void setSystemDocumentLogDAO(DocumentLogDAO documentLogDAO) {
		this.documentLogDAO = documentLogDAO;
		super.setDao(documentLogDAO);
	}
	
	
}
