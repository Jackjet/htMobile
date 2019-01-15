package com.kwchina.work.nightWatch.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.common.controller.BasicController;
import com.kwchina.core.common.page.PageList;
import com.kwchina.core.common.page.Pages;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.DateHelper;
import com.kwchina.core.util.json.JSONConvert;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.nightWatch.entity.NwArea;
import com.kwchina.work.nightWatch.entity.NwDetailLog;
import com.kwchina.work.nightWatch.entity.NwItem;
import com.kwchina.work.nightWatch.entity.NwList;
import com.kwchina.work.nightWatch.entity.NwWork;
import com.kwchina.work.nightWatch.entity.NwWorkDetail;
import com.kwchina.work.nightWatch.service.NwAreaManager;
import com.kwchina.work.nightWatch.service.NwDetailLogManager;
import com.kwchina.work.nightWatch.service.NwItemManager;
import com.kwchina.work.nightWatch.service.NwListManager;
import com.kwchina.work.nightWatch.service.NwWorkDetailManager;
import com.kwchina.work.nightWatch.service.NwWorkManager;
import com.kwchina.work.nightWatch.vo.NwDetailLogVo;
import com.kwchina.work.nightWatch.vo.NwWorkDetailVo;
import com.kwchina.work.nightWatch.vo.OpNightWatchVo;
import com.kwchina.work.nightWatch.vo.OpNwAreaVo;
import com.kwchina.work.nightWatch.vo.PcNwVo;
import com.kwchina.work.util.JPushUtils;


@Controller
@RequestMapping("/nightWatch.htm")
public class NightWatchController extends BasicController {
	Logger logger = Logger.getLogger(NightWatchController.class);
	
	
	
	@Resource
	private NwListManager nwListManager;
	
	@Resource
	private NwWorkManager nwWorkManager;
	
	@Resource
	private NwWorkDetailManager nwWorkDetailManager;
	
	@Resource
	private NwItemManager nwItemManager;
	
	@Resource
	private NwDetailLogManager nwDetailLogManager;
	
	@Resource
	private UserManager userManager;
	
