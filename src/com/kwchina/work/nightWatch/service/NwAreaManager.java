package com.kwchina.work.nightWatch.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.nightWatch.entity.NwArea;

public interface NwAreaManager extends BasicManager{
	public NwArea getNwAreaByAreaId(Integer areaId);
	public List<NwArea> getAllValid();
}
