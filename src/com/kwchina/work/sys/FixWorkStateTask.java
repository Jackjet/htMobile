package com.kwchina.work.sys;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kwchina.work.nightWatch.entity.NwWork;
import com.kwchina.work.nightWatch.entity.NwWorkDetail;
import com.kwchina.work.nightWatch.service.NwWorkDetailManager;
import com.kwchina.work.nightWatch.service.NwWorkManager;
import com.kwchina.work.patrol.entity.ItemDetail;
import com.kwchina.work.patrol.entity.WorkItem;
import com.kwchina.work.patrol.service.ItemDetailManager;
import com.kwchina.work.patrol.service.WorkItemManager;

/**
 * 查询出实际已完成，但状态仍为“进行中”的巡检和巡更任务，将其状态改为已完成 
 * @author suguan
 *
 */
public class FixWorkStateTask extends TimerTask { 
		 
	private FixWorkStateTask(){}
	   
	private static FixWorkStateTask _checkTask = new FixWorkStateTask();
	public static FixWorkStateTask getCheckLicensidTask(){
		return _checkTask;
	}
	   
	private boolean _isRunning  = false;
	private ServletContext context = null;
	
	public void setContext(ServletContext context){
		this.context = context;
	}

	public void run(){
		
		try{
		//if (!this._isRunning){
			//this._isRunning = true;
		
		    //从容器中得到注入的bean 
			WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
//			DailyItemsCountInforManager dailyItemsCountInforManager = (DailyItemsCountInforManager)applicationContext.getBean("dailyItemsCountInforManager");
			WorkItemManager workItemManager = (WorkItemManager)applicationContext.getBean("workItemManager");
			NwWorkManager nwWorkManager = (NwWorkManager)applicationContext.getBean("nwWorkManager");
			ItemDetailManager itemDetailManager = (ItemDetailManager)applicationContext.getBean("itemDetailManager");
			NwWorkDetailManager nwWorkDetailManager = (NwWorkDetailManager)applicationContext.getBean("nwWorkDetailManager");
			
			Timestamp now = new Timestamp(System.currentTimeMillis());
			
			//巡检
			String hql_patrol = "from WorkItem where valid=1 and workState=0 ";
			List<WorkItem> patrolList = workItemManager.getResultByQueryString(hql_patrol);
			int patrolCount = 0;
			for(WorkItem tmpWork : patrolList){
				// 先检查一下是否是实际已做完，但状态仍是进行中，如果是，则修改状态
				List<ItemDetail> allDetails = itemDetailManager.getDetailsByItemId(tmpWork.getItemId());
				boolean hasAllDetailsDone = true;
				for(ItemDetail tmpDetail : allDetails){
					if(tmpDetail.getWorkState() == 0){
						hasAllDetailsDone = false;
						break;
					}
				}
				
				if(hasAllDetailsDone){
					patrolCount ++;
					tmpWork.setWorkState(1);
					tmpWork.setFinishTime(now);
					tmpWork.setSysTime(now);
					workItemManager.save(tmpWork);
				}
			}
			
			//巡更
			String hql_nw = "from NwWork where valid=1 and workState=0 ";
			List<NwWork> nwList = nwWorkManager.getResultByQueryString(hql_nw);
			int nwCount = 0;
			for(NwWork tmpWork : nwList){
				// 先检查一下是否是实际已做完，但状态仍是进行中，如果是，则修改状态
				List<NwWorkDetail> allDetails = nwWorkDetailManager.getDetailsByWorkId(tmpWork.getWorkId());
				boolean hasAllDetailsDone = true;
				for(NwWorkDetail tmpDetail : allDetails){
					if(tmpDetail.getWorkState() == 0){
						hasAllDetailsDone = false;
						break;
					}
				}
				
				if(hasAllDetailsDone){
					nwCount ++;
					tmpWork.setWorkState(1);
					tmpWork.setFinishTime(now);
					tmpWork.setSysTime(now);
					nwWorkManager.save(tmpWork);
				}
			}
			
			System.out.println("===============修复状态："+patrolCount+"条巡检，"+nwCount+"条巡更！================");
			
			this._isRunning = false;
		//}else{
		//	context.log("上一次任务执行还未结束 ");
		//	this._isRunning = false;
		//}
		}catch(Exception e){
			//this._isRunning = false;
			e.printStackTrace();
		}

	}
	
	   
	   
	public void destroy(){
		//Just puts "destroy " string in log
		//Put your code here
	}

}