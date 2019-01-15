package com.kwchina.work.commonWork.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.commonWork.entity.XWork;
import com.kwchina.work.commonWork.vo.XWorkVo;

public interface XWorkManager extends BasicManager{
	public XWork getWorkByWorkId (Integer workId,boolean valid);
	
	public XWorkVo transferToVo(XWork xWork);
	
	public List<XWorkVo> getWorkByDate(String queryDate);
}
