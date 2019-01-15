package com.kwchina.work.security.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.security.entity.SecurityDocument;

public interface SecurityDocumentManager extends BasicManager{
	 /**
     * 获取某个分类下的文档  
     */
    public List<SecurityDocument> getAllDocuments(int categoryId);
    
}
