package com.kwchina.work.patrol.vo;

import java.util.ArrayList;
import java.util.List;

public class PcPatrolVo {
	private Integer itemId;        //主键
	private Integer listId;        //所属工作
	private String workName;     //工作标题
	private Integer executerId;   //执行人ID
	private String executerName; //执行人姓名
	private Integer createrId;   //创建人ID
	private String createrName;  //创建人姓名
	private int workState;         //状态 0-未完成，1-已完成

	private String beginTime;   //开始时间
	private String endTime;     //结束时间
	private String finishTime;  //完成时间

    private boolean valid;		//是否有效:0-无效;1-有效.
	private List<OpBigCategoryVo> bigList = new ArrayList<OpBigCategoryVo>(); //大项
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
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public Integer getExecuterId() {
		return executerId;
	}
	public void setExecuterId(Integer executerId) {
		this.executerId = executerId;
	}
	public String getExecuterName() {
		return executerName;
	}
	public void setExecuterName(String executerName) {
		this.executerName = executerName;
	}
	public Integer getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public int getWorkState() {
		return workState;
	}
	public void setWorkState(int workState) {
		this.workState = workState;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public List<OpBigCategoryVo> getBigList() {
		return bigList;
	}
	public void setBigList(List<OpBigCategoryVo> bigList) {
		this.bigList = bigList;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	
	
	
}
