package com.kwchina.work.nightWatch.vo;

public class NwItemVo {
	private Integer xId;
	private String itemName;    //项目名称
    private String itemCode;    //项目代码
	private int orderNo;	        //排序号
    
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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
    
    
}
