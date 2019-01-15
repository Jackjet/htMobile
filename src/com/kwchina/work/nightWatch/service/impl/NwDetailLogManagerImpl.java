package com.kwchina.work.nightWatch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.nightWatch.dao.NwDetailLogDAO;
import com.kwchina.work.nightWatch.entity.NwDetailLog;
import com.kwchina.work.nightWatch.service.NwDetailLogManager;

@Service("nwDetailLogManager")
public class NwDetailLogManagerImpl extends BasicManagerImpl<NwDetailLog> implements NwDetailLogManager{

	private NwDetailLogDAO nwDetailLogDAO;

	@Autowired
	public void setSystemNwDetailLogDAO(NwDetailLogDAO nwDetailLogDAO) {
		this.nwDetailLogDAO = nwDetailLogDAO;
		super.setDao(nwDetailLogDAO);
	}
	
	public List<NwDetailLog> getLogsByDetailId(Integer detailId){
		List<NwDetailLog> list = new ArrayList<NwDetailLog>();
		String hql = "from NwDetailLog log where log.detailId = '" + detailId + "' order by log.logTime";
		list = this.nwDetailLogDAO.getResultByQueryString(hql);
		return list;
	}
	
	
	public NwDetailLog getLogByLogId(Integer logId){
		NwDetailLog log = new NwDetailLog();
		String sql = "from NwDetailLog log where log.logId='" + logId + "'";
		List<NwDetailLog> list = this.nwDetailLogDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			log = list.get(0);
		}
		
		return log;
	}
	
}
