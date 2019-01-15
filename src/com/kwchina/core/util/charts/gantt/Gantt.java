package com.kwchina.core.util.charts.gantt;

import java.util.List;

public class Gantt {
	private String startDate;
	private String endDate;
	private List<String[]> taskInfors;

	public List<String[]> getTaskInfors() {
		return taskInfors;
	}

	public void setTaskInfors(List<String[]> taskInfors) {
		this.taskInfors = taskInfors;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}