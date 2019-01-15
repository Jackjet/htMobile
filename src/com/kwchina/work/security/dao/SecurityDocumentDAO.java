package com.kwchina.work.security.dao;

import java.util.List;

import com.kwchina.core.common.dao.BasicDao;
import com.kwchina.work.security.entity.SecurityDocument;

public interface SecurityDocumentDAO extends BasicDao<SecurityDocument> {
	/**
     * 获取某个分类下的文档  
     */
    public List<SecurityDocument> getAllDocuments(int categoryId);
}
