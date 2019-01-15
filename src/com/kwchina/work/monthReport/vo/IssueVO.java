package com.kwchina.work.monthReport.vo;

import lombok.Data;

@Data
public class IssueVO {
    private Integer issueId;
    private Integer categoryId;
    private String categoryName;
    private String month;
    private String content;
    private String year;
}
