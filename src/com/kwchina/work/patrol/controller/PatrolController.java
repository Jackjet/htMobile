package com.kwchina.work.patrol.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.common.page.PageList;
import com.kwchina.core.common.page.Pages;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.DateHelper;
import com.kwchina.core.util.file.GKFileViewUtil;
import com.kwchina.core.util.json.JSONConvert;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.patrol.entity.BigCategory;
import com.kwchina.work.patrol.entity.ItemDetail;
import com.kwchina.work.patrol.entity.SmallCategory;
import com.kwchina.work.patrol.entity.WorkItem;
import com.kwchina.work.patrol.entity.WorkList;
import com.kwchina.work.patrol.service.BigCategoryManager;
import com.kwchina.work.patrol.service.ItemDetailManager;
import com.kwchina.work.patrol.service.SmallCategoryManager;
import com.kwchina.work.patrol.service.WorkItemManager;
import com.kwchina.work.patrol.service.WorkListManager;
import com.kwchina.work.patrol.vo.ItemDetailVo;
import com.kwchina.work.patrol.vo.OpBigCategoryVo;
import com.kwchina.work.patrol.vo.OpPatrolVo;
import com.kwchina.work.patrol.vo.PcPatrolVo;
import com.kwchina.work.util.JPushUtils;


@Controller
@RequestMapping("/patrol.htm")
public class PatrolController extends BasicController {
	Logger logger = Logger.getLogger(PatrolController.class);
	
	
	
	@Resource
	private WorkListManager workListManager;
	
	@Resource
	private WorkItemManager workItemManager;
	
	@Resource
	private ItemDetailManager itemDetailManager;
	
	@Resource
	private BigCategoryManager bigCategoryManager;
	
	@Resource
	private SmallCategoryManager smallCategoryManager;
	
	@Resource
	private UserManager userManager;
	
	@Resource
	private DepartmentManager departmentManager;
	
	
	/***
	 * PC端巡检任务列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="method=patrolList")
	public void patrolList(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 构造查询语句
//		String areaId = request.getParameter("areaId");
		String[] queryString = new String[2];
		queryString[0] = "from WorkItem instance where 1=1 ";
		queryString[1] = "select count(*) from  WorkItem instance where 1=1 ";
		
		String mainCondition = "";
		
		queryString = this.workItemManager.generateQueryString(queryString, getSearchParams(request));
		//构造查询条件
//		String[] params = getSearchParams(request);
//		String conditions = this.workItemManager.generateCondition(params[3]);
//		//查询操作时,加入查询条件
//		if (params[2].equals("true") && conditions != null && conditions.length() > 0) {
////			queryString[0] += " and (" + conditions + ")";
////			queryString[1] += " and (" + conditions + ")";
//			mainCondition += " and listId in (select listId from WorkList where 1=1 and "+conditions+")";
//		}
//		
//		queryString[0] += mainCondition;
//		queryString[1] += mainCondition;
//		queryString[0] += " order by " + params[0] + " " + params[1];
		
		String page = request.getParameter("page"); // 当前页
		String rowsNum = request.getParameter("rows"); // 每页显示的行数
		Pages pages = new Pages(request);
		pages.setPage(Integer.valueOf(page));
		pages.setPerPageNum(Integer.valueOf(rowsNum));

		PageList pl = this.workItemManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
		List<WorkItem> list = pl.getObjectList();
		List<PcPatrolVo> vos = new ArrayList<PcPatrolVo>();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//转vo
		for(WorkItem tmpWork : list){
			PcPatrolVo vo = new PcPatrolVo();
			vo.setItemId(tmpWork.getItemId());
			vo.setListId(tmpWork.getListId());
			vo.setValid(tmpWork.isValid());
			
			WorkList workList = this.workListManager.getWorkListByListId(tmpWork.getListId());
			vo.setWorkName(workList.getWorkTitle());
			vo.setExecuterId(tmpWork.getOperatorId());
			User executer = this.userManager.getUserByUserId(tmpWork.getOperatorId());
			vo.setExecuterName(executer.getName());
			
			vo.setCreaterId(workList.getCreaterId());
			User creater = this.userManager.getUserByUserId(workList.getCreaterId());
			vo.setCreaterName(creater.getName());
			
			vo.setWorkState(tmpWork.getWorkState());
			
			if(tmpWork.getBeginTime() != null){
				vo.setBeginTime(sf.format(tmpWork.getBeginTime()));
			}
			if(tmpWork.getEndTime() != null){
				vo.setEndTime(sf.format(tmpWork.getEndTime()));
			}
			if(tmpWork.getFinishTime() != null){
				vo.setFinishTime(sf.format(tmpWork.getFinishTime()));
			}
			
			
			vos.add(vo);
		}
		
		//排序
//		Comparator<?> mycmp = ComparableComparator.getInstance();
//        mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null
//        if (params[1].equals("desc")) {
//            mycmp = ComparatorUtils.reversedComparator(mycmp); // 逆序
//        }
//        
//        String orderStr = params[0];
//        if(orderStr.equals("sta")){
//        	orderStr = "workState";
//        }
//        Collections.sort(vos, new BeanComparator(orderStr, mycmp));

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
		rows = convert.modelCollect2JSONArray(vos, awareObject);
		jsonObj.put("rows", rows); // 返回到前台每页显示的数据(名称必须为rows)

		// 设置字符编码
		response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	
	/**
	 * 巡检项
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=getBigs")
	@Transactional
	public void getBigs(HttpServletRequest request,HttpServletResponse response)throws Exception {

		JSONObject jsonObj = new JSONObject();
		//设置OpBigCategoryVo
		List<OpBigCategoryVo> bigVos = new ArrayList<OpBigCategoryVo>();
		
		String workItemIdStr = request.getParameter("itemId");
		if(StringUtil.isNotEmpty(workItemIdStr)){
			int itemId = Integer.valueOf(workItemIdStr);
			WorkItem tmpWork = this.workItemManager.getItemByItemId(itemId);
			WorkList workList = this.workListManager.getWorkListByListId(tmpWork.getListId());
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String bigIds = workList.getBigItems();
			String[] bigIdArray = bigIds.split(",");
			for(String tmpBigId : bigIdArray){
				Integer tmpBigIdInt = Integer.valueOf(tmpBigId);
				BigCategory bigCategory = this.bigCategoryManager.getBigCategoryByBigId(tmpBigIdInt);
				
				OpBigCategoryVo bigVo = new OpBigCategoryVo();
				bigVo.setxId(bigCategory.getxId());
				bigVo.setBigId(tmpBigIdInt);
				bigVo.setCategoryName(bigCategory.getCategoryName());
				bigVo.setCategoryCode(bigCategory.getCategoryCode());
				bigVo.setOrderNo(bigCategory.getOrderNo());

				//设置详情项
				//先到所有工作详细项
				List<ItemDetailVo> detailVos = new ArrayList<ItemDetailVo>();
				List<ItemDetail> allDetails = this.itemDetailManager.getDetailsByItemIdBigId(tmpWork.getItemId(),tmpBigIdInt);
				for(ItemDetail tmpDetail : allDetails){
					ItemDetailVo detailVo = new ItemDetailVo();
					BeanUtils.copyProperties(detailVo, tmpDetail);
					SmallCategory smallCategory = this.smallCategoryManager.getSmallCategoryBySmallId(tmpDetail.getSmallId());
					detailVo.setSmallName(smallCategory.getCategoryName());
					detailVo.setBigName(bigCategory.getCategoryName());
					if(tmpDetail.getBeginTime() != null){
						detailVo.setBeginTimeStr(sf.format(tmpDetail.getBeginTime()));
					}
					if(tmpDetail.getEndTime() != null){
						detailVo.setEndTimeStr(sf.format(tmpDetail.getEndTime()));
					}
					
					detailVos.add(detailVo);
					
//					itemSum ++;
//					if(tmpDetail.getWorkState() == 1 && tmpDetail.getEndTime() != null){
//						itemDoneSum ++;
//					}
				}
				bigVo.setDetails(detailVos);
				bigVos.add(bigVo);
			}
			
		}
		

		jsonObj.put("result", bigVos);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	
	
	
	
	
	/**
	 * 新建巡检
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=newPatrol")
	@Transactional
	public void newPatrol(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String workTitle = request.getParameter("workTitle");   //标题
		String bigItems = request.getParameter("bigItems");     //大项bigId,用逗号拼接
		String executerId = request.getParameter("executerId"); //执行人

		String loopType = request.getParameter("loopType");     //循环类型
		String weekDays = request.getParameter("weekDays");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		
		//执行时间段数组，例：opTimes=[{'beginTime':'09:00','endTime':'11:00'},{'beginTime':'12:00','endTime':'14:00'}]
		String opTimes = request.getParameter("opTimes");
		
//		String beginTime = request.getParameter("beginTime");
//		String endTime = request.getParameter("endTime");
		
		workTitle = StringUtil.isNotEmpty(workTitle) ? workTitle : "日常巡检";
		loopType = StringUtil.isNotEmpty(loopType) ? loopType : "0";
//		beginTime = StringUtil.isNotEmpty(beginDate) ? beginTime : "00:00";
//		endTime = StringUtil.isNotEmpty(endDate) ? endTime : "23:59";
		Timestamp now = new Timestamp(System.currentTimeMillis());
		try {
			
			if(user != null){
				executerId = StringUtil.isNotEmpty(executerId) ? executerId : user.getUserId().toString();
				
				if(StringUtil.isNotEmpty(bigItems) && StringUtil.isNotEmpty(beginDate) && StringUtil.isNotEmpty(endDate)){
					WorkList workList = new WorkList();
					Integer listId = this.workListManager.getMaxId("listId");
					workList.setListId(listId);
					workList.setWorkTitle(workTitle);
					workList.setBigItems(bigItems);
					workList.setExecuterId(Integer.valueOf(executerId));
					workList.setCreaterId(user.getUserId());
					workList.setCreateTime(now);
					workList.setSysTime(now);
					workList.setLoopType(Integer.valueOf(loopType));
					workList.setBeginDate(Date.valueOf(beginDate));
					workList.setEndDate(Date.valueOf(endDate));
//					workList.setBeginTime(beginTime);
//					workList.setEndTime(endTime);
					workList.setWeekDays(weekDays);
					workList.setWorkState(0);
					workList.setValid(true);
					
					//1、先保存workList
					this.workListManager.save(workList);
					
					//2、再保存执行人的工作
					
					//循环每个时间段
					String[] opTimeArray = opTimes.split(";");
					for(String eachTimes : opTimeArray){
						if(StringUtil.isNotEmpty(eachTimes)){
							JSONObject obj = new JSONObject();
							obj = JSONObject.fromObject(eachTimes);
							
							if(obj != null){
								String beginTime = obj.getString("beginTime");
								String endTime = obj.getString("endTime");
								
								/*
								 * loopType = 0一次性，创建一条
								 * 1,每天，根据截止时间循环创建
								 * 2、每个工作日创建（周一至周五）
								 * 3、每周几创建
								 */
								int loopTypeInt = Integer.valueOf(loopType);
								int beginHour = Integer.valueOf(beginTime.split(":")[0]);  //开始的小时
								int endHour = Integer.valueOf(endTime.split(":")[0]);      //结束的小时
								List<String[]> timeArray = new ArrayList<String[]>();
								
