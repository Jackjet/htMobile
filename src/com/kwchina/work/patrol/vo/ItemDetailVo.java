package com.kwchina.work.patrol.vo;


public class ItemDetailVo {
	private Integer xId;
	private Integer detailId;        //主键
	private Integer listId;        //所属工作
	private Integer itemId;        //所属的具体工作
	private Integer bigId;         //所属大项
	private String bigName;        //大项名称
	private Integer smallId;       //所属小项
	private String smallName;      //小项名称
	private int opResult;          //操作结果  0-初始  1-是 -1-否
	private Integer operatorId;	   //执行人
	private String operatorName;   //执行人姓名
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	private String beginTimeStr;   //开始时间
	private String endTimeStr;     //结束时间（执行时间）
	private Integer workState;     //状态
								   //0-	进行中
								   //1-	已完成
	private String errorLog;       //异常信息
	private String attachs;        //图片路径
	private String departmentName;
	private String[] attachArray;
	public Integer getxId() {
		return xId;
	}
	public void setxId(Integer xId) {
		this.xId = xId;
	}
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public Integer getListId() {
		return listId;
	}
	public void setListId(Integer listId) {
		this.listId = listId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getBigId() {
		return bigId;
	}
	public void setBigId(Integer bigId) {
		this.bigId = bigId;
	}
	public String getBigName() {
		return bigName;
	}
	public void setBigName(String bigName) {
		this.bigName = bigName;
	}
	public Integer getSmallId() {
		return smallId;
	}
	public void setSmallId(Integer smallId) {
		this.smallId = smallId;
	}
	public String getSmallName() {
		return smallName;
	}
	public void setSmallName(String smallName) {
		this.smallName = smallName;
	}
	public int getOpResult() {
		return opResult;
	}
	public void setOpResult(int opResult) {
		this.opResult = opResult;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public Integer getWorkState() {
		return workState;
	}
	public void setWorkState(Integer workState) {
		this.workState = workState;
	}
	public String getErrorLog() {
		return errorLog;
	}
	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}
	public String getAttachs() {
		return attachs;
	}
	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}
	public String getBeginTimeStr() {
		return beginTimeStr;
	}
	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String[] getAttachArray() {
		return attachArray;
	}
	public void setAttachArray(String[] attachArray) {
		this.attachArray = attachArray;
	}
	
	
}
