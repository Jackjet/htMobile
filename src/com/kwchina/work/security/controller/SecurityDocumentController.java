package com.kwchina.work.security.controller;

import java.io.File;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kwchina.work.sys.LoginConfirm;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.security.entity.SecurityCategory;
import com.kwchina.work.security.entity.SecurityDocument;
import com.kwchina.work.security.service.SecurityCategoryManager;
import com.kwchina.work.security.service.SecurityDocumentManager;
import com.kwchina.work.security.util.SecurityDocUtil;
import com.kwchina.work.security.vo.AppCategory;
import com.kwchina.work.security.vo.AppDocument;
import com.kwchina.work.security.vo.SecurityCategoryDTO;
import com.kwchina.work.sys.SysCommonMethod;

@Controller
@RequestMapping("/security/document.htm")
public class SecurityDocumentController {
	Logger logger = Logger.getLogger(SecurityDocumentController.class);
	@Resource
	private SecurityCategoryManager securityCategoryManager;
	
	@Resource
	private SecurityDocumentManager securityDocumentManager;
	

	/**
	 * 浏览文档分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=list")
	public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String categoryIdStr = request.getParameter("categoryId");
		try {
			int categoryId = 0;
			if(StringUtil.isNotEmpty(categoryIdStr)){
				categoryId = Integer.valueOf(categoryIdStr);
			}
			
			//树状显示分类
			List<SecurityCategoryDTO> dtos = new ArrayList<SecurityCategoryDTO>();
			
			//得到所有分类
			List<SecurityCategory> allSubCategories = this.securityCategoryManager.getAllSubCategories(categoryId);
			
			//转到DTO
			for(SecurityCategory tmpCategory : allSubCategories){
				SecurityCategoryDTO dto = new SecurityCategoryDTO();
				dto.setId(tmpCategory.getCategoryId().intValue());
				dto.setName(tmpCategory.getCategoryName());
				
				int pid = tmpCategory.getPid().intValue();
				if(pid == categoryId){
					pid = 0;
				}
				dto.setPid(pid);
				
				dtos.add(dto);
			}
			
			//request.setAttribute("_Categories", dtos);			
			String rtnResult = "";
			Gson gson = new Gson();
			rtnResult = gson.toJson(dtos,new TypeToken<List<SecurityCategoryDTO>>(){}.getType());
			request.setAttribute("_Categories", rtnResult);		
			request.setAttribute("_CategoryId", categoryId);
//			String categoryName = "安全文档";
//			SecurityCategory pCategory = (SecurityCategory)this.securityCategoryManager.get(categoryId);
//			if(pCategory != null && pCategory.getCategoryId().intValue() > 0){
//				categoryName = pCategory.getCategoryName();
//			}
//			request.setAttribute("_CategoryName", categoryName);
//		
//			
//			//此分类下可能同时存在子分类和文件，此处再得到其下的文件
//			List<SecurityDocument> documents = new ArrayList<SecurityDocument>();
//			documents = this.securityDocumentManager.getAllDocuments(categoryId);
//			
//			request.setAttribute("_Documents", documents);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "securityDocument";
	}
	/**
	 * 获取查询条件数据(分类名称信息)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=getDocuments")
	public void getDocuments(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jsonObj = new JSONObject();
		String categoryIdStr = request.getParameter("categoryId");
		List<SecurityDocument> documents = new ArrayList<SecurityDocument>();
		try {
			int categoryId = 0;
			if(StringUtil.isNotEmpty(categoryIdStr)){
				categoryId = Integer.valueOf(categoryIdStr);
				
				documents = this.securityDocumentManager.getAllDocuments(categoryId);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		jsonObj.put("_Documents", documents);

		// 设置字符编码
		response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	/**
	 * 新增文档
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=addDocument")
	public String addDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SecurityCategory category = new SecurityCategory();
		String categoryIdStr = request.getParameter("categoryId");
		int categoryId = 0;
		if(StringUtil.isNotEmpty(categoryIdStr)){
			categoryId = Integer.valueOf(categoryIdStr);
			category = (SecurityCategory)this.securityCategoryManager.get(categoryId);
		}
		System.out.println(category.getFullPath());
		request.setAttribute("_Category", category);
		return "editDocument";
	}
	
	//增加节点  
	@RequestMapping(params = "method=addCategory",method=RequestMethod.GET)
    public void addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {  
        req.setCharacterEncoding("utf-8");  
        String id = req.getParameter("pId");  
        id = URLDecoder.decode(id,"utf-8");  
        int pid= Integer.valueOf(id);
        String name = req.getParameter("name");  
        name = URLDecoder.decode(name,"utf-8");  
         try{
        	 
        	SecurityCategory category = (SecurityCategory)this.securityCategoryManager.get(pid);
     		String fullPath = category.getFullPath()+"\\"+name;
     		int layer = category.getLayer();
     		int maxId = this.securityCategoryManager.getMaxId("categoryId");
     		SecurityCategory newCategory=new SecurityCategory();
     		newCategory.setFullPath(fullPath);
     		newCategory.setCategoryName(name);
     		newCategory.setPid(pid);
     		newCategory.setCategoryId(maxId);
     		newCategory.setLayer(layer+1);
     		this.securityCategoryManager.add(newCategory); 
         }catch(Exception e){
        	 e.printStackTrace();
         }
    }  
	//删除节点
	@RequestMapping(params = "method=delCategory",method=RequestMethod.POST)
    public void delCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {  
        req.setCharacterEncoding("utf-8");  
        String id = req.getParameter("id");  
        id = URLDecoder.decode(id,"utf-8");  
        int dId= Integer.valueOf(id);

			List<SecurityDocument> documentList = this.securityDocumentManager.getAllDocuments(dId);
			if(documentList.size()>0){
				for(SecurityDocument document:documentList){
					String fullPath = document.getFullPath();
					if(StringUtil.isNotEmpty(fullPath)){
						File file = new File(fullPath);
	                    if (!file.isDirectory()) {
	                        file.delete();
	                    }
					}
					
					this.securityDocumentManager.remove(document);
				}
			}
			SecurityCategory byCategoryId = this.securityCategoryManager.getByCategoryId(dId);
			File file = new File(byCategoryId.getFullPath());
			file.delete();
			this.securityCategoryManager.remove(dId);
    }  
	//修改节点
	@RequestMapping(params = "method=renameCategory",method=RequestMethod.POST)
    public void renameCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {  
        req.setCharacterEncoding("utf-8");  
        String id = req.getParameter("id");  
        id = URLDecoder.decode(id,"utf-8");  
        int dId= Integer.valueOf(id);
        String name = req.getParameter("name");  
        name = URLDecoder.decode(name,"utf-8");  
         try{
        	 SecurityCategory category = (SecurityCategory)this.securityCategoryManager.get(dId);
        	 category.setCategoryName(name);
     		this.securityCategoryManager.save(category); 
         }catch(Exception e){
        	 e.printStackTrace();
         }
    }
	/**
	 * 保存文档
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=saveDocument")
	public void saveDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		boolean success = true;
		String categoryIdStr = request.getParameter("categoryId");
		String attachment = request.getParameter("uploadAttachment");
		User user = SysCommonMethod.getSystemUser(request);
		try {
			int categoryId = 0;
			if(StringUtil.isNotEmpty(categoryIdStr)){
				categoryId = Integer.valueOf(categoryIdStr);
				SecurityCategory category = (SecurityCategory)this.securityCategoryManager.get(categoryId);
				String categoryFullPath = category.getFullPath();
				
				if(StringUtil.isNotEmpty(attachment)){
					String[] fileArray = attachment.split(";");
					for(String tmpFile : fileArray){
//						String tmpPath = tmpFile.split("\\|")[0];
						String tmpName = tmpFile.split("\\|")[1];
						
						SecurityDocument document = new SecurityDocument();
						document.setAuthorId(user.getUserId());
						document.setCategoryId(categoryId);
						int documentId = this.securityDocumentManager.getMaxId("documentId");
						document.setDocumentId(documentId);
						document.setDocumentTitle(tmpName);
						document.setFullPath(categoryFullPath+"\\"+tmpName);
						document.setUpdateTime(new Timestamp(System.currentTimeMillis()));
						
						this.securityDocumentManager.save(document);
					}
				}
			}
			
			
		}catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		jsonObj.put("success", success);

		// 设置字符编码
		response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	/**
	 * 删除文档
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=deleteDocument")
	public void deleteDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jsonObj = new JSONObject();
		boolean success = true;
		String documentIdStr = request.getParameter("documentId");
//		User user = SysCommonMethod.getSystemUser(request);
		try {
			int documentId = 0;
			if(StringUtil.isNotEmpty(documentIdStr)){
				documentId = Integer.valueOf(documentIdStr);
				SecurityDocument document = (SecurityDocument)this.securityDocumentManager.get(documentId);
				if(document != null && document.getDocumentId().intValue() > 0){
					String fullPath = document.getFullPath();
					if(StringUtil.isNotEmpty(fullPath)){
						File file = new File(fullPath);
	                    if (!file.isDirectory()) {
	                        file.delete();
	                    }
					}
					
					this.securityDocumentManager.remove(document);
				}
				
				
			}
			
			
		}catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		jsonObj.put("success", success);

		// 设置字符编码
		response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}

	/**
	 * 下载文档
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=downDocument")
	public String downDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		JSONObject jsonObj = new JSONObject();
//		boolean success = true;
		String documentIdStr = request.getParameter("documentId");
//		User user = SysCommonMethod.getSystemUser(request);
		String filepath = "";
		try {
			int documentId = 0;
			if(StringUtil.isNotEmpty(documentIdStr)){
				documentId = Integer.valueOf(documentIdStr);
				SecurityDocument document = (SecurityDocument)this.securityDocumentManager.get(documentId);
				if(document != null && document.getDocumentId().intValue() > 0){
					filepath = document.getFullPath();
//					if(StringUtil.isNotEmpty(fullPath)){
//						File file = new File(fullPath);
//	                    if (!file.isDirectory()) {
//	                        file.delete();
//	                    }
//					}
					
					//this.securityDocumentManager.remove(document);
				}
				
				
			}
			
			
			
			
		}catch (Exception e) {
//			success = false;
			e.printStackTrace();
		}
		
//		jsonObj.put("success", success);
//
//		// 设置字符编码
//		response.setContentType(CoreConstant.CONTENT_TYPE);
//		response.getWriter().print(jsonObj);
		request.getSession().setAttribute("_File_Path", filepath);
		if(StringUtil.isNotEmpty(request.getParameter("source"))){
			//手机端请求，判断文件大小是否超过20M，如果超过，不能下载
			File filea = new File(filepath);
			long fileLength = 0;
			if(filea.exists() && filea.isFile()){
				fileLength = filea.length();
			}
			if((fileLength / 1024 / 1024) > 20){
				response.setContentType(CoreConstant.CONTENT_TYPE);
				response.getWriter().print("文件超过20MB，请用电脑查看！");
			}else{
				
				return "/common/download";
				
			}
		}else{
			return "/common/download";
		}
		
		return null;
	}


	/**
	 * 读取特定目录，更新目录及文件表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=updateCategory")
	public void updateCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jsonObj = new JSONObject();
		boolean success = true;
		try {
//			SecurityCategory rootCategory = (SecurityCategory)this.securityCategoryManager.get(1);
//			
//			//先删除，再更新
//			String hql = "delete from x_security_category where categoryId > " + rootCategory.getCategoryId();
//			this.securityCategoryManager.doSqlDelete(hql);
//			
//			String hql_document = "delete from x_security_Document";
//			this.securityDocumentManager.doSqlDelete(hql_document);
			
			
			
//			String hql = "from SecurityCategory where categoryId > " + rootCategory.getCategoryId();
//			List<SecurityCategory> allCategories = this.securityCategoryManager.getResultByQueryString(hql);
//			for(SecurityCategory tmpCategory : allCategories){
//				this.securityCategoryManager.remove(tmpCategory);
//			}
//			
//			String hql_document = "from SecurityDocument";
//			List<SecurityDocument> allDocuments = this.securityDocumentManager.getResultByQueryString(hql_document);
//			for(SecurityDocument tmpDocument : allDocuments){
//				this.securityDocumentManager.remove(tmpDocument);
//			}	
			
//			SecurityDocUtil.readFiles(rootCategory.getFullPath(),rootCategory.getFullPath(),rootCategory.getCategoryId().intValue(),0);
			SecurityDocUtil.updateDirs();
			
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		jsonObj.put("success", success);

		// 设置字符编码
		response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
		
	}
	
	
	
	
	/**
	 * 移动端获取安全文档目录（第一页面，包含第一层、第二层，及第一层下面的文档）
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=getFirstCategories")
	@Transactional
	public void getCategories(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;
		List<AppCategory> categoryVos = new ArrayList<AppCategory>();
		List<AppDocument> documentVos = new ArrayList<AppDocument>();
		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		try {
			
			if(user != null){
				
				//根目录
//				SecurityCategory rootCategory = (SecurityCategory)this.securityCategoryManager.get(1);
				
				//先得到第一层目录
				List<SecurityCategory> firstList = this.securityCategoryManager.getLayerCategorys(1);
				for(SecurityCategory tmpCategory : firstList){
					AppCategory vo = new AppCategory();
					vo.setCategoryId(tmpCategory.getCategoryId());
					vo.setCategoryName(tmpCategory.getCategoryName());
					vo.setDisplayNo(tmpCategory.getDisplayNo());
					vo.setPid(1);
					vo.setLayer(tmpCategory.getLayer() - 1);
					
					//得到其下的直属目录及文档
					List<SecurityCategory> children = this.securityCategoryManager.getSubCategories(tmpCategory.getCategoryId());
					List<SecurityDocument> documents = this.securityDocumentManager.getAllDocuments(tmpCategory.getCategoryId());
					
//					List<AppCategory> childrenVos = new ArrayList<AppCategory>();
					for(SecurityCategory subCategory : children){
						AppCategory subVo = new AppCategory();
						subVo.setCategoryId(subCategory.getCategoryId());
						subVo.setCategoryName(subCategory.getCategoryName());
						subVo.setPid(tmpCategory.getCategoryId());
						subVo.setDisplayNo(subCategory.getDisplayNo());
						subVo.setLayer(subCategory.getLayer() - 1);
						categoryVos.add(subVo);
					}
//					vo.setChildren(childrenVos);
					
//					List<AppDocument> documentVos = new ArrayList<AppDocument>();
					for(SecurityDocument tmpDocument : documents){
						AppDocument documentVo = new AppDocument();
						documentVo.setDocumentId(tmpDocument.getDocumentId());
						documentVo.setDocumentTitle(tmpDocument.getDocumentTitle());
						documentVo.setCategoryId(tmpDocument.getCategoryId());
						documentVo.setLayer(tmpCategory.getLayer());
						
						documentVos.add(documentVo);
					}
//					vo.setDocuments(documentVos);
					
					categoryVos.add(vo);
				}
			
			}else {
				message = "请登录！";
				success = false;
			}
			
			
		} catch (Exception e) {
			message = "后台出错，请重试！";
			success = false;
			e.printStackTrace();
			logger.error("出现错误================="+e.getLocalizedMessage());
			logger.error(e,e.fillInStackTrace());
		}
		
		jsonObj.put("success", success);
		jsonObj.put("message", message);
		jsonObj.put("result_category", categoryVos);
		jsonObj.put("result_document", documentVos);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	/**
	 * 移动端获取安全文档目录（第二页面，包含第三层及以下，及每一层下面的文档）
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=getSecondCategories")
	@Transactional
	public void getSecondCategories(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;

		List<AppCategory> categoryVos = new ArrayList<AppCategory>();
		List<AppDocument> documentVos = new ArrayList<AppDocument>();
		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		String categoryId = request.getParameter("categoryId");
		
		try {
			
			if(user != null){
				if(StringUtil.isNotEmpty(categoryId)){
					SecurityCategory category = (SecurityCategory)this.securityCategoryManager.get(Integer.valueOf(categoryId));
					if(category != null && category.getCategoryId().intValue() > 0){
						addSubItems(categoryVos, category);
						
						//得到自己下的文档
						List<SecurityDocument> documents_self = this.securityDocumentManager.getAllDocuments(Integer.valueOf(categoryId));
						for(SecurityDocument tmpDocument : documents_self){
							AppDocument documentVo = new AppDocument();
							documentVo.setDocumentId(tmpDocument.getDocumentId());
							documentVo.setDocumentTitle(tmpDocument.getDocumentTitle());
							documentVo.setCategoryId(tmpDocument.getCategoryId());
							documentVo.setLayer(category.getLayer());
							
							documentVos.add(documentVo);
						}
						
						//得到其下的所有文档
						List<SecurityCategory> allSubItems = this.securityCategoryManager.getAllSubCategories(Integer.valueOf(categoryId));
						for(SecurityCategory tmpCategory : allSubItems){
							List<SecurityDocument> documents_o = this.securityDocumentManager.getAllDocuments(tmpCategory.getCategoryId());
//							List<AppDocument> documentVos_o = new ArrayList<AppDocument>();
							for(SecurityDocument tmpDocument : documents_o){
								AppDocument documentVo = new AppDocument();
								documentVo.setDocumentId(tmpDocument.getDocumentId());
								documentVo.setDocumentTitle(tmpDocument.getDocumentTitle());
								documentVo.setCategoryId(tmpDocument.getCategoryId());
								documentVo.setLayer(tmpCategory.getLayer());
								
								documentVos.add(documentVo);
							}
						}
						
					}
					
//					categoryVos = ListUtils.removeDuplicate(categoryVos);
					
				}else {
					success =false;
					message = "参数不完整！";
				}
				
				
			
			}else {
				message = "请登录！";
				success = false;
			}
			
			
		} catch (Exception e) {
			message = "后台出错，请重试！";
			success = false;
			e.printStackTrace();
			logger.error("出现错误================="+e.getLocalizedMessage());
			logger.error(e,e.fillInStackTrace());
		}
		
		jsonObj.put("success", success);
		jsonObj.put("message", message);
		jsonObj.put("result_category", categoryVos);
		jsonObj.put("result_document", documentVos);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	
	private void addSubItems(List<AppCategory> list,SecurityCategory category){
		
		
		try {
			//得到其下子目录
			List<SecurityCategory> firstList = this.securityCategoryManager.getSubCategories(category.getCategoryId());
			for(SecurityCategory tmpCategory : firstList){
				AppCategory vo = new AppCategory();
				vo.setCategoryId(tmpCategory.getCategoryId());
				vo.setCategoryName(tmpCategory.getCategoryName());
				vo.setPid(tmpCategory.getPid());
				vo.setDisplayNo(tmpCategory.getDisplayNo());
				vo.setLayer(tmpCategory.getLayer() - 1);
				
//				//得到其下的目录及文档
//				List<SecurityDocument> documents = this.securityDocumentManager.getAllDocuments(tmpCategory.getCategoryId());
//				
//				List<AppDocument> documentVos = new ArrayList<AppDocument>();
//				for(SecurityDocument tmpDocument : documents){
//					AppDocument documentVo = new AppDocument();
//					documentVo.setDocumentId(tmpDocument.getDocumentId());
//					documentVo.setDocumentTitle(tmpDocument.getDocumentTitle());
//					documentVo.setCategoryId(tmpDocument.getCategoryId());
//					
//					documentVos.add(documentVo);
//				}
//				vo.setDocuments(documentVos);
				
				list.add(vo);
				
				addSubItems(list, tmpCategory);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 移动端查询文档
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=queryDocument")
	@Transactional
	public void queryDocument(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;
		List<AppDocument> documentVos = new ArrayList<AppDocument>();
		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		String keyword = request.getParameter("keyword");
		
		try {
			
			if(user != null){
				if(StringUtil.isNotEmpty(keyword)){
					String hql = " from SecurityDocument where documentTitle like '%" + keyword + "%' order by documentTitle";
					List<SecurityDocument> documents = this.securityDocumentManager.getResultByQueryString(hql);
					for(SecurityDocument tmpDocument : documents){
						AppDocument documentVo = new AppDocument();
						documentVo.setDocumentId(tmpDocument.getDocumentId());
						documentVo.setDocumentTitle(tmpDocument.getDocumentTitle());
						documentVo.setCategoryId(tmpDocument.getCategoryId());
						documentVo.setLayer(0);
						
						documentVos.add(documentVo);
					}
				}else{
					message = "请输入文档标题关键字！";
					success = false;
				}
			
			}else {
				message = "请登录！";
				success = false;
			}
			
			
		} catch (Exception e) {
			message = "后台出错，请重试！";
			success = false;
			e.printStackTrace();
			logger.error("出现错误================="+e.getLocalizedMessage());
			logger.error(e,e.fillInStackTrace());
		}
		
		jsonObj.put("success", success);
		jsonObj.put("message", message);
		jsonObj.put("result", documentVos);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
}
