<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<!doctype html>
<head>
	<link href="/js/timepicker/css/lyz.calendar.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
</head>
<html>
<div style="padding-left: 30px">
	<table cellspacing="0" cellpadding="0" border="0" style="width: 90%">
        <tbody id="tblGrid">
        <tr  style="height: 30px;">
            <td colspan="2">【整改详情】<hr/></td>
            
        </tr> 
        <tr style="height: 30px;">
            <td>隐患类型：</td>
            <td style="width: 70%">${reformDetail.errorType}</td>
        </tr>
        <tr style="height: 30px;">
            <td>异常情况：</td>
            <td style="width: 70%">${reformDetail.errorInfo}</td>
        </tr>
        <tr style="height: 30px;">
            <td>异常附件：</td>
            <td style="width: 70%">
                <c:forEach items="${reformDetail.errorAttachs}" var="attach" varStatus="a">
                    <c:if test="${attach!=''}">
                        <a href="${attach}" target="_blank"><img title="点击查看大图" width="240" height="180" src="${attach}"></a>
                    </c:if>
                </c:forEach>
            </td>
        </tr>
        <tr style="height: 30px;">
            <td>责任部门：</td>
            <td style="width: 85%">${reformDetail.dutyDeptName}</td>
        </tr>
        <tr  style="height: 30px;">
            <td>发现人：</td>
            <td style="width: 85%">${reformDetail.findUserName}</td>
        </tr>
        <tr  style="height: 30px;">
            <td>发现时间：</td>
            <td style="width: 85%">${reformDetail.findTime}</td>
        </tr>
        <tr  style="height: 30px;">
            <td>隐患区域：</td>
            <td style="width: 85%">${reformDetail.areaName}</td>
        </tr>
        <tr  style="height: 30px;">
            <td>整改期限：</td>
            <td style="width: 85%">${reformDetail.reformTime}</td>
        </tr>
        <tr style="height: 30px;">
            <td><font style="font-weight: bold;">完成情况：</font></td>
            <td style="width: 85%;"><c:if test="${reformDetail.reformStatus==1}"><font style="color: red">待整改..</font></c:if><c:if test="${reformDetail.reformStatus==2}"><font color="orange">整改中</font></c:if><c:if test="${reformDetail.reformStatus==3}"><font color="#ff4500">待复查</font></c:if><c:if test="${reformDetail.reformStatus==4}"><font style="color: green">已完成</font></c:if></td>
        </tr>      
        <tr><td colspan="2"><br/></td></tr>     
        <c:forEach items="${reformDetail.scheduleVos }" var="schedule">
        	 <tr style="height: 100px;">
           		<td style="width: 15%; border-top: solid 0.5px"><div style="height: 80px;width: 100%;"><c:if test="${schedule.reformStatus==1}"><font style="color: red">待整改</font></c:if><c:if test="${schedule.reformStatus==2}"><font style="color: orange">整改中</font></c:if><c:if test='${schedule.reformStatus==3}'><font style='color: orangered'>待复查</font></c:if><c:if test='${schedule.reformStatus==4}'><font style="color: green;">已完成</font></c:if></div></td>
            	<td style="width: 50%; border-top: solid 0.5px">
            		<table>
            			<tr>
            				<td colspan="2">${schedule.reformInfo }</td>
            			</tr>
            			<tr>
                            <td colspan="2" >
                               <c:forEach items="${schedule.reformAttachs}" var="attachs" varStatus="a">
                                  <a href="${attachs}" target="_blank"><img title="点击查看大图" width="160" height="120" src="${attachs}"></a>
                               </c:forEach>
                            </td>
            			</tr>
            			<tr>
            				<td>整改时间:</td>
            				<td >${schedule.executerName}:&nbsp;&nbsp;${schedule.reformTime}</td>
            			</tr>
            		</table>
            	</td>
       		 </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
        <hr style="border:1px double;margin-bottom:0px"/>
    <div id="footer" style="text-align:center;
				color:black;
				font-size:15px;padding-bottom: 0px">上海慧智计算机技术有限公司 技术支持</div>
</html>