								if(loopTypeInt == 0){
									String[] tmpArray = new String[2];
									tmpArray[0] = beginDate + " " + beginTime + ":00";
									tmpArray[1] = endDate + " " + endTime + ":59";
									timeArray.add(tmpArray);
								}else if(loopTypeInt == 1){
									java.util.Date bDate = DateHelper.getDate(beginDate);
									java.util.Date eDate = DateHelper.getDate(endDate);
									for(int i=0;i<DateHelper.daysBetween(bDate, eDate)+1;i++){
										//System.out.println(DateHelper.getString(DateHelper.addDay(bDate, i)));
										String tmpBTimeStr = DateHelper.getString(DateHelper.addDay(bDate, i)) + " " + beginTime + ":00";
										String tmpETimeStr = "";
										if(endHour < beginHour){  //结束小时 小于 开始小时时,认为是跨天
											tmpETimeStr = DateHelper.getString(DateHelper.addDay(bDate, i+1)) + " " + endTime + ":59";
										}else{
											tmpETimeStr = DateHelper.getString(DateHelper.addDay(bDate, i)) + " " + endTime + ":59";
										}
										String[] tmpArray = new String[2];
										tmpArray[0] = tmpBTimeStr;
										tmpArray[1] = tmpETimeStr;
										timeArray.add(tmpArray);
									}
								}else if(loopTypeInt == 2){
							        
							        java.util.Date bDate = DateHelper.getDate(beginDate);
									java.util.Date eDate = DateHelper.getDate(endDate);
									for(int i=0;i<DateHelper.daysBetween(bDate, eDate)+1;i++){
										Calendar cal = Calendar.getInstance();
								        cal.setTime(DateHelper.addDay(bDate, i));
								        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;  //周几,0-日 ,周一-1，周二-2
								        
								        if(w > 0 && w < 6){
								        	String tmpBTimeStr = DateHelper.getString(DateHelper.addDay(bDate, i)) + " " + beginTime + ":00";
											String tmpETimeStr = "";
											if(endHour < beginHour){  //结束小时 小于 开始小时时,认为是跨天
												tmpETimeStr = DateHelper.getString(DateHelper.addDay(bDate, i+1)) + " " + endTime + ":59";
											}else{
												tmpETimeStr = DateHelper.getString(DateHelper.addDay(bDate, i)) + " " + endTime + ":59";
											}
											String[] tmpArray = new String[2];
											tmpArray[0] = tmpBTimeStr;
											tmpArray[1] = tmpETimeStr;
											timeArray.add(tmpArray);
								        }
									}
								}else if(loopTypeInt == 3){
									
									weekDays.replaceAll("7", "0");
									String[] dayIndexs = weekDays.split(",");
									List<Integer> dayIndexArray = new ArrayList<Integer>();
									for(int i = 0;i<dayIndexs.length;i++){
										dayIndexArray.add(Integer.valueOf(dayIndexs[i]));
									}
									
									java.util.Date bDate = DateHelper.getDate(beginDate);
									java.util.Date eDate = DateHelper.getDate(endDate);
									for(int i=0;i<DateHelper.daysBetween(bDate, eDate)+1;i++){
										Calendar cal = Calendar.getInstance();
								        cal.setTime(DateHelper.addDay(bDate, i));
								        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;  //周几,0-日 ,周一-1，周二-2
								        
								        
								        if(dayIndexArray.contains(w)){
								        	String tmpBTimeStr = DateHelper.getString(DateHelper.addDay(bDate, i)) + " " + beginTime + ":00";
											String tmpETimeStr = "";
											if(endHour < beginHour){  //结束小时 小于 开始小时时,认为是跨天
												tmpETimeStr = DateHelper.getString(DateHelper.addDay(bDate, i+1)) + " " + endTime + ":59";
											}else{
												tmpETimeStr = DateHelper.getString(DateHelper.addDay(bDate, i)) + " " + endTime + ":59";
											}
											String[] tmpArray = new String[2];
											tmpArray[0] = tmpBTimeStr;
											tmpArray[1] = tmpETimeStr;
											timeArray.add(tmpArray);
								        }
									}
								}
								
