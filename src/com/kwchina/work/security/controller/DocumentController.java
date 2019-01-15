package com.kwchina.work.security.controller;

import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.common.page.PageList;
import com.kwchina.core.common.page.Pages;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.file.UploadifyViewUtil;
import com.kwchina.core.util.json.JSONConvert;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.security.entity.DocumentCategory;
import com.kwchina.work.security.entity.DocumentInfor;
import com.kwchina.work.security.entity.DocumentLog;
import com.kwchina.work.security.service.DocumentCategoryManager;
import com.kwchina.work.security.service.DocumentInforManager;
import com.kwchina.work.security.service.DocumentLogManager;
import com.kwchina.work.security.util.DocumentConstant;
import com.kwchina.work.security.vo.DocumentCategoryVo;
import com.kwchina.work.security.vo.DocumentInforVo;
import com.kwchina.work.sys.SysCommonMethod;

@Controller
@RequestMapping("/security/document_ori.htm")
public class DocumentController extends BasicController {

	private static Log log = LogFactory.getLog(DocumentController.class);

	@Autowired
	private DocumentInforManager documentInforManager;

	@Autowired
	private DocumentCategoryManager documentCategoryManager;

	@Autowired
	private UserManager userManager;

	
	@Autowired
	private DocumentLogManager documentLogManager;

	
	/**
	 * 根据不同分类跳转到不同浏览页面
	 * @param inforPath
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=listBase")
	public String listBase(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//根据path获取categoryId
		String returnPath = "listDocument";
		String categoryId = request.getParameter("categoryId");
		if (categoryId == null || categoryId.length() == 0) {
			returnPath = "";
		}
		
		
		
		//假如该分类下包含子分类,则跳转到包含左边树状分类的inforBaseList页面
		if(categoryId != null && categoryId.length() > 0){
			DocumentCategory category = (DocumentCategory)this.documentCategoryManager.get(Integer.valueOf(categoryId));
			request.setAttribute("_Category", category);
			
			if (category.getChilds() != null && category.getChilds().size() > 0) {
				returnPath = "treeDocument";
			}
		}
		
		return returnPath;
	}
	
	
	/**
	 * 按照树状结构获取文档分类信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=list")
	public void listTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("entering 'DocumentAction.listTree' method...");
		}

		User user = SysCommonMethod.getSystemUser(request);
		String categoryId = request.getParameter("categoryId");
		
		ArrayList returnArray = new ArrayList();
		if(StringUtil.isNotEmpty(categoryId)){
			returnArray = this.documentCategoryManager.getCategoryAsTree(Integer.valueOf(categoryId), user);
		}else {
			returnArray = this.documentCategoryManager.getCategoryAsTree(DocumentConstant._Root_Category_Id, user);
		}
		 

		// 将set集置空
		Iterator it = returnArray.iterator();
		List returnList = new ArrayList();
		while (it.hasNext()) {
			DocumentCategory dc = (DocumentCategory) it.next();
			dc.setChilds(null);
			dc.setDocuments(null);
			returnList.add(dc);
		}

		String page = request.getParameter("page"); // 当前页
		String rowsNum = request.getParameter("rows"); // 每页显示的行数
		Pages pages = new Pages(request);
		pages.setPage(Integer.valueOf(page));
		pages.setPerPageNum(Integer.valueOf(rowsNum));

		// 定义返回的数据类型：json，使用了json-lib
		JSONObject jsonObj = new JSONObject();

		// 定义rows，存放数据
		JSONArray rows = new JSONArray();

		JSONConvert convert = new JSONConvert();
		rows = convert.modelCollect2JSONArray(returnList, new ArrayList());
		jsonObj.put("rows", rows); // 返回到前台每页显示的数据(名称必须为rows)

		// 设置字符编码
		response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}

	/**
	 * 获取查询条件数据(分类名称信息)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=getCategoryName")
	public void getCategoryName(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jsonObj = new JSONObject();
		JSONConvert convert = new JSONConvert();


		// 分类信息
		JSONArray categoryArray = new JSONArray();
		List categoryNames = this.documentInforManager.getCategoryName();
		categoryArray = convert.modelCollect2JSONArray(categoryNames, new ArrayList());
		jsonObj.put("_CategoryNames", categoryArray);

		// 设置字符编码
		response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}

	/**
	 * 修改分类名称
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 * @throws Exception
	 */
	@RequestMapping(params = "method=rename")
	public void save(HttpServletRequest request, HttpServletResponse response, DocumentCategoryVo vo) throws Exception {

		DocumentCategory category = new DocumentCategory();
		Integer categoryId = Integer.valueOf(request.getParameter("cateId"));

		if (categoryId != null && categoryId.intValue() != 0) {
			category = (DocumentCategory) this.documentCategoryManager.get(categoryId.intValue());
			category.setCategoryName(request.getParameter("categoryName"));
		}

		this.documentCategoryManager.save(category);

	}

