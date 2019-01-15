package com.kwchina.work.security.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 安全文档
 * @author suguan
 *
 */
@Entity
@Table(name = "x_security_DocumentInfor")//, schema = "dbo"
@ObjectId(id="documentId")
public class DocumentInfor implements JSONNotAware{

	private Integer documentId;     //主键
    private String documentTitle;    //标题
	private String keyWord;	         //关键字
    private String attachment;		 //附件路径
    private Timestamp createTime;    //创建时间

    private Integer authorId;		 //作者
    private Integer departmentId;     //作者所在部门

    private DocumentCategory category;//所属分类
    private boolean valid;		//是否有效:0-无效;1-有效.


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	

 	@Column(columnDefinition = "nvarchar(4000)",nullable = true)
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	

	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	

	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="categoryId")
	public DocumentCategory getCategory() {
		return category;
	}
	public void setCategory(DocumentCategory category) {
		this.category = category;
	}
	
	
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}



}


