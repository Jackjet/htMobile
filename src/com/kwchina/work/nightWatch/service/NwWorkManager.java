package com.kwchina.work.nightWatch.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.nightWatch.entity.NwWork;
import com.kwchina.work.nightWatch.vo.OpNightWatchVo;

public interface NwWorkManager extends BasicManager{
	
	public NwWork getWorkByWorkId(Integer workId);
	
	public List<NwWork> getWorksByListId(Integer listId);
	
	/**
	 * 进入巡更（巡更详情）
	 * @param workId
	 * @return
	 */
	public OpNightWatchVo getNWDetail(Integer workId);
}
