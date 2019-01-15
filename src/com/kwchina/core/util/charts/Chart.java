package com.kwchina.core.util.charts;

import java.util.ArrayList;
import java.util.List;

public class Chart {

	private String caption = "";

	private String xAxisName = "";

	private String yAxisName = "";

	private String numberPrefix = "";

	private String numberSuffix = "��Ԫ";

	private String imageSaveURL = "";

	private int palette = 1;

	private List<ChartData> datas = new ArrayList<ChartData>();

	private List<ChartCategory> categories = new ArrayList<ChartCategory>();

	private List<ChartDataSet> datasets = new ArrayList<ChartDataSet>();

	public Chart(String caption, String xAxisName, String yAxisName) {
		this.caption = caption;
		this.xAxisName = xAxisName;
		this.yAxisName = yAxisName;
	}

	public Chart(String caption, String xAxisName, String yAxisName, String numberSuffix) {
		this.caption = caption;
		this.xAxisName = xAxisName;
		this.yAxisName = yAxisName;
		this.numberSuffix = numberSuffix;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getXAxisName() {
		return xAxisName;
	}

	public void setXAxisName(String axisName) {
		xAxisName = axisName;
	}

	public String getYAxisName() {
		return yAxisName;
	}

	public void setYAxisName(String axisName) {
		yAxisName = axisName;
	}

	public String getNumberPrefix() {
		return numberPrefix;
	}

	public void setNumberPrefix(String numberPrefix) {
		this.numberPrefix = numberPrefix;
	}

	public String getImageSaveURL() {
		return imageSaveURL;
	}

	public void setImageSaveURL(String imageSaveURL) {
		this.imageSaveURL = imageSaveURL;
	}

	public int getPalette() {
		return palette;
	}

	public void setPalette(int palette) {
		this.palette = palette;
	}

	public String getNumberSuffix() {
		return numberSuffix;
	}

	public void setNumberSuffix(String numberSuffix) {
		this.numberSuffix = numberSuffix;
	}

	public List<ChartData> getDatas() {
		return datas;
	}

	public void setDatas(List<ChartData> datas) {
		this.datas = datas;
	}

	public List<ChartDataSet> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<ChartDataSet> datasets) {
		this.datasets = datasets;
	}

	public List<ChartCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ChartCategory> categories) {
		this.categories = categories;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<chart caption='" + this.caption + "' xAxisName='" + this.xAxisName + "' yAxisName='" + this.yAxisName + "' palette='" + this.palette + "'");
		sb.append(" rotateYAxisName='0' showFCMenuItem='0' imageSave='1' imageSaveURL='" + this.imageSaveURL + "'").append(
				" baseFontSize ='12' bgColor='999999,FFFFFF' borderColor='1D8BD1' borderThickness='1'").append(" borderAlpha='50' bgAlpha='30' showBorder='1' animation='1' formatNumberScale='0'")
				.append(" showValues='1' showPercentInToolTip='1' numberPrefix='" + this.numberPrefix + "' numberSuffix='" + this.numberSuffix + "'>");
		if (!this.datas.isEmpty()) {
			for (ChartData data : this.datas)
				sb.append(data.toString());
		} else if (!this.categories.isEmpty()) {
			sb.append("<categories>");
			for (ChartCategory category : this.categories)
				sb.append(category.toString());
			sb.append("</categories>");
			for (ChartDataSet dataset : this.datasets)
				sb.append(dataset.toString());
		}
		sb.append("</chart>");
		return sb.toString();
	}
}
