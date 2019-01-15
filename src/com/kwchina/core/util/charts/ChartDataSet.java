package com.kwchina.core.util.charts;

import java.util.ArrayList;
import java.util.List;

public class ChartDataSet {

	private String seriesName = "";
	
	private String color = "";
	
	private int showValues = 1;
	
	private List<ChartData> datas = new ArrayList<ChartData>();
	
	public ChartDataSet(String seriesName,String color){
		this.seriesName = seriesName;
		this.color = color;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getShowValues() {
		return showValues;
	}

	public void setShowValues(int showValues) {
		this.showValues = showValues;
	}

	public List<ChartData> getDatas() {
		return datas;
	}

	public void setDatas(List<ChartData> datas) {
		this.datas = datas;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("<dataset seriesName='" + this.seriesName + "' color='"
				+ this.color + "' showValues='" + this.showValues + "'>");
		if(!this.datas.isEmpty())
			for (ChartData data : this.datas) {
				sb.append(data.toString());
			}
		sb.append("</dataset>");
		return sb.toString();
	}
}
