package com.kwchina.work.security.util;

import java.io.File;
import java.sql.Timestamp;

import com.kwchina.work.security.entity.SecurityCategory;
import com.kwchina.work.security.entity.SecurityDocument;
import com.kwchina.work.security.service.SecurityCategoryManager;
import com.kwchina.work.security.service.SecurityDocumentManager;
import com.kwchina.work.sys.MyApplicationContextUtil;

public class SecurityDocUtil {
	
	public static void updateDirs (){
		SecurityDocumentManager securityDocumentManager = (SecurityDocumentManager)MyApplicationContextUtil.getBean("securityDocumentManager");
		SecurityCategoryManager securityCategoryManager = (SecurityCategoryManager)MyApplicationContextUtil.getBean("securityCategoryManager");
		
		SecurityCategory rootCategory = (SecurityCategory)securityCategoryManager.get(1);
		
		//先删除，再更新
		String hql = "delete from x_security_category where categoryId > " + rootCategory.getCategoryId();
		securityCategoryManager.doSqlDelete(hql);
		
		String hql_document = "delete from x_security_Document";
		securityDocumentManager.doSqlDelete(hql_document);
		
		readFiles(rootCategory.getFullPath(),rootCategory.getFullPath(),rootCategory.getCategoryId().intValue(),0);
	}
	
	public static void readFiles (String filePath,String rootPath,int pid,int layer){
		try {
			SecurityDocumentManager securityDocumentManager = (SecurityDocumentManager)MyApplicationContextUtil.getBean("securityDocumentManager");
			SecurityCategoryManager securityCategoryManager = (SecurityCategoryManager)MyApplicationContextUtil.getBean("securityCategoryManager");
			//更新
			File file = new File(filePath);
			if (!file.isDirectory()) {
//				System.out.println("文件");
//				System.out.println("path=" + file.getPath());
//				System.out.println("absolutepath=" + file.getAbsolutePath());
//				System.out.println("name=" + file.getName());
				
				//文件

			} else if (file.isDirectory()) {
//				System.out.println("文件夹：" + file.getPath());
				//目录
				String[] filelist = file.list();
				
				int tmpDisplayNo = 0;
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filePath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
//						System.out.println("path=" + readfile.getPath());
//						System.out.println("absolutepath="
//								+ readfile.getAbsolutePath());
//						System.out.println("name=" + readfile.getName());
						
						//文件
						
						SecurityDocument tmpDocument = new SecurityDocument();
						int documentId = securityDocumentManager.getMaxId("documentId");
						tmpDocument.setDocumentId(documentId);
						tmpDocument.setDocumentTitle(readfile.getName());
						tmpDocument.setFullPath(readfile.getAbsolutePath());
						tmpDocument.setCategoryId(pid);
						tmpDocument.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						tmpDocument.setAuthorId(1);
						
						securityDocumentManager.save(tmpDocument);

					} else if (readfile.isDirectory()) {
						//目录
						tmpDisplayNo ++;
//						SecurityCategoryManager securityCategoryManager = (SecurityCategoryManager)MyApplicationContextUtil.getBean("securityCategoryManager");
						SecurityCategory tmpCategory = new SecurityCategory();
						int categoryId = securityCategoryManager.getMaxId("categoryId");
						tmpCategory.setCategoryId(categoryId);
						tmpCategory.setCategoryName(readfile.getPath().substring(readfile.getPath().lastIndexOf("\\")+1));
						tmpCategory.setFullPath(readfile.getPath());
						
//						int layer = 0;
//						int index = readfile.getPath().indexOf(rootPath);
//						
//						if(index > -1){
//							int backCount = readfile.getPath().substring(rootPath.length()).split("/").length-1;
//							layer = backCount;
//						}
						
						tmpCategory.setDisplayNo(tmpDisplayNo);
						tmpCategory.setLayer(layer + 1);
						tmpCategory.setPid(pid);
						
						securityCategoryManager.save(tmpCategory);
						//SecurityCategory newCategory = (SecurityCategory)
						
						readFiles(filePath + "\\" + filelist[i],rootPath,categoryId,layer+1);
					}
				}

			}

		} catch (Exception e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
	}
}
