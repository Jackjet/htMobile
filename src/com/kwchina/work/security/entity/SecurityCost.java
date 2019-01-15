package com.kwchina.work.security.entity;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;

/**
 * 安全费用
 * @author suguan
 *
 */
@Entity
@Table(name = "x_security_cost")//, schema = "dbo"
@ObjectId(id="costId")
public class SecurityCost implements JSONNotAware{

	private Integer costId;          //主键
    private int dataYear;		//年份
    private int dataMonth;      //月份
	
    private int costType;		 /*费用类型
								    1-	安全设施设备安装维护
								    2-	应急救援
								    3-	防患整改
								    4-	劳动防护
								    5-	安全教育和培训
								    6-	安全设施设备检测
								    7-	其他直接相关支出
								    8-  购置靠泊专用设备等支出
								    9-  安全生产标准化建设支出
								    10- 安全生产推广应用支出
								    11- 专职安全管理人员薪酬
								    */

    private Double planCount;     //预算
    private Double realCount;     //实际
    
    private Integer createrId;    //创建人
    private Timestamp createTime; //创建时间
    private Integer updaterId;    //最近更新人
    private Timestamp updateTime; //最近更新时间
    

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getCostId() {
		return costId;
	}
	public void setCostId(Integer costId) {
		this.costId = costId;
	}
	
	
	public int getDataYear() {
		return dataYear;
	}
	public void setDataYear(int dataYear) {
		this.dataYear = dataYear;
	}
	
	
	public int getDataMonth() {
		return dataMonth;
	}
	public void setDataMonth(int dataMonth) {
		this.dataMonth = dataMonth;
	}
	
	
	public int getCostType() {
		return costType;
	}
	public void setCostType(int costType) {
		this.costType = costType;
	}
	
	
	public Double getPlanCount() {
		return planCount;
	}
	public void setPlanCount(Double planCount) {
		this.planCount = planCount;
	}
	
	
	public Double getRealCount() {
		return realCount;
	}
	public void setRealCount(Double realCount) {
		this.realCount = realCount;
	}
	
	
	public Integer getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
	public Integer getUpdaterId() {
		return updaterId;
	}
	public void setUpdaterId(Integer updaterId) {
		this.updaterId = updaterId;
	}
	
	
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
    

}


