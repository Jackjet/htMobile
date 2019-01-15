package com.kwchina.work.nightWatch.service.impl;

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
import com.kwchina.work.nightWatch.dao.NwWorkDAO;
import com.kwchina.work.nightWatch.entity.NwArea;
import com.kwchina.work.nightWatch.entity.NwDetailLog;
import com.kwchina.work.nightWatch.entity.NwItem;
import com.kwchina.work.nightWatch.entity.NwList;
import com.kwchina.work.nightWatch.entity.NwWork;
import com.kwchina.work.nightWatch.entity.NwWorkDetail;
import com.kwchina.work.nightWatch.service.NwAreaManager;
import com.kwchina.work.nightWatch.service.NwDetailLogManager;
import com.kwchina.work.nightWatch.service.NwItemManager;
import com.kwchina.work.nightWatch.service.NwListManager;
import com.kwchina.work.nightWatch.service.NwWorkDetailManager;
import com.kwchina.work.nightWatch.service.NwWorkManager;
import com.kwchina.work.nightWatch.vo.NwDetailLogVo;
import com.kwchina.work.nightWatch.vo.NwWorkDetailVo;
import com.kwchina.work.nightWatch.vo.OpNightWatchVo;
import com.kwchina.work.nightWatch.vo.OpNwAreaVo;
import com.kwchina.work.patrol.entity.BigCategory;
import com.kwchina.work.patrol.entity.WorkList;
import com.kwchina.work.patrol.vo.OpBigCategoryVo;

@Service("nwWorkManager")
public class NwWorkManagerImpl extends BasicManagerImpl<NwWork> implements NwWorkManager{

	private NwWorkDAO nwWorkDAO;
	
	@Autowired
	private NwWorkDetailManager nwWorkDetailManager;
	@Autowired
	private NwItemManager nwItemManager;
	@Autowired
	private NwDetailLogManager nwDetailLogManager;
//	@Autowired
//	private NwWorkManager nwWorkManager;
	@Autowired
	private NwListManager nwListManager;
	@Autowired
	private NwAreaManager nwAreaManager;
	@Autowired
	private UserManager userManager;

	@Autowired
	public void setSystemNwWorkDAO(NwWorkDAO nwWorkDAO) {
		this.nwWorkDAO = nwWorkDAO;
		super.setDao(nwWorkDAO);
	}
	
	public NwWork getWorkByWorkId(Integer workId){
		NwWork work = new NwWork();
		String sql = "from NwWork work where work.workId=" + workId + "";
		List<NwWork> list = this.nwWorkDAO.getResultByQueryString(sql);
		
		if(list != null && list.size() > 0){
			work = list.get(0);
		}
		
		return work;
	}
	
	public List<NwWork> getWorksByListId(Integer listId){
		List<NwWork> list = new ArrayList<NwWork>();
		String hql = "from NwWork work where work.listId = '" + listId + "' and valid=1 order by work.beginTime";
		list = this.nwWorkDAO.getResultByQueryString(hql);
		return list;
	}
	

