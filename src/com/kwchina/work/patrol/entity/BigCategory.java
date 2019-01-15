package com.kwchina.work.patrol.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 巡检大项
 * @author suguan
 *
 */
@Entity
@Table(name = "x_patrol_bigCategory")//, schema = "dbo"
@ObjectId(id="xId")
public class BigCategory implements JSONNotAware{

	private Integer xId;
	private Integer bigId;          //主键
    private String categoryName;    //大项名称
    private String categoryCode;    //大项代码
	private int orderNo;	        //排序号


    private boolean valid;		//是否有效:0-无效;1-有效.


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getxId() {
		return xId;
	}

	public void setxId(Integer xId) {
		this.xId = xId;
	}

	public Integer getBigId() {
		return bigId;
	}

	public void setBigId(Integer bigId) {
		this.bigId = bigId;
	}

	

	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
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

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}



    
}


