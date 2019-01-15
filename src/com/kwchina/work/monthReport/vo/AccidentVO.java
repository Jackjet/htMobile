package com.kwchina.work.monthReport.vo;

import lombok.Data;

import java.util.List;

@Data
public class AccidentVO {
    private Integer accidentId;
    private String time;
    private String address;
    private String content;
    private List<String> attaches;
    private String departmentName;
    private Integer departmentId;
}
