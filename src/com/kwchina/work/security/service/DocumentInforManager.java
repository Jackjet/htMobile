package com.kwchina.work.security.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.security.entity.DocumentCategory;
import com.kwchina.work.security.entity.DocumentInfor;

public interface DocumentInforManager extends BasicManager{
	 /**
     * 获取某个分类下的文档  
     */
    public List<DocumentInfor> getAllDocuments(int categoryId);
    
    //获取新的文档编号
    public String getNewDocumentCode(DocumentInfor document);
    
    //通过文档编号获取文档
	public List getDocumentByCode(String documentCode);
	
	
	//获取分类
	public List<DocumentCategory> getCategoryName();
	
	//根据文档类型及url获得文档信息
	public List<DocumentInfor> getDocumentByType(int documentType,String documentUrl);
}
