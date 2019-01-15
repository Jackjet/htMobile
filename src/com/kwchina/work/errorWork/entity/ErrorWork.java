package com.kwchina.work.errorWork.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kwchina.work.reform.entity.ReformArea;
import lombok.Data;

import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 巡检上报
 * @author lijj
 *
 */
@Entity
@Table(name = "x_error_work")//, schema = "dbo"
@Data
public class ErrorWork implements JSONNotAware{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer errorWorkId;			//主键
    private String title;      //标题
    private Integer errorType;       //巡检类型
    private String result;  
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="executerId")
	private User executer;	   		//上报人  
	private String errorInfo;  //上报事件信息
	private Date checkTime;  //上报时间
	private String errorAttachs;  //上报事件附件
	private String memo;  //描述
	private String memoAttachs;  //描述附件
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="department1Id")
	private Department department1;       //责任部门1
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="department2Id")
	private Department department2;       //责任部门2
	private String taskId;  //任务id
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="areaId")
	private ReformArea area; //区域
	private boolean valid;
}


