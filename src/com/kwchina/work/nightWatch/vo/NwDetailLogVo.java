package com.kwchina.work.nightWatch.vo;



/**
 * 巡更详情异常项
 * @author suguan
 *
 */
public class NwDetailLogVo{

	private Integer xId;
	private Integer logId;          //主键
	private Integer detailId;       //所属小项
	private Integer workId;        //所属任务
	private Integer listId;        //所属工作
	private String logTimeStr;   //操作时间
	private String logContent;   //异常内容
	private String logAttachs;   //异常图片
	private String submitTimeStr;  //上报时间
	private Integer submitState;     //上报状态
								   //0-	未上报
								   //1-	已上报
	
	private int solveState;       //解决状态  0-否  1-是
    private String solveTimeStr;		//解决时间

    private String solveWord;      //整改后的文字说明
    private String solveAttachs;   //整改后的图片
    
	public Integer getxId() {
		return xId;
	}
	public void setxId(Integer xId) {
		this.xId = xId;
	}
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	public Integer getListId() {
		return listId;
	}
	public void setListId(Integer listId) {
		this.listId = listId;
	}
	public String getLogTimeStr() {
		return logTimeStr;
	}
	public void setLogTimeStr(String logTimeStr) {
		this.logTimeStr = logTimeStr;
	}
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public String getLogAttachs() {
		return logAttachs;
	}
	public void setLogAttachs(String logAttachs) {
		this.logAttachs = logAttachs;
	}
	public String getSubmitTimeStr() {
		return submitTimeStr;
	}
	public void setSubmitTimeStr(String submitTimeStr) {
		this.submitTimeStr = submitTimeStr;
	}
	public Integer getSubmitState() {
		return submitState;
	}
	public void setSubmitState(Integer submitState) {
		this.submitState = submitState;
	}
	public int getSolveState() {
		return solveState;
	}
	public void setSolveState(int solveState) {
		this.solveState = solveState;
	}
	public String getSolveTimeStr() {
		return solveTimeStr;
	}
	public void setSolveTimeStr(String solveTimeStr) {
		this.solveTimeStr = solveTimeStr;
	}
	public String getSolveWord() {
		return solveWord;
	}
	public void setSolveWord(String solveWord) {
		this.solveWord = solveWord;
	}
	public String getSolveAttachs() {
		return solveAttachs;
	}
	public void setSolveAttachs(String solveAttachs) {
		this.solveAttachs = solveAttachs;
	}

 	

   
}


