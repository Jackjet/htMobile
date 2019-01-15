package com.kwchina.work.security.entity;

public class Category {
	private Integer categoryId;     //主键
    private String categoryName;    //分类名称
	private int layer;	            //层级
    private String fullPath;		//全路径
    private String categoryCode;    //分类代码

    private int displayNo;			//显示次序
    private int pid;
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public int getDisplayNo() {
		return displayNo;
	}
	public void setDisplayNo(int displayNo) {
		this.displayNo = displayNo;
	}
	
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", layer=" + layer + ", fullPath=" + fullPath
				+ ", categoryCode=" + categoryCode + ", leaf=" 
				+ ", displayNo=" + displayNo + ", leftIndex="
				+ ", rightIndex=" + ", valid=" 
				+ ", parentId=" + pid + "]";
	}
}
