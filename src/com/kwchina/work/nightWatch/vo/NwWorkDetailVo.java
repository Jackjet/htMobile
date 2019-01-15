package com.kwchina.work.nightWatch.vo;

import java.util.ArrayList;
import java.util.List;



/**
 * 巡更详情
 * @author suguan
 *
 */
public class NwWorkDetailVo{

	private Integer xId;
	private Integer detailId;        //主键
	private Integer listId;        //所属工作
	private Integer workId;        //所属任务
	private Integer areaId;        //区域
	private String areaName;       //区域名称
	private Integer itemId;        //巡更小项 
	private String itemName;       //小项名称

	private int opResult;          //操作结果  0-初始  1-是 -1-否
	private String beginTimeStr;   //开始时间
	private String finishTimeStr;  //完成时间
	private Integer executerId;	   //执行人
	private String executerName;   //执行人姓名
	private Integer workState;     //状态
								   //0-	进行中
								   //1-	已完成

	private String attachs;        //图片路径
	private List<NwDetailLogVo> logs = new ArrayList<NwDetailLogVo>();//异常信息

    private boolean valid;		//是否有效:0-无效;1-有效.

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

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getOpResult() {
		return opResult;
	}

	public void setOpResult(int opResult) {
		this.opResult = opResult;
	}

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getFinishTimeStr() {
		return finishTimeStr;
	}

	public void setFinishTimeStr(String finishTimeStr) {
		this.finishTimeStr = finishTimeStr;
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

	public List<NwDetailLogVo> getLogs() {
		return logs;
	}

	public void setLogs(List<NwDetailLogVo> logs) {
		this.logs = logs;
	}

	public String getExecuterName() {
		return executerName;
	}

	public void setExecuterName(String executerName) {
		this.executerName = executerName;
	}
    

	public String getAttachs() {
		return attachs;
	}
	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

 	


    
}


