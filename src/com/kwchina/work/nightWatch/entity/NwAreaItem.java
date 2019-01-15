package com.kwchina.work.nightWatch.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 巡更区域与项配置
 * @author suguan
 *
 */
@Entity
@Table(name = "x_nightWatch_areaItem")
@ObjectId(id="xId")
public class NwAreaItem implements JSONNotAware{


	private Integer xId;
	private Integer itemId;     //项目
	private Integer areaId;     //区域
    

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
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	


    
}