	@Resource
	private NwAreaManager nwAreaManager;
	
	
	/***
	 * PC端巡更任务列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="method=nwList")
	public void nwList(Model model,HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 构造查询语句
//		String areaId = request.getParameter("areaId");
		String[] queryString = new String[2];
		queryString[0] = "from NwWork instance where 1=1 ";
		queryString[1] = "select count(*) from  NwWork instance where 1=1 ";
		

		String mainCondition = "";
		queryString = this.nwWorkManager.generateQueryString(queryString, getSearchParams(request));
		//构造查询条件
//		String[] params = getSearchParams(request);
//		String conditions = this.nwWorkManager.generateCondition(params[3]);
//		//查询操作时,加入查询条件
//		if (params[2].equals("true") && conditions != null && conditions.length() > 0) {
//			mainCondition += " and listId in (select listId from NwList where 1=1 and "+conditions+")";
//		}
//		queryString[0] += mainCondition;
//		queryString[1] += mainCondition;
//		queryString[0] += " order by " + params[0] + " " + params[1];
		
		
		String page = request.getParameter("page"); // 当前页
		String rowsNum = request.getParameter("rows"); // 每页显示的行数
		Pages pages = new Pages(request);
		pages.setPage(Integer.valueOf(page));
		pages.setPerPageNum(Integer.valueOf(rowsNum));

		PageList pl = this.nwWorkManager.getResultByQueryString(queryString[0], queryString[1], true, pages);
		List<NwWork> list = pl.getObjectList();
		List<PcNwVo> vos = new ArrayList<PcNwVo>();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//转vo
		for(NwWork tmpWork : list){
			PcNwVo vo = new PcNwVo();
			vo.setWorkId(tmpWork.getWorkId());
			vo.setListId(tmpWork.getListId());
			vo.setValid(tmpWork.isValid());
			
			NwList workList = this.nwListManager.getListByListId(tmpWork.getListId());
			vo.setWorkName(workList.getWorkTitle());
			vo.setExecuterId(tmpWork.getExecuterId());
			User executer = this.userManager.getUserByUserId(tmpWork.getExecuterId());
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
	 * 得到巡更详情
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=getAreas")
	@Transactional
	public void getAreas(HttpServletRequest request,HttpServletResponse response)throws Exception {

		JSONObject jsonObj = new JSONObject();
		//设置OpBigCategoryVo
		List<OpNwAreaVo> areaVos = new ArrayList<OpNwAreaVo>();
		String workIdStr = request.getParameter("workId");
		if(StringUtil.isNotEmpty(workIdStr)){
			int workId = Integer.valueOf(workIdStr);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			NwWork workItem = this.nwWorkManager.getWorkByWorkId(workId);
			NwList workList = this.nwListManager.getListByListId(workItem.getListId());
			String bigIds = workList.getAreaIds();
			String[] bigIdArray = bigIds.split(",");
			for(String tmpBigId : bigIdArray){
				Integer tmpBigIdInt = Integer.valueOf(tmpBigId);
				NwArea area = this.nwAreaManager.getNwAreaByAreaId(tmpBigIdInt);
				
				OpNwAreaVo areaVo = new OpNwAreaVo();
				areaVo.setxId(area.getxId());
				areaVo.setAreaId(area.getAreaId());
				areaVo.setAreaName(area.getAreaName());
				areaVo.setAreaCode(area.getAreaCode());
				areaVo.setOrderNo(area.getOrderNo());
				
				//设置详情项
				//先到所有工作详细项
				List<NwWorkDetailVo> detailVos = new ArrayList<NwWorkDetailVo>();
				List<NwWorkDetail> allDetails = this.nwWorkDetailManager.getDetailsByWorkIdAreaId(workId,tmpBigIdInt);
				for(NwWorkDetail tmpDetail : allDetails){
					NwWorkDetailVo detailVo = new NwWorkDetailVo();
					BeanUtils.copyProperties(detailVo, tmpDetail);
					NwItem smallCategory = this.nwItemManager.getNwItemByItemId(tmpDetail.getItemId());
					detailVo.setItemName(smallCategory.getItemName());
					
					if(tmpDetail.getExecuterId() > 0){
						User executer = this.userManager.getUserByUserId(tmpDetail.getExecuterId());
						if(executer != null && executer.getxId() > 0){
							detailVo.setExecuterName(executer.getName());
						}else{
							detailVo.setExecuterName("");
						}
					}else{
						detailVo.setExecuterName("");
					}
					
//					NwArea area2 = this.nwAreaManager.getNwAreaByAreaId(tmpDetail.getAreaId());
					detailVo.setAreaName(area.getAreaName());
					
					if(tmpDetail.getBeginTime() != null){
						detailVo.setBeginTimeStr(sf.format(tmpDetail.getBeginTime()));
					}
					if(tmpDetail.getFinishTime() != null){
						detailVo.setFinishTimeStr(sf.format(tmpDetail.getFinishTime()));
					}
					
					
//					itemSum ++;
//					if(tmpDetail.getWorkState() == 1 && tmpDetail.getFinishTime() != null){
//						itemDoneSum ++;
//					}
					
					//设置异常信息detailLogVo
					List<NwDetailLogVo> logVos = new ArrayList<NwDetailLogVo>();
					List<NwDetailLog> allLogs = this.nwDetailLogManager.getLogsByDetailId(tmpDetail.getDetailId());
					for(NwDetailLog tmpLog : allLogs){
						NwDetailLogVo logVo = new NwDetailLogVo();
						BeanUtils.copyProperties(logVo, tmpLog);
						if(tmpLog.getLogTime() != null){
							logVo.setLogTimeStr(sf.format(tmpLog.getLogTime()));
						}
						if(tmpLog.getSolveTime() != null){
							logVo.setSolveTimeStr(sf.format(tmpLog.getSolveTime()));
						}
						if(tmpLog.getSubmitTime() != null){
							logVo.setSubmitTimeStr(sf.format(tmpLog.getSubmitTime()));
						}
						
						logVos.add(logVo);
					}
					
					detailVo.setLogs(logVos);
					detailVo.setAttachs(tmpDetail.getAttachs());
					

					detailVos.add(detailVo);
				}
				
				areaVo.setDetailList(detailVos);
				areaVos.add(areaVo);
			}
			
		}
		

		jsonObj.put("result", areaVos);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	
	/**
	 * 新建巡更
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=newNightWatch")
	@Transactional
	public void newNightWatch(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String workTitle = request.getParameter("workTitle");   //标题
		String areaIds = request.getParameter("areaIds");     //区域ID,用逗号拼接
		String executerId = request.getParameter("executerId"); //执行人

		String loopType = request.getParameter("loopType");     //循环类型
		String weekDays = request.getParameter("weekDays");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		
		//执行时间段数组，例：opTimes=[{'beginTime':'09:00','endTime':'11:00'},{'beginTime':'12:00','endTime':'14:00'}]
		String opTimes = request.getParameter("opTimes");
		
//		String beginTime = request.getParameter("beginTime");
//		String endTime = request.getParameter("endTime");
		
		workTitle = StringUtil.isNotEmpty(workTitle) ? workTitle : "巡更";
		loopType = StringUtil.isNotEmpty(loopType) ? loopType : "0";
//		beginTime = StringUtil.isNotEmpty(beginDate) ? beginTime : "00:00";
//		endTime = StringUtil.isNotEmpty(endDate) ? endTime : "23:59";
		Timestamp now = new Timestamp(System.currentTimeMillis());
		try {
			
			if(user != null){
				executerId = StringUtil.isNotEmpty(executerId) ? executerId : user.getUserId().toString();
				
				if(StringUtil.isNotEmpty(areaIds) && StringUtil.isNotEmpty(beginDate) && StringUtil.isNotEmpty(endDate)){
					NwList workList = new NwList();
					Integer listId = this.nwListManager.getMaxId("listId");
					workList.setListId(listId);
					workList.setWorkTitle(workTitle);
					workList.setAreaIds(areaIds);
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
					this.nwListManager.save(workList);
					
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
								List<NwWork> allWorkItems = new ArrayList<NwWork>();
								for(String[] tmpTArray : timeArray){
									
									Timestamp startTime = Timestamp.valueOf(tmpTArray[0]);
									Timestamp toTime = Timestamp.valueOf(tmpTArray[1]);
									
									NwWork workItem = new NwWork();
									Integer workId = this.nwWorkManager.getMaxId("workId");
									workItem.setWorkId(workId);
									workItem.setValid(true);
									workItem.setListId(listId);
									workItem.setWorkState(0);
//									workItem.setAreaId(Integer.valueOf(areaId));
									workItem.setExecuterId(Integer.valueOf(executerId));
									workItem.setSysTime(now);
									workItem.setReadSafeRules(0);
									
									workItem.setBeginTime(startTime);
									workItem.setEndTime(toTime);
									
									this.nwWorkManager.save(workItem);
									allWorkItems.add(workItem);
								}
								
								
								//3、生成每个区域下的所有小项为最终工作项
								String[] areaIdArray = areaIds.split(",");
								for(NwWork tmpWorkItem : allWorkItems){
									for(int i = 0;i<areaIdArray.length;i++){
//										Integer tmpBigId = Integer.valueOf(areaId);
										Integer tmpBigId = Integer.valueOf(areaIdArray[i]);
										
//										BigCategory bigCategory = this.nwAreaManager.getBigCategoryByBigId(tmpBigId);
										List<NwItem> smallList = this.nwItemManager.getNwItemsByAreaId(tmpBigId);
										for(NwItem tmpSmall : smallList){

											NwWorkDetail itemDetail = new NwWorkDetail();
											Integer detailId = this.nwWorkDetailManager.getMaxId("detailId");
											itemDetail.setDetailId(detailId);
											itemDetail.setListId(listId);
											itemDetail.setWorkId(tmpWorkItem.getWorkId());
											itemDetail.setAreaId(tmpBigId);
											itemDetail.setItemId(tmpSmall.getItemId());
											itemDetail.setOpResult(0);
											itemDetail.setExecuterId(Integer.valueOf(executerId));
											itemDetail.setBeginTime(tmpWorkItem.getBeginTime());
											itemDetail.setWorkState(0);
											itemDetail.setValid(true);
											
											this.nwWorkDetailManager.save(itemDetail);
										}
									}
									
								}
							}
						}
							
					}
					
					success = true;
					
					/**************新建工作后，给执行人发送一条推送*****************/
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String pushContent = user.getName()+" 于 "+sf.format(now)+" 创建了一条巡更任务【"+workTitle+"】给您，请点击更新！";
					JPushUtils pushUtil = JPushUtils.getInstance();
					