								//批量生成工作项
								List<WorkItem> allWorkItems = new ArrayList<WorkItem>();
								for(String[] tmpTArray : timeArray){
									
									Timestamp startTime = Timestamp.valueOf(tmpTArray[0]);
									Timestamp toTime = Timestamp.valueOf(tmpTArray[1]);
									
									WorkItem workItem = new WorkItem();
									Integer itemId = this.workItemManager.getMaxId("itemId");
									workItem.setItemId(itemId);
									workItem.setValid(true);
									workItem.setListId(listId);
									workItem.setWorkState(0);
									workItem.setOperatorId(Integer.valueOf(executerId));
									workItem.setReadSafeRules(0);
									workItem.setSysTime(now);
//									String[] tmpArray = new String[2];
//									tmpArray[0] = beginDate + " " + beginTime + ":00";
//									tmpArray[1] = endDate + " " + endTime + ":00";
//									timeArray.add(tmpArray);
									
									workItem.setBeginTime(startTime);
									workItem.setEndTime(toTime);
									
									this.workItemManager.save(workItem);
									allWorkItems.add(workItem);
								}
								
								
								//3、生成每个大项下的所有小项为最终工作项
								String[] bigItemIds = bigItems.split(",");
								for(WorkItem tmpWorkItem : allWorkItems){
									for(int i = 0;i<bigItemIds.length;i++){
										Integer tmpBigId = Integer.valueOf(bigItemIds[i]);
										
//										BigCategory bigCategory = this.bigCategoryManager.getBigCategoryByBigId(tmpBigId);
										List<SmallCategory> smallList = this.smallCategoryManager.getSmallCategoriesByBigId(tmpBigId);
										for(SmallCategory tmpSmall : smallList){

											ItemDetail itemDetail = new ItemDetail();
											Integer detailId = this.itemDetailManager.getMaxId("detailId");
											itemDetail.setDetailId(detailId);
											itemDetail.setListId(listId);
											itemDetail.setItemId(tmpWorkItem.getItemId());
											itemDetail.setBigId(tmpBigId);
											itemDetail.setSmallId(tmpSmall.getSmallId());
											itemDetail.setOpResult(0);
											itemDetail.setOperatorId(Integer.valueOf(executerId));
											itemDetail.setBeginTime(tmpWorkItem.getBeginTime());
											itemDetail.setSysTime(now);
											itemDetail.setWorkState(0);
											itemDetail.setValid(true);
											
											this.itemDetailManager.save(itemDetail);
										}
									}
									
								}
							}
						}
							
					}
					
					success = true;
					

					
