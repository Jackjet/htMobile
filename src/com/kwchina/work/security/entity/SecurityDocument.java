package com.kwchina.work.security.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 安全文档
 * @author suguan
 *
 */
@Entity
@Table(name = "x_security_Document")//, schema = "dbo"
//@ObjectId(id="documentId")
public class SecurityDocument implements JSONNotAware{

	private Integer documentId;     //主键
    private String documentTitle;    //标题
    private String fullPath;		 //全路径
    private Timestamp updateTime;    //更新时间

    private Integer authorId;		 //作者

    private Integer categoryId;   //所属目录


    @Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	

	

	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	

 	@Column(columnDefinition = "nvarchar(4000)",nullable = true)
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	
	
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	

	
	




}


