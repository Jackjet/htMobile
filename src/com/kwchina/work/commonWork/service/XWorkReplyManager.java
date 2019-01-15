package com.kwchina.work.commonWork.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.commonWork.entity.XWorkReply;

public interface XWorkReplyManager extends BasicManager{
	public List<XWorkReply> getReplysByWorkId(Integer workId);
}
