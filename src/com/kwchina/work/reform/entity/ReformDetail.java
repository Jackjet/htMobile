package com.kwchina.work.reform.entity;

import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "x_reform_Detail")
@Data
public class ReformDetail implements JSONNotAware {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reformId;			//主键
	private String taskId;         //任务Id
	private String errorTitle;        //隐患标题
	private Integer errorTypeId;        //隐患类型
	private Integer errorAreaId;         //隐患区域
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="departmentId")
	private Department department;       //责任部门
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dutyPersonId")
	private User dutyPerson;			//责任人	
	private Date reformTime;          //整改时间
	private String errorContent;	   //隐患描述
	private String errorAttach;   //隐患附件
	private Date findTime;     //发现时间
	private Integer reformStatus;    //整改状态 0 待整改  1整改中  2 待复查 3 整改通过
	private Date finishTime;		//完成时间
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="findUserId")
	private User findUser;     //发现人
	@OneToMany(mappedBy = "reformDetail",fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	private List<Schedule> scheduleList=new ArrayList<Schedule>();
	private boolean valid;		//是否有效:0-无效;1-有效.
	private Integer superviseDeptId;	//监管部门Id
	private boolean morningReport;
}


