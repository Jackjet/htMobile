package com.kwchina.work.nightWatch.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 巡更项
 * @author suguan
 *
 */
@Entity
@Table(name = "x_nightWatch_item")
@ObjectId(id="xId")
public class NwItem implements JSONNotAware{


	private Integer xId;
	private Integer itemId;     //主键
    private String itemName;    //项目名称
    private String itemCode;    //项目代码
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
	

	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}



    
}


