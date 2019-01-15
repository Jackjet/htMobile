package com.kwchina.work.monthReport.vo;

import lombok.Data;

@Data
public class CountVO {
    private String categoryName;
    private int nowCount;
    private int lastCount;
    private int ringCount;
    private String issueDetail;
}
