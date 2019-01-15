package com.kwchina.work.nightWatch.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.nightWatch.entity.NwDetailLog;

public interface NwDetailLogManager extends BasicManager{
	public List<NwDetailLog> getLogsByDetailId(Integer detailId);
	
	
	public NwDetailLog getLogByLogId(Integer logId);
}
