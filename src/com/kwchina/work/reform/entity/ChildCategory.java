package com.kwchina.work.reform.entity;


import com.kwchina.core.util.json.JSONNotAware;
import lombok.Data;

import javax.persistence.*;

/**
 * 小类型
 * @author suguan
 *
 */
@Entity
@Table(name = "x_child_Category")//, schema = "dbo"
@Data
public class ChildCategory implements JSONNotAware{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
    private String categoryName;    //小类名称
    private String categoryCode;    //小类代码
	private int orderNo;	        //排序号
	private Integer parentId;
    private boolean valid;		//是否有效:0-无效;1-有效.
}


