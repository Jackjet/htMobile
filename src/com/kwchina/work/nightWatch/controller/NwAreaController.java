package com.kwchina.work.nightWatch.controller;

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
import com.kwchina.work.nightWatch.entity.NwArea;
import com.kwchina.work.nightWatch.entity.NwAreaItem;
import com.kwchina.work.nightWatch.entity.NwItem;
import com.kwchina.work.nightWatch.service.NwAreaItemManager;
import com.kwchina.work.nightWatch.service.NwAreaManager;
import com.kwchina.work.nightWatch.service.NwItemManager;
import com.kwchina.work.nightWatch.vo.NwAreaVo;
import com.kwchina.work.nightWatch.vo.OpNwAreaVo;
import com.kwchina.work.nightWatch.vo.OpNwItemVo;

@Controller
@RequestMapping("/nightWatch/area.htm")
public class NwAreaController extends BasicController {
	Logger logger = Logger.getLogger(NwAreaController.class);


	@Resource
	private NwAreaManager nwAreaManager;
	
	@Resource
	private NwItemManager nwItemManager;
	
	@Resource
	private NwAreaItemManager nwAreaItemManager;
	

	/***
	 * 显示所有区域
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="method=list")
	public void list(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 构造查询语句
		String[] queryString = new String[2];
		queryString[0] = "from NwArea instance where 1=1 and valid = 1";
		queryString[1] = "select count(*) from  NwArea instance where 1=1 and valid = 1";
		
		queryString = this.nwAreaManager.generateQueryString(queryString, getSearchParams(request));
		
		String page = request.getParameter("page"); // 当前页
		String rowsNum = request.getParameter("rows"); // 每页显示的行数
		Pages pages = new Pages(request);
		pages.setPage(Integer.valueOf(page));
		pages.setPerPageNum(Integer.valueOf(rowsNum));

		PageList pl = this.nwAreaManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
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
	public String edit(HttpServletRequest request, HttpServletResponse response, NwAreaVo nwAreaVo) throws Exception {

		Integer xId = nwAreaVo.getxId();

		//得到所有的小项
		List<NwItem> allItems = this.nwItemManager.getAllValid();
		request.setAttribute("_Items", allItems);
		
		try {
			NwArea area = null;
			//修改
			if (xId != null && xId.intValue() > 0) {
				area = (NwArea) this.nwAreaManager.get(xId);

				BeanUtils.copyProperties(nwAreaVo, area);
				
				//Set<Integer> areaItemIds = new HashSet<Integer>();
				List<NwItem> areaItems = this.nwItemManager.getNwItemsByAreaId(area.getAreaId());
				
				Integer[] itemIds = new Integer[allItems.size()];
				
				for (int i = 0; i < allItems.size(); i++) {
					NwItem tmpItem = (NwItem) allItems.get(i);
					Integer tmpItemId = tmpItem.getItemId();
					
					for (NwItem item : areaItems) {
						Integer itemId = item.getItemId();
						if (itemId.intValue() == tmpItemId.intValue()) {
							//该项属于该区域
							itemIds[i] = itemId;
						}
					}
				}
				
				nwAreaVo.setItemIds(itemIds);
				request.setAttribute("_ItemIds", itemIds);
			}

			request.setAttribute("_Area", nwAreaVo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "editArea";
	}

	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param bigCategoryVo
	 * @throws Exception
	 */
	@RequestMapping(params = "method=save")
	public void save(HttpServletRequest request, HttpServletResponse response, NwAreaVo nwAreaVo) throws Exception {

		NwArea area = new NwArea();
		Integer xId = nwAreaVo.getxId();

		/** 获得区域对应的小项 */
		Integer[] itemIds = nwAreaVo.getItemIds();		
		List<NwItem> allItems = this.nwItemManager.getAllValid();
		if (xId != null && xId.intValue() != 0) {
			area = (NwArea) this.nwAreaManager.get(xId.intValue());
			
			List<NwItem> areaItems = this.nwItemManager.getNwItemsByAreaId(area.getAreaId());	//原来的小项集
			
			for (NwItem item : allItems) {
				boolean yetHave = false;
				boolean nowHave = false;
					
				if (areaItems != null && areaItems.size() != 0) {
					for (NwItem oriItem : areaItems) {
							
						if (oriItem.getItemId().intValue() == item.getItemId().intValue()) {
							yetHave = true;
						}
					}
				}
					
				if (itemIds != null && itemIds.length != 0) {
					for (int k = 0; k < itemIds.length; k++) {
						Integer nowItemId = itemIds[k];
							
						if (nowItemId.intValue() == item.getItemId().intValue()) {
							nowHave = true;
						}
					}
				}					
					
				if (yetHave && !nowHave) {
					NwAreaItem tmpInstance = this.nwAreaItemManager.getInstanceByItemIdAreaId(item.getItemId(), area.getAreaId());
					this.nwAreaItemManager.remove(tmpInstance);
				}
					
				if (!yetHave && nowHave) {
					NwAreaItem nwAreaItem = new NwAreaItem();
					nwAreaItem.setAreaId(area.getAreaId());
					nwAreaItem.setItemId(item.getItemId());
					
					this.nwAreaItemManager.save(nwAreaItem);
				}
					
			}
		}else {
			Integer areaId = this.nwAreaManager.getMaxId("areaId");
			area.setAreaId(areaId);
			
			if (itemIds != null && itemIds.length > 0) {
				for (int k = 0; k < itemIds.length; k++) {
					NwAreaItem nwAreaItem = new NwAreaItem();
					nwAreaItem.setAreaId(areaId);
					nwAreaItem.setItemId(itemIds[k]);
					
					this.nwAreaItemManager.save(nwAreaItem);
				}
			}
		}

		//BeanUtils.copyProperties(area, nwAreaVo);
		area.setAreaCode(nwAreaVo.getAreaCode());
		area.setAreaName(nwAreaVo.getAreaName());
		area.setOrderNo(nwAreaVo.getOrderNo());
		area.setValid(true);
		
		this.nwAreaManager.save(area);

	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param bigCategoryVo
	 * @throws Exception
	 */
	@RequestMapping(params = "method=delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, NwAreaVo nwAreaVo) throws Exception {
		
		Integer xId = nwAreaVo.getxId();
		if (xId != null && xId.intValue() > 0) {
			NwArea area = (NwArea)this.nwAreaManager.get(xId);
			area.setValid(false);
			this.nwAreaManager.save(area);
			
		}
	}
	
	
	/**
	 * app端得到所有区域
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=getAllArea")
	@Transactional
	public void getAllArea(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;
		List<OpNwAreaVo> rtnList = new ArrayList<OpNwAreaVo>();

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		try {
			
			if(user != null){
				
				List<NwArea> allAreas = this.nwAreaManager.getAllValid();
				for(NwArea tmpArea : allAreas){
					OpNwAreaVo areaVo = new OpNwAreaVo();
					BeanUtils.copyProperties(areaVo, tmpArea);
					
					//小项
					List<OpNwItemVo> itemVos = new ArrayList<OpNwItemVo>();
					
					String itemHql = " from NwItem ni where ni.valid = 1 and itemId in " +
							"(select itemId from NwAreaItem where areaId = '" + tmpArea.getAreaId() + "') order by ni.orderNo";
					List<NwItem> allItems = this.nwItemManager.getResultByQueryString(itemHql);
					for(NwItem tmpItem : allItems){
						OpNwItemVo itemVo = new OpNwItemVo();
						BeanUtils.copyProperties(itemVo, tmpItem);
						itemVos.add(itemVo);
					}
					
					areaVo.setItemVos(itemVos);
					
					rtnList.add(areaVo);
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
