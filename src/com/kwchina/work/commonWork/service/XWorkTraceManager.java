package com.kwchina.work.commonWork.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.commonWork.entity.XWorkTrace;

public interface XWorkTraceManager extends BasicManager{
	public List<XWorkTrace> getTracesByWorkId(Integer workId);
}
