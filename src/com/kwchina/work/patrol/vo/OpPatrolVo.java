package com.kwchina.work.patrol.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 进入操作巡检的vo
 * @author suguan
 *
 */
public class OpPatrolVo {
	private Integer itemId;        //主键
	private Integer listId;        //所属工作
	private String beginTime;   //开始时间
	private String endTime;     //结束时间
	private Integer operatorId;	   //执行人
	private String finishTime;  //完成时间
	private Integer workState;     //状态
								   //0-	进行中
								   //1-	已完成

	private int readSafeRules;     //是否已熟知安全方案  0-否,1-是
	private List<OpBigCategoryVo> bigList = new ArrayList<OpBigCategoryVo>(); //大项
	
	private int itemSum;   //总项目数
	private int itemDoneSum;//完成数

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

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getWorkState() {
		return workState;
	}

	public void setWorkState(Integer workState) {
		this.workState = workState;
	}

	public List<OpBigCategoryVo> getBigList() {
		return bigList;
	}

	public void setBigList(List<OpBigCategoryVo> bigList) {
		this.bigList = bigList;
	}

	public int getItemSum() {
		return itemSum;
	}

	public void setItemSum(int itemSum) {
		this.itemSum = itemSum;
	}

	public int getItemDoneSum() {
		return itemDoneSum;
	}

	public void setItemDoneSum(int itemDoneSum) {
		this.itemDoneSum = itemDoneSum;
	}

	public int getReadSafeRules() {
		return readSafeRules;
	}

	public void setReadSafeRules(int readSafeRules) {
		this.readSafeRules = readSafeRules;
	}

	
}