	public OpNightWatchVo getNWDetail(Integer workId){
		OpNightWatchVo vo = new OpNightWatchVo();
		
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//根据itemId得到信息
			NwWork workItem = getWorkByWorkId(Integer.valueOf(workId));
			
//			vo.setAreaId(workItem.getAreaId());
			vo.setWorkId(workItem.getWorkId());
			vo.setListId(workItem.getListId());
			if(workItem.getBeginTime() != null){
				vo.setBeginTime(sf.format(workItem.getBeginTime()));
			}
			if(workItem.getEndTime() != null){
				vo.setEndTime(sf.format(workItem.getEndTime()));
			}
			vo.setOperatorId(workItem.getExecuterId());
			if(workItem.getFinishTime() != null){
				vo.setFinishTime(sf.format(workItem.getFinishTime()));
			}
			vo.setWorkState(workItem.getWorkState());
			vo.setReadSafeRules(workItem.getReadSafeRules());//能进来说明已读过安全说明
			
			
			int itemSum = 0;
			int itemDoneSum = 0;
			
			
			//设置OpBigCategoryVo
			List<OpNwAreaVo> areaVos = new ArrayList<OpNwAreaVo>();
			NwList workList = this.nwListManager.getListByListId(workItem.getListId());
			String bigIds = workList.getAreaIds();
			String[] bigIdArray = bigIds.split(",");
			for(String tmpBigId : bigIdArray){
				Integer tmpBigIdInt = Integer.valueOf(tmpBigId);
				NwArea area = this.nwAreaManager.getNwAreaByAreaId(tmpBigIdInt);
				
				OpNwAreaVo areaVo = new OpNwAreaVo();
				areaVo.setxId(area.getxId());
				areaVo.setAreaId(area.getAreaId());
				areaVo.setAreaName(area.getAreaName());
				areaVo.setAreaCode(area.getAreaCode());
				areaVo.setOrderNo(area.getOrderNo());
				
				//设置详情项
				//先到所有工作详细项
				List<NwWorkDetailVo> detailVos = new ArrayList<NwWorkDetailVo>();
				List<NwWorkDetail> allDetails = this.nwWorkDetailManager.getDetailsByWorkIdAreaId(Integer.valueOf(workId),tmpBigIdInt);
				for(NwWorkDetail tmpDetail : allDetails){
					NwWorkDetailVo detailVo = new NwWorkDetailVo();
					BeanUtils.copyProperties(detailVo, tmpDetail);
					NwItem smallCategory = this.nwItemManager.getNwItemByItemId(tmpDetail.getItemId());
					detailVo.setItemName(smallCategory.getItemName());
					
					if(tmpDetail.getExecuterId() > 0){
						User executer = this.userManager.getUserByUserId(tmpDetail.getExecuterId());
						if(executer != null && executer.getxId() > 0){
							detailVo.setExecuterName(executer.getName());
						}else{
							detailVo.setExecuterName("");
						}
					}else{
						detailVo.setExecuterName("");
					}
					
//					NwArea area2 = this.nwAreaManager.getNwAreaByAreaId(tmpDetail.getAreaId());
					detailVo.setAreaName(area.getAreaName());
					
					if(tmpDetail.getBeginTime() != null){
						detailVo.setBeginTimeStr(sf.format(tmpDetail.getBeginTime()));
					}
					if(tmpDetail.getFinishTime() != null){
						detailVo.setFinishTimeStr(sf.format(tmpDetail.getFinishTime()));
					}
					
					
					itemSum ++;
					if(tmpDetail.getWorkState() == 1 && tmpDetail.getFinishTime() != null){
						itemDoneSum ++;
					}
					
					//设置异常信息detailLogVo
					List<NwDetailLogVo> logVos = new ArrayList<NwDetailLogVo>();
					List<NwDetailLog> allLogs = this.nwDetailLogManager.getLogsByDetailId(tmpDetail.getDetailId());
					for(NwDetailLog tmpLog : allLogs){
						NwDetailLogVo logVo = new NwDetailLogVo();
						BeanUtils.copyProperties(logVo, tmpLog);
						if(tmpLog.getLogTime() != null){
							logVo.setLogTimeStr(sf.format(tmpLog.getLogTime()));
						}
						if(tmpLog.getSolveTime() != null){
							logVo.setSolveTimeStr(sf.format(tmpLog.getSolveTime()));
						}
						if(tmpLog.getSubmitTime() != null){
							logVo.setSubmitTimeStr(sf.format(tmpLog.getSubmitTime()));
						}
						
						logVos.add(logVo);
					}
					
					detailVo.setLogs(logVos);
					detailVo.setAttachs(tmpDetail.getAttachs());
					

					detailVos.add(detailVo);
				}
				
				areaVo.setDetailList(detailVos);
				areaVos.add(areaVo);
			}
			
			
			
			vo.setAreaList(areaVos);
			
//		//设置OpBigCategoryVo
//		List<OpBigCategoryVo> bigVos = new ArrayList<OpBigCategoryVo>();
//		WorkList workList = this.nwListManager.getWorkListByListId(workItem.getListId());
//		String bigIds = workList.getBigItems();
//		String[] bigIdArray = bigIds.split(",");
//		for(String tmpBigId : bigIdArray){
//			Integer tmpBigIdInt = Integer.valueOf(tmpBigId);
//			BigCategory bigCategory = this.nwAreaManager.getBigCategoryByBigId(tmpBigIdInt);
//			
//			OpBigCategoryVo bigVo = new OpBigCategoryVo();
//			bigVo.setxId(bigCategory.getxId());
//			bigVo.setBigId(tmpBigIdInt);
//			bigVo.setCategoryName(bigCategory.getCategoryName());
//			bigVo.setCategoryCode(bigCategory.getCategoryCode());
//			bigVo.setOrderNo(bigCategory.getOrderNo());
//
//			
//		}
			
			
			//总条目数及完成数
			vo.setItemSum(itemSum);
			vo.setItemDoneSum(itemDoneSum);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return vo;
	}
}
