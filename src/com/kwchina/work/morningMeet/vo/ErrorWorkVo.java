package com.kwchina.work.morningMeet.vo;


import lombok.Data;

import java.util.List;

/**
 * 巡检大项
 * @author suguan
 *
 */
@Data
public class ErrorWorkVo {
	private Integer errorWorkId;
    private String content;
	private List<String> errorAttachs;
	private String errorType;
	private String areaName;
	private String title;
	private String departmentName;
	}



