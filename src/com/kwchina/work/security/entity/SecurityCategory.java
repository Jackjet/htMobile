package com.kwchina.work.security.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 安全文档类型
 * @author suguan
 *
 */
@Entity
@Table(name = "x_security_category")//, schema = "dbo"
//@ObjectId(id="categoryId")
public class SecurityCategory implements JSONNotAware{

	private Integer categoryId;     //主键
    private String categoryName;    //分类名称
	private int layer;	            //层级
    private String fullPath;		//全路径
    private String categoryCode;    //分类代码

    private int displayNo;			//显示次序
    private Integer pid;            //父id

    @Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
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

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	

 	@Column(columnDefinition = "nvarchar(100)",nullable = true)
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

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	
}


