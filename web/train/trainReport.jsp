<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/inc/taglibs.jsp" %>
<html>
<head>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/moment.js/2.18.1/moment-with-locales.min.js"></script>
    <link href="/js/dateTimePicker/css/bootstrap-datetimepicker.css" rel="stylesheet">
    <script src="/js/dateTimePicker/js/bootstrap-datetimepicker.js"></script>
    <script>
        $(function () {
            $("#year").datetimepicker({format: 'YYYY', locale: moment.locale('zh-cn')});
        });

        function changeYear(year) {
            window.location.href = "/train.htm?method=trainReport&year=" + year;
        }
    </script>
    <style>

    </style>
</head>
<body>
<div>
    <h3 align="center">
        <select id="year" class="selectpicker show-tick" data-first-option="false" required onchange="changeYear(this.value);">
            <option <c:if test="${year eq '2017'}">selected="selected"</c:if> value="2017">2017</option>
            <option <c:if test="${year eq '2018'}">selected="selected"</c:if> value="2018">2018</option>
            <option <c:if test="${year eq '2019'}">selected="selected"</c:if> value="2019">2019</option>
            <option <c:if test="${year eq '2020'}">selected="selected"</c:if> value="2020">2020</option>
            <option <c:if test="${year eq '2021'}">selected="selected"</c:if> value="2021">2021</option>
            <option <c:if test="${year eq '2022'}">selected="selected"</c:if> value="2022">2022</option>
            <option <c:if test="${year eq '2023'}">selected="selected"</c:if> value="2023">2023</option>
            <option <c:if test="${year eq '2024'}">selected="selected"</c:if> value="2024">2024</option>
            <option <c:if test="${year eq '2025'}">selected="selected"</c:if> value="2025">2025</option>
            <option <c:if test="${year eq '2026'}">selected="selected"</c:if> value="2026">2026</option>
            <option <c:if test="${year eq '2027'}">selected="selected"</c:if> value="2027">2027</option>
        </select>年度综合培训人员出勤情况统计表</h3>
</div>
<div style="margin: 20px">
    <table class="table table-striped">
        <tr style="background:#C3C3C3;height:35px;">
            <th style="text-align: center">月份</th>
            <th style="text-align: center">堆场技劳</th>
            <th style="text-align: center">东雷公司</th>
            <th style="text-align: center">整车物流</th>
            <th style="text-align: center">驻外网点</th>
            <th style="text-align: center">驻外单位</th>
            <th style="text-align: center">其他</th>
            <th style="text-align: center">总人数</th>
        </tr>
        <c:forEach items="${trainList}" var="list" varStatus="index">
            <tr>
                <td style="text-align: center">${list.month}</td>
                <td style="text-align: center">${list.dcCount}</td>
                <td style="text-align: center">${list.dlCount}</td>
                <td style="text-align: center">${list.wlCount}</td>
                <td style="text-align: center">${list.netCount}</td>
                <td style="text-align: center">${list.unitCount}</td>
                <td style="text-align: center">${list.otherCount}</td>
                <td style="text-align: center">${list.allCount}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>