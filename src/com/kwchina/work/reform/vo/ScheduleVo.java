package com.kwchina.work.reform.vo;

import lombok.Data;

import java.util.List;

/**
 * 进度
 * @author lijunjie
 *
 */
@Data
public class ScheduleVo{

	private Integer scheduleId;
    private List<String> reformAttachs;
    private String reformInfo;
    private String reformTime;
	private Integer reformStatus;	        //排序号
	private Integer executerId;				//执行人Id
	private String executerName;    //执行人姓名

}