//					/**************新建工作后，给执行人发送一条推送*****************/
//					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					String pushContent = user.getName()+" 于 "+sf.format(now)+" 创建了一条巡检任务【"+workTitle+"】给您，请点击更新！";
//					JPushUtils pushUtil = JPushUtils.getInstance();
//
//					User executer = this.userManager.getUserByUserId(Integer.valueOf(executerId));
//					pushUtil.pushNewWork( pushContent, 1, executer.getUserName());
					
				}else{
					message = "执行人、开始日期、结束日期三项必填！";
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
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	
	/**
	 * 熟知安全规范
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params="method=readRules")
	@Transactional
	public void readRules(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String itemId = request.getParameter("itemId");  
		
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(itemId)){
					WorkItem workItem = this.workItemManager.getItemByItemId(Integer.valueOf(itemId));
					
					//能进来说明已读过安全说明
					workItem.setReadSafeRules(1);
					this.workItemManager.save(workItem);
					
				}else{
					message = "参数不完整！";
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
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	/**
	 * 查看巡检详情(进入巡检)
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=opPatrol")
	@Transactional
	public void opPatrol(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;
		OpPatrolVo vo = new OpPatrolVo();

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String itemId = request.getParameter("itemId");
		
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(itemId)){
					vo = this.workItemManager.getPatrolDetail(Integer.valueOf(itemId));
					
				}else{
					message = "workId必填！";
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
		jsonObj.put("result", vo);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	
	/**
	 * 完成小项工作
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=opDetail")
	@Transactional
	public void opDetail(HttpServletRequest request,HttpServletResponse response,DefaultMultipartHttpServletRequest multipartRequest)throws Exception {
		String message = "";
		boolean success = true;

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String detailId = request.getParameter("detailId");
		String opResult = request.getParameter("opResult");
		String errorLog = request.getParameter("errorLog");
		
		logger.warn("巡检===上传小项===detailId===" + detailId);
		
		opResult = StringUtil.isNotEmpty(opResult) ? opResult : "1";
		Timestamp now = new Timestamp(System.currentTimeMillis());

		String finishTimeStr = request.getParameter("finishTime");
		Timestamp finishTime = null;
		
		if(StringUtil.isNotEmpty(finishTimeStr)){
			finishTime = Timestamp.valueOf(finishTimeStr);
		}else{
			finishTime = now;
		}
		

		
		String workFinishTimeStr = request.getParameter("workFinishTime");//任务的完成 时间 
		Timestamp workFinishTime = null;
		if(StringUtil.isNotEmpty(workFinishTimeStr)){
			workFinishTime = Timestamp.valueOf(workFinishTimeStr);
		}else{
			workFinishTime = now;
		}
		
		
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(detailId)){
					ItemDetail detail = this.itemDetailManager.getDetailByDetailId(Integer.valueOf(detailId));
					
					if(detail.getOpResult() != 0 && detail.getEndTime() != null){
						//已上传过的，无须理会
					}else{
						detail.setEndTime(finishTime);
						detail.setSysTime(now);
						detail.setErrorLog(errorLog);
						detail.setOpResult(Integer.valueOf(opResult));
						
						//附件
						String attachs = uploadAttachmentBySize(multipartRequest, "patrol");
						detail.setAttachs(attachs);
						
						detail.setWorkState(1);
						
						this.itemDetailManager.save(detail);
						
						//判断本次巡检小项是否已全部完成,如果已完成,则更改WorkItem及WorkList状态
						List<ItemDetail> allDetails = this.itemDetailManager.getDetailsByItemId(detail.getItemId());
						boolean hasAllDetailsDone = true;
						for(ItemDetail tmpDetail : allDetails){
							if(tmpDetail.getWorkState() == 0){
								hasAllDetailsDone = false;
								break;
							}
						}
						if(hasAllDetailsDone){
							WorkItem workItem = this.workItemManager.getItemByItemId(detail.getItemId());
							workItem.setFinishTime(workFinishTime);
							workItem.setSysTime(now);
							workItem.setWorkState(1);
							this.workItemManager.save(workItem);
							
							List<WorkItem> allItems = this.workItemManager.getItemsByListId(detail.getListId());
							boolean hasAllItemsDone = true;
							for(WorkItem tmpItem : allItems){
								if(tmpItem.getWorkState() == 0){
									hasAllItemsDone = false;
									break;
								}
							}
							if(hasAllItemsDone){
								WorkList workList = this.workListManager.getWorkListByListId(detail.getListId());
								workList.setFinishTime(workFinishTime);
								workList.setSysTime(now);
								workList.setWorkState(1);
								this.workListManager.save(workList);
							}
							
						}
					}
					
					
				}else{
					message = "参数不完整！";
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
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	
	/**
	 * 删除巡检
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=delPatrol")
	@Transactional
	public void delPatrol(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String itemId = request.getParameter("itemId");  
		
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(itemId)){
					WorkItem workItem = this.workItemManager.getItemByItemId(Integer.valueOf(itemId));
					workItem.setValid(false);
					this.workItemManager.save(workItem);
					
					//发送推送给执行任务者
					WorkList workList = this.workListManager.getWorkListByListId(workItem.getListId());
					if(workList != null && workList.getxId().intValue() > 0){
						Integer executerId = workItem.getOperatorId();
						if(executerId != null && executerId.intValue() > 0){
							User creater = this.userManager.getUserByUserId(executerId);
							if(creater != null && creater.getxId().intValue() > 0){
								
								String pushContent = user.getName()+" 取消了巡检任务【" + workList.getWorkTitle() + "】，请点击本通知更新！";
								JPushUtils pushUtil = JPushUtils.getInstance();
								pushUtil.pushDelPatrol(workItem.getItemId(), pushContent, 1, creater.getUserName());
							}
						}
					}
					
				}else{
					message = "参数不完整！";
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
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	
	/**
	 * 巡检统计
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=patrolReport")
	@Transactional
	public void patrolReport(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;
		
		int rightCount = 0;  //正常数
		int errorCount = 0;  //异常数
		
		List<ItemDetailVo> rtnList = new ArrayList<ItemDetailVo>();

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			
			if(user != null){
				beginDate += " 00:00:00";
				endDate  += " 23:59:59";
				//查询出时间段内所有的异常项及正常项个数
				String rightHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.opResult=1 and endTime >= '" + beginDate + "' and endTime <= '" + endDate + "'";
				List<ItemDetail> rightList = this.itemDetailManager.getResultByQueryString(rightHql);
				rightCount = rightList.size();
				
				String errorHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.opResult=-1 and endTime >= '" + beginDate + "' and endTime <= '" + endDate + "'";
				List<ItemDetail> errorList = this.itemDetailManager.getResultByQueryString(errorHql);
				errorCount = errorList.size();
				
				//异常项
				if(errorList != null && errorList.size() > 0){
					for(ItemDetail tmpDetail : errorList){
						ItemDetailVo detailVo = new ItemDetailVo();
						BeanUtils.copyProperties(detailVo, tmpDetail);
						
						SmallCategory smallCategory = this.smallCategoryManager.getSmallCategoryBySmallId(tmpDetail.getSmallId());
						detailVo.setSmallName(smallCategory.getCategoryName());
						
						BigCategory bigCategory = this.bigCategoryManager.getBigCategoryByBigId(tmpDetail.getBigId());
						detailVo.setBigName(bigCategory.getCategoryName());
						
						User operator = this.userManager.getUserByUserId(tmpDetail.getOperatorId());
						detailVo.setOperatorName(operator.getName());
						
						if(tmpDetail.getBeginTime() != null){
							detailVo.setBeginTimeStr(sf.format(tmpDetail.getBeginTime()));
						}
						if(tmpDetail.getEndTime() != null){
							detailVo.setEndTimeStr(sf.format(tmpDetail.getEndTime()));
						}
						
						rtnList.add(detailVo);
					}
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
		jsonObj.put("rightCount", rightCount);
		jsonObj.put("errorCount", errorCount);
		jsonObj.put("result", rtnList);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	
	
	/**
	 * 网页端获取年度按部门统计
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
//	@RequestMapping(params="method=patrolYearReport")
//	@Transactional
//	public void patrolYearReport(HttpServletRequest request,HttpServletResponse response)throws Exception {
//		String message = "";
//		boolean success = true;
//
//		//int rightCount = 0;  //正常数
//		//int errorCount = 0;  //异常数
//
//		List<Map<String, List<Map<String, Integer>>>> resultList = new ArrayList<Map<String,List<Map<String,Integer>>>>();
//
//		JSONObject jsonObj = new JSONObject();
//		//用户
//		//String token = request.getParameter("token");
//		//User user = LoginConfirm.loginUserMap.get(token);
//
//		//参数：
//		//String beginDate = request.getParameter("beginDate");
//		//String endDate = request.getParameter("endDate");
//
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
////		SimpleDateFormat sf_m = new SimpleDateFormat("m");
//
//		//得到当前年
//		String thisYear = sf_y.format(new java.util.Date());
//		String yearBegin = thisYear + "-01-01 00:00:00";
//		String yearEnd = thisYear + "-12-31 23:59:59";
//
//
//		try {
//
//			//if(user != null){
//				//查询出时间段内所有的异常项
////				String rightHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.opResult=1 and endTime >= '" + yearBegin + "' and endTime <= '" + yearEnd + "'";
////				List<ItemDetail> rightList = this.itemDetailManager.getResultByQueryString(rightHql);
////				rightCount = rightList.size();
//
//				String errorHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.opResult=-1 and endTime >= '" + yearBegin + "' and endTime <= '" + yearEnd + "'";
//				List<ItemDetail> errorList = this.itemDetailManager.getResultByQueryString(errorHql);
//				//errorCount = errorList.size();
//
//				//按部门、月份统计
//				/*
//				 * 部门代码：
//				 *  11-市场部
//					12-工程部
//					13-客货运部
//					14-安监部
//					15-综合办
//					16-党群部
//					17-企发部
//					18-财务部
//
//					19-资产管理公司
//					20-上港中免
//					21-上港旅行社
//					22-万航旅业
//					23-上港船服
//					24-外包服务
//				 * */
//
//				//月份所有部门异常数量
//				int allCount = 0;
//
//				//第一层循环，部门
//				for(int i=11;i<25;i++){
//					Map<String, List<Map<String, Integer>>> dMap = new HashMap<String, List<Map<String, Integer>>>();
//
//					//所有月份及合计
//					List<Map<String, Integer>> list = new ArrayList<Map<String,Integer>>();
//
//					//第二层循环，月份及合计
//					int allMonthCount = 0;
//					for(int j=1;j<13;j++){
//						int monthErrorCount = 0;
//
//						//异常项
//						if(errorList != null && errorList.size() > 0){
//							for(ItemDetail tmpDetail : errorList){
//								String departmentNo = "";
//								Integer operatorId = tmpDetail.getOperatorId();
//
////								int opMonth = Integer.valueOf(sf_m.format(tmpDetail.getEndTime()));
//								int opMonth = tmpDetail.getEndTime().getMonth() + 1;
//								//System.out.println("=="+opMonth);
//								if(operatorId != null){
//									User operator = this.userManager.getUserByUserId(operatorId);
//									if(operator != null && operator.getxId() > 0){
//										Integer departmentId = operator.getDepartmentId();
//										if(departmentId != null && departmentId.intValue() > 0){
//											Department department = this.departmentManager.getDepartmentByDeparmentId(departmentId);
//											if(department != null && department.getxId()>0){
//												departmentNo = department.getDepartmentNo();
//												//System.out.println(departmentNo+"--"+i+"------------"+opMonth+"-===="+j);
//												if(departmentNo.equals(String.valueOf(i)) && opMonth == j){
//													monthErrorCount += 1;
//												}
//											}
//										}
//									}
//								}
//							}
//						}
//
//						allMonthCount += monthErrorCount;
//
//						Map<String, Integer> monthMap = new HashMap<String, Integer>();
//						monthMap.put(String.valueOf(j), monthErrorCount);
////						monthMap.put("13", allMonthCount);
//
//						list.add(monthMap);
//					}
//					Map<String, Integer> monthMap = new HashMap<String, Integer>();
//					monthMap.put("13", allMonthCount);
//					list.add(monthMap);
//
//					dMap.put(String.valueOf(i), list);
//
//					resultList.add(dMap);
//				}
//
//
//				int allMonthCount = 0;
//				List<Map<String, Integer>> hjList = new ArrayList<Map<String,Integer>>();
//				for(int j=1;j<13;j++){
//					int monthErrorCount = 0;
//
//					//异常项
//					if(errorList != null && errorList.size() > 0){
//						for(ItemDetail tmpDetail : errorList){
////							int opMonth = Integer.valueOf(sf_m.format(tmpDetail.getEndTime()));
//							int opMonth = tmpDetail.getEndTime().getMonth() + 1;
//							//allMonthCount += 1;
//							if(opMonth == j){
//								monthErrorCount += 1;
//							}
//						}
//					}
//
//					allMonthCount += monthErrorCount;
//
//					Map<String, Integer> monthAllMap = new HashMap<String, Integer>();
//					monthAllMap.put(String.valueOf(j),monthErrorCount);
//					hjList.add(monthAllMap);
//				}
//
//				Map<String, Integer> allAllMap = new HashMap<String, Integer>();
//				allAllMap.put("13", allMonthCount);
//				hjList.add(allAllMap);
//
//				Map<String, List<Map<String, Integer>>> fMap = new HashMap<String, List<Map<String, Integer>>>();
//				fMap.put("99", hjList);
//
//				resultList.add(fMap);
//
//
//
//			//}else {
//			//	message = "请登录！";
//			//	success = false;
//			//}
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
//		jsonObj.put("result", resultList);
//		jsonObj.put("thisYear", thisYear);
//
//		//设置字符编码
//        response.setContentType(CoreConstant.CONTENT_TYPE);
//		response.getWriter().print(jsonObj);
//	}
	
	
	/**
	 * 网页端获取月度按部门统计
	 * 
	 * @param request
	 * @param response
	 * @param
	 */
	@RequestMapping(params="method=patrolMonthReport")
	@Transactional
	public String patrolMonthReport(HttpServletRequest request,HttpServletResponse response)throws Exception {

		List<Map<String, List<Integer>>> resultList = new ArrayList<Map<String,List<Integer>>>();
		List<BigCategory> bigCategories = new ArrayList<BigCategory>();
		List<ItemDetailVo> details = new ArrayList<ItemDetailVo>();

		//参数：
		String dataYearStr = request.getParameter("year");
		String dataMonthStr = request.getParameter("month");

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {

			if(StringUtil.isNotEmpty(dataYearStr) && StringUtil.isNotEmpty(dataMonthStr)){
				int dataYear = Integer.valueOf(dataYearStr);
				int dataMonth = Integer.valueOf(dataMonthStr);


				String monthBeginDate = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(DateHelper.getDate(dataYear + "-" + (dataMonth<10?"0"+dataMonth:dataMonth) + "-01")));
				String monthEndDate = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(DateHelper.getDate(dataYear + "-" + (dataMonth<10?"0"+dataMonth:dataMonth) + "-01")));;

				String monthBeginTime = monthBeginDate + " 00:00:00";
				String monthEndTime = monthEndDate + " 23:59:59";

				String errorHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.opResult=-1 and endTime >= '" + monthBeginTime + "' and endTime <= '" + monthEndTime + "' order by endTime";
				List<ItemDetail> errorList = this.itemDetailManager.getResultByQueryString(errorHql);


				//得到所有大项（按顺序）
				bigCategories = this.bigCategoryManager.getAllValid();


				//大项所有部门异常数量
				int allCount = 0;

				String[] departIds = {"11","12","13","14","15","16","17","18","19","20","21","22","23"};
				String[] departs = {"市场营销部","人力资源部","整车物流部","质量安全部","码头运营部","党群工作部","总经理办公室","计划财务部","采购部","技术规划部","信息技术部","数据业务部","零部件物流部"};

				//第一层循环，部门
				for(int i=0;i<departs.length;i++){

					Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();

					//部门下，每个大项的异常量
					List<Integer> eachDepartCountList = new ArrayList<Integer>();
					if(bigCategories != null && bigCategories.size() > 0){

						//if(i<departs.length - 1){
							for(BigCategory tmpBigCategory : bigCategories){

								int eachBigCount = 0;
								Integer bigCategoryId = tmpBigCategory.getBigId();
								if(errorList != null && errorList.size() > 0){
									for(ItemDetail tmpDetail : errorList){
										Integer tmpBigId = tmpDetail.getBigId();
										Integer operatorId = tmpDetail.getOperatorId();
										if(operatorId != null){
											User operator = this.userManager.getUserByUserId(operatorId);
											if(operator != null && operator.getxId() > 0){
												Integer departmentId = operator.getDepartmentId();
												Department department = this.departmentManager.getDepartmentByDeparmentId(departmentId);
												if(department != null && department.getxId().intValue() > 0){
													String departmentNo = department.getDepartmentNo();
													if(departmentNo.endsWith(departIds[i]) && tmpBigId.intValue() == bigCategoryId){
														eachBigCount += 1;
													}
												}

											}
										}
									}
								}

								eachDepartCountList.add(eachBigCount);
							}

							int eachDepCategoryAllCount = 0;
							for(Integer tmpCount : eachDepartCountList){
								eachDepCategoryAllCount += tmpCount;
							}
							eachDepartCountList.add(eachDepCategoryAllCount);
							map.put(departs[i], eachDepartCountList);
						//}
							/*else{
							int eachDepAllCount = 0;
							for(Integer tmpCount : eachDepartCountList){
								eachDepAllCount += tmpCount;
							}

							eachDepartCountList.add(eachDepAllCount);
						}


						map.put(departs[i], eachDepartCountList);*/

						resultList.add(map);
					}

				}


				//底部合计
				List<Integer> eachBigCountList = new ArrayList<Integer>();
				if(bigCategories != null && bigCategories.size() > 0){

					for(BigCategory tmpBigCategory : bigCategories){

						int eachBigCount = 0;
						Integer bigCategoryId = tmpBigCategory.getBigId();
						if(errorList != null && errorList.size() > 0){
							for(ItemDetail tmpDetail : errorList){
								Integer tmpBigId = tmpDetail.getBigId();

								if(tmpBigId.intValue() == bigCategoryId){
									eachBigCount += 1;
								}
							}
						}

						eachBigCountList.add(eachBigCount);
					}

					int eachCategoryAllCount = 0;
					for(Integer tmpCount : eachBigCountList){
						eachCategoryAllCount += tmpCount;
					}
					eachBigCountList.add(eachCategoryAllCount);
				}

				Map<String, List<Integer>> allMap = new HashMap<String, List<Integer>>();
				allMap.put("合计", eachBigCountList);
				resultList.add(allMap);


				for(ItemDetail tmpDetail : errorList){
					ItemDetailVo detailVo = new ItemDetailVo();
					BeanUtils.copyProperties(detailVo, tmpDetail);

					SmallCategory smallCategory = this.smallCategoryManager.getSmallCategoryBySmallId(tmpDetail.getSmallId());
					detailVo.setSmallName(smallCategory.getCategoryName());

					BigCategory bigCategory = this.bigCategoryManager.getBigCategoryByBigId(tmpDetail.getBigId());
					detailVo.setBigName(bigCategory.getCategoryName());

					User operator = this.userManager.getUserByUserId(tmpDetail.getOperatorId());
					detailVo.setOperatorName(operator.getName());

					if(operator != null && operator.getDepartmentId() != null){
						Department department = this.departmentManager.getDepartmentByDeparmentId(operator.getDepartmentId());
						detailVo.setDepartmentName(department.getDepartmentName());
					}

					if(tmpDetail.getBeginTime() != null){
						detailVo.setBeginTimeStr(sf.format(tmpDetail.getBeginTime()));
					}
					if(tmpDetail.getEndTime() != null){
						detailVo.setEndTimeStr(sf.format(tmpDetail.getEndTime()));
					}

					//附件
					if(StringUtil.isNotEmpty(tmpDetail.getAttachs())){
						String[][] attachment = GKFileViewUtil.viewAttachment(tmpDetail.getAttachs());
						String[] tmpAttach = attachment[0];
						detailVo.setAttachArray(tmpAttach);
					}

					details.add(detailVo);
				}
			}





			//}else {
			//	message = "请登录！";
			//	success = false;
			//}


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("出现错误================="+e.getLocalizedMessage());
			logger.error(e,e.fillInStackTrace());
		}

