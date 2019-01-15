package com.kwchina.work.patrol.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 巡检项注意事项
 * @author suguan
 *
 */
@Entity
@Table(name = "x_patrol_attention")
@ObjectId(id="xId")
public class Attention implements JSONNotAware{

	private Integer xId;
	private Integer bigId;          //所属大项
    private String harmGrade;    //危险等级
    private String harmElement;    //危害因素
	private String riskPoint;	        //隐患风险点
	private String riskLevel;      //风险度
	private String securityMethod; //安全措施



    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getxId() {
		return xId;
	}

	public void setxId(Integer xId) {
		this.xId = xId;
	}

	public Integer getBigId() {
		return bigId;
	}

	public void setBigId(Integer bigId) {
		this.bigId = bigId;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getHarmGrade() {
		return harmGrade;
	}

	public void setHarmGrade(String harmGrade) {
		this.harmGrade = harmGrade;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getHarmElement() {
		return harmElement;
	}

	public void setHarmElement(String harmElement) {
		this.harmElement = harmElement;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getRiskPoint() {
		return riskPoint;
	}

	public void setRiskPoint(String riskPoint) {
		this.riskPoint = riskPoint;
	}

	

 	@Column(columnDefinition = "nvarchar(500)",nullable = true)
	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	

 	@Column(columnDefinition = "nvarchar(4000)",nullable = true)
	public String getSecurityMethod() {
		return securityMethod;
	}

	public void setSecurityMethod(String securityMethod) {
		this.securityMethod = securityMethod;
	}

    
}


