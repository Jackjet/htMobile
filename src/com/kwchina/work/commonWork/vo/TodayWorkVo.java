package com.kwchina.work.commonWork.vo;

import com.kwchina.work.nightWatch.vo.OpNightWatchVo;
import com.kwchina.work.patrol.vo.OpPatrolVo;
import com.kwchina.work.reform.vo.ReformVo;
import lombok.Data;

/**
 * 今日工作通用
 * @author suguan
 *
 */
@Data
public class TodayWorkVo {
//	private int xId;
	private String workName;     //工作标题
	private String workTime;     //工作开始时间
	private Integer executerId;   //执行人ID
	private String executerName; //执行人姓名
	
	private Integer createrId;   //创建人ID
	private String createrName;  //创建人姓名
	

	private int readSafeRules;     //是否已熟知安全方案  0-否,1-是
	private int workState;         //状态 0-未完成，1-已完成
	private Integer commonWorkId;   //日常任务主键
	private Integer patrolItemId;   //巡检任务主键
	private Integer nightWatchWorkId;//巡更任务主键
	private Integer reformId;		//整改任务主键
	
	//离线时，取巡检、巡更的详情放入
	private OpNightWatchVo nwVo;
	private OpPatrolVo patrolVo;
	private ReformVo reformVo;

}
