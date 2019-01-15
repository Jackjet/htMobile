package com.kwchina.work.reform.entity;


import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;
import lombok.Data;

import javax.persistence.*;

/**
 * 大项
 * @author suguan
 *
 */
@Entity
@Table(name = "x_parent_Category")//, schema = "dbo"
@Data
public class ParentCategory implements JSONNotAware{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pId;
    private String pName;    //大类名称
    private String pCode;    //大类代码
	private int orderNo;	        //排序号
    private boolean valid;		//是否有效:0-无效;1-有效.
}


