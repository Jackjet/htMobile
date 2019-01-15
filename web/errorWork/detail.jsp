<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/inc/taglibs.jsp" %>
<!doctype html>
<head>
    <link href="/js/timepicker/css/lyz.calendar.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
</head>
<html>
<div style="padding-left: 30px">
    <table cellspacing="0" cellpadding="0" border="0" style="width: 90%">
        <tbody id="tblGrid">
        <tr style="height: 30px;">
            <td colspan="2">【动检详情】
                <hr/>
            </td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">动检标题：</td>
            <td style="width: 70%">${errorWork.errorTitle}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">动检类型：</td>
            <td style="width: 70%">${errorWork.errorType}</td>
        </tr>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">动检区域：</td>
            <td style="width: 70%">${errorWork.areaName}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">异常情况：</td>
            <td style="width: 70%">${errorWork.errorInfo}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">异常附件：</td>
            <td style="width: 70%">
                <c:forEach items="${errorWork.errorAttachs}" var="attach" varStatus="a">
                    <a href="${attach}" target="_blank"><img title="点击查看大图" width="240" height="180"
                                                             src="${attach}"></a>
                </c:forEach>
            </td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">责任部门：</td>
            <td style="width: 70%">${errorWork.dutyDept1Name}&nbsp;&nbsp;${errorWork.dutyDept2Name}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">执行人：</td>
            <td style="width: 70%">${errorWork.executerName}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">执行时间：</td>
            <td style="width: 70%">${errorWork.checkTime}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">备注：</td>
            <td style="width: 70%">${errorWork.memo}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">备注附件：</td>
            <td style="width: 70%">
                <c:if test="${errorWork.memoAttachs!=null}">
                    <c:forEach items="${errorWork.memoAttachs}" var="attach" varStatus="a">
                        <a href="${attach}" target="_blank"><img title="点击查看大图" width="240" height="180"
                                                                 src="${attach}"></a>
                    </c:forEach>
                </c:if>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<hr style="border:1px double;margin-bottom:0px"/>
<div id="footer" style="text-align:center;
				color:black;
				font-size:15px;padding-bottom: 0px">上海慧智计算机技术有限公司 技术支持
</div>
</html>