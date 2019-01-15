package com.kwchina.work.nightWatch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.nightWatch.dao.NwAreaItemDAO;
import com.kwchina.work.nightWatch.entity.NwAreaItem;
import com.kwchina.work.nightWatch.service.NwAreaItemManager;

@Service("nwAreaItemManager")
public class NwAreaItemManagerImpl extends BasicManagerImpl<NwAreaItem> implements NwAreaItemManager{

	private NwAreaItemDAO nwAreaItemDAO;

	@Autowired
	public void setSystemNwAreaItemDAO(NwAreaItemDAO nwAreaItemDAO) {
		this.nwAreaItemDAO = nwAreaItemDAO;
		super.setDao(nwAreaItemDAO);
	}
	

	public List<NwAreaItem> getListByItemId(Integer itemId){
		List<NwAreaItem> list = new ArrayList<NwAreaItem>();
		String hql = "from NwAreaItem where itemId = '" + itemId + "'";
		list = this.nwAreaItemDAO.getResultByQueryString(hql);
		return list;
	}
	public List<NwAreaItem> getListByAreaId(Integer areaId){
		List<NwAreaItem> list = new ArrayList<NwAreaItem>();
		String hql = "from NwAreaItem where areaId = '" + areaId + "'";
		list = this.nwAreaItemDAO.getResultByQueryString(hql);
		return list;
	}
	public NwAreaItem getInstanceByItemIdAreaId(Integer itemId,Integer areaId){
		NwAreaItem instance = new NwAreaItem();
		String sql = "from NwAreaItem instance where instance.areaId='" + areaId + "' and instance.itemId = '" + itemId + "'";
		List<NwAreaItem> list = this.nwAreaItemDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			instance = list.get(0);
		}
		
		return instance;
	}
}
