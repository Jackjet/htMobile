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
 * 巡更区域
 * @author suguan
 *
 */
@Entity
@Table(name = "x_nightWatch_area")
@ObjectId(id="xId")
public class NwArea implements JSONNotAware{


	private Integer xId;
	private Integer areaId;     //主键
    private String areaName;    //区域名称
    private String areaCode;    //区域代码
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
	

	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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


