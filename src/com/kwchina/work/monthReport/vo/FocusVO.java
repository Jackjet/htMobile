package com.kwchina.work.monthReport.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FocusVO {
    private Integer focusId;
    private String time;
    private Map<String,String> records=new HashMap<>();
    private List<String> attaches=new ArrayList<>();
}
