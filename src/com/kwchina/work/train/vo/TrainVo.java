package com.kwchina.work.train.vo;

import lombok.Data;

import java.util.List;

@Data
public class TrainVo {
    private Integer trainId;     //主键
    private String title;		//培训项目
    private int personCount;     //出勤人次
    private String trainerName;    //培训师
    private List<String> attaches;          //培训附件
    private String departmentName;  //培训部门
    private String recordTime; //记录时间
    private String trainTime; //培训时间
    private int departmentNum;  //部门编号
    private String deptName;
    private Integer categoryId;  //培训类别
    private String categoryName;
    private Integer recorderId;
    private String recorderName;
}
