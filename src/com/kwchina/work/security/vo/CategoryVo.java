package com.kwchina.work.security.vo;

public class CategoryVo {
	private Integer categoryId;
    private String categoryName;
    private Integer parentId;
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	@Override
	public String toString() {
		return "CategoryVo [categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", parentId=" + parentId + "]";
	}
    
}
