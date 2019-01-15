package com.kwchina.work.morningMeet.vo;


import lombok.Data;

import java.util.List;

/**
 * 巡检大项
 * @author suguan
 *
 */
@Data
public class HandleVo {
	private Integer handleId;
	private String title;
    private String content;
    private List<String> attaches;
}



