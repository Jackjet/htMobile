package com.kwchina.work.security.entity;


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

/**
 * 安全文档类型
 * @author suguan
 *
 */
@Entity
@Table(name = "x_security_DocumentCategory")//, schema = "dbo"
@ObjectId(id="categoryId")
public class DocumentCategory implements JSONNotAware{

	private Integer categoryId;     //主键
    private String categoryName;    //分类名称
	private int layer;	            //层级
    private String fullPath;		//全路径
    private String categoryCode;    //分类代码

    private boolean leaf;		    //是否页分类:0-否;1-是
    private int displayNo;			//显示次序
    private int leftIndex;
    private int rightIndex;


    private DocumentCategory parent;	//父分类
    private Set<DocumentCategory> childs = new HashSet<DocumentCategory>(0);

    private boolean valid;		//是否有效:0-无效;1-有效.


    private Set<DocumentInfor> documents = new HashSet<DocumentInfor>(0);

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	
	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	

 	@Column(columnDefinition = "nvarchar(100)",nullable = true)
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	
	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	
	
	public int getDisplayNo() {
		return displayNo;
	}

	public void setDisplayNo(int displayNo) {
		this.displayNo = displayNo;
	}

	
	
	public int getLeftIndex() {
		return leftIndex;
	}

	public void setLeftIndex(int leftIndex) {
		this.leftIndex = leftIndex;
	}

	
	
	public int getRightIndex() {
		return rightIndex;
	}

	public void setRightIndex(int rightIndex) {
		this.rightIndex = rightIndex;
	}

	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parentId")
	@JsonIgnore
	public DocumentCategory getParent() {
		return parent;
	}

	public void setParent(DocumentCategory parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @OrderBy("displayNo")
	public Set<DocumentCategory> getChilds() {
		return childs;
	}

	public void setChilds(Set<DocumentCategory> childs) {
		this.childs = childs;
	}

	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public Set<DocumentInfor> getDocuments() {
        return this.documents;
    }
    
    public void setDocuments(Set<DocumentInfor> documents) {
        this.documents = documents;
    }
    
}


