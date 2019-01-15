package com.kwchina.work.nightWatch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.nightWatch.dao.NwWorkDetailDAO;
import com.kwchina.work.nightWatch.entity.NwWorkDetail;
import com.kwchina.work.nightWatch.service.NwWorkDetailManager;

@Service("nwWorkDetailManager")
public class NwWorkDetailManagerImpl extends BasicManagerImpl<NwWorkDetail> implements NwWorkDetailManager{

	private NwWorkDetailDAO nwWorkDetailDAO;

	@Autowired
	public void setSystemNwWorkDetailDAO(NwWorkDetailDAO nwWorkDetailDAO) {
		this.nwWorkDetailDAO = nwWorkDetailDAO;
		super.setDao(nwWorkDetailDAO);
	}
	
	public List<NwWorkDetail> getDetailsByWorkId(Integer workId){
		List<NwWorkDetail> list = new ArrayList<NwWorkDetail>();
		String hql = "from NwWorkDetail detail where detail.workId = '" + workId + "' and valid=1 order by detail.areaId,detail.itemId";
		list = this.nwWorkDetailDAO.getResultByQueryString(hql);
		return list;
	}
	
	public List<NwWorkDetail> getDetailsByWorkIdAreaId(Integer workId,Integer areaId){
		List<NwWorkDetail> list = new ArrayList<NwWorkDetail>();
		String hql = "from NwWorkDetail detail where detail.workId = '" + workId + "' and detail.areaId = '"+areaId+"' and valid=1 order by detail.areaId,detail.itemId";
		list = this.nwWorkDetailDAO.getResultByQueryString(hql);
		return list;
	}

	public List<NwWorkDetail> getDetailsByListId(Integer listId){
		List<NwWorkDetail> list = new ArrayList<NwWorkDetail>();
		String hql = "from NwWorkDetail detail where detail.listId = '" + listId + "' and valid=1 order by detail.areaId,detail.itemId";
		list = this.nwWorkDetailDAO.getResultByQueryString(hql);
		return list;
	}
	
	public NwWorkDetail getDetailByDetailId(Integer detailId){
		NwWorkDetail detail = new NwWorkDetail();
		String sql = "from NwWorkDetail detail where detail.detailId='" + detailId + "'";
		List<NwWorkDetail> list = this.nwWorkDetailDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			detail = list.get(0);
		}
		
		return detail;
	}
}