					User executer = this.userManager.getUserByUserId(Integer.valueOf(executerId));
					//pushUtil.pushNewWork( pushContent, 1, executer.getUserName());
					
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
		String workId = request.getParameter("workId");  
		
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(workId)){
					NwWork workItem = this.nwWorkManager.getWorkByWorkId(Integer.valueOf(workId));
					
					//能进来说明已读过安全说明
					workItem.setReadSafeRules(1);
					this.nwWorkManager.save(workItem);
					
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
	 * 查看巡更详情(进入巡更)
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=opNightWatch")
	@Transactional
	public void opNightWatch(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;
		OpNightWatchVo vo = new OpNightWatchVo();

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String workId = request.getParameter("workId");
		
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(workId)){
					
					vo = this.nwWorkManager.getNWDetail(Integer.valueOf(workId));
					
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
	 * 查看单条巡更记录
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=viewNightWatch")
	@Transactional
	public void viewNightWatch(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;
//		OpNightWatchVo vo = new OpNightWatchVo();
		List<NwWorkDetailVo> rtnList = new ArrayList<NwWorkDetailVo>();

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String workId = request.getParameter("workId");
		
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(workId)){
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					//根据workId得到信息
//					NwWork workItem = this.nwWorkManager.getWorkByWorkId(Integer.valueOf(workId));
					
					//得到下面的巡更项
					List<NwWorkDetail> allDetails = this.nwWorkDetailManager.getDetailsByWorkId(Integer.valueOf(workId));
					
					for(NwWorkDetail tmpDetail : allDetails){
						NwWorkDetailVo detailVo = new NwWorkDetailVo();
						BeanUtils.copyProperties(detailVo, tmpDetail);
						NwItem smallCategory = this.nwItemManager.getNwItemByItemId(tmpDetail.getItemId());
						detailVo.setItemName(smallCategory.getItemName());
						if(tmpDetail.getBeginTime() != null){
							detailVo.setBeginTimeStr(sf.format(tmpDetail.getBeginTime()));
						}
						if(tmpDetail.getFinishTime() != null){
							detailVo.setFinishTimeStr(sf.format(tmpDetail.getFinishTime()));
						}
						if(tmpDetail.getExecuterId() != null && tmpDetail.getExecuterId().intValue() > 0){
							User executer = this.userManager.getUserByUserId(tmpDetail.getExecuterId());
							detailVo.setExecuterName(executer.getName());
						}
						
						
						//设置异常信息detailLogVo
						List<NwDetailLogVo> logVos = new ArrayList<NwDetailLogVo>();
						List<NwDetailLog> allLogs = this.nwDetailLogManager.getLogsByDetailId(tmpDetail.getDetailId());
						for(NwDetailLog tmpLog : allLogs){
							NwDetailLogVo logVo = new NwDetailLogVo();
							BeanUtils.copyProperties(logVo, tmpLog);
							if(tmpLog.getLogTime() != null){
								logVo.setLogTimeStr(sf.format(tmpLog.getLogTime()));
							}
							if(tmpLog.getSolveTime() != null){
								logVo.setSolveTimeStr(sf.format(tmpLog.getSolveTime()));
							}
							if(tmpLog.getSubmitTime() != null){
								logVo.setSubmitTimeStr(sf.format(tmpLog.getSubmitTime()));
							}
							
							logVos.add(logVo);
						}
						
						detailVo.setLogs(logVos);
						

						rtnList.add(detailVo);
					}
					
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
		jsonObj.put("result", rtnList);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	
	/**
	 * 完成小项工作(不包含异常信息)
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
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		//参数：
		String detailId = request.getParameter("detailId");
		String opResult = request.getParameter("opResult");
//		String errorLog = request.getParameter("errorLog");
		String finishTimeStr = request.getParameter("finishTime");
		Timestamp finishTime = null;
		
		String workFinishTimeStr = request.getParameter("workFinishTime");//任务的完成 时间 
		Timestamp workFinishTime = null;
		
		if(StringUtil.isNotEmpty(finishTimeStr)){
			finishTime = Timestamp.valueOf(finishTimeStr);
		}else{
			finishTime = now;
		}
		
		if(StringUtil.isNotEmpty(workFinishTimeStr)){
			workFinishTime = Timestamp.valueOf(workFinishTimeStr);
		}else{
			workFinishTime = now;
		}
		
		opResult = StringUtil.isNotEmpty(opResult) ? opResult : "1";
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(detailId)){
					NwWorkDetail detail = this.nwWorkDetailManager.getDetailByDetailId(Integer.valueOf(detailId));
					
					if(detail.getOpResult() != 0 && detail.getFinishTime() != null){
						//已上传过的，无须理会
					}else{
					
						detail.setFinishTime(finishTime);
						detail.setSysTime(now);
	//					detail.setErrorLog(errorLog);
						detail.setOpResult(Integer.valueOf(opResult));
						
						//附件
						String attachs = uploadAttachmentBySize(multipartRequest, "nightWatch");
						detail.setAttachs(attachs);
						
						detail.setWorkState(1);
						
						this.nwWorkDetailManager.save(detail);
						
						//判断本次巡检小项是否已全部完成,如果已完成,则更改WorkItem及WorkList状态
						List<NwWorkDetail> allDetails = this.nwWorkDetailManager.getDetailsByWorkId(detail.getWorkId());
						boolean hasAllDetailsDone = true;
						for(NwWorkDetail tmpDetail : allDetails){
							if(tmpDetail.getWorkState() == 0){
								hasAllDetailsDone = false;
								break;
							}
						}
						if(hasAllDetailsDone){
							NwWork workItem = this.nwWorkManager.getWorkByWorkId(detail.getWorkId());
							workItem.setFinishTime(workFinishTime);
							workItem.setSysTime(now);
							workItem.setWorkState(1);
							this.nwWorkManager.save(workItem);
							
							List<NwWork> allItems = this.nwWorkManager.getWorksByListId(detail.getListId());
							boolean hasAllItemsDone = true;
							for(NwWork tmpItem : allItems){
								if(tmpItem.getWorkState() == 0){
									hasAllItemsDone = false;
									break;
								}
							}
							if(hasAllItemsDone){
								NwList workList = this.nwListManager.getListByListId(detail.getListId());
								workList.setFinishTime(workFinishTime);
								workList.setSysTime(now);
								workList.setWorkState(1);
								this.nwListManager.save(workList);
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
	 * 上报小项的异常信息
	 * 有此上报，意味着此小项不正常
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=opDetailLog")
	@Transactional
	public void opDetailLog(HttpServletRequest request,HttpServletResponse response,DefaultMultipartHttpServletRequest multipartRequest)throws Exception {
		String message = "";
		boolean success = true;
		Integer logId = 0;
		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String detailId = request.getParameter("detailId");
		String logContent = request.getParameter("logContent");
		String solveStateStr = request.getParameter("solveState");
		String solveTimeStr = request.getParameter("solveTime");
//		String solveWord = request.getParameter("solveWord");
		
		int solveState = 0;
		if(StringUtil.isNotEmpty(solveStateStr)){
			solveState = Integer.valueOf(solveStateStr);
		}
		
		
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		Timestamp solveTime = new Timestamp(System.currentTimeMillis());
		if(StringUtil.isNotEmpty(solveTimeStr)){
			solveTime = Timestamp.valueOf(solveTimeStr);
		}
		
		String logTimeStr = request.getParameter("logTime");
		Timestamp logTime = null;
		
		if(StringUtil.isNotEmpty(logTimeStr)){
			logTime = Timestamp.valueOf(logTimeStr);
		}else{
			logTime = now;
		}
		
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(detailId)){
					NwWorkDetail detail = this.nwWorkDetailManager.getDetailByDetailId(Integer.valueOf(detailId));
					if(detail.getOpResult() == 1 || detail.getOpResult() == 0){
						//有此上报，意味着此小项不正常，如果之前提交的是正常，则改为异常
						detail.setOpResult(-1);
						this.nwWorkDetailManager.save(detail);
					}
					//附件
					String attachs = uploadAttachmentBySize(multipartRequest, "nightWatch");
					
					NwDetailLog log = new NwDetailLog();
					log.setDetailId(Integer.valueOf(detailId));
					log.setListId(detail.getListId());
					logId = this.nwDetailLogManager.getMaxId("logId");
					log.setLogId(logId);
					log.setLogContent(logContent);
					log.setLogTime(logTime);
					log.setSysTime(now);
					log.setWorkId(detail.getWorkId());
					log.setSolveState(solveState);
					log.setSubmitState(0);
//					if(solveState == 1){
						log.setSolveTime(solveTime);
//						log.setSolveWord(solveWord);
//						log.setSolveAttachs(attachs);
//					}else{
						log.setLogAttachs(attachs);
//					}
					
					
					
					
					
					this.nwDetailLogManager.save(log);
					
					
					
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
		jsonObj.put("result", logId);
		
		//设置字符编码
        response.setContentType(CoreConstant.CONTENT_TYPE);
		response.getWriter().print(jsonObj);
	}
	
	/**
	 * 上报异常按钮
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=opSubmit")
	@Transactional
	public void opSubmit(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String logId = request.getParameter("logId");
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(logId)){
					NwDetailLog log = this.nwDetailLogManager.getLogByLogId(Integer.valueOf(logId));
					
//					log.setSolveState(0);
					log.setSubmitState(1);
					log.setSubmitTime(now);
					log.setSysTime(now);
					
					this.nwDetailLogManager.save(log);
					
					//发送推送给布置任务者
					NwList nwList = this.nwListManager.getListByListId(log.getListId());
					if(nwList != null && nwList.getxId().intValue() > 0){
						Integer createrId = nwList.getCreaterId();
						if(createrId != null && createrId.intValue() > 0){
							User creater = this.userManager.getUserByUserId(createrId);
							if(creater != null && creater.getxId().intValue() > 0){
								
								String pushContent = user.getName()+" 于 "+sf.format(now)+" 上报了一条异常信息，请及时查看！";
								JPushUtils pushUtil = JPushUtils.getInstance();
								pushUtil.pushErrorSubmit(log.getWorkId(), pushContent, 1, creater.getUserName());
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
	 * 确认解决按钮
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=opSolve")
	@Transactional
	public void opSolve(HttpServletRequest request,HttpServletResponse response,DefaultMultipartHttpServletRequest multipartRequest)throws Exception {
		String message = "";
		boolean success = true;

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String logId = request.getParameter("logId");
		String solveWord = request.getParameter("solveWord");
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(logId)){
					NwDetailLog log = this.nwDetailLogManager.getLogByLogId(Integer.valueOf(logId));
					
					log.setSolveState(1);
//					log.setSubmitState(1);
					if(log.getSolveTime() == null){
						log.setSolveTime(now);
					}
					
					log.setSolveWord(solveWord);
					
					//附件
					String attachs = uploadAttachmentBySize(multipartRequest, "nightWatch");
					log.setSolveAttachs(attachs);
					
					this.nwDetailLogManager.save(log);
					
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
	 * 删除巡更
	 * 
	 * @param request
	 * @param response
	 * @param vo
	 */
	@RequestMapping(params="method=delNwWork")
	@Transactional
	public void delNwWork(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String message = "";
		boolean success = true;

		JSONObject jsonObj = new JSONObject();
		//用户
		String token = request.getParameter("token");
		User user = LoginConfirm.loginUserMap.get(token);
		
		//参数：
		String workId = request.getParameter("workId");  
		
		try {
			
			if(user != null){
				
				if(StringUtil.isNotEmpty(workId)){
					NwWork workItem = this.nwWorkManager.getWorkByWorkId(Integer.valueOf(workId));
					workItem.setValid(false);
					this.nwWorkManager.save(workItem);
					
					//发送推送给执行任务者
					NwList nwList = this.nwListManager.getListByListId(workItem.getListId());
					if(nwList != null && nwList.getxId().intValue() > 0){
						Integer executerId = workItem.getExecuterId();
						if(executerId != null && executerId.intValue() > 0){
							User creater = this.userManager.getUserByUserId(executerId);
							if(creater != null && creater.getxId().intValue() > 0){
								
								String pushContent = user.getName()+" 取消了巡更任务【" + nwList.getWorkTitle() + "】，请点击本通知更新！";
								JPushUtils pushUtil = JPushUtils.getInstance();
								pushUtil.pushDelNightWatch(workItem.getWorkId(), pushContent, 1, creater.getUserName());
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
	
}
