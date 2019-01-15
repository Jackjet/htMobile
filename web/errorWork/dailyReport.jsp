<%@page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ include file="/inc/taglibs.jsp" %>
<!doctype html>
<head>
    <link href="/js/timepicker/css/lyz.calendar.css" rel="stylesheet" type="text/css"/>
    <script src="http://www.jq22.com/jquery/1.4.4/jquery.min.js"></script>
    <link href="/js/timepicker/css/lyz.calendar.css" rel="stylesheet" type="text/css"/>
    <script src="/js/timepicker/js/lyz.calendar.min.js" type="text/javascript"></script>
    <script type='text/javascript' src=../js/expertToExcel.js></script>
    <script type="text/javascript">
        $(function () {
            $("#beginDate").calendar();
            $("#endDate").calendar();
            $(".delBtn").click(function () {
                var msg = "您确定要删除吗？";
                if (confirm(msg)==true){
                    $.ajax({
                        type:'post',
                        url:"${pageContext.request.contextPath}/errorWork.htm?method=delete",
                        data:{errorWorkId:this.value},
                        success:function (data) {
                            window.opener.location.reload();//刷新
                        },
                        error:function (data) {
                            alert("后台出错")
                        }
                    })
                }else{
                    return false;
                }

            });
        });
    </script>
</head>
<html>
<form name="form1" action="${pageContext.request.contextPath}/errorWork.htm?method=dailyReport" method="post">
    <%
        Date endDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String end = sdf.format(endDate);
        Date beginDate = new Date((endDate.getTime() - 86400000));
        String begin = sdf.format(beginDate);
    %>
    <table>
        <tr>
            <td style="padding-left: 25px">
                <b>日期:</b>&nbsp;&nbsp;<input id="beginDate" style="height: 20px;width: 100px" name="beginDate"
                                             value="${beginDate}">&nbsp;&nbsp;至&nbsp;&nbsp;<input
                    id="endDate" style="height: 20px;width: 100px" name="endDate" value="${endDate}">
            </td>
            <td style="padding-left: 25px">
                <b>部门:</b>
                <select name="dept" style="height: 25px">
                    <option value="">全部</option>
                    <c:forEach items="${_UnionDepartments}" var="department" varStatus="index">
                        <option
                                <c:if test="${not empty dept && dept.departmentName eq department.departmentName }">selected="selected"</c:if>
                                value=${department.departmentName }>${department.departmentName }</option>
                    </c:forEach>
                </select>
            </td>
            <td style="padding-left: 25px">
                <b>动检类型:</b>
                <select name="category" style="height: 25px">
                    <option value="">全部</option>
                    <c:forEach items="${_Category}" var="map" varStatus="index">
                        <option
                                <c:if test="${not empty category && map.key == category}">selected="selected"</c:if>
                                value=${map.key}>${map.value}</option>
                    </c:forEach>
                </select>
            </td>
            <td style="padding-left: 25px">
                <b>发现人:</b>
                <select name="findUserName" style="height: 25px">
                    <option value="">全部</option>
                    <c:forEach items="${findUsers}" var="user" varStatus="index">
                        <option <c:if test="${not empty findUserName && user.name eq findUserName }">selected="selected"</c:if>  value=${user.name}>${user.name }</option>
                    </c:forEach>
                </select>
            </td>
            <td style="padding-left: 20px">
                <input type="submit" value="查询">
            </td>
            <td style="padding-left: 30px">动检统计:&nbsp;&nbsp;<span id="errorCount"
                                                                  style="color:#F00;font-size: 20px">${errorCount}</span>
            </td>
        </tr>
    </table>
</form>
<table border=0 cellspacing=0 cellpadding=0 width="100%" style="text-align:center">
    <tr style="background:#C3C3C3;height:35px;">
        <th>序号</th>
        <th>检查时间</th>
        <th>检查结果</th>
        <th>动检类型</th>
        <th>责任部门</th>
        <th>执行人</th>
        <th>操作</th>
    </tr>
    <tr><br/></tr>
    <c:forEach items="${details}" var="detail" varStatus="index">
        <tr style="font-size: 12px;height: 50px;border-bottom: solid 2px;">
            <td style="border-bottom: solid 1px #C3C3C3">${index.index+1}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${detail.checkTime}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder"><a
                    href="${pageContext.request.contextPath}/errorWork.htm?method=detail&errorWorkId=${detail.errorWorkId}"
                    target="_blank">${detail.result}</a></td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">
                ${detail.errorType}
            </td>

            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${detail.dutyDept1Name}&nbsp;&nbsp;${detail.dutyDept2Name}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${detail.executerName}</td>

            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder"><a
                    href="${pageContext.request.contextPath}/errorWork.htm?method=edit&errorWorkId=${detail.errorWorkId}" target="_blank"><font
                    color=red>编辑</font></a>
                &nbsp;&nbsp; <button style="background-color: transparent;border: none;" class="delBtn" value="${detail.errorWorkId}"><font color="red">删除</font></button>

            </td>
        </tr>
    </c:forEach>
</table>

</html>