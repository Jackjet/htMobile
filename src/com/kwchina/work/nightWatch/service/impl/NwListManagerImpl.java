package com.kwchina.work.nightWatch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.nightWatch.dao.NwListDAO;
import com.kwchina.work.nightWatch.entity.NwList;
import com.kwchina.work.nightWatch.service.NwListManager;

@Service("nwListManager")
public class NwListManagerImpl extends BasicManagerImpl<NwList> implements NwListManager{

	private NwListDAO nwListDAO;

	@Autowired
	public void setSystemNwListDAO(NwListDAO nwListDAO) {
		this.nwListDAO = nwListDAO;
		super.setDao(nwListDAO);
	}
	
	public NwList getListByListId(Integer listId){
		NwList workList = new NwList();
		String sql = "from NwList workList where workList.listId='" + listId + "'";
		List<NwList> list = this.nwListDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			workList = list.get(0);
		}
		
		return workList;
	}
}
