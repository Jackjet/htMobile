package com.kwchina.work.nightWatch.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.nightWatch.entity.NwArea;
import com.kwchina.work.nightWatch.entity.NwItem;

public interface NwItemManager extends BasicManager{
	public NwItem getNwItemByItemId(Integer itemId);
	public List<NwItem> getNwItemsByAreaId(Integer areaId);
	public List<NwItem> getAllValid();
}
