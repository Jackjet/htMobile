package com.kwchina.work.patrol.vo;

import java.util.ArrayList;
import java.util.List;



/**
 * 巡检大项
 * @author suguan
 *
 */
public class OpBigCategoryVo{

	private Integer xId;
	private Integer bigId;
    private String categoryName;    //大项名称
    private String categoryCode;    //大项代码
	private int orderNo;	        //排序号
	private List<ItemDetailVo> details = new ArrayList<ItemDetailVo>();
	
	private List<OpSmallCategory> smallCategoryList = new ArrayList<OpSmallCategory>();   //包含小项
	
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
	public List<ItemDetailVo> getDetails() {
		return details;
	}
	public void setDetails(List<ItemDetailVo> details) {
		this.details = details;
	}
	public Integer getBigId() {
		return bigId;
	}
	public void setBigId(Integer bigId) {
		this.bigId = bigId;
	}
	public List<OpSmallCategory> getSmallCategoryList() {
		return smallCategoryList;
	}
	public void setSmallCategoryList(List<OpSmallCategory> smallCategoryList) {
		this.smallCategoryList = smallCategoryList;
	}




    
}


