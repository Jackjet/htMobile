package com.kwchina.work.morningMeet.vo;


import lombok.Data;

import java.util.List;

/**
 * 巡检大项
 *
 * @author suguan
 */
@Data
public class UnionVo {
    private Integer unionId;
    private Integer reporterId;
    private String reporterName;
    private String reportTime;
    private String category;
    private List<String> unionAttachs;
}



