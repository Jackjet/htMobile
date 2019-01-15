package com.kwchina.work.commonWork.entity;


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
 * 工作任务
 * @author suguan
 *
 */
@Entity
@Table(name = "x_work")//, schema = "dbo"
@ObjectId(id="workId")
public class XWork implements JSONNotAware{

	private Integer xId;          //主键
	private Integer workId;
    private String workTitle;		//标题
    private Timestamp beginTime;    //开始时间
    private Timestamp endTime;      //结束时间

    private Integer executerId;		 //执行人ID
    private String copyTo;           //抄送人(将userId用英文逗号连接)
    private String location;           //地点
    private String content;           //内容
    private String attachs;           //附件
    private String source;           //来源 一次、周期


    private int workState;           /*
										状态
										0-	进行中
										1-	已完成
									*/
    private Timestamp createTime;    //创建时间
    private Integer createrId;       //创建人
    private Timestamp finishTime;    //完成时间
    private Integer finisherId;      //完成者ID

    private boolean valid;           //是否有效

    

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getxId() {
		return xId;
	}

	public void setxId(Integer xId) {
		this.xId = xId;
	}

	public Integer getWorkId() {
		return workId;
	}


	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getWorkTitle() {
		return workTitle;
	}

	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}

	
	
	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	
	
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	
	
	public Integer getExecuterId() {
		return executerId;
	}

	public void setExecuterId(Integer executerId) {
		this.executerId = executerId;
	}

	

 	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
	public String getCopyTo() {
		return copyTo;
	}

	public void setCopyTo(String copyTo) {
		this.copyTo = copyTo;
	}

	

 	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	

 	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	

 	@Column(columnDefinition = "nvarchar(2000)",nullable = true)
	public String getAttachs() {
		return attachs;
	}

	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}

	

 	@Column(columnDefinition = "nvarchar(200)",nullable = true)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	
	
	public int getWorkState() {
		return workState;
	}

	public void setWorkState(int workState) {
		this.workState = workState;
	}

	
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	
	
	public Timestamp getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Timestamp finishTime) {
		this.finishTime = finishTime;
	}

	
	
	public Integer getFinisherId() {
		return finisherId;
	}

	public void setFinisherId(Integer finisherId) {
		this.finisherId = finisherId;
	}

	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Integer getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}


}


