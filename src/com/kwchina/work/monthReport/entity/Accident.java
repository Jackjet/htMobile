package com.kwchina.work.monthReport.entity;

import com.kwchina.core.base.entity.Department;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "x_accident_detail")
@Data
public class Accident {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accidentId;
	private Date accidentTime;
	private String accidentAddress;
	private String content;
	private String accidentAttach;
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="departmentId")
	private Department dutyDept;
	private boolean valid;
}
