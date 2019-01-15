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
            <td colspan="2">【培训详情】
                <hr/>
            </td>

        </tr>
        <tr style="height: 30px;">
            <td>培训项目：</td>
            <td style="width: 70%">${trainVo.title}</td>
        </tr>
        <tr style="height: 30px;">
            <td>培训部门：</td>
            <td style="width: 70%">${trainVo.departmentName}</td>
        </tr>
        <tr style="height: 30px;">
            <td>所属公司：</td>
            <td style="width: 85%">${trainVo.deptName}</td>
        </tr>
        <tr style="height: 30px;">
            <td>培训时间：</td>
            <td style="width: 70%">${trainVo.trainTime}</td>
        </tr>
        <tr style="height: 30px;">
            <td>培训师：</td>
            <td style="width: 85%">${trainVo.trainerName}</td>
        </tr>
        <tr style="height: 30px;">
            <td>出勤人数：</td>
            <td style="width: 85%">${trainVo.personCount}</td>
        </tr>
        <tr style="height: 30px;">
            <td>记录时间：</td>
            <td style="width: 85%">${trainVo.recordTime}</td>
        </tr>
        <tr style="height: 30px;">
            <td>记录人：</td>
            <td style="width: 85%">${trainVo.recorderName}</td>
        </tr>
        <tr style="height: 30px;">
            <td>培训附件：</td>
            <td style="width: 70%">
                <c:forEach items="${trainVo.attaches}" var="attach" varStatus="a">
                    <c:if test="${attach!=''}">
                        <a href="${attach}" target="_blank"><img title="点击查看大图" width="240" height="180"
                                                                 src="${attach}"></a>
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