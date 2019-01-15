package com.kwchina.work.patrol.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.common.page.PageList;
import com.kwchina.core.common.page.Pages;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.json.JSONConvert;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.patrol.entity.SmallCategory;
import com.kwchina.work.patrol.service.SmallCategoryManager;
import com.kwchina.work.patrol.vo.SmallCategoryVo;

@Controller
@RequestMapping("/patrol/smallCategory.htm")
public class SmallCategoryController extends BasicController {

	@Resource
	private SmallCategoryManager smallCategoryManager;
	

	/***
	 * 显示所有小项
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="method=list")
	public void list(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 构造查询语句
		String bigIdStr = request.getParameter("bigId");
		String[] queryString = new String[2];
		queryString[0] = "from SmallCategory category where 1=1 and valid = 1";
		queryString[1] = "select count(*) from  SmallCategory category where 1=1 and valid = 1";
		
		if(StringUtil.isNotEmpty(bigIdStr)){
			queryString[0] += " and category.bigId = " + bigIdStr;
			queryString[1] += " and category.bigId = " + bigIdStr;
		}
		
		queryString = this.smallCategoryManager.generateQueryString(queryString, getSearchParams(request));
		
		String page = request.getParameter("page"); // 当前页
		String rowsNum = request.getParameter("rows"); // 每页显示的行数
		Pages pages = new Pages(request);
		pages.setPage(Integer.valueOf(page));
		pages.setPerPageNum(Integer.valueOf(rowsNum));

		PageList pl = this.smallCategoryManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
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
	 * @param smallCategoryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, SmallCategoryVo smallCategoryVo) throws Exception {

		Integer xId = smallCategoryVo.getxId();
		SmallCategory category = null;
		//修改
		if (xId != null && xId.intValue() > 0) {
			category = (SmallCategory) this.smallCategoryManager.get(xId);

			BeanUtils.copyProperties(smallCategoryVo, category);
		}

		request.setAttribute("_Category", category);

		return "editSmallCategory";
	}

	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param smallCategoryVo
	 * @throws Exception
	 */
	@RequestMapping(params = "method=save")
	public void save(HttpServletRequest request, HttpServletResponse response, SmallCategoryVo smallCategoryVo) throws Exception {

		SmallCategory category = new SmallCategory();
		Integer xId = smallCategoryVo.getxId();

		if (xId != null && xId.intValue() != 0) {
			category = (SmallCategory) this.smallCategoryManager.get(xId.intValue());
		}else {
			Integer smallId = this.smallCategoryManager.getMaxId("smallId");
			category.setSmallId(smallId);
		}

		BeanUtils.copyProperties(category, smallCategoryVo);
		
		category.setValid(true);
		
		this.smallCategoryManager.save(category);

	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param smallCategoryVo
	 * @throws Exception
	 */
	@RequestMapping(params = "method=delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, SmallCategoryVo smallCategoryVo) throws Exception {
		
		Integer xId = smallCategoryVo.getxId();
		if (xId != null && xId.intValue() > 0) {
			SmallCategory category = (SmallCategory)this.smallCategoryManager.get(xId);
			category.setValid(false);
			this.smallCategoryManager.save(category);
			
		}
	}
}
