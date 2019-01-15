package com.kwchina.work.monthReport.entity;

import com.kwchina.work.reform.entity.ChildCategory;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "x_issue_detail")
@Data
public class Issue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer issueId;
	private String month;
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="categoryId")
	private ChildCategory category;
	private String content;
	private String year;
	private boolean valid;
}
