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
                var msg = "确定删除吗？";
                if (confirm(msg)==true){
                    $.ajax({
                        type:'post',
                        url:"${pageContext.request.contextPath}/DailyReport.htm?method=delete",
                        data:{reformId:this.value},
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

        function del(reformId) {
            window.open("${pageContext.request.contextPath}/DailyReport.htm?method=delete&reformId="+reformId,"_self")
        }
    </script>
</head>
<html>
<form name="form1" action="${pageContext.request.contextPath}/DailyReport.htm?method=dailyReport" method="post">
    <table>
        <tr>
            <td style="padding-left: 25px">
                <b>日期:</b>&nbsp;&nbsp;<input id="beginDate" style="height: 20px;width: 100px" name="beginDate" value="${beginDate}">&nbsp;&nbsp;至&nbsp;&nbsp;<input
                    id="endDate" style="height: 20px;width: 100px" name="endDate" value="${endDate}">
            </td>
            <td style="padding-left: 25px">
                <b>部门:</b>
                <select name="dept" style="height: 25px">
                    <option value="">全部</option>
                    <c:forEach items="${_Departments}" var="department" varStatus="index">
                        <option <c:if test="${not empty dept && dept.departmentName eq department.departmentName }">selected="selected"</c:if> value=${department.departmentName }>${department.departmentName }</option>
                    </c:forEach>
                </select>
            </td>
            <td style="padding-left: 25px">
                <b>隐患类型:</b>
                <select name="category" style="height: 25px">
                    <option value="">全部</option>
                    <c:forEach items="${_Category}" var="categorys" varStatus="index">
                        <option <c:if test="${not empty category && categorys.categoryName eq category.categoryName }">selected="selected"</c:if>  value=${categorys.categoryId}>${categorys.categoryName }</option>
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
            <td style="padding-left: 30px">异常统计:&nbsp;&nbsp;<span id="errorCount"
                                                                   style="color:#F00;font-size: 20px">${errorCount}</span>
            </td>
        </tr>
    </table>
</form>
<table  border=0 cellspacing=0 cellpadding=0 width="98%" style="text-align:center;table-layout:fixed">
    <tr style="background:#C3C3C3;height:35px;">
        <th>序号</th>
        <th>检查时间</th>
        <th>隐患类型</th>
        <th>异常情况</th>
        <th>责任部门</th>
        <th>责任人</th>
        <th>发现人</th>
        <th>完成情况</th>
        <th>操作</th>
    </tr>
    <tr><br/></tr>
    <c:forEach items="${details}" var="detail" varStatus="index">

        <tr style="font-size: 12px; height: 50px;border-bottom: solid 2px;<c:if test="${!detail.morningReport}">background-color:#00b3ee;</c:if>">
            <td style="border-bottom: solid 1px #C3C3C3">${index.index+1}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${detail.findTime}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${detail.errorType}</td>
            <td style="border-bottom: solid 1px #C3C3C3;overflow:hidden; white-space:nowrap; " class="hasBottomBoder"><a href="${pageContext.request.contextPath}/DailyReport.htm?method=detail&reformId=${detail.reformId}" target="_blank"><font color=red>${detail.errorInfo}</font></a></td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${detail.dutyDeptName}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${detail.reformUserName}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${detail.findUserName}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder;"><c:if test="${detail.reformStatus==1 }"><font
                    style="color: red">待整改...</font></c:if><c:if test="${detail.reformStatus==2 }"><font
                    style="color: #FF8435">整改中...</font></c:if><c:if test="${detail.reformStatus==3 }"><font
                    style="color:blue">待复查...</font></c:if><c:if test="${detail.reformStatus==4 }"><font
                    style="color:green">已完成</font></c:if></td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder"><a
                    href="${pageContext.request.contextPath}/DailyReport.htm?method=edit&reformId=${detail.reformId}"
                    target="_blank"><font color=red>编辑</font></a>
                &nbsp;&nbsp; <button style="background-color: transparent;border: none;" class="delBtn" value="${detail.reformId}"><font color="red">删除</font></button>
            </td>
        </tr>
    </c:forEach>
</table>
<%--<div style="text-align: right;margin-right: 30px;margin-top: 20px">--%>
    <%--<span style="background-color: greenyellow;">晨会</span>--%>
    <%--<span style="background-color:coral;margin-left: 20px">隐藏</span>--%>
<%--</div>--%>

</html>