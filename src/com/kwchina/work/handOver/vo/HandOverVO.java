package com.kwchina.work.handOver.vo;

import lombok.Data;

import java.util.List;

@Data
public class HandOverVO {
    private Integer handId;
    private String title;
    private String content;
    private String handTime;
    private Integer handerId;
    private String handerName;
    private List<String> attaches;
}
