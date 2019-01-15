package com.kwchina.work.patrol.vo;



/**
 * 巡检小项
 * @author suguan
 *
 */
public class SmallCategoryVo{

	private Integer xId;
    private String categoryName;    //小项名称
    private String categoryCode;    //小项代码
	private int orderNo;	            //排序号


	private Integer bigId;     //所属大项


	public Integer getxId() {
		return xId;
	}


	public void setxId(Integer xId) {
		this.xId = xId;
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


	public Integer getBigId() {
		return bigId;
	}


	public void setBigId(Integer bigId) {
		this.bigId = bigId;
	}


    
}


