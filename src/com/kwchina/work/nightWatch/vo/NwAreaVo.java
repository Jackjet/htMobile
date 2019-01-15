package com.kwchina.work.nightWatch.vo;

public class NwAreaVo {
	private Integer xId;
	private String areaName;    //区域名称
    private String areaCode;    //区域代码
	private int orderNo;	        //排序号
	private Integer[] itemIds;
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public Integer getxId() {
		return xId;
	}
	public void setxId(Integer xId) {
		this.xId = xId;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public Integer[] getItemIds() {
		return itemIds;
	}
	public void setItemIds(Integer[] itemIds) {
		this.itemIds = itemIds;
	}
    
    
}
