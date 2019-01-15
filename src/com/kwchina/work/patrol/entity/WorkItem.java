package com.kwchina.work.patrol.entity;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 巡检工作各人的工作项
 * @author suguan
 *
 */
@Entity
@Table(name = "x_patrol_workItem")
@ObjectId(id="xId")
public class WorkItem implements JSONNotAware{

	private Integer xId;
	private Integer itemId;        //主键
	private Integer listId;        //所属工作
	private Timestamp beginTime;   //开始时间
	private Timestamp endTime;     //结束时间
	private Integer operatorId;	   //执行人
	private Timestamp finishTime;  //完成时间
	private Timestamp sysTime;     //系统接收时间
	private Integer workState;     //状态
								   //0-	进行中
								   //1-	已完成

	private int readSafeRules;     //是否已熟知安全方案  0-否,1-是

    private boolean valid;		//是否有效:0-无效;1-有效.

 	

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getxId() {
		return xId;
	}

	public void setxId(Integer xId) {
		this.xId = xId;
	}

	
	
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	
	
	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	
	
	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	
	
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	
	
	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	
	
	public Timestamp getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
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

	public int getReadSafeRules() {
		return readSafeRules;
	}

	public void setReadSafeRules(int readSafeRules) {
		this.readSafeRules = readSafeRules;
	}

	public Timestamp getSysTime() {
		return sysTime;
	}

	public void setSysTime(Timestamp sysTime) {
		this.sysTime = sysTime;
	}

	
	



    
}


