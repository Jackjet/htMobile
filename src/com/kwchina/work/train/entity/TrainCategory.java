package com.kwchina.work.train.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 培训类型
 * @author suguan
 *
 */
@Entity
@Table(name = "x_train_category")//, schema = "dbo"
@ObjectId(id="categoryId")
public class TrainCategory implements JSONNotAware{

	private Integer categoryId;          //主键
	private String categoryName;    //类别名称  
	private String shortName;       //简称
	private int orderNo;            //排序号

	private int valid;  //是否有效

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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


 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	
	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	
	

}