//		jsonObj.put("success", success);
//		jsonObj.put("message", message);
//		jsonObj.put("result", resultList);
//		jsonObj.put("categories", bigCategories);
		request.setAttribute("resultReport", resultList);
		request.setAttribute("categories", bigCategories);
		request.setAttribute("dataYear", dataYearStr);
		request.setAttribute("dataMonth", dataMonthStr);
		request.setAttribute("details", details);
//
//		//设置字符编码
//        response.setContentType(CoreConstant.CONTENT_TYPE);
//		response.getWriter().print(jsonObj);

		return "/security/patrolMonthReport";
	}
	
	/**
	 * 获取首页需要显示的数据(柱状图)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=getYearCount")
	public void getYearCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String dataDateStr = request.getParameter("dataDate");  //格式为yyyy-MM
//		SimpleDateFormat ymSf = new SimpleDateFormat("yyyy-MM");

		JSONObject jsonObj = new JSONObject();


		List<Integer> rightCountList = new ArrayList<Integer>();  //正常量
		List<Integer> errorCountList = new ArrayList<Integer>();  //异常量
		
		
		String currentYearStr = request.getParameter("currentYear");
		int thisYear = 0;
		int realYear = 0;
		//默认显示当年 
		java.util.Date now = new java.util.Date();
		SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
		realYear = Integer.valueOf(sf_y.format(now));
		
		if(StringUtil.isNotEmpty(currentYearStr)){
			
			thisYear = Integer.valueOf(currentYearStr);
			
		}else{
			thisYear = realYear;
		}
		jsonObj.put("_Year", thisYear);
		jsonObj.put("_RealYear", realYear);
		
		String bigIdStr = request.getParameter("bigId");
		try {
			if(StringUtil.isNotEmpty(bigIdStr)){
				int bigId = Integer.valueOf(bigIdStr);
				
				String rightHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and bigId = " + bigId + " and detail.opResult=1 and endTime >= '" + currentYearStr + "-01-01 00:00:00 ' and endTime <= '" + currentYearStr + "-12-31 23:59:59' order by endTime";
				List<ItemDetail> rightList = this.itemDetailManager.getResultByQueryString(rightHql);
				
				String errorHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and bigId = " + bigId + " and detail.opResult=-1 and endTime >= '" + currentYearStr + "-01-01 00:00:00 ' and endTime <= '" + currentYearStr + "-12-31 23:59:59' order by endTime";
				List<ItemDetail> errorList = this.itemDetailManager.getResultByQueryString(errorHql);
				
				for(int i=1;i<13;i++){
					int rightCount = 0;
					int errorCount = 0;
					
					String eachMonthStr = thisYear+"-"+(i<10?"0"+i:i)+"-01";
	//				java.util.Date eachMonthBeginDate = DateHelper.getFirstBeginDateOfMonth(eachMonthStr);
					java.util.Date eachMonthEndDate = DateHelper.getFirstEndDateOfMonth(eachMonthStr);
					
					String eachMonthBeginStr = eachMonthStr + " 00:00:00";
					String eachMonthEndStr = DateHelper.getString(eachMonthEndDate) + " 23:59:59";
					
					for(ItemDetail tmpDetail : rightList){
						if(tmpDetail.getEndTime().after(Timestamp.valueOf(eachMonthBeginStr)) && tmpDetail.getEndTime().before(Timestamp.valueOf(eachMonthEndStr))){
							rightCount += 1;
						}
					}
					
					for(ItemDetail tmpDetail : errorList){
						if(tmpDetail.getEndTime().after(Timestamp.valueOf(eachMonthBeginStr)) && tmpDetail.getEndTime().before(Timestamp.valueOf(eachMonthEndStr))){
							errorCount += 1;
						}
					}
					
					rightCountList.add(rightCount);
					errorCountList.add(errorCount);
					
				}
			}
		
		
		
			jsonObj.put("_RightCountList", rightCountList);
			jsonObj.put("_ErrorCountList", errorCountList);
			
			//设置字符编码
			response.setContentType(CoreConstant.CONTENT_TYPE);
			response.getWriter().print(jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * 获取首页需要显示的数据(饼图)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=getMonthCount")
	public void getMonthCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dataDateStr = request.getParameter("dataDate");  //格式为yyyy-MM
		SimpleDateFormat ymSf = new SimpleDateFormat("yyyy-MM");
		
//		List<Integer> allCountList = new ArrayList<Integer>();    //总项
//		List<Integer> rightCountList = new ArrayList<Integer>();    //正常项
//		List<Integer> errorCountList = new ArrayList<Integer>();    //异常项
//		List<String> bigCategoryNames = new ArrayList<String>();
		

		List<Integer> allCountList_dpt = new ArrayList<Integer>();    //部门总项
//		List<Integer> rightCountList_dpt = new ArrayList<Integer>();    //部门正常项
		List<Integer> errorCountList_dpt = new ArrayList<Integer>();    //部门异常项
		List<String> rightDptNames = new ArrayList<String>();
		List<String> errorDptNames = new ArrayList<String>();
		
		
//		String currentMonthStr = request.getParameter("currentMonth");
		
		if(StringUtil.isNotEmpty(dataDateStr)){
			String tag = request.getParameter("tag");
			if(StringUtil.isNotEmpty(tag)){
				if(tag.equals("0")){  //0为上一月
					dataDateStr = ymSf.format(DateHelper.addMonth(DateHelper.getDate(dataDateStr+"-01"), -1));
				}else if(tag.equals("1")) {  //1为下一月
					dataDateStr = ymSf.format(DateHelper.addMonth(DateHelper.getDate(dataDateStr+"-01"), 1));
				}
			}else{
				dataDateStr = ymSf.format(new java.util.Date());
			}
			
		}else{
			dataDateStr = ymSf.format(new java.util.Date());
		}
		
		//为空时，默认取当月
		if(!StringUtil.isNotEmpty(dataDateStr) || dataDateStr.toLowerCase().contains("undefined")){
			dataDateStr = ymSf.format(new java.util.Date());
		}
		
		
		
		
		try {
			JSONObject jsonObj = new JSONObject();
			List<WorkList> allList = new ArrayList<WorkList>();    //总项
			List<ItemDetail> errorList = new ArrayList<ItemDetail>();    //异常项

			
			String monthBeginDate = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(dataDateStr + "-01"));
			String monthEndDate = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(dataDateStr + "-01"));
			
			String monthBeginTime = monthBeginDate + " 00:00:00";
			String monthEndTime = monthEndDate + " 23:59:59";
			
			String errorHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.opResult=-1 and endTime >= '" + monthBeginTime + "' and endTime <= '" + monthEndTime + "' order by endTime";
			errorList = this.itemDetailManager.getResultByQueryString(errorHql);

//			String allHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and endTime >= '" + monthBeginTime + "' and endTime <= '" + monthEndTime + "' order by endTime";
//			allList = this.itemDetailManager.getResultByQueryString(allHql);
			
			String allHql = " from WorkList list where list.listId in (select listId from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.endTime >= '" + monthBeginTime + "' and detail.endTime <= '" + monthEndTime + "' order by detail.endTime)";
			allList = this.workListManager.getResultByQueryString(allHql);
			

			

			/*********按部门统计*********/
			List<Department> dptList = new ArrayList<Department>();
			//得到所有部门（按顺序）
			String dptHql = " from Department where valid=1 and parent.xId = 1 order by orderNo";
			dptList = this.departmentManager.getResultByQueryString(dptHql);
			
			//只显示部分部门
			//"安监部","客货运部","工程部","市场部","资产管理公司","上港中免","万航旅业","上港船服"
			List<String> partDpts = new ArrayList<String>();
			partDpts.add("安监部");
			partDpts.add("客货运部");
			partDpts.add("工程部");
			partDpts.add("市场部");
			partDpts.add("资产管理公司");
			partDpts.add("上港中免");
			partDpts.add("万航旅业");
			partDpts.add("上港船服");
			
			
			if(dptList != null && dptList.size() > 0){
				for(Department tmpDepartment : dptList){
					if(partDpts.indexOf(tmpDepartment.getDepartmentName()) > -1){
						int rightCount = 0;
						int errorCount = 0;
						
						int dptId = tmpDepartment.getDepartmentId();
						
//						for(ItemDetail rightDetail : allList){
//							User operator = this.userManager.getUserByUserId(rightDetail.getOperatorId());
//							if(operator.getDepartmentId() == dptId){
//								rightCount += 1;
//							}
//						}
						for(WorkList tmpList : allList){
							User operator = this.userManager.getUserByUserId(tmpList.getExecuterId());
							if(operator.getDepartmentId() == dptId){
								//rightCount += 1;
								if(StringUtil.isNotEmpty(tmpList.getBigItems())){
									String[] allBigItems = tmpList.getBigItems().split(",");
									for(String tmpBigItem : allBigItems){
										if(StringUtil.isNotEmpty(tmpBigItem)){
											rightCount += 1;
										}
									}
								}
								
							}
						}
						for(ItemDetail errorDetail : errorList){
							User operator = this.userManager.getUserByUserId(errorDetail.getOperatorId());
							if(operator.getDepartmentId() == dptId){
								errorCount += 1;
							}
						}
						
							allCountList_dpt.add(rightCount);
							rightDptNames.add("'"+tmpDepartment.getDepartmentName()+"'");
						
							errorCountList_dpt.add(errorCount);
							errorDptNames.add("'"+tmpDepartment.getDepartmentName()+"'");
					}
					
				}
			}
			
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月");
			
