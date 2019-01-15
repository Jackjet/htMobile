<%@page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
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
            <td colspan="2">【违章详情】
                <hr/>
            </td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">违章类型：</td>
            <td style="width: 70%">${peccancyDetail.peccancyTypeName}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">违章描述：</td>
            <td style="width: 70%">${peccancyDetail.content}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">责任部门：</td>
            <td style="width: 70%">${peccancyDetail.dutyDeptName}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">责任人：</td>
            <td style="width: 70%">${peccancyDetail.dutyPersonName}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">备注：</td>
            <td style="width: 70%">${peccancyDetail.remark}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">发现人：</td>
            <td style="width: 70%">${peccancyDetail.offenderName}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">发现时间：</td>
            <td style="width: 70%">${peccancyDetail.findTime}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">违章附件：</td>
            <td style="width: 70%">
                <c:forEach items="${peccancyDetail.ruleAttaches}" var="attach" varStatus="a">
                    <c:if test="${attach!=''}">
                        <a href="${attach}" target="_blank"><img title="点击查看大图" width="240" height="180"
                                                                 src="${attach}"></a>
                    </c:if>
                </c:forEach>
            </td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">违章条例：</td>
            <td style="width: 70%">${peccancyDetail.peccancyRules}</td>
        </tr>
        <tr style="height: 30px;">
            <td style="width:60px">整改附件：</td>
            <td style="width: 70%">
                <c:forEach items="${peccancyDetail.reformAttaches}" var="attach" varStatus="a">
                    <c:if test="${attach!=''}">
                        <a href="${attach}" target="_blank"><img title="点击查看大图" width="40" height="30" src="${attach}"></a>
                    </c:if>
                </c:forEach>
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