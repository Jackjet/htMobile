package com.kwchina.work.patrol.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.patrol.dao.ItemDetailDAO;
import com.kwchina.work.patrol.entity.ItemDetail;
import com.kwchina.work.patrol.service.ItemDetailManager;

@Service("itemDetailManager")
public class ItemDetailManagerImpl extends BasicManagerImpl<ItemDetail> implements ItemDetailManager{

	private ItemDetailDAO itemDetailDAO;

	@Autowired
	public void setSystemItemDetailDAO(ItemDetailDAO itemDetailDAO) {
		this.itemDetailDAO = itemDetailDAO;
		super.setDao(itemDetailDAO);
	}
	
	public List<ItemDetail> getDetailsByItemId(Integer itemId){
		List<ItemDetail> list = new ArrayList<ItemDetail>();
		String hql = "from ItemDetail detail where detail.itemId = '" + itemId + "' and valid=1 order by detail.bigId,detail.smallId";
		list = this.itemDetailDAO.getResultByQueryString(hql);
		return list;
	}
	
	public List<ItemDetail> getDetailsByItemIdBigId(Integer itemId,Integer bigId){
		List<ItemDetail> list = new ArrayList<ItemDetail>();
		String hql = "from ItemDetail detail where detail.itemId = '" + itemId + "' and detail.bigId = '"+bigId+"' and valid=1 order by detail.bigId,detail.smallId";
		list = this.itemDetailDAO.getResultByQueryString(hql);
		return list;
	}

	public List<ItemDetail> getDetailsByListId(Integer listId){
		List<ItemDetail> list = new ArrayList<ItemDetail>();
		String hql = "from ItemDetail detail where detail.listId = '" + listId + "' and valid=1 order by detail.bigId,detail.smallId";
		list = this.itemDetailDAO.getResultByQueryString(hql);
		return list;
	}
	
	public ItemDetail getDetailByDetailId(Integer detailId){
		ItemDetail detail = new ItemDetail();
		String sql = "from ItemDetail detail where detail.detailId='" + detailId + "'";
		List<ItemDetail> list = this.itemDetailDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			detail = list.get(0);
		}
		
		return detail;
	}
}
