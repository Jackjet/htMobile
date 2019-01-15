package com.kwchina.work.nightWatch.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.nightWatch.entity.NwAreaItem;

public interface NwAreaItemManager extends BasicManager{
	public List<NwAreaItem> getListByItemId(Integer itemId);
	public List<NwAreaItem> getListByAreaId(Integer areaId);
	public NwAreaItem getInstanceByItemIdAreaId(Integer itemId,Integer areaId);
}
