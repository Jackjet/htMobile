package com.kwchina.work.patrol.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;

@Entity
@Table(name = "x_patrol_exception")
@ObjectId(id="xId")
public class Abnormal {
	private Integer xId;
	private Integer exceptionId;        //主键
	private String errorlog;        //异常信息
	private String errorImage;         //异常图片
	private Integer departmentId;       //责任部门
	private Timestamp markTime;   //记录时间
	private Integer errorState;     //状态
								   //0-	待整改
									//1-待复查
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)							   //2-	已完成
	public Integer getxId() {
		return xId;
	}
	public void setxId(Integer xId) {
		this.xId = xId;
	}
	public Integer getExceptionId() {
		return exceptionId;
	}
	public void setExceptionId(Integer exceptionId) {
		this.exceptionId = exceptionId;
	}
	public String getErrorlog() {
		return errorlog;
	}
	public void setErrorlog(String errorlog) {
		this.errorlog = errorlog;
	}
	public String getErrorImage() {
		return errorImage;
	}
	public void setErrorImage(String errorImage) {
		this.errorImage = errorImage;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public Timestamp getMarkTime() {
		return markTime;
	}
	public void setMarkTime(Timestamp markTime) {
		this.markTime = markTime;
	}
	public Integer getErrorState() {
		return errorState;
	}
	public void setErrorState(Integer errorState) {
		this.errorState = errorState;
	}
}
