package com.kwchina.work.monthReport.vo;

import lombok.Data;

@Data
public class SafeCheckVO implements Comparable<SafeCheckVO> {
    private String projectName;
    private int lastCount;
    private int nowCount;
    private int ringCount;
    private Integer orderNo;


    @Override
    public int compareTo(SafeCheckVO o) {
        return this.getOrderNo().compareTo(o.getOrderNo());
    }
}
