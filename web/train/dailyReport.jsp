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
                if (confirm(msg) == true) {
                    $.ajax({
                        type: 'post',
                        url: "${pageContext.request.contextPath}/train.htm?method=delete",
                        data: {trainId: this.value},
                        success: function (data) {
                            alert("删除成功")
                            location.reload();//刷新
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

        function del(reformId) {
            window.open("${pageContext.request.contextPath}/DailyReport.htm?method=delete&reformId=" + reformId, "_self")
        }
    </script>
</head>
<html>
<form name="form1" action="${pageContext.request.contextPath}/train.htm?method=dailyReport" method="post">
    <table>
        <tr>
            <td style="padding-left: 25px">
                <b>日期:</b>&nbsp;&nbsp;<input id="beginDate" style="height: 20px;width: 100px" name="beginDate"
                                             value="${beginDate}">&nbsp;&nbsp;至&nbsp;&nbsp;<input
                    id="endDate" style="height: 20px;width: 100px" name="endDate" value="${endDate}">
            </td>
            <td style="padding-left: 25px">
                <b>所属公司:</b>
                <select name="dept" style="height: 25px">
                    <option value="">全部</option>
                    <option
                            <c:if test="${dept!=null&&dept eq '技劳堆场'}">selected="selected"</c:if> value="1">--技劳堆场--
                    </option>
                    <option
                            <c:if test="${dept!=null&&dept eq '东雷公司'}">selected="selected"</c:if> value="2">--东雷公司--
                    </option>
                    <option
                            <c:if test="${dept!=null&&dept eq '整车物流'}">selected="selected"</c:if> value="3">--整车物流--
                    </option>
                    <option
                            <c:if test="${dept!=null&&dept eq '驻外网点'}">selected="selected"</c:if> value="4">--驻外网点--
                    </option>
                    <option
                            <c:if test="${dept!=null&&dept eq '驻港单位'}">selected="selected"</c:if> value="5">--驻港单位--
                    </option>
                    <option
                            <c:if test="${dept!=null&&dept eq '其他'}">selected="selected"</c:if> value="5">--其他--
                    </option>
                </select>
            </td>
            <td style="padding-left: 25px">
                <b>培训类型:</b>
                <select name="category" style="height: 25px">
                    <option value="">全部</option>
                    <c:forEach items="${_Category}" var="categorys" varStatus="index">
                        <option
                                <c:if test="${not empty category && categorys.categoryName eq category.categoryName }">selected="selected"</c:if>
                                value=${categorys.categoryId}>${categorys.categoryName }</option>
                    </c:forEach>
                </select>
            </td>
            <td style="padding-left: 20px">
                <input type="submit" value="查询">
            </td>
            <td style="padding-left: 30px">培训统计:&nbsp;&nbsp;<span id="errorCount"
                                                                  style="color:#F00;font-size: 20px">${trainCount}次</span>
            </td>
        </tr>
    </table>
</form>
<table border=0 cellspacing=0 cellpadding=0 width="98%" style="text-align:center;table-layout:fixed">
    <tr style="background:#C3C3C3;height:35px;">
        <th>序号</th>
        <th>培训项目</th>
        <th>培训部门</th>
        <th>培训时间</th>
        <th>培训师</th>
        <th>出勤人数</th>
        <th>操作</th>
    </tr>
    <tr><br/></tr>
    <c:forEach items="${trainVos}" var="train" varStatus="index">
        <tr style="font-size: 12px;height: 50px;border-bottom: solid 2px;">
            <td style="border-bottom: solid 1px #C3C3C3">${index.index+1}</td>
            <td style="border-bottom: solid 1px #C3C3C3;overflow:hidden; white-space:nowrap; " class="hasBottomBoder"><a
                    href="${pageContext.request.contextPath}/train.htm?method=detail&trainId=${train.trainId}"
                    target="_blank"><font color=red>${train.title}</font></a></td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${train.departmentName}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${train.trainTime}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${train.trainerName}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder">${train.personCount}</td>
            <td style="border-bottom: solid 1px #C3C3C3" class="hasBottomBoder"><a
                    href="${pageContext.request.contextPath}/train.htm?method=edit&trainId=${train.trainId}"
                    target="_blank"><font color=red>编辑</font></a>
                &nbsp;&nbsp;
                <button style="background-color: transparent;border: none;" class="delBtn" value="${train.trainId}">
                    <font color="red">删除</font></button>
            </td>
        </tr>
    </c:forEach>
</table>

</html>