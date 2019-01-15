package com.kwchina.work.monthReport.entity;

import com.kwchina.core.base.entity.Department;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "x_focus_detail")
@Data
public class Focus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer focusId;
	private Date recordTime;
	private String content;
	private String attach;
	private Integer type;//1:上月反馈 2：本月重点
	private boolean valid;
}
