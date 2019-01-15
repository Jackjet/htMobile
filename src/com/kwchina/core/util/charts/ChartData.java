package com.kwchina.core.util.charts;

public class ChartData {
	
	private String label = "";
	
	private String value = "";

	public ChartData(String value) {
		this.value = value;
	}
	
	public ChartData(String label, String value) {
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		String ret = "<set label='" + this.label + "' value='" + this.value + "'/>";
		return ret;
	}
}
