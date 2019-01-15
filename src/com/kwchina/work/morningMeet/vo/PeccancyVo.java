package com.kwchina.work.morningMeet.vo;


import lombok.Data;

import java.util.List;

/**
 * 巡检大项
 * @author suguan
 *
 */
@Data
public class PeccancyVo {
	private Integer peccancyId;
    private String content;
	private List<String> errorAttachs;
}



