package com.kwchina.work.security.controller;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cms.workflow.webflow.base.i;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.sys.CoreConstant;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.security.entity.DocumentCategory;
import com.kwchina.work.security.entity.SecurityCost;
import com.kwchina.work.security.service.DocumentCategoryManager;
import com.kwchina.work.security.service.DocumentInforManager;
import com.kwchina.work.security.service.SecurityCostManager;
import com.kwchina.work.security.util.DocumentConstant;
import com.kwchina.work.security.vo.DocumentCategoryVo;
import com.kwchina.work.sys.SysCommonMethod;

import freemarker.template.SimpleDate;

@Controller
@RequestMapping("/security/cost.htm")
public class SecurityCostController {

	@Resource
	private SecurityCostManager securityCostManager;
	
	@Resource
	private DocumentCategoryManager documentCategoryManager;

	@Resource
	private UserManager userManager;


	

	/**
	 * 编辑预算
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=editYs")
	public String editYs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String currentYearStr = request.getParameter("currentYear");
		int thisYear = 0;
		
		int realYear = 0;
		//默认显示当年 
		Date now = new Date();
		SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
		realYear = Integer.valueOf(sf_y.format(now));
		
		if(StringUtil.isNotEmpty(currentYearStr)){
			String tag = request.getParameter("tag");
			if(StringUtil.isNotEmpty(tag)){
				if(tag.equals("0")){  //0为上一年
					thisYear = Integer.valueOf(currentYearStr) - 1;
				}else if(tag.equals("1")) {  //1为下一年 
					thisYear = Integer.valueOf(currentYearStr) + 1;
				}
			}else{
				thisYear = realYear;
			}
			
		}else{
			thisYear = realYear;
		}
		
		
		
		request.setAttribute("_Year", thisYear);

		request.setAttribute("_RealYear", realYear);
		
		//取值，并赋值到页面  name格式为 1_1,即前面为类型,后面为月份
		List<SecurityCost> yearCosts = this.securityCostManager.getYearCosts(thisYear);
		for(SecurityCost tmpCost : yearCosts){
			String tmpKey = "v"+tmpCost.getCostType()+"_"+tmpCost.getDataMonth();
			//System.out.println(tmpKey);
			request.setAttribute(tmpKey, tmpCost.getPlanCount());
		}
		
		
		return "editCostYs";
	}
	
	/**
	 * 保存预算
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=saveYs")
	public String saveYs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = SysCommonMethod.getSystemUser(request);
		//获得年份
		String yearStr = request.getParameter("dataYear");
		int dataYear = 0;
		if(StringUtil.isNotEmpty(yearStr)){
			dataYear = Integer.valueOf(yearStr);
		}else{
			Date now = new Date();
			SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
			dataYear = Integer.valueOf(sf_y.format(now));
		}
		
		//保存,如果已存在 ,修改
//		List<SecurityCost> yearCosts = this.securityCostManager.getYearCosts(dataYear);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		for(int i = 1;i<12;i++){
			for(int j = 1;j<13;j++){
				String tmpValue = request.getParameter(i+"_"+j);
				if(StringUtil.isNotEmpty(tmpValue)){
					SecurityCost tmpCost = this.securityCostManager.getInstance(dataYear, j, i);
					if(tmpCost != null && tmpCost.getCostId() != null && tmpCost.getCostId().intValue() > 0){
						//已存在
						tmpCost.setPlanCount(Double.valueOf(tmpValue));
						tmpCost.setUpdaterId(user.getUserId());
						tmpCost.setUpdateTime(now);
						
						this.securityCostManager.save(tmpCost);
					}else{
						//不存在 
						tmpCost.setPlanCount(Double.valueOf(tmpValue));
						tmpCost.setRealCount(0.0);
						tmpCost.setUpdaterId(user.getUserId());
						tmpCost.setUpdateTime(now);
						
						tmpCost.setCostType(i);
						tmpCost.setCreaterId(user.getUserId());
						tmpCost.setCreateTime(now);
						tmpCost.setDataMonth(j);
						tmpCost.setDataYear(dataYear);
						
						this.securityCostManager.save(tmpCost);
					}
				}
				
			}
		}
		
//		for(SecurityCost tmpCost : yearCosts){
//			request.setAttribute(tmpCost.getCostType()+"_"+tmpCost.getDataMonth(), tmpCost.getPlanCount());
//		}
		
		
		return "redirect:cost.htm?method=editYs";
	}




	/**
	 * 编辑支出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=editZc")
	public String editZc(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		//默认显示当年 
//		Date now = new Date();
//		SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
//		int thisYear = Integer.valueOf(sf_y.format(now));
//		request.setAttribute("_Year", thisYear);
		
		String currentYearStr = request.getParameter("currentYear");
		int thisYear = 0;
		
		int realYear = 0;
		//默认显示当年 
		Date now = new Date();
		SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
		realYear = Integer.valueOf(sf_y.format(now));
		
		if(StringUtil.isNotEmpty(currentYearStr)){
			String tag = request.getParameter("tag");
			if(StringUtil.isNotEmpty(tag)){
				if(tag.equals("0")){  //0为上一年
					thisYear = Integer.valueOf(currentYearStr) - 1;
				}else if(tag.equals("1")) {  //1为下一年 
					thisYear = Integer.valueOf(currentYearStr) + 1;
				}
			}else{
				thisYear = realYear;
			}
			
		}else{
			thisYear = realYear;
		}
		
		request.setAttribute("_Year", thisYear);
		request.setAttribute("_RealYear", realYear);
		
		
		//取值，并赋值到页面  name格式为 1_1,即前面为类型,后面为月份
		List<SecurityCost> yearCosts = this.securityCostManager.getYearCosts(thisYear);
		for(SecurityCost tmpCost : yearCosts){
			String tmpKey = "v"+tmpCost.getCostType()+"_"+tmpCost.getDataMonth();
			//System.out.println(tmpKey);
			request.setAttribute(tmpKey, tmpCost.getRealCount());
		}
		
		
		return "editCostZc";
	}
	
	/**
	 * 保存支出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=saveZc")
	public String saveZc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = SysCommonMethod.getSystemUser(request);
		//获得年份
		String yearStr = request.getParameter("dataYear");
		int dataYear = 0;
		if(StringUtil.isNotEmpty(yearStr)){
			dataYear = Integer.valueOf(yearStr);
		}else{
			Date now = new Date();
			SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
			dataYear = Integer.valueOf(sf_y.format(now));
		}
		
		//保存,如果已存在 ,修改
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		for(int i = 1;i<12;i++){
			for(int j = 1;j<13;j++){
				String tmpValue = request.getParameter(i+"_"+j);
				if(StringUtil.isNotEmpty(tmpValue)){
					SecurityCost tmpCost = this.securityCostManager.getInstance(dataYear, j, i);
					if(tmpCost != null && tmpCost.getCostId() != null && tmpCost.getCostId().intValue() > 0){
						//已存在
						tmpCost.setRealCount(Double.valueOf(tmpValue));
						tmpCost.setUpdaterId(user.getUserId());
						tmpCost.setUpdateTime(now);
						
						this.securityCostManager.save(tmpCost);
					}else{
						//不存在 
						tmpCost.setPlanCount(0.0);
						tmpCost.setRealCount(Double.valueOf(tmpValue));
						tmpCost.setUpdaterId(user.getUserId());
						tmpCost.setUpdateTime(now);
						
						tmpCost.setCostType(i);
						tmpCost.setCreaterId(user.getUserId());
						tmpCost.setCreateTime(now);
						tmpCost.setDataMonth(j);
						tmpCost.setDataYear(dataYear);
						
						this.securityCostManager.save(tmpCost);
					}
				}
				
			}
		}
		
//		for(SecurityCost tmpCost : yearCosts){
//			request.setAttribute(tmpCost.getCostType()+"_"+tmpCost.getDataMonth(), tmpCost.getPlanCount());
//		}
		
		
		return "redirect:cost.htm?method=editZc";
	}
	
	/**
	 * 获取首页需要显示的数据(第一个柱状图)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=getDataByType")
	public void getDataByType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			JSONObject jsonObj = new JSONObject();
			List<Double> ysList = new ArrayList<Double>();
			List<Double> zcList = new ArrayList<Double>();
			
			
			
			String currentYearStr = request.getParameter("currentYear");
			int thisYear = 0;
			int realYear = 0;
			//默认显示当年 
			Date now = new Date();
			SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
			realYear = Integer.valueOf(sf_y.format(now));
			
			if(StringUtil.isNotEmpty(currentYearStr)){
//				String tag = request.getParameter("tag");
//				if(StringUtil.isNotEmpty(tag)){
//					if(tag.equals("0")){  //0为上一年
//						thisYear = Integer.valueOf(currentYearStr) - 1;
//					}else if(tag.equals("1")) {  //1为下一年 
//						thisYear = Integer.valueOf(currentYearStr) + 1;
//					}
//				}else{
//					thisYear = realYear;
//				}
				thisYear = Integer.valueOf(currentYearStr);
				
			}else{
				thisYear = realYear;
			}
			jsonObj.put("_Year", thisYear);
			jsonObj.put("_RealYear", realYear);
			
			
			String costTypeStr = request.getParameter("costType");
			if(StringUtil.isNotEmpty(costTypeStr)){
				int costType = Integer.valueOf(costTypeStr);
				
				//默认显示当年 
//				Date now = new Date();
//				SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
//				int thisYear = Integer.valueOf(sf_y.format(now));
				
				
				for(int i=1;i<13;i++){
					SecurityCost tmpCost = this.securityCostManager.getInstance(thisYear, i, costType);
					if(tmpCost != null && tmpCost.getCostId() != null && tmpCost.getCostId().intValue() > 0){
						//有值
						ysList.add(Double.valueOf(tmpCost.getPlanCount()));
						zcList.add(Double.valueOf(tmpCost.getRealCount()));
					}else{
						//无值，设为0
						ysList.add(0.0);
						zcList.add(0.0);
					}
				}
			}
			
			
			jsonObj.put("_YsList", ysList);
			jsonObj.put("_ZcList", zcList);
			
			//设置字符编码
			response.setContentType(CoreConstant.CONTENT_TYPE);
			response.getWriter().print(jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * 获取首页需要显示的数据(第二个，饼图)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=getYearData")
	public void getYearData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			JSONObject jsonObj = new JSONObject();
			List<Double> ysList = new ArrayList<Double>();
			List<Double> zcList = new ArrayList<Double>();
			
//			String costTypeStr = request.getParameter("costType");
//			if(StringUtil.isNotEmpty(costTypeStr)){
//				int costType = Integer.valueOf(costTypeStr);
				
				//默认显示当年 
//				Date now = new Date();
//				SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
//				int thisYear = Integer.valueOf(sf_y.format(now));
			String currentYearStr = request.getParameter("currentYear");
			int thisYear = 0;
			int realYear = 0;
			//默认显示当年 
			Date now = new Date();
			SimpleDateFormat sf_y = new SimpleDateFormat("yyyy");
			realYear = Integer.valueOf(sf_y.format(now));
			
			if(StringUtil.isNotEmpty(currentYearStr)){
				thisYear = Integer.valueOf(currentYearStr);
				
			}else{
				thisYear = realYear;
			}
				
//				List<SecurityCost> yearCosts = this.securityCostManager.getYearCosts(dataYear);
				
				for(int m=1;m<8;m++){
					double yearYsCount = 0;
					double yearZcCount = 0;
							
					for(int i=1;i<13;i++){
						SecurityCost tmpCost = this.securityCostManager.getInstance(thisYear, i, m);
						if(tmpCost != null && tmpCost.getCostId() != null && tmpCost.getCostId().intValue() > 0){
							//有值
							yearYsCount += (Double.valueOf(tmpCost.getPlanCount()));
							yearZcCount += (Double.valueOf(tmpCost.getRealCount()));
						}
					}
					
//					Map<String, Double> ysMap = new HashMap<String, Double>();
//					ysMap.put(String.valueOf(m), yearYsCount);
					
					ysList.add(yearYsCount);
					
//					Map<String, Double> zcMap = new HashMap<String, Double>();
//					zcMap.put(String.valueOf(m), yearZcCount);
					zcList.add(yearZcCount);
				}
				
//			}
			
				
			double allYsCount = 0;
			for(double eachCount : ysList){
				allYsCount += eachCount;
			}
			double allZcsCount = 0;
			for(double eachCount : zcList){
				allZcsCount += eachCount;
			}
			DecimalFormat df = new DecimalFormat("#.0");
			jsonObj.put("_YsList", ysList);
			jsonObj.put("_ZcList", zcList);
			
			jsonObj.put("_YsCount", df.format(allYsCount));
			jsonObj.put("_ZcCount", df.format(allZcsCount));
			
			//设置字符编码
			response.setContentType(CoreConstant.CONTENT_TYPE);
			response.getWriter().print(jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
