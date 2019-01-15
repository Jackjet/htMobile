package com.kwchina.work.errorWork.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorWorkVO {
	private Integer errorWorkId;    //主键
	private String errorTitle;     //工作标题
	private String errorInfo;		//异常信息
	private List<String> errorAttachs;		//异常附件
	private List<String> memoAttachs;		//备注附件
	private Integer executerId;   //执行人ID
	private String executerName; //执行人姓名
	private String checkTime;   //发现时间
	private String result;     //
	private String errorType;  //动检类型
	private Integer areaId;
	private String areaName;		//区域名称
	private String memo;	//备注
	private String taskId;
	private String dutyDept1Name;//责任部门1
	private String dutyDept2Name;//责任部门2
}
