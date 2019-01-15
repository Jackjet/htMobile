package com.kwchina.work.security.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 安全文档修改记录
 * @author suguan
 *
 */
@Entity
@Table(name = "x_security_DocumentLog")//, schema = "dbo"
@ObjectId(id="logId")
public class DocumentLog implements JSONNotAware{

	private Integer logId;          //主键
    private String operateLog;		//操作记录
    private Timestamp operateTime;  //操作时间

    private Integer operatorId;		 //操作人ID
    private String operatorName;     //操作人姓名

    private DocumentInfor document;


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	

 	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
	public String getOperateLog() {
		return operateLog;
	}

	public void setOperateLog(String operateLog) {
		this.operateLog = operateLog;
	}

	
	
	public Timestamp getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="documentId")
	public DocumentInfor getDocument() {
		return document;
	}

	public void setDocument(DocumentInfor document) {
		this.document = document;
	}



}


