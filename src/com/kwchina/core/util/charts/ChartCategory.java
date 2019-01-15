package com.kwchina.core.util.charts;

public class ChartCategory {

	private String label = "";
	
	public ChartCategory(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String toString() {
		return "<category label='" + this.label + "'/>";
	}
}
