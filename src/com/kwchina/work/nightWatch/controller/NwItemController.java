package com.kwchina.work.nightWatch.controller;

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
import com.kwchina.work.nightWatch.entity.NwItem;
import com.kwchina.work.nightWatch.service.NwAreaItemManager;
import com.kwchina.work.nightWatch.service.NwAreaManager;
import com.kwchina.work.nightWatch.service.NwItemManager;
import com.kwchina.work.nightWatch.vo.NwItemVo;
import com.kwchina.work.patrol.entity.SmallCategory;
import com.kwchina.work.patrol.service.SmallCategoryManager;
import com.kwchina.work.patrol.vo.SmallCategoryVo;

@Controller
@RequestMapping("/nightWatch/item.htm")
public class NwItemController extends BasicController {

	@Resource
	private NwItemManager nwItemManager;
	
	@Resource
	private NwAreaManager nwAreaManager;
	
	@Resource
	private NwAreaItemManager nwAreaItemManager;
	

	/***
	 * 根据区域显示其下的小项
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="method=listByArea")
	public void listByArea(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 构造查询语句
		String areaId = request.getParameter("areaId");
		String[] queryString = new String[2];
		queryString[0] = "from NwItem instance where 1=1 and valid = 1";
		queryString[1] = "select count(*) from  NwItem instance where 1=1 and valid = 1";
		
		if(StringUtil.isNotEmpty(areaId)){
			queryString[0] += " and itemId in (select itemId from NwAreaItem where areaId = '" + areaId + "')";
			queryString[1] += " and itemId in (select itemId from NwAreaItem where areaId = '" + areaId + "')";
		}
		
		queryString = this.nwItemManager.generateQueryString(queryString, getSearchParams(request));
		
		String page = request.getParameter("page"); // 当前页
		String rowsNum = request.getParameter("rows"); // 每页显示的行数
		Pages pages = new Pages(request);
		pages.setPage(Integer.valueOf(page));
		pages.setPerPageNum(Integer.valueOf(rowsNum));

		PageList pl = this.nwItemManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
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
	
	
	/***
	 * 根据所有的小项
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="method=list")
	public void list(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 构造查询语句
		String[] queryString = new String[2];
		queryString[0] = "from NwItem instance where 1=1 and valid = 1";
		queryString[1] = "select count(*) from  NwItem instance where 1=1 and valid = 1";
		
		
		queryString = this.nwItemManager.generateQueryString(queryString, getSearchParams(request));
		
		String page = request.getParameter("page"); // 当前页
		String rowsNum = request.getParameter("rows"); // 每页显示的行数
		Pages pages = new Pages(request);
		pages.setPage(Integer.valueOf(page));
		pages.setPerPageNum(Integer.valueOf(rowsNum));

		PageList pl = this.nwItemManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
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
	public String edit(HttpServletRequest request, HttpServletResponse response, NwItemVo nwItemVo) throws Exception {

		Integer xId = nwItemVo.getxId();
		NwItem item = null;
		//修改
		if (xId != null && xId.intValue() > 0) {
			item = (NwItem) this.nwItemManager.get(xId);

			BeanUtils.copyProperties(nwItemVo, item);
		}

		request.setAttribute("_Item", item);

		return "editItem";
	}

	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param smallCategoryVo
	 * @throws Exception
	 */
	@RequestMapping(params = "method=save")
	public void save(HttpServletRequest request, HttpServletResponse response, NwItemVo nwItemVo) throws Exception {

		NwItem item = new NwItem();
		Integer xId = nwItemVo.getxId();

		if (xId != null && xId.intValue() != 0) {
			item = (NwItem) this.nwItemManager.get(xId.intValue());
		}else {
			Integer itemId = this.nwItemManager.getMaxId("itemId");
			item.setItemId(itemId);
		}

		BeanUtils.copyProperties(item, nwItemVo);
		
		item.setValid(true);
		
		this.nwItemManager.save(item);

	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param smallCategoryVo
	 * @throws Exception
	 */
	@RequestMapping(params = "method=delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, NwItemVo nwItemVo) throws Exception {
		
		Integer xId = nwItemVo.getxId();
		if (xId != null && xId.intValue() > 0) {
			NwItem item = (NwItem)this.nwItemManager.get(xId);
			item.setValid(false);
			this.nwItemManager.save(item);
			
		}
	}
	
	@RequestMapping(params = "method=test")
	public void test(HttpServletRequest request, HttpServletResponse response, NwItemVo nwItemVo) throws Exception {
		String a = "游艇码头、小港池、3#泊位、4#泊位、5#泊位、高阳路大门、太平路大门、500号大门、候船大厅B2层、行李房B2层、联检大厅B1层、上港超市、邮食荟、上港旅行社、尚9一滴水大堂、尚9一滴水1F、尚9一滴水2F、超市卸货区、保税仓库、游艇保税区、尚9一滴水3F、停车库B2、停车库B3、北外滩滨江绿地东段、彩虹桥、上港花园、置阳段绿地、置阳段码头";
		String[] as = a.split("、");
		for(int i=0;i<as.length;i++){
			NwItem item = new NwItem();
			item.setItemId(i+1);
			item.setItemName(as[i]);
			item.setValid(false);
			this.nwItemManager.save(item);
		}
	}
}
