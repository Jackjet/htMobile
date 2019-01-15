package com.kwchina.work.peccancy.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.util.json.JSONNotAware;
import com.kwchina.work.reform.entity.ChildCategory;
import lombok.Data;

@Entity
@Table(name = "x_peccancy_detail")//, schema = "dbo"
@Data
public class PeccancyDetail implements JSONNotAware{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer peccancyId;//违章Id
	private String content;        //违章描述
	private String attachment;        //附件
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="departmentId")
	private Department department;        //责任部门
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="categoryId")
	private ChildCategory category;    //违章类型
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="offenderId")
	private User offender;		//记录人
	private String dutyPersonName;	//责任人
	private Date markTime;			//记录日期
	private String reformAttach;
	private String peccancyRules;
	private String remark;		//备注
	private boolean valid;
	private boolean morningReport;

	
	
}
