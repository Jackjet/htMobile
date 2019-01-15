package com.kwchina.work.nightWatch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.nightWatch.dao.NwAreaDAO;
import com.kwchina.work.nightWatch.entity.NwArea;
import com.kwchina.work.nightWatch.service.NwAreaManager;

@Service("nwAreaManager")
public class NwAreaManagerImpl extends BasicManagerImpl<NwArea> implements NwAreaManager{

	private NwAreaDAO nwAreaDAO;

	@Autowired
	public void setSystemNwAreaDAO(NwAreaDAO nwAreaDAO) {
		this.nwAreaDAO = nwAreaDAO;
		super.setDao(nwAreaDAO);
	}
	
	public NwArea getNwAreaByAreaId(Integer areaId){
		NwArea area = new NwArea();
		String sql = "from NwArea area where area.areaId='" + areaId + "'";
		List<NwArea> list = this.nwAreaDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			area = list.get(0);
		}
		
		return area;
	}
	
	public List<NwArea> getAllValid(){
		List<NwArea> list = new ArrayList<NwArea>();
		String hql = "from NwArea where valid = 1 order by orderNo";
		list = this.nwAreaDAO.getResultByQueryString(hql);
		return list;
	}
	
}
