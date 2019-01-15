package com.kwchina.work.commonWork.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.commonWork.dao.XWorkTraceDAO;
import com.kwchina.work.commonWork.entity.XWorkTrace;
import com.kwchina.work.commonWork.service.XWorkTraceManager;

@Service("xWorkTraceManager")
public class XWorkTraceManagerImpl extends BasicManagerImpl<XWorkTrace> implements XWorkTraceManager{

	private XWorkTraceDAO xWorkTraceDAO;

	@Autowired
	public void setSystemXWorkTraceDAO(XWorkTraceDAO xWorkTraceDAO) {
		this.xWorkTraceDAO = xWorkTraceDAO;
		super.setDao(xWorkTraceDAO);
	}
	

	public List<XWorkTrace> getTracesByWorkId(Integer workId){
		List<XWorkTrace> list = new ArrayList<XWorkTrace>();
		String hql = " from XWorkTrace trace where trace.workId = '" + workId + "' order by trace.operateTime asc";
		list = this.getResultByQueryString(hql);
		return list;
		
	}
}
