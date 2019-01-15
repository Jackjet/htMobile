package com.kwchina.work.commonWork.vo;



/**
 * 工作任务
 * @author suguan
 *
 */
public class XWorkVo{

	private Integer xId;          //主键
	private Integer workId;
    private String workTitle;		//标题
    private String beginTimeStr;    //开始时间
    private String endTimeStr;      //结束时间

    private Integer executerId;		 //执行人ID
    private String executerName;    //执行人姓名
    private String copyTo;           //抄送人(将userId用英文逗号连接)
    private String copyToName;       //抄送人姓名
    private String location;           //地点
    private String content;           //内容
    private String attachs;           //附件
    private String source;           //来源 一次、周期


    private int workState;           /*
										状态
										0-	进行中
										1-	已完成
									*/
    private Integer createrId;       //创建人
    private String createrName;      //创建人
    private String createTimeStr;    //创建时间
    private String finishTimeStr;    //完成时间
    private Integer finisherId;      //完成者ID
    private String finisherName;     //完成者姓名

    private boolean valid;           //是否有效

	public Integer getxId() {
		return xId;
	}

	public void setxId(Integer xId) {
		this.xId = xId;
	}

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public String getWorkTitle() {
		return workTitle;
	}

	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
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

	public Integer getExecuterId() {
		return executerId;
	}

	public void setExecuterId(Integer executerId) {
		this.executerId = executerId;
	}

	public String getExecuterName() {
		return executerName;
	}

	public void setExecuterName(String executerName) {
		this.executerName = executerName;
	}

	public String getCopyTo() {
		return copyTo;
	}

	public void setCopyTo(String copyTo) {
		this.copyTo = copyTo;
	}

	public String getCopyToName() {
		return copyToName;
	}

	public void setCopyToName(String copyToName) {
		this.copyToName = copyToName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachs() {
		return attachs;
	}

	public void setAttachs(String attachs) {
		this.attachs = attachs;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getWorkState() {
		return workState;
	}

	public void setWorkState(int workState) {
		this.workState = workState;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getFinishTimeStr() {
		return finishTimeStr;
	}

	public void setFinishTimeStr(String finishTimeStr) {
		this.finishTimeStr = finishTimeStr;
	}

	public Integer getFinisherId() {
		return finisherId;
	}

	public void setFinisherId(Integer finisherId) {
		this.finisherId = finisherId;
	}

	public String getFinisherName() {
		return finisherName;
	}

	public void setFinisherName(String finisherName) {
		this.finisherName = finisherName;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Integer getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

    

   

}