	/**
	 * 删除分类名称
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=deleteCategoryName")
	public void deleteCategoryName(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Integer categoryId = Integer.valueOf(request.getParameter("cateId"));
		if (categoryId != null && categoryId.intValue() > 0) {
			DocumentCategory category = (DocumentCategory) this.documentCategoryManager.get(categoryId);
			deleteChildren(category);

		}
	}

	/**
	 * 
	 * @param category
	 */
	private void deleteChildren(DocumentCategory category) {
		Set childs = category.getChilds();

		if (childs != null && childs.size() > 0) {
			List tmpList = new ArrayList(childs);
			for (Iterator it = tmpList.iterator(); it.hasNext();) {
				DocumentCategory tpS = (DocumentCategory) it.next();

				// 从父对象移除
				childs.remove(tpS);

				deleteChildren(tpS);
			}
		}
		this.documentCategoryManager.remove(category);
	}

	/**
	 * 根据path获取categoryId
	 * 
	 * @param inforPath
	 * @return
	 */
//	public Integer getCategoryId(String inforPath) {
//
//		Integer categoryId = null;
//
//		// 如果没有带入categoryId,则根据path去判断
//		List allCategory = this.documentCategoryManager.getAll();
//		for (Iterator it = allCategory.iterator(); it.hasNext();) {
//			DocumentCategory category = (DocumentCategory) it.next();
//			String urlPath = category.getUrlPath();
//			if (urlPath != null && urlPath.length() > 0) {
//				if (inforPath.equals(urlPath)) {
//					categoryId = category.getCategoryId();
//					break;
//				}
//			}
//		}
//		return categoryId;
//	}

	/**
	 * 获取文档信息
	 * 
	 * @param inforPath
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=getDocumentInfor")
	public void getInforDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			/**
			 * 判断逻辑: 1. 通过url或参数categoryId对该分类进行判断: a. 如果该分类为叶子分类,则查看该分类的信息 b.
			 * 如果该分类不是叶子分类,则查看其所有子分类的信息 2.判断该分类是否为归档分类 a.若果不是,则直接查看文档大全下面的信息
			 * b.如果是,则通过documentType获取到工作流里的信息
			 */
			String ids = ""; // 所有子分类的Id
			String categoryId = request.getParameter("categoryId");
	
			// 当点击某个分类时
			if (categoryId != null && categoryId.length() > 0) {
	
				DocumentCategory category = (DocumentCategory) this.documentCategoryManager.get(Integer.valueOf(categoryId));
				request.setAttribute("_Category", category);
	
				if (category.getChilds() != null && category.getChilds().size() > 0) {
					// 需要获取该分类所有的子分类Id
					ids = this.documentCategoryManager.getChildIds(Integer.valueOf(categoryId));
				}
			}
	
			// 构造查询语句
			String[] queryString = new String[2];
			queryString[0] = " from DocumentInfor document where 1=1 and valid = 1 ";
			queryString[1] = " select count(document.documentId) from DocumentInfor document where 1=1 and valid = 1 ";
			String condition = "";
	
			// 分类及其子分类
			if (ids.length() > 0) {
				condition += " and document.category.categoryId in (" + ids + ")";
			} else if (categoryId != null && categoryId.length() > 0) {
				condition += " and document.category.categoryId = " + categoryId;
			}
			
//			
			queryString[0] += condition;
	
	
			String page = request.getParameter("page"); // 当前页
			String rowsNum = request.getParameter("rows"); // 每页显示的行数

			queryString[0] += condition;
			queryString[1] += condition;
			
			queryString = this.documentInforManager.generateQueryString(queryString, getSearchParams(request));
			
			Pages pages = new Pages(request);
			pages.setPage(Integer.valueOf(page));
			pages.setPerPageNum(Integer.valueOf(rowsNum));

