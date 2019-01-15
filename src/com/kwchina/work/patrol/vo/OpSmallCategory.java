package com.kwchina.work.patrol.vo;

public class OpSmallCategory {
	private Integer xId;
	private Integer smallId;     //主键
    private String categoryName;    //小项名称
    private String categoryCode;    //小项代码
	private int orderNo;	            //排序号
	public Integer getxId() {
		return xId;
	}
	public void setxId(Integer xId) {
		this.xId = xId;
	}
	public Integer getSmallId() {
		return smallId;
	}
	public void setSmallId(Integer smallId) {
		this.smallId = smallId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
