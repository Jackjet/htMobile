package com.kwchina.work.reform.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ReformVo {
	private Integer reformId;    //主键
	private String taskId;			//任务Id
	private String errorTitle;     //工作标题
	private String errorInfo;		//异常信息
	private List<String> errorAttachs;		//异常附件
	private Integer findUserId;   //发现人ID
	private String findUserName; //发现人姓名
	private Integer reformUserId;
	private String reformUserName;
	private Integer reformStatus;         //状态 1-待整改，2-整改中，3-待复查，4-已完成,
	private String statusStr;
	private String findTime;   //发现时间
	private String reformTime;     //整改期限
	private String finishTime;  //完成时间
	private String areaName;		//区域名称
	private Integer areaId;
	private String errorType;	//隐患类型
	private Integer errorTypeId; //类型Id
	private String dutyDeptName;//责任部门
	private String superviseDeptName; //监管部门
	private List<ScheduleVo> scheduleVos=new ArrayList<>();
	private boolean morningReport;
}
