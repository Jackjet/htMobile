package com.kwchina.core.util.charts.gantt;

import java.sql.Date;

import com.kwchina.core.util.DateConverter;


public class GanttFactory {
	private Gantt gantt;

	private StringBuilder xml;

	public GanttFactory(Gantt gantt) {
		this.gantt = gantt;
	}

	public void initialize() throws Exception {
		xml = new StringBuilder();
		Date subProEndDate = DateConverter.getDateAddMonth(DateConverter.getCurrDate(gantt.getEndDate()), 1);
		int tmpEndDate[] = DateConverter.getYearMonthDay(subProEndDate);
		String subProEndDateStr = "" + tmpEndDate[0] + "-" + tmpEndDate[1] + "-01";
		// 头部信息
		xml.append("<chart dateFormat='yyyy/mm/dd' "
				+ "hoverCapBorderColor='2222ff' " + "hoverCapBgColor='e1f5ff' "
				+ "ganttWidthPercent='85' " + "ganttLineAlpha='80' "
				+ "canvasBorderColor='024455' " + "canvasBorderThickness='0' "
				+ "gridBorderColor='4567aa' " + "gridBorderAlpha='20'>");
		xml.append("<categories bgColor='ee0000'> " + "<category start='"
				+ gantt.getStartDate().replace("-", "/") + "' " + "end='" + subProEndDateStr.replace("-", "/")
				+ "' " + "align='center' " + "name='工程分项进度' "
				+ "isBold='1' fontSize='16' /> " + "</categories>");
		
		xml.append("<categories bgColor='4567aa' " + "fontColor='ff0000'> "
				+ "<category start='" + gantt.getStartDate().replace("-", "/") + "' " + "end='"
				+ subProEndDateStr.replace("-", "/") + "' align='center' name='日期格式：月 年' "
				+ "alpha='' font='Verdana' "
				+ "fontColor='ffffff' isBold='1' fontSize='16' /> "
				+ "</categories>");
		Date tmpDate = DateConverter.getCurrDate(gantt.getStartDate());
		xml.append("<categories bgColor='ffffff' fontColor='1288dd' fontSize='10'>");
		tmpDate = DateConverter.getDateAddMonth(tmpDate, -1);
		while(tmpDate.before(DateConverter.getCurrDate(gantt.getEndDate()))){
			tmpDate = DateConverter.getDateAddMonth(tmpDate, 1);
			int starts[] = DateConverter.getYearMonthDay(tmpDate);
			String startDateStr = "" + starts[0] + "-" + starts[1] + "-01";
			Date tmpStartDate = DateConverter.getCurrDate(startDateStr);
			tmpStartDate = DateConverter.getDateAddMonth(tmpStartDate, 1);
			Date endDate = DateConverter.getDateAddDay(tmpStartDate, -1);
			String endDateStr = DateConverter.getDateString(endDate);
			String endYear = String.valueOf(starts[0]);
			endYear = endYear.substring(endYear.length()-2, endYear.length());
			xml.append(" <category start='"+startDateStr.replace("-", "/")+"' end='"+endDateStr.replace("-", "/")+"' align='center' name='"+starts[1]+"." + endYear +"' isBold='0' /> ");
		}
		xml.append(" </categories>");
		// 组成实际内容
		xml.append("<processes headerText='序号' " + "fontColor='ffffff' "
				+ "fontSize='10' isBold='1' "
				+ "isAnimated='1' bgColor='4567aa' "
				+ " headerVAlign='right' headerbgColor='4567aa' "
				+ "headerFontColor='ffffff' headerFontSize='11' "
				+ "width='40' align='center'>");
		for (int i = 0; i < gantt.getTaskInfors().size(); i++)
			xml.append("<process Name='" + (i + 1) + "' id='" + (i + 1)
					+ "' />");
		xml.append("</processes>");
		xml.append("<dataTable showProcessName='1' "
				+ "nameAlign='left' fontColor='000000' "
				+ "fontSize='10' isBold='1' " + "headerBgColor='00ffff' "
				+ "headerFontColor='4567aa' " + "headerFontSize='11' "
				+ "vAlign='right' align='left'> ");
		xml.append("<dataColumn width='110' headerfontcolor='ffffff' "
				+ "bgColor='eeeeee' headerbgColor='4567aa' "
				+ "fontColor='000000' headerText='分项名称' isBold='0'>");
		for (String[] taskInfor : gantt.getTaskInfors())
			xml.append("<text label='" + taskInfor[0] + "' />");

		xml.append("</dataColumn>");

		xml.append("</dataTable>");
		xml.append("<tasks  width='10' >");
		int i = 1;
		for (String[] taskInfor : gantt.getTaskInfors()) {
			xml.append("<task name='" + taskInfor[0] + "' processId='" + i
					+ "' " + "start='" + taskInfor[1].replace("-", "/") + "' end='"
					+ taskInfor[2].replace("-", "/") + "' " + "Id='" + i
					+ "' color='cccccc' height='10' topPadding='19'/>");
			i++;
		}
		xml.append("</tasks>");
		xml.append("</chart>");
	}

	public String toString() {
		return xml.toString();
	}
}