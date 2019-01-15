package com.kwchina.work.patrol.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.base.entity.User;
import com.kwchina.core.base.service.UserManager;
import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.patrol.dao.WorkItemDAO;
import com.kwchina.work.patrol.entity.BigCategory;
import com.kwchina.work.patrol.entity.ItemDetail;
import com.kwchina.work.patrol.entity.SmallCategory;
import com.kwchina.work.patrol.entity.WorkItem;
import com.kwchina.work.patrol.entity.WorkList;
import com.kwchina.work.patrol.service.BigCategoryManager;
import com.kwchina.work.patrol.service.ItemDetailManager;
import com.kwchina.work.patrol.service.SmallCategoryManager;
import com.kwchina.work.patrol.service.WorkItemManager;
import com.kwchina.work.patrol.service.WorkListManager;
import com.kwchina.work.patrol.vo.ItemDetailVo;
import com.kwchina.work.patrol.vo.OpBigCategoryVo;
import com.kwchina.work.patrol.vo.OpPatrolVo;

@Service("workItemManager")
public class WorkItemManagerImpl extends BasicManagerImpl<WorkItem> implements WorkItemManager{

	private WorkItemDAO workItemDAO;
	
	@Autowired
	private WorkListManager workListManager;
	@Autowired
	private ItemDetailManager itemDetailManager;
	@Autowired
	private BigCategoryManager bigCategoryManager;
	@Autowired
	private SmallCategoryManager smallCategoryManager;
	@Autowired
	private UserManager userManager;

	@Autowired
	public void setSystemWorkItemDAO(WorkItemDAO workItemDAO) {
		this.workItemDAO = workItemDAO;
		super.setDao(workItemDAO);
	}
	
	public WorkItem getItemByItemId(Integer itemId){
		WorkItem item = new WorkItem();
		String sql = "from WorkItem item where item.itemId=" + itemId + "";
		List<WorkItem> list = this.workItemDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			item = list.get(0);
		}
		
		return item;
	}
	
	public List<WorkItem> getItemsByListId(Integer listId){
		List<WorkItem> list = new ArrayList<WorkItem>();
		String hql = "from WorkItem item where item.listId = '" + listId + "' and valid=1 order by item.beginTime";
		list = this.workItemDAO.getResultByQueryString(hql);
		return list;
	}
	
	@Override
	public OpPatrolVo getPatrolDetail(Integer itemId) {
		OpPatrolVo vo = new OpPatrolVo();
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//根据itemId得到信息
			WorkItem workItem = getItemByItemId(Integer.valueOf(itemId));
			
			//能进来说明已读过安全说明
//			workItem.setReadSafeRules(1);
//			save(workItem);
			
			vo.setItemId(workItem.getItemId());
			vo.setListId(workItem.getListId());
			if(workItem.getBeginTime() != null){
				vo.setBeginTime(sf.format(workItem.getBeginTime()));
			}
			if(workItem.getEndTime() != null){
				vo.setEndTime(sf.format(workItem.getEndTime()));
			}
			vo.setOperatorId(workItem.getOperatorId());
			if(workItem.getFinishTime() != null){
				vo.setFinishTime(sf.format(workItem.getFinishTime()));
			}
			vo.setWorkState(workItem.getWorkState());
			vo.setReadSafeRules(workItem.getReadSafeRules());//能进来说明已读过安全说明
			
			int itemSum = 0;
			int itemDoneSum = 0;
			
			//设置OpBigCategoryVo
			List<OpBigCategoryVo> bigVos = new ArrayList<OpBigCategoryVo>();
			WorkList workList = this.workListManager.getWorkListByListId(workItem.getListId());
			String bigIds = workList.getBigItems();
			String[] bigIdArray = bigIds.split(",");
			for(String tmpBigId : bigIdArray){
				Integer tmpBigIdInt = Integer.valueOf(tmpBigId);
				BigCategory bigCategory = this.bigCategoryManager.getBigCategoryByBigId(tmpBigIdInt);
				
				OpBigCategoryVo bigVo = new OpBigCategoryVo();
				bigVo.setxId(bigCategory.getxId());
				bigVo.setBigId(tmpBigIdInt);
				bigVo.setCategoryName(bigCategory.getCategoryName());
				bigVo.setCategoryCode(bigCategory.getCategoryCode());
				bigVo.setOrderNo(bigCategory.getOrderNo());

				//设置详情项
				//先到所有工作详细项
				List<ItemDetailVo> detailVos = new ArrayList<ItemDetailVo>();
				List<ItemDetail> allDetails = this.itemDetailManager.getDetailsByItemIdBigId(Integer.valueOf(itemId),tmpBigIdInt);
				for(ItemDetail tmpDetail : allDetails){
					ItemDetailVo detailVo = new ItemDetailVo();
					BeanUtils.copyProperties(detailVo, tmpDetail);
					SmallCategory smallCategory = this.smallCategoryManager.getSmallCategoryBySmallId(tmpDetail.getSmallId());
					detailVo.setSmallName(smallCategory.getCategoryName());
					detailVo.setBigName(bigCategory.getCategoryName());
					if(tmpDetail.getBeginTime() != null){
						detailVo.setBeginTimeStr(sf.format(tmpDetail.getBeginTime()));
					}
					if(tmpDetail.getEndTime() != null){
						detailVo.setEndTimeStr(sf.format(tmpDetail.getEndTime()));
					}
					
					if(tmpDetail.getOperatorId() > 0){
						User executer = this.userManager.getUserByUserId(tmpDetail.getOperatorId());
						if(executer != null && executer.getxId() > 0){
							detailVo.setOperatorName(executer.getName());
						}else{
							detailVo.setOperatorName("");
						}
					}else{
						detailVo.setOperatorName("");
					}
					
					detailVos.add(detailVo);
					
					itemSum ++;
					if(tmpDetail.getWorkState() == 1 && tmpDetail.getEndTime() != null){
						itemDoneSum ++;
					}
				}
				bigVo.setDetails(detailVos);
				bigVos.add(bigVo);
			}
			
			vo.setBigList(bigVos);
			
			//总条目数及完成数
			vo.setItemSum(itemSum);
			vo.setItemDoneSum(itemDoneSum);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
	}
	
}
