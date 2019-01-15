package com.kwchina.work.commonWork.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.commonWork.dao.XWorkDAO;
import com.kwchina.work.commonWork.entity.XWork;
import com.kwchina.work.commonWork.service.XWorkManager;
import com.kwchina.work.commonWork.vo.XWorkVo;

@Service("xWorkManager")
public class XWorkManagerImpl extends BasicManagerImpl<XWork> implements XWorkManager{

	private XWorkDAO xWorkDAO;
	
	@Autowired
	private UserManager userManager;

	@Autowired
	public void setSystemXWorkDAO(XWorkDAO xWorkDAO) {
		this.xWorkDAO = xWorkDAO;
		super.setDao(xWorkDAO);
	}
	
	/**
	 * 根据workId得到XWork
	 */
	public XWork getWorkByWorkId (Integer workId,boolean valid){
		XWork work = new XWork();
		String sql = "from XWork work where work.workId='" + workId + "'";
		sql += " and work.valid = "+valid;
		List<XWork> list = this.xWorkDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			work = list.get(0);
		}
		
		return work;
	}
	
	/**
	 * 根据日期得到当天的所有工作
	 */
	public List<XWorkVo> getWorkByDate(String queryDate){
		List<XWorkVo> list = new ArrayList<XWorkVo>();
		String queryBTime = queryDate + " 00:00:00";
		String queryETime = queryDate + " 23:59:59";
		// work.workState = 1 and work.finishTime != null and 
		String hql = " from XWork work where work.beginTime >= '" + queryBTime + "' and work.beginTime <= '" + queryETime + "' order by work.beginTime";
		List<XWork> works = this.xWorkDAO.getResultByQueryString(hql);
		for(XWork tmpWork : works){
			XWorkVo vo = transferToVo(tmpWork);
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 转换成vo
	 */
	public XWorkVo transferToVo(XWork xWork){
		XWorkVo vo = new XWorkVo();
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(xWork != null && xWork.getxId() != null){
				BeanUtils.copyProperties(vo, xWork);
				vo.setBeginTimeStr(sf.format(xWork.getBeginTime()));
				vo.setEndTimeStr(sf.format(xWork.getEndTime()));
				User executer = this.userManager.getUserByUserId(xWork.getExecuterId());
				vo.setExecuterName(executer.getName());
				
				if(xWork.getFinisherId() != null && xWork.getFinisherId().intValue() > 0){
					User finisher = this.userManager.getUserByUserId(xWork.getFinisherId());
					if(finisher != null){
						vo.setFinisherName(finisher.getName());
					}
				}
				
//				if(xWork.getCreaterId() != null && xWork.getCreaterId().intValue() > 0){
//					User creater = this.userManager.getUserByUserId(xWork.getCreaterId());
//					if(creater != null){
//						vo.setCreaterName(creater.getName());
//					}
//				}
				
				if(xWork.getCreateTime() != null){
					vo.setCreateTimeStr(sf.format(xWork.getCreateTime()));
				}
				if(xWork.getFinishTime() != null){
					vo.setFinishTimeStr(sf.format(xWork.getFinishTime()));
				}
				if(StringUtil.isNotEmpty(xWork.getCopyTo())){
					String copyToName = "";
					String[] userIds = xWork.getCopyTo().split(",");
					for(String tmpUserId : userIds){
						User tmpUser = this.userManager.getUserByUserId(Integer.valueOf(tmpUserId));
						if(tmpUser != null){
							copyToName += tmpUser.getName() + ",";
						}
					}
					if(copyToName.endsWith(",")){
						copyToName = copyToName.substring(0,copyToName.length() - 1);
					}
					vo.setCopyToName(copyToName);
				}
				
				if(xWork.getCreaterId() != null && xWork.getCreaterId().intValue() > 0){
					User creater = this.userManager.getUserByUserId(xWork.getCreaterId());
					if(creater != null){
						vo.setCreaterName(creater.getName());
					}
				}
				
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return vo;
	}
	
}
