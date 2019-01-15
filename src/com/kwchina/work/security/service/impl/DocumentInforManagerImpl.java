package com.kwchina.work.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.security.dao.DocumentCategoryDAO;
import com.kwchina.work.security.dao.DocumentInforDAO;
import com.kwchina.work.security.entity.DocumentCategory;
import com.kwchina.work.security.entity.DocumentInfor;
import com.kwchina.work.security.service.DocumentInforManager;

@Service("documentInforManager")
public class DocumentInforManagerImpl extends BasicManagerImpl<DocumentInfor> implements DocumentInforManager{

	private DocumentInforDAO documentInforDAO;
			
	@Resource
	private DocumentCategoryDAO documentCategoryDAO;

	@Autowired
	public void setSystemDocumentInforDAO(DocumentInforDAO documentInforDAO) {
		this.documentInforDAO = documentInforDAO;
		super.setDao(documentInforDAO);
	}
	
	/**
	 * 获取某个分类下的文档
	 */
	public List<DocumentInfor> getAllDocuments(int categoryId) {
		List<DocumentInfor> list = this.documentInforDAO.getAllDocuments(categoryId);
		return list;
	}

	// 获取新的文档编号
	public String getNewDocumentCode(DocumentInfor document) {
		String documentCode = "";

//		documentCode += document.getDepartment().getOrganizeNo();
//		documentCode += "-";
//		documentCode += document.getCategory().getCategoryCode();
//
//		String keyCode = documentCode;
//
//		documentCode += "-";
//
//		// 目前年份月份
//		java.util.Date dateNow = new java.util.Date(System.currentTimeMillis());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String nowStr = sdf.format(dateNow);
//		String yearStr = nowStr.substring(0, 4);
//		String monthStr = nowStr.substring(5, 6);
//		documentCode += yearStr + monthStr;
//		documentCode += "-";
//
//		DocumentInfor topDocument = this.documentInforDAO.getLastedDocument(keyCode);
//		if (topDocument != null) {
//			String tp_code = topDocument.getDocumentCode();
//
//			if (tp_code != null && !tp_code.equals("")) {
//				// 取出编号的后四位
//				String serialCode = tp_code.substring(tp_code.length() - 4);
//				// 编号+1为新编号
//				int newSerial = Integer.parseInt(serialCode) + 1;
//				String serial = String.valueOf(newSerial);
//				// 新编号，长度不足4位，补足
//				for (int k = 0; k < 4 - serial.length(); k++) {
//					documentCode += "0";
//				}
//				documentCode += serial;
//			}else{
//				documentCode += "0001";
//			}
//		} else {
//			documentCode += "0001";
//		}

		return documentCode;
	}
	
	// 通过文档编号获取文档
	public List getDocumentByCode(String documentCode) {
		String hql = " from DocumentInfor document where document.documentCode = '" + documentCode + "'";
		List documentList = this.documentInforDAO.getResultByQueryString(hql);
		return documentList;
	}
	
	
	//获取分类名称
	public List<DocumentCategory> getCategoryName() {
		List<DocumentCategory> returnLs = new ArrayList<DocumentCategory>();
 		String hql = "from DocumentCategory";
		returnLs = this.documentCategoryDAO.getResultByQueryString(hql); 		

 		return returnLs;
	}

	//根据文档类型及url获得文档信息
	public List<DocumentInfor> getDocumentByType(int documentType,String documentUrl){
		List<DocumentInfor> returnLs = new ArrayList<DocumentInfor>();
		String hql = "from DocumentInfor where documentType="+documentType+" and documentURL='"+documentUrl+"'";
		returnLs = this.documentInforDAO.getResultByQueryString(hql);
		return returnLs;
	}
	
}
