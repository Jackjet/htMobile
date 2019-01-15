package com.kwchina.work.peccancy.vo;

import lombok.Data;

import java.util.List;

@Data
public class PeccancyVo {
	private Integer peccancyId;//违章Id
	private String content;        //违章描述
	private List<String> ruleAttaches;		//异常附件
	private List<String> reformAttaches;
	private Integer offenderId;   //发现人ID
	private String offenderName; //发现人姓名
	private String findTime;   //记录时间
	private String areName;		//区域名称
	private String peccancyTypeName;	//违章类型
	private Integer peccancyTypeId;
	private String dutyDeptName;//责任部门
	private String dutyPersonName;	//责任人
	private String peccancyRules;
	private String remark;		//备注
	private boolean morningReport;
}
