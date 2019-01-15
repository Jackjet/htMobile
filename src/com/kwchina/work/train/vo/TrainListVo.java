package com.kwchina.work.train.vo;

import lombok.Data;

@Data
public class TrainListVo {
    private String month;     //月份
    private int dcCount;        //堆场人数
    private int dlCount;        //东雷人数
    private int wlCount;        //整车物流人数
    private int netCount;        //驻外网点人数
    private int unitCount;        //驻外单位人数
    private int otherCount;       //其他人数
    private int allCount;       //总人数
}
