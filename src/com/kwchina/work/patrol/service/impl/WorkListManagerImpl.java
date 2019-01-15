package com.kwchina.work.patrol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.patrol.dao.WorkListDAO;
import com.kwchina.work.patrol.entity.WorkList;
import com.kwchina.work.patrol.service.WorkListManager;

@Service("workListManager")
public class WorkListManagerImpl extends BasicManagerImpl<WorkList> implements WorkListManager{

	private WorkListDAO workListDAO;

	@Autowired
	public void setSystemWorkListDAO(WorkListDAO workListDAO) {
		this.workListDAO = workListDAO;
		super.setDao(workListDAO);
	}
	
	
	public WorkList getWorkListByListId(Integer listId){
		WorkList workList = new WorkList();
		String sql = "from WorkList workList where workList.listId='" + listId + "'";
		List<WorkList> list = this.workListDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			workList = list.get(0);
		}
		
		return workList;
	}
	
}
