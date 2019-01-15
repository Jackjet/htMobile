package com.kwchina.work.nightWatch.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.nightWatch.dao.NwItemDAO;
import com.kwchina.work.nightWatch.entity.NwArea;
import com.kwchina.work.nightWatch.entity.NwAreaItem;
import com.kwchina.work.nightWatch.entity.NwItem;
import com.kwchina.work.nightWatch.service.NwAreaItemManager;
import com.kwchina.work.nightWatch.service.NwItemManager;

@Service("nwItemManager")
public class NwItemManagerImpl extends BasicManagerImpl<NwItem> implements NwItemManager{

	private NwItemDAO nwItemDAO;
	
	@Autowired
	private NwAreaItemManager nwAreaItemManager;

	@Autowired
	public void setSystemNwItemDAO(NwItemDAO nwItemDAO) {
		this.nwItemDAO = nwItemDAO;
		super.setDao(nwItemDAO);
	}
	
	public NwItem getNwItemByItemId(Integer itemId){
		NwItem category = new NwItem();
		String sql = "from NwItem item where item.itemId='" + itemId + "'";
		List<NwItem> list = this.nwItemDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			category = list.get(0);
		}
		
		return category;
		
	}
	public List<NwItem> getNwItemsByAreaId(Integer areaId){
		List<NwItem> list = new ArrayList<NwItem>();
		String hql = "from NwAreaItem  where areaId = '" + areaId + "'";
		List<NwAreaItem> allList = this.nwAreaItemManager.getResultByQueryString(hql);
		for(NwAreaItem tmpAreaItem : allList){
			NwItem item = getNwItemByItemId(tmpAreaItem.getItemId());
			if(item.isValid()){
				list.add(item);
			}
		}
		
		return list;
	}
	

	public List<NwItem> getAllValid(){
		List<NwItem> list = new ArrayList<NwItem>();
		String hql = "from NwItem where valid = 1 order by orderNo";
		list = this.nwItemDAO.getResultByQueryString(hql);
		return list;
	}
	
}
