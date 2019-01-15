package com.kwchina.work.patrol.service;

import java.util.List;

import com.kwchina.core.common.service.BasicManager;
import com.kwchina.work.patrol.entity.BigCategory;
import com.kwchina.work.patrol.entity.ItemDetail;

public interface ItemDetailManager extends BasicManager{
	public List<ItemDetail> getDetailsByItemId(Integer itemId);
	
	public List<ItemDetail> getDetailsByItemIdBigId(Integer itemId,Integer bigId);

	public List<ItemDetail> getDetailsByListId(Integer listId);
	
	public ItemDetail getDetailByDetailId(Integer detailId);
}
