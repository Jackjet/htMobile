package com.kwchina.work.nightWatch.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.nightWatch.entity.NwWorkDetail;

public interface NwWorkDetailManager extends BasicManager{
	public List<NwWorkDetail> getDetailsByWorkId(Integer workId);
	
	public List<NwWorkDetail> getDetailsByWorkIdAreaId(Integer workId,Integer areaId);

	public List<NwWorkDetail> getDetailsByListId(Integer listId);
	
	public NwWorkDetail getDetailByDetailId(Integer detailId);
}
