package com.kwchina.work.patrol.entity;


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
 * 巡检工作各人的详细工作小项
 * @author suguan
 *
 */
@Entity
@Table(name = "x_patrol_itemDetail")
@ObjectId(id="xId")
public class ItemDetail implements JSONNotAware{

	private Integer xId;
	private Integer detailId;        //主键
	private Integer listId;        //所属工作
	private Integer itemId;        //所属的具体工作
	private Integer bigId;         //所属大项
	private Integer smallId;       //所属小项
	private int opResult;          //操作结果  0-初始  1-是 -1-否
	private Integer operatorId;	   //执行人
	private Timestamp beginTime;   //开始时间
	private Timestamp endTime;     //结束时间（执行时间）
	private Timestamp sysTime;     //系统接收时间
	private Integer workState;     //状态
								   //0-	进行中
								   //1-	已完成
	private String errorLog;       //异常信息
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
	
	public Integer getDetailId() {
		return detailId;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	
	
	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	
	
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	
	
	public Integer getBigId() {
		return bigId;
	}

	public void setBigId(Integer bigId) {
		this.bigId = bigId;
	}

	
	
	public Integer getSmallId() {
		return smallId;
	}

	public void setSmallId(Integer smallId) {
		this.smallId = smallId;
	}

	
	
	public int getOpResult() {
		return opResult;
	}

	public void setOpResult(int opResult) {
		this.opResult = opResult;
	}

	
	
	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
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

	
	
	public Integer getWorkState() {
		return workState;
	}

	public void setWorkState(Integer workState) {
		this.workState = workState;
	}

	

 	@Column(columnDefinition = "nvarchar(4000)",nullable = true)
	public String getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}

	

 	@Column(columnDefinition = "nvarchar(4000)",nullable = true)
	public String getAttachs() {
		return attachs;
	}

	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}

	
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Timestamp getSysTime() {
		return sysTime;
	}

	public void setSysTime(Timestamp sysTime) {
		this.sysTime = sysTime;
	}

	
	
	
	
	



    
}


