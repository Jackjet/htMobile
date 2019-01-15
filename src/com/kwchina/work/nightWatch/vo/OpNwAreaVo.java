package com.kwchina.work.nightWatch.vo;

import java.util.ArrayList;
import java.util.List;

public class OpNwAreaVo {
	private Integer xId;
	private Integer areaId;     //主键
	private String areaName;    //区域名称
    private String areaCode;    //区域代码
	private int orderNo;	        //排序号
	private List<NwWorkDetailVo> detailList = new ArrayList<NwWorkDetailVo>();  
	private List<OpNwItemVo> itemVos = new ArrayList<OpNwItemVo>();  //所包含小项
	
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
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public List<NwWorkDetailVo> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<NwWorkDetailVo> detailList) {
		this.detailList = detailList;
	}
	public List<OpNwItemVo> getItemVos() {
		return itemVos;
	}
	public void setItemVos(List<OpNwItemVo> itemVos) {
		this.itemVos = itemVos;
	}
	
	
}
