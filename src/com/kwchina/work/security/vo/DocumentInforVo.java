package com.kwchina.work.security.vo;

public class DocumentInforVo {

	private Integer documentId;
	private String documentTitle; 	// 文档标题
	private String keyWord; 		// 关键字
	private String attachment; 	//附件

	private String[] attatchmentArray = {}; // 附件路径
//	private Integer authorId; 			// 作者
	private Integer categoryId; 		// 文档分类
//	private Integer departmentId; 		// 所属部门
	
	
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String[] getAttatchmentArray() {
		return attatchmentArray;
	}
	public void setAttatchmentArray(String[] attatchmentArray) {
		this.attatchmentArray = attatchmentArray;
	}
//	public Integer getAuthorId() {
//		return authorId;
//	}
//	public void setAuthorId(Integer authorId) {
//		this.authorId = authorId;
//	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
//	public Integer getDepartmentId() {
//		return departmentId;
//	}
//	public void setDepartmentId(Integer departmentId) {
//		this.departmentId = departmentId;
//	}
	
	
	
}
