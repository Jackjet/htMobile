<%@page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ include file="/inc/taglibs.jsp" %>
<!doctype html>
<head>
    <link href="/js/timepicker/css/lyz.calendar.css" rel="stylesheet" type="text/css"/>
    <script src="http://www.jq22.com/jquery/1.4.4/jquery.min.js"></script>
    <script src="/js/timepicker/js/lyz.calendar.min.js" type="text/javascript"></script>
    <script type='text/javascript' src=../js/expertToExcel.js></script>
    <script type="text/javascript">
        $(function () {
            $("#beginDate").calendar();
            $("#endDate").calendar();
            $(".delBtn").click(function () {
                var msg = "您确定要删除吗？";
                if (confirm(msg) == true) {
                    $.ajax({
                        type: 'post',
                        url: "${pageContext.request.contextPath}/peccancy.htm?method=delete",
                        data: {peccancyId: this.value},
                        success: function (data) {
                            window.opener.location.reload();//刷新
                        },
                        error: function (data) {
                            alert("后台出错")
                        }
                    })
                } else {
                    return false;
                }
            });
        });
    </script>

</head>
<html>
<form name="form1" action="/peccancy.htm?method=listPeccancy" method="post">
    <table>
        <tr>
            <%
                Date endDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String end = sdf.format(endDate);
                Date beginDate = new Date((endDate.getTime() - 86400000));
                String begin = sdf.format(beginDate);
            %>
            <td colspan="2" style="padding-left: 25px">
                <b>日期:</b>&nbsp;<input id="beginDate" style="height: 20px;width: 100px" name="beginDate"
                                       value="${beginDate}">&nbsp;&nbsp;至&nbsp;&nbsp;<input id="endDate"
                                                                                            style="height: 20px;width: 100px"
                                                                                            name="endDate"
                                                                                            value="${endDate}">
            </td>
            <td style="padding-left: 25px">
                <b>部门:</b>
                <select name="dept" style="height: 25px">
                    <option value="">全部</option>
                    <c:forEach items="${_Departments}" var="department" varStatus="index">
                        <option
                                <c:if test="${not empty dept && dept.departmentName eq department.departmentName }">selected="selected"</c:if>
                                value=${department.departmentName }>${department.departmentName }</option>
                    </c:forEach>
                </select>
            </td>
            <td style="padding-left: 25px">
                <b>隐患类型:</b>
                <select name="category" style="height: 25px">
                    <option value="">全部</option>
                    <c:forEach items="${_Category}" var="categorys" varStatus="index">
                        <option
                                <c:if test="${not empty category && categorys.categoryName eq category.categoryName }">selected="selected"</c:if>
                                value=${categorys.categoryId}>${categorys.categoryName }</option>
                    </c:forEach>
                </select>
            </td>
            <td style="padding-left: 25px">
                <b>发现人:</b>
                <select name="findUserName" style="height: 25px">
                    <option value="">全部</option>
                    <c:forEach items="${findUsers}" var="user" varStatus="index">
                        <option
                                <c:if test="${not empty findUserName && user.name eq findUserName }">selected="selected"</c:if>
                                value=${user.name}>${user.name }</option>
                    </c:forEach>
                </select>
            </td>
            <td style="padding-left: 25px">
                <input type="submit" value="查询">
            </td>
            <td style="padding-left: 30px"></td>
            <td style="padding-left: 150px"><a href="javascript:;"
                                               onclick="javascript:toExcel('peccancyList','隐患异常情况汇总');">导出Excel</a></td>
        </tr>
    </table>
</form>
<table id="peccancyList" border=0 cellspacing=0 cellpadding=0 width="98%"
       style="text-align:center;margin-top:15px;table-layout:fixed">
    <tr style="background:#eeeeee;height:35px;">
        <th style="border: solid 0.5px #C3C3C3;width: 50px">序号</th>
        <th style="border: solid 0.5px #C3C3C3;width: 200px">违章时间</th>
        <th style="border: solid 0.5px #C3C3C3">违章类型</th>
        <th style="border: solid 0.5px #C3C3C3;width: 400px">违章描述</th>
        <th style="border: solid 0.5px #C3C3C3">责任部门</th>
        <th style="border: solid 0.5px #C3C3C3">责任人</th>
        <th style="border: solid 0.5px #C3C3C3">发现人</th>
        <th style="border: solid 0.5px #C3C3C3;width: 100px">备注</th>
        <th style="border: solid 0.5px #C3C3C3">操作</th>
    </tr>
    <c:forEach items="${details}" var="detail" varStatus="index">
        <tr style="font-size: 12px; height:40px;
            <c:if test="${!detail.morningReport}">background-color:#00b3ee;</c:if>">
            <td style="border: solid 0.5px #C3C3C3">${index.index+1}</td>
            <td style="border: solid 0.5px #C3C3C3" class="hasBottomBoder">${detail.markTime }</td>
            <td style="border: solid 0.5px #C3C3C3" class="hasBottomBoder">${detail.category.categoryName }</td>
            <td style="border-bottom: solid 1px #C3C3C3;overflow:hidden; white-space:nowrap;" class="hasBottomBoder"><a
                    href="${pageContext.request.contextPath}/peccancy.htm?method=detail&peccancyId=${detail.peccancyId}"
                    target="_blank"><font color=red>${detail.content}</font></a></td>
            <td style="border: solid 0.5px #C3C3C3" class="hasBottomBoder">${detail.department.departmentName }</td>
            <td style="border: solid 0.5px #C3C3C3" class="hasBottomBoder">${detail.dutyPersonName}</td>
            <td style="border: solid 0.5px #C3C3C3" class="hasBottomBoder">${detail.offender.name}</td>
            <td style="border: solid 0.5px #C3C3C3;overflow:hidden; white-space:nowrap;"
                class="hasBottomBoder">${detail.remark}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder"><a
                    href="${pageContext.request.contextPath}/peccancy.htm?method=edit&peccancyId=${detail.peccancyId}"
                    target="_blank"><font color=red>编辑</font></a>
                <c:if test="${_GLOBAL_USER.userType==99}">
                &nbsp;&nbsp;
                <button style="background-color: transparent;border: none;" class="delBtn" value="${detail.peccancyId}">
                    <font color="red">删除</font></button>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
<div style="text-align: right; width: 90%"><font style="font-size: 25px">总计:</font><span id="errorCount"
                                                                                         style="color:#F00;font-size: 30px">${errorCount}</span>
</div>
<%--<div style="text-align: right;margin-right: 30px;margin-top: 20px">--%>
    <%--<span style="background-color: greenyellow;">晨会</span>--%>
    <%--<span style="background-color:coral;margin-left: 20px">隐藏</span>--%>
<%--</div>--%>
</html>