package com.kwchina.work.morningMeet.vo;

import lombok.Data;

import java.util.List;

@Data
public class WorkItemVo {
    private Integer workId;
    private String workName;
    private List<PatrolVo> patrolVos;
}
