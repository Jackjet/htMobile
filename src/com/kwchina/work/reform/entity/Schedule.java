package com.kwchina.work.reform.entity;

import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.entity.User;
import com.kwchina.core.util.annotation.ObjectId;
import com.kwchina.core.util.json.JSONNotAware;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "x_reform_Schedule")
@ObjectId(id="xId")
@Data
public class Schedule implements JSONNotAware {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer scheduleId;			//主键
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reformId")
	private ReformDetail reformDetail;         //任务Id
	private String reformInfo;		//整改信息
	private String reformAttach;		//整改附件
	private Date reformTime;	//整改时限
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reformUserId")
	private  User reformUser;			//整改人
	private Integer reformStatus;		//整改状态
	
}


