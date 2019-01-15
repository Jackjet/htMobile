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
 * 工作任务回复记录
 * @author suguan
 *
 */
@Entity
@Table(name = "x_work_reply")
@ObjectId(id="replyId")
public class XWorkReply implements JSONNotAware{

	private Integer replyId;          //主键
	private Integer workId;         //任务
    private Integer operatorId;		//操作人
    private String operatorName;    //操作人姓名
    private String operateTime;  //操作时间
    private String content;         //操作内容
    private String attachs;         //附件


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getReplyId() {
		return replyId;
	}
	
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	

	public Integer getOperatorId() {
		return operatorId;
	}
	
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	

 	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
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
	

 	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
 	public String getAttachs() {
		return attachs;
	}

	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}
	
 	
	public Integer getWorkId() {
		return workId;
	}
	

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}






}


