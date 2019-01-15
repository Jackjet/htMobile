package com.kwchina.work.patrol.vo;



/**
 * 巡检大项
 * @author suguan
 *
 */
public class BigCategoryVo{

	private Integer xId;
    private String categoryName;    //大项名称
    private String categoryCode;    //大项代码
	private int orderNo;	        //排序号
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




    
}


