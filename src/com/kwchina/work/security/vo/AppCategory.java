package com.kwchina.work.security.vo;


public class AppCategory {
	private Integer categoryId;
	private String categoryName;
	private Integer pid;
    private int displayNo;				//显示次序
    private int layer;
//	private List<AppCategory> children = new ArrayList<AppCategory>();
//	private List<AppDocument> documents = new ArrayList<AppDocument>();
	
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public int getDisplayNo() {
		return displayNo;
	}
	public void setDisplayNo(int displayNo) {
		this.displayNo = displayNo;
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
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
//	public List<AppCategory> getChildren() {
//		return children;
//	}
//	public void setChildren(List<AppCategory> children) {
//		this.children = children;
//	}
//	public List<AppDocument> getDocuments() {
//		return documents;
//	}
//	public void setDocuments(List<AppDocument> documents) {
//		this.documents = documents;
//	}
	
	
}