//			int allCount = allList.size();
			int allCount = 0;
			for(WorkList tmpList : allList){
				if(StringUtil.isNotEmpty(tmpList.getBigItems())){
					String[] allBigItems = tmpList.getBigItems().split(",");
					for(String tmpBigItem : allBigItems){
						if(StringUtil.isNotEmpty(tmpBigItem)){
							allCount += 1;
						}
					}
				}
					
			}
//			for(Integer eachCount : allCountList){
//				allCount += eachCount;
//			}
			
			int errorCount = errorList.size();
//			for(Integer eachCount : errorCountList){
//				errorCount += eachCount;
//			}
			
			jsonObj.put("_NowDate", dataDateStr);
			jsonObj.put("_NowYearMonth", sf.format(DateHelper.getDate(dataDateStr+"-01")));
			jsonObj.put("_AllCount", allCount);
			jsonObj.put("_ErrorCount", errorCount);
//			jsonObj.put("_BigCategoryNames", bigCategoryNames);

			jsonObj.put("_AllCount_dpt", allCountList_dpt);
			jsonObj.put("_ErrorCount_dpt", errorCountList_dpt);
			jsonObj.put("_RightDptNames", rightDptNames);
			jsonObj.put("_ErrorDptNames", errorDptNames);
			
			//设置字符编码
			response.setContentType(CoreConstant.CONTENT_TYPE);
			response.getWriter().print(jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	
//	/**
//	 * 获取首页需要显示的数据(饼图)
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping(params = "method=getMonthCount")
//	public void getMonthCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String dataDateStr = request.getParameter("dataDate");  //格式为yyyy-MM
//		SimpleDateFormat ymSf = new SimpleDateFormat("yyyy-MM");
//		
//
//		List<Integer> rightCountList = new ArrayList<Integer>();    //正常项
//		List<Integer> errorCountList = new ArrayList<Integer>();    //异常项
//		List<String> bigCategoryNames = new ArrayList<String>();
//		
//
//		List<Integer> rightCountList_dpt = new ArrayList<Integer>();    //部门正常项
//		List<Integer> errorCountList_dpt = new ArrayList<Integer>();    //部门异常项
//		List<String> rightDptNames = new ArrayList<String>();
//		List<String> errorDptNames = new ArrayList<String>();
//		
//		
////		String currentMonthStr = request.getParameter("currentMonth");
//		
//		if(StringUtil.isNotEmpty(dataDateStr)){
//			String tag = request.getParameter("tag");
//			if(StringUtil.isNotEmpty(tag)){
//				if(tag.equals("0")){  //0为上一月
//					dataDateStr = ymSf.format(DateHelper.addMonth(DateHelper.getDate(dataDateStr+"-01"), -1));
//				}else if(tag.equals("1")) {  //1为下一月
//					dataDateStr = ymSf.format(DateHelper.addMonth(DateHelper.getDate(dataDateStr+"-01"), 1));
//				}
//			}else{
//				dataDateStr = ymSf.format(new java.util.Date());
//			}
//			
//		}else{
//			dataDateStr = ymSf.format(new java.util.Date());
//		}
//		
//		//为空时，默认取当月
//		if(!StringUtil.isNotEmpty(dataDateStr) || dataDateStr.toLowerCase().contains("undefined")){
//			dataDateStr = ymSf.format(new java.util.Date());
//		}
//		
//		
//		
//		
//		try {
//			JSONObject jsonObj = new JSONObject();
//			List<ItemDetail> rightList = new ArrayList<ItemDetail>();    //正常项
//			List<ItemDetail> errorList = new ArrayList<ItemDetail>();    //异常项
//
//			List<BigCategory> bigCategories = new ArrayList<BigCategory>();
//			//得到所有大项（按顺序）
//			bigCategories = this.bigCategoryManager.getAllValid();
//			
//			
//			String monthBeginDate = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(dataDateStr + "-01"));
//			String monthEndDate = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(dataDateStr + "-01"));
//			
//			String monthBeginTime = monthBeginDate + " 00:00:00";
//			String monthEndTime = monthEndDate + " 23:59:59";
//			
//			String errorHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.opResult=-1 and endTime >= '" + monthBeginTime + "' and endTime <= '" + monthEndTime + "' order by endTime";
//			errorList = this.itemDetailManager.getResultByQueryString(errorHql);
//
//			String rightHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.opResult=1 and endTime >= '" + monthBeginTime + "' and endTime <= '" + monthEndTime + "' order by endTime";
//			rightList = this.itemDetailManager.getResultByQueryString(rightHql);
//			
//			//分别计算每个大类下的正常、异常数
//			if(bigCategories != null && bigCategories.size() > 0){
//				
//				for(BigCategory tmpBigCategory : bigCategories){
//					int rightCount = 0;
//					int errorCount = 0;
//					
//					for(ItemDetail rightDetail : rightList){
//						if(rightDetail.getBigId().intValue() == tmpBigCategory.getBigId().intValue()){
//							rightCount += 1;
//						}
//					}
//					for(ItemDetail errorDetail : errorList){
//						if(errorDetail.getBigId().intValue() == tmpBigCategory.getBigId().intValue()){
//							errorCount += 1;
//						}
//					}
//					rightCountList.add(rightCount);
//					errorCountList.add(errorCount);
//					
//					bigCategoryNames.add(tmpBigCategory.getCategoryName());
//				}
//			}
//			
//
//			/*********按部门统计*********/
//			List<Department> dptList = new ArrayList<Department>();
//			//得到所有部门（按顺序）
//			String dptHql = " from Department where valid=1 and parent.xId = 1 order by orderNo";
//			dptList = this.departmentManager.getResultByQueryString(dptHql);
//			if(dptList != null && dptList.size() > 0){
//				for(Department tmpDepartment : dptList){
//					int rightCount = 0;
//					int errorCount = 0;
//					
//					int dptId = tmpDepartment.getDepartmentId();
//					
//					for(ItemDetail rightDetail : rightList){
//						User operator = this.userManager.getUserByUserId(rightDetail.getOperatorId());
//						if(operator.getDepartmentId() == dptId){
//							rightCount += 1;
//						}
//					}
//					for(ItemDetail errorDetail : errorList){
//						User operator = this.userManager.getUserByUserId(errorDetail.getOperatorId());
//						if(operator.getDepartmentId() == dptId){
//							errorCount += 1;
//						}
//					}
//					
//					//if(rightCount > 0){
//						rightCountList_dpt.add(rightCount);
//						rightDptNames.add("'"+tmpDepartment.getDepartmentName()+"'");
//					//}
//					
//					//if(errorCount > 0){
//						errorCountList_dpt.add(errorCount);
//						errorDptNames.add("'"+tmpDepartment.getDepartmentName()+"'");
//					//}
//					
//				}
//			}
//			
//			
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月");
//			
//			jsonObj.put("_NowDate", dataDateStr);
//			jsonObj.put("_NowYearMonth", sf.format(DateHelper.getDate(dataDateStr+"-01")));
//			jsonObj.put("_RightCount", rightCountList);
//			jsonObj.put("_ErrorCount", errorCountList);
//			jsonObj.put("_BigCategoryNames", bigCategoryNames);
//
//			jsonObj.put("_RightCount_dpt", rightCountList_dpt);
//			jsonObj.put("_ErrorCount_dpt", errorCountList_dpt);
//			jsonObj.put("_RightDptNames", rightDptNames);
//			jsonObj.put("_ErrorDptNames", errorDptNames);
//			
//			//设置字符编码
//			response.setContentType(CoreConstant.CONTENT_TYPE);
//			response.getWriter().print(jsonObj);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//	}
	
//	/**
//	 * 获取首页需要显示的数据(饼状图)
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping(params = "method=getDptCount")
//	public void getDptCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String dataDateStr = request.getParameter("dataDate");  //格式为yyyy-MM
//		SimpleDateFormat ymSf = new SimpleDateFormat("yyyy-MM");
//		
//
//		List<Integer> rightCountList = new ArrayList<Integer>();    //正常项
//		List<Integer> errorCountList = new ArrayList<Integer>();    //异常项
//		List<String> dptNames = new ArrayList<String>();
//		
//		//为空时，默认取当月
//		if(!StringUtil.isNotEmpty(dataDateStr) || dataDateStr.toLowerCase().contains("undefined")){
//			dataDateStr = ymSf.format(new java.util.Date());
//		}
//		try {
//			JSONObject jsonObj = new JSONObject();
//			List<ItemDetail> rightList = new ArrayList<ItemDetail>();    //正常项
//			List<ItemDetail> errorList = new ArrayList<ItemDetail>();    //异常项
//
//			List<Department> dptList = new ArrayList<Department>();
//			//得到所有部门（按顺序）
//			String dptHql = " from Department where valid=0 and parent.xId = 1 order by orderNo";
//			dptList = this.departmentManager.getResultByQueryString(dptHql);
//			
//			
//			String monthBeginDate = DateHelper.getString(DateHelper.getFirstBeginDateOfMonth(dataDateStr + "-01"));
//			String monthEndDate = DateHelper.getString(DateHelper.getFirstEndDateOfMonth(dataDateStr + "-01"));
//			
//			String monthBeginTime = monthBeginDate + " 00:00:00";
//			String monthEndTime = monthEndDate + " 23:59:59";
//			
//			String errorHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.opResult=-1 and endTime >= '" + monthBeginTime + "' and endTime <= '" + monthEndTime + "' order by endTime";
//			errorList = this.itemDetailManager.getResultByQueryString(errorHql);
//
//			String rightHql = " from ItemDetail detail where detail.valid=1 and detail.workState=1 and detail.opResult=1 and endTime >= '" + monthBeginTime + "' and endTime <= '" + monthEndTime + "' order by endTime";
//			rightList = this.itemDetailManager.getResultByQueryString(rightHql);
//			
//			//分别计算每个部门下的正常、异常数
//			if(dptList != null && dptList.size() > 0){
//				
//				for(Department tmpDpt : dptList){
//					int rightCount = 0;
//					int errorCount = 0;
//					
//					for(ItemDetail rightDetail : rightList){
//						if(rightDetail.getBigId().intValue() == tmpBigCategory.getBigId().intValue()){
//							rightCount += 1;
//						}
//					}
//					for(ItemDetail errorDetail : errorList){
//						if(errorDetail.getBigId().intValue() == tmpBigCategory.getBigId().intValue()){
//							errorCount += 1;
//						}
//					}
//					rightCountList.add(rightCount);
//					errorCountList.add(errorCount);
//					
//					bigCategoryNames.add(tmpBigCategory.getCategoryName());
//				}
//			}
//			
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月");
//			
//			jsonObj.put("_NowDate", dataDateStr);
//			jsonObj.put("_NowYearMonth", sf.format(DateHelper.getDate(dataDateStr+"-01")));
//			jsonObj.put("_RightCount", rightCountList);
//			jsonObj.put("_ErrorCount", errorCountList);
//			jsonObj.put("_BigCategoryNames", bigCategoryNames);
//			
//			//设置字符编码
//			response.setContentType(CoreConstant.CONTENT_TYPE);
//			response.getWriter().print(jsonObj);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//	}
	
}