			PageList pl = this.documentInforManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
			List infors = pl.getObjectList();
			

			// 定义返回的数据类型：json，使用了json-lib
			JSONObject jsonObj = new JSONObject();

			// 定义rows，存放数据
			JSONArray rows = new JSONArray();
			jsonObj.put("page", pl.getPages().getCurrPage()); // 当前页(名称必须为page)
			jsonObj.put("total", pl.getPages().getTotalPage()); // 总页数(名称必须为total)
			jsonObj.put("records", pl.getPages().getTotals()); // 总记录数(名称必须为records)

			JSONConvert convert = new JSONConvert();
			// 通知Convert，哪些关联对象需要获取
			List awareObject = new ArrayList();
			awareObject.add("category");
			rows = convert.modelCollect2JSONArray(infors, awareObject);
			jsonObj.put("rows", rows); // 返回到前台每页显示的数据(名称必须为rows)

			// 全部分类树状显示(需要判断分类的权限)
			ArrayList returnArray = this.documentCategoryManager.getCategoryAsTree(DocumentConstant._Root_Category_Id);
			request.setAttribute("_TREE", returnArray);

			// 所属部门
//			List departments = this.departmentManager.getDepartments();
//			request.setAttribute("_Departments", departments);

			// 设置字符编码
			response.setContentType(CoreConstant.CONTENT_TYPE);
			response.getWriter().print(jsonObj);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 新增或者修改信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, DocumentInforVo vo, Model model) throws Exception {

		DocumentInfor document = new DocumentInfor();
		Integer documentId = vo.getDocumentId();
		// 创建时间
		String createTime = new Date(System.currentTimeMillis()).toString();
		User user = SysCommonMethod.getSystemUser(request);

		//附件路径 
		String attachStrs = "";
		if (documentId != null && documentId.intValue() > 0) {
			// 修改信息时
			document = (DocumentInfor) documentInforManager.get(Integer.valueOf(documentId));
			attachStrs = document.getAttachment();
			
			BeanUtils.copyProperties(vo, document);

			// 所属分类、所属部门、作者
			vo.setCategoryId(document.getCategory().getCategoryId());
//			vo.setDepartmentId(document.getDepartmentId());
//			vo.setAuthorId(document.getAuthorId());

			// 创建时间
			if (document.getCreateTime() != null) {
				createTime = document.getCreateTime().toString();
			}

			// 对附件信息进行处理
			String attachmentFile = document.getAttachment();
			if (attachmentFile != null && !attachmentFile.equals("")) {
				String[][] attachment = processFile(attachmentFile);
				request.setAttribute("_Attachment_Names", attachment[1]);
				request.setAttribute("_Attachments", attachment[0]);
			}
		}
		request.setAttribute("_CreateTime", createTime);
		request.setAttribute("_AttachStrs", attachStrs);

		// 获取部门信息
//		List departments = this.departmentManager.getDepartments();
//		request.setAttribute("_Departments", departments);

		// 全部分类树状显示
		ArrayList returnArray = this.documentCategoryManager.getCategoryAsTree(DocumentConstant._Root_Category_Id);
		request.setAttribute("_TREE", returnArray);

		// 所有用户
//		List allSystemUsers = userManager.getAllValid();
//		request.setAttribute("_AllSystemUsers", allSystemUsers);


		return "editDocumentInfor";
	}

	/**
	 * 查看信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=view")
	public String view(HttpServletRequest request, HttpServletResponse response, Model model, DocumentCategoryVo vo) throws Exception {

		DocumentInfor document = new DocumentInfor();
		String rowId = request.getParameter("rowId");
		String categoryId = request.getParameter("categoryId");

		// 获取用户信息
		// List users = this.userManager.getAll();

		DocumentCategory category = null;
		if (rowId != null && rowId.length() > 0) {

			document = (DocumentInfor) documentInforManager.get(Integer.valueOf(rowId));
			request.setAttribute("_DocumentInfor", document);
			
			//作者
			Integer authorId = document.getAuthorId();
			if(authorId != null && authorId.intValue() > 0){
				User author = this.userManager.getUserByUserId(authorId);
				request.setAttribute("_Author", author.getName());
			}

			categoryId = document.getCategory().getCategoryId().toString();
			int categoryIdd = Integer.valueOf(categoryId);
			// 所属分类
			category = (DocumentCategory) this.documentCategoryManager.get(categoryIdd);
			BeanUtils.copyProperties(vo, category);

			// 父分类
			if (category.getParent() != null) {
				vo.setParentId(category.getParent().getCategoryId().intValue());
			}
			// 是否叶子分类
			if (category.isLeaf()) {
				vo.setLeaf(true);
			} else {
				vo.setLeaf(false);
			}
			// 对附件信息进行处理
			String attachmentFile = document.getAttachment();
			if (attachmentFile != null && !attachmentFile.equals("")) {
				
				String[][] attachment = UploadifyViewUtil.viewAttachment(attachmentFile);

				request.setAttribute("_Attachment_Names", attachment[1]);
				request.setAttribute("_Attachments", attachment[0]);
				request.setAttribute("_Attachment_Sizes", attachment[2]);
			}


		}

		return "viewDocumentInfor";
	}

	/**
	 * 保存信息
	 * 
	 * @param request
	 * @param response
	 * @param multipartRequest
	 * @throws Exception
	 */
	@RequestMapping(params = "method=save")
	public void save(HttpServletRequest request, HttpServletResponse response, Model model, DocumentInforVo vo) throws Exception {//, DefaultMultipartHttpServletRequest multipartRequest

		DocumentInfor documentInfor = new DocumentInfor();
		Integer documentId = vo.getDocumentId();

		// 当前登录用户
		User user = SysCommonMethod.getSystemUser(request);
		Timestamp now = new Timestamp(System.currentTimeMillis());

		String oldFiles = "";

		boolean isEdit = false;
		
		DocumentInfor oldDocument = new DocumentInfor();
		// 修改保存时，记录修改记录
		if (documentId != null && documentId.intValue() > 0) {
			documentInfor = (DocumentInfor) this.documentInforManager.get(documentId);
			oldDocument = documentInfor;
			isEdit = true;
		}else {
			documentInfor.setCreateTime(now);
			// 保存作者信息
//			User author = (User) this.userManager.getUserByUserId(Integer.valueOf(request.getParameter("authorId")));
			documentInfor.setAuthorId(user.getUserId());

			// 保存部门信息
			documentInfor.setDepartmentId(user.getDepartmentId());
		}

		BeanUtils.copyProperties(documentInfor, vo);


		// 保存文档分类
		DocumentCategory category = new DocumentCategory();
		category.setCategoryId(Integer.valueOf(request.getParameter("categoryId")));

		documentInfor.setCategory(category);

		String attachment = request.getParameter("uploadAttachment");
		documentInfor.setAttachment(attachment);
		documentInfor.setValid(true);

		this.documentInforManager.save(documentInfor);
		
		if(isEdit){
			DocumentLog dl = new DocumentLog();
			
			
			
			// 内容
			String logContent = "";
			
			boolean changeTitle = false;
			boolean changeAttach = false;
			if(!oldDocument.getDocumentTitle().equals(documentInfor.getDocumentTitle())){
				logContent += "标题由‘"+oldDocument.getDocumentTitle()+"’改为‘"+documentInfor.getDocumentTitle()+"’";
				changeTitle = true;
			}
			if(!oldDocument.getAttachment().equals(documentInfor.getAttachment())){
				logContent += "附件由‘"+oldDocument.getAttachment()+"’改为‘"+documentInfor.getAttachment()+"’";
				changeAttach = true;
			}
			
			if(changeTitle || changeAttach){
				dl.setDocument(documentInfor);
				dl.setOperateTime(now);
				dl.setOperatorId(user.getUserId());
				dl.setOperatorName(user.getName());
				dl.setOperateLog(logContent);
				this.documentLogManager.save(dl);
			}
		}

		/**
		 * 该语句不放在编辑页面的原因: 若在编辑页面提交数据后立即执行window.close()操作, 则后台无法取到编辑页面的数据.
		 * (此情况仅在页面包含附件操作时存在)
		 */
		PrintWriter out = response.getWriter();
		out.print("<script language='javascript'>");
		out.print("var returnArray = [\"refresh\"," + documentInfor.getCategory().getCategoryId() + "];");
		out.print("window.returnValue = returnArray;");
		out.print("window.close();");
		out.print("</script>");

	}


	/**
	 * 删除相关信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String rowIds = request.getParameter("rowIds");
		if (rowIds != null && rowIds.length() > 0) {
			String[] detleteIds = rowIds.split(",");
			if (detleteIds.length > 0) {
				for (int i = 0; i < detleteIds.length; i++) {

					Integer documentId = Integer.valueOf(detleteIds[i]);
					DocumentInfor documentInfor = (DocumentInfor) this.documentInforManager.get(documentId);

					documentInfor.setValid(false);
					this.documentInforManager.save(documentInfor);
				}
			}
		}
	}
	

	/**
	 * 移动端查看安全手册（第一层页面，两级目录）
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
//	@RequestMapping(params="method=viewNightWatch")
//	@Transactional
//	public void viewNightWatch(HttpServletRequest request,HttpServletResponse response)throws Exception {
//		String message = "";
//		boolean success = true;
////		OpNightWatchVo vo = new OpNightWatchVo();
//		List<NwWorkDetailVo> rtnList = new ArrayList<NwWorkDetailVo>();
//
//		JSONObject jsonObj = new JSONObject();
//		//用户
//		String token = request.getParameter("token");
//		User user = LoginConfirm.loginUserMap.get(token);
//		
//		//参数：
//		String workId = request.getParameter("workId");
//		
//		try {
//			
//			if(user != null){
//				
//				if(StringUtil.isNotEmpty(workId)){
//					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					//根据workId得到信息
////					NwWork workItem = this.nwWorkManager.getWorkByWorkId(Integer.valueOf(workId));
//					
//					//得到下面的巡更项
//					List<NwWorkDetail> allDetails = this.nwWorkDetailManager.getDetailsByWorkId(Integer.valueOf(workId));
//					
//					for(NwWorkDetail tmpDetail : allDetails){
//						NwWorkDetailVo detailVo = new NwWorkDetailVo();
//						BeanUtils.copyProperties(detailVo, tmpDetail);
//						NwItem smallCategory = this.nwItemManager.getNwItemByItemId(tmpDetail.getItemId());
//						detailVo.setItemName(smallCategory.getItemName());
//						if(tmpDetail.getBeginTime() != null){
//							detailVo.setBeginTimeStr(sf.format(tmpDetail.getBeginTime()));
//						}
//						if(tmpDetail.getFinishTime() != null){
//							detailVo.setFinishTimeStr(sf.format(tmpDetail.getFinishTime()));
//						}
//						if(tmpDetail.getExecuterId() != null && tmpDetail.getExecuterId().intValue() > 0){
//							User executer = this.userManager.getUserByUserId(tmpDetail.getExecuterId());
//							detailVo.setExecuterName(executer.getName());
//						}
//						
//						
//						//设置异常信息detailLogVo
//						List<NwDetailLogVo> logVos = new ArrayList<NwDetailLogVo>();
//						List<NwDetailLog> allLogs = this.nwDetailLogManager.getLogsByDetailId(tmpDetail.getDetailId());
//						for(NwDetailLog tmpLog : allLogs){
//							NwDetailLogVo logVo = new NwDetailLogVo();
//							BeanUtils.copyProperties(logVo, tmpLog);
//							if(tmpLog.getLogTime() != null){
//								logVo.setLogTimeStr(sf.format(tmpLog.getLogTime()));
//							}
//							if(tmpLog.getSolveTime() != null){
//								logVo.setSolveTimeStr(sf.format(tmpLog.getSolveTime()));
//							}
//							if(tmpLog.getSubmitTime() != null){
//								logVo.setSubmitTimeStr(sf.format(tmpLog.getSubmitTime()));
//							}
//							
//							logVos.add(logVo);
//						}
//						
//						detailVo.setLogs(logVos);
//						
//
//						rtnList.add(detailVo);
//					}
//					
//				}else{
//					message = "workId必填！";
//					success = false;
//				}
//			
//			}else {
//				message = "请登录！";
//				success = false;
//			}
//			
//			
//		} catch (Exception e) {
//			message = "后台出错，请重试！";
//			success = false;
//			e.printStackTrace();
//			logger.error("出现错误================="+e.getLocalizedMessage());
//			logger.error(e,e.fillInStackTrace());
//		}
//		
//		jsonObj.put("success", success);
//		jsonObj.put("message", message);
//		jsonObj.put("result", rtnList);
//		
//		//设置字符编码
//        response.setContentType(CoreConstant.CONTENT_TYPE);
//		response.getWriter().print(jsonObj);
//	}


}
