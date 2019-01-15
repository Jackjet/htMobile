package com.kwchina.work.commonWork.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 工作任务修改记录
 * @author suguan
 *
 */
@Entity
@Table(name = "x_work_trace")
@ObjectId(id="traceId")
public class XWorkTrace implements JSONNotAware{

	private Integer traceId;          //主键
	private Integer workId;         //任务
    private Integer operatorId;		//操作人
    private String operatorName;    //操作人姓名
    private Timestamp operateTime;  //操作时间


    private Integer toUserId;		//新执行人
    private String toUserName;      //新执行人姓名
    private String content;         //操作内容
    private String memo;         //操作结果备注
    private String attachs;         //图片附件
    private String opTimeStr;
    

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getTraceId() {
		return traceId;
	}
	public void setTraceId(Integer traceId) {
		this.traceId = traceId;
	}
	
	
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	
	
	public Timestamp getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}
	
	
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	

 	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

 	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	

 	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	

	@Transient
	public String getOpTimeStr() {
		return opTimeStr;
	}
	public void setOpTimeStr(String opTimeStr) {
		this.opTimeStr = opTimeStr;
	}
	

 	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	

 	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
	public String getAttachs() {
		return attachs;
	}
	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}






}


