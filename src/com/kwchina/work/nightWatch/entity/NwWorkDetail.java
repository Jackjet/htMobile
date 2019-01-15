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
 * 巡更详情
 * @author suguan
 *
 */
@Entity
@Table(name = "x_nightWatch_workDetail")//, schema = "dbo"
@ObjectId(id="xId")
public class NwWorkDetail implements JSONNotAware{

	private Integer xId;
	private Integer detailId;        //主键
	private Integer listId;        //所属工作
	private Integer workId;        //所属任务
	private Integer areaId;        //区域
	private Integer itemId;        //巡更小项 

	private int opResult;          //操作结果  0-初始  1-是 -1-否
	private Timestamp beginTime;   //开始时间
	private Timestamp finishTime;  //完成时间
	private Timestamp sysTime;     //系统接收时间
	private Integer executerId;	   //执行人
	private Integer workState;     //状态
								   //0-	进行中
								   //1-	已完成
	private String attachs;        //图片路径

    private boolean valid;		//是否有效:0-无效;1-有效.

 	

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getxId() {
		return xId;
	}

	public void setxId(Integer xId) {
		this.xId = xId;
	}

	
	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	
	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	

	
	public Integer getExecuterId() {
		return executerId;
	}

	public void setExecuterId(Integer executerId) {
		this.executerId = executerId;
	}

	

	
	public Integer getWorkState() {
		return workState;
	}

	public void setWorkState(Integer workState) {
		this.workState = workState;
	}

	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Timestamp getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public int getOpResult() {
		return opResult;
	}

	public void setOpResult(int opResult) {
		this.opResult = opResult;
	}

	public Timestamp getSysTime() {
		return sysTime;
	}

	public void setSysTime(Timestamp sysTime) {
		this.sysTime = sysTime;
	}



 	@Column(columnDefinition = "nvarchar(4000)",nullable = true)
	public String getAttachs() {
		return attachs;
	}

	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}



    
}


