package com.kwchina.work.reform.entity;


import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;
import lombok.Data;

import javax.persistence.*;

/**
 * 巡更区域
 * @author suguan
 *
 */
@Entity
@Table(name = "x_reform_area")
@ObjectId(id="xId")
@Data
public class ReformArea implements JSONNotAware{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer areaId;     //主键
    private String areaName;    //区域名称
    private String areaCode;    //区域代码
	private int orderNo;	        //排序号
    private boolean valid;		//是否有效:0-无效;1-有效.
}


