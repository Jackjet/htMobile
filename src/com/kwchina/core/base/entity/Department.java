package com.kwchina.core.base.entity;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

@Entity
@Table(name = "x_core_department")//, schema = "dbo"
@ObjectId(id="xId")
public class Department implements JSONNotAware{

	private Integer xId;            //主键
    private Integer departmentId;    //用来被关联
	private String departmentName;	//名称
    private String shortName;		//简称
    private String departmentNo;		//编号
    private int type;
	private int layer;				//层级
    private int levelId;			//组织层级:0-公司(集团公司);1-部门;2-班组;3-分公司;4-投资公司.
    private int orderNo;			//排序编号
    private Department parent;	//父部门或公司
//    private UserInfor director;	//经理/主管
    private String userId;          //部门长
    private Set<Department> children = new HashSet<Department>(0);

    private boolean valid;		//是否有效:0-无效;1-有效.

    public String toString(){
    	System.out.println(this.parent.departmentName);
    	return this.departmentName;
    }


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getxId() {
		return xId;
	}

	public void setxId(Integer xId) {
		this.xId = xId;
	}


 	@Column(columnDefinition = "nvarchar(100)",nullable = true)
    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }



 	@Column(columnDefinition = "nvarchar(80)")
    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }



 	@Column(columnDefinition = "nvarchar(80)")
 	public String getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}



	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}


    public int getLayer() {
        return this.layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }


    public int getLevelId() {
        return this.levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }



    public int getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parentId")
	@JsonIgnore
    public Department getParent() {
        return this.parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }


	@OneToMany(mappedBy = "parent",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	@OrderBy("orderNo")
	@JsonIgnore
    public Set<Department> getChildren() {
        return this.children;
    }

    public void setChildren(Set<Department> children) {
        this.children = children;
    }


	public Integer getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}


	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}


