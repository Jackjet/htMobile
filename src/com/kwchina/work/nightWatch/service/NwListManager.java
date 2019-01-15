package com.kwchina.work.nightWatch.service;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.nightWatch.entity.NwList;

public interface NwListManager extends BasicManager{
	public NwList getListByListId(Integer listId);
}
