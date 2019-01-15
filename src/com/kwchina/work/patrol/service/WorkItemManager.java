package com.kwchina.work.patrol.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.patrol.entity.BigCategory;
import com.kwchina.work.patrol.entity.ItemDetail;
import com.kwchina.work.patrol.entity.WorkItem;
import com.kwchina.work.patrol.vo.OpPatrolVo;

public interface WorkItemManager extends BasicManager{
	public WorkItem getItemByItemId(Integer itemId);
	
	public List<WorkItem> getItemsByListId(Integer listId);
	
	/**
	 * 进入巡检，查看巡检详情
	 * @param itemId
	 * @return
	 */
	public OpPatrolVo getPatrolDetail(Integer itemId);
}
