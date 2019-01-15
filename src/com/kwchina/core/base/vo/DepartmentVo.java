package com.kwchina.core.base.vo;

import com.kwchina.core.common.vo.BaseVo;
import lombok.Data;


@Data
public class DepartmentVo extends BaseVo{

	private Integer xId;            //主键
    private Integer departmentId;    //用来被关联
    private String departmentName;	//名称
    private String shortName;		//简称
    private String departmentNo;		//编号
    private int layer;				//层级
    private int levelId;			//组织层级:0-公司(集团公司);1-部门;2-班组;3-分公司;4-投资公司.
    private int orderNo;			//排序编号
    private Integer parentId;		//父部门或公司
    private String userId;		//经理/主管

}


