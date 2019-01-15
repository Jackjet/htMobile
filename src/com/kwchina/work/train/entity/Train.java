package com.kwchina.work.train.entity;


import com.kwchina.core.base.entity.User;
import com.kwchina.core.util.json.JSONNotAware;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 培训
 * @author lijj
 *
 */
@Entity
@Table(name = "x_train")//, schema = "dbo"
@Data
public class Train implements JSONNotAware{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainId;     //主键
    private String title;		//培训项目
    private int personCount;     //出勤人次
    private String trainerName;    //培训师
    private String attach;          //培训附件
    private String departmentName;  //培训部门
    private Date recordTime; //记录时间
    private Date trainTime; //培训时间
    private int departmentNum;  //公司编号
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="categoryId")
    private TrainCategory category;  //培训类别
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="recorderId")
    private User recorder;
    private boolean valid;
}


