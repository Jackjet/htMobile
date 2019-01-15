package com.kwchina.work.nightWatch.entity;


import java.sql.Date;
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
 * 巡更工作列表
 * @author suguan
 *
 */
@Entity
@Table(name = "x_nightWatch_list")//, schema = "dbo"
@ObjectId(id="xId")
public class NwList implements JSONNotAware{

	private Integer xId;
	private Integer listId;        //主键
    private String workTitle;      //标题
	private String areaIds;        //巡更区域
	private Integer executerId;	   //执行人
	private Integer createrId;     //创建人
	private Timestamp createTime;  //创建时间
	private Timestamp finishTime;  //完成时间
	private Timestamp sysTime;     //系统接收时间
	private int loopType;          /*
							                    循环类型
								    0-	一次性
								    1-	每天
								    2-	工作日（周一至周五）
								    3-	每周（周几）
	 						       */
	private Date beginDate;        //开始日期
	private Date endDate;          //结束日期
	private String beginTime;      //开始时间（小时：分钟）
	private String endTime;        //结束时间（小时：分钟）
	private String weekDays;       //loopType为3时，拼接的星期几，如周一、周三，则传1,3
	private Integer workState;     //状态
								   //0-	进行中
								   //1-	已完成



    private boolean valid;		//是否有效:0-无效;1-有效.

 	

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getxId() {
		return xId;
	}

	public void setxId(Integer xId) {
		this.xId = xId;
	}

	
	
	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getWorkTitle() {
		return workTitle;
	}

	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public Integer getExecuterId() {
		return executerId;
	}

	public void setExecuterId(Integer executerId) {
		this.executerId = executerId;
	}

	
	
	public Integer getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}

	
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	
	
	public int getLoopType() {
		return loopType;
	}

	public void setLoopType(int loopType) {
		this.loopType = loopType;
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


 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(String weekDays) {
		this.weekDays = weekDays;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}


 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Timestamp getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}

	public Timestamp getSysTime() {
		return sysTime;
	}

	public void setSysTime(Timestamp sysTime) {
		this.sysTime = sysTime;
	}





    
}


