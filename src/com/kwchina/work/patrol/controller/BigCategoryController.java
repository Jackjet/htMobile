package com.kwchina.work.patrol.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kwchina.work.sys.LoginConfirm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kwchina.core.base.entity.User;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.common.page.PageList;
import com.kwchina.core.common.page.Pages;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.json.JSONConvert;
import com.kwchina.work.patrol.entity.BigCategory;
import com.kwchina.work.patrol.entity.SmallCategory;
import com.kwchina.work.patrol.service.BigCategoryManager;
import com.kwchina.work.patrol.service.SmallCategoryManager;
import com.kwchina.work.patrol.vo.BigCategoryVo;
import com.kwchina.work.patrol.vo.OpBigCategoryVo;
import com.kwchina.work.patrol.vo.OpSmallCategory;

@Controller
@RequestMapping("/patrol/bigCategory.htm")
public class BigCategoryController extends BasicController {
	Logger logger = Logger.getLogger(BigCategoryController.class);


	@Resource
	private BigCategoryManager bigCategoryManager;
	
	@Resource
	private SmallCategoryManager smallCategoryManager;
	

	/***
	 * 显示所有大项
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="method=list")
	public void list(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 构造查询语句
		String[] queryString = new String[2];
		queryString[0] = "from BigCategory category where 1=1 and valid = 1";
		queryString[1] = "select count(*) from  BigCategory category where 1=1 and valid = 1";
		
		queryString = this.bigCategoryManager.generateQueryString(queryString, getSearchParams(request));
		
		String page = request.getParameter("page"); // 当前页
		String rowsNum = request.getParameter("rows"); // 每页显示的行数
		Pages pages = new Pages(request);
		pages.setPage(Integer.valueOf(page));
		pages.setPerPageNum(Integer.valueOf(rowsNum));

		PageList pl = this.bigCategoryManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
		List list = pl.getObjectList();

		// 定义返回的数据类型：json，使用了json-lib
		JSONObject jsonObj = new JSONObject();

		// 定义rows，存放数据
		JSONArray rows = new JSONArray();
		jsonObj.put("page", pl.getPages().getCurrPage()); // 当前页(名称必须为page)
		jsonObj.put("total", pl.getPages().getTotalPage()); // 总页数(名称必须为total)
		jsonObj.put("records", pl.getPages().getTotals()); // 总记录数(名称必须为records)

		JSONConvert convert = new JSONConvert();
		//通知Convert，哪些关联对象需要获取
		List awareObject = new ArrayList();
		rows = convert.modelCollect2JSONArray(list, awareObject);
		jsonObj.put("rows", rows); // 返回到前台每页显示的数据(名称必须为rows)

		// 设置字符编码
		response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	
	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, BigCategoryVo bigCategoryVo) throws Exception {

		Integer xId = bigCategoryVo.getxId();

		
		BigCategory category = null;
		//修改
		if (xId != null && xId.intValue() > 0) {
			category = (BigCategory) this.bigCategoryManager.get(xId);

			BeanUtils.copyProperties(bigCategoryVo, category);
		}

		request.setAttribute("_Category", category);

		return "editBigCategory";
	}

	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param bigCategoryVo
	 * @throws Exception
	 */
	@RequestMapping(params = "method=save")
	public void save(HttpServletRequest request, HttpServletResponse response, BigCategoryVo bigCategoryVo) throws Exception {

		BigCategory category = new BigCategory();
		Integer xId = bigCategoryVo.getxId();

		if (xId != null && xId.intValue() != 0) {
			category = (BigCategory) this.bigCategoryManager.get(xId.intValue());
		}else {
			Integer bigId = this.bigCategoryManager.getMaxId("bigId");
			category.setBigId(bigId);
		}

		BeanUtils.copyProperties(category, bigCategoryVo);
		
		category.setValid(true);
		
		this.bigCategoryManager.save(category);

	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param bigCategoryVo
	 * @throws Exception
	 */
	@RequestMapping(params = "method=delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, BigCategoryVo bigCategoryVo) throws Exception {
		
		Integer xId = bigCategoryVo.getxId();
		if (xId != null && xId.intValue() > 0) {
			BigCategory category = (BigCategory)this.bigCategoryManager.get(xId);
//			deleteChildren(category);
			category.setValid(false);
			this.bigCategoryManager.save(category);
			
		}
	}
	
	
	/**
	 * app端得到所有大项
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=getAllBigCategory")
	@Transactional
	public void getAllBigCategory(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;
		List<OpBigCategoryVo> rtnList = new ArrayList<OpBigCategoryVo>();

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		try {
			
			if(user != null){
				
				List<BigCategory> allBigs = this.bigCategoryManager.getAllValid();
				for(BigCategory tmpBigCategory : allBigs){
					OpBigCategoryVo bigVo = new OpBigCategoryVo();
					BeanUtils.copyProperties(bigVo, tmpBigCategory);
					
					//小项
					List<OpSmallCategory> smallList = new ArrayList<OpSmallCategory>();
					String smallHql = "from SmallCategory category where 1=1 and valid = 1 " +
							"and category.bigId = " + tmpBigCategory.getBigId() + " order by orderNo";
					List<SmallCategory> allSmalls = this.smallCategoryManager.getResultByQueryString(smallHql);
					for(SmallCategory tmpSmall : allSmalls){
						OpSmallCategory smallVo = new OpSmallCategory();
						BeanUtils.copyProperties(smallVo, tmpSmall);
						smallList.add(smallVo);
					}
					bigVo.setSmallCategoryList(smallList);
					
					rtnList.add(bigVo);
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
		jsonObj.put("result", rtnList);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
}
