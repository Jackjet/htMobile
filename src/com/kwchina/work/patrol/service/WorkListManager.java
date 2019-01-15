package com.kwchina.work.patrol.service;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.patrol.entity.WorkList;

public interface WorkListManager extends BasicManager{
	public WorkList getWorkListByListId(Integer listId);
}
