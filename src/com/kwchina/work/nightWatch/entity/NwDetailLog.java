package com.kwchina.work.nightWatch.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 巡更详情异常项
 * @author suguan
 *
 */
@Entity
@Table(name = "x_nightWatch_detailLog")//, schema = "dbo"
@ObjectId(id="xId")
public class NwDetailLog implements JSONNotAware{

	private Integer xId;
	private Integer logId;          //主键
	private Integer detailId;        //所属小项
	private Integer workId;        //所属任务
	private Integer listId;        //所属工作
	private Timestamp logTime;   //操作时间
	private Timestamp sysTime;     //系统接收时间
	private String logContent;   //异常内容
	private String logAttachs;   //异常图片
	private Timestamp submitTime;  //上报时间
	private Integer submitState;     //上报状态
								   //0-	未上报
								   //1-	已上报
	
	private int solveState;       //解决状态  0-否  1-是
    private Timestamp solveTime;		//解决时间
    private String solveWord;      //整改后的文字说明
    private String solveAttachs;   //整改后的图片

 	

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getxId() {
		return xId;
	}

	public void setxId(Integer xId) {
		this.xId = xId;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public Timestamp getLogTime() {
		return logTime;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}

	

 	@Column(columnDefinition = "nvarchar(4000)",nullable = true)
	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}


 	@Column(columnDefinition = "nvarchar(4000)",nullable = true)
	public String getLogAttachs() {
		return logAttachs;
	}

	public void setLogAttachs(String logAttachs) {
		this.logAttachs = logAttachs;
	}

	public Timestamp getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	public Integer getSubmitState() {
		return submitState;
	}

	public void setSubmitState(Integer submitState) {
		this.submitState = submitState;
	}

	public int getSolveState() {
		return solveState;
	}

	public void setSolveState(int solveState) {
		this.solveState = solveState;
	}

	public Timestamp getSolveTime() {
		return solveTime;
	}

	public void setSolveTime(Timestamp solveTime) {
		this.solveTime = solveTime;
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public Timestamp getSysTime() {
		return sysTime;
	}

	public void setSysTime(Timestamp sysTime) {
		this.sysTime = sysTime;
	}

	public String getSolveWord() {
		return solveWord;
	}

	public void setSolveWord(String solveWord) {
		this.solveWord = solveWord;
	}

	public String getSolveAttachs() {
		return solveAttachs;
	}

	public void setSolveAttachs(String solveAttachs) {
		this.solveAttachs = solveAttachs;
	}

}


