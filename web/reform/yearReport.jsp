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
            window.location.href = "/reform.htm?method=yearReport&year=" + year;
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
        </select>年度隐患及违章明细统计表</h3>
</div>
<div style="margin: 20px">
    <table class="table table-striped">
        <tr style="background:#C3C3C3;height: 35px">
            <th style="text-align: center;vertical-align: center">类</th>
            <th style="text-align: center">
                <table  class="table table-striped">
                        <tr style="background:#C3C3C3;border: none">
                            <th style="text-align: center;width: 15%">项</th>
                            <th style="text-align: center">1月</th>
                            <th style="text-align: center">2月</th>
                            <th style="text-align: center">3月</th>
                            <th style="text-align: center">4月</th>
                            <th style="text-align: center">5月</th>
                            <th style="text-align: center">6月</th>
                            <th style="text-align: center">7月</th>
                            <th style="text-align: center">8月</th>
                            <th style="text-align: center">9月</th>
                            <th style="text-align: center">10月</th>
                            <th style="text-align: center">11月</th>
                            <th style="text-align: center">12月</th>
                            <th style="text-align: center">汇总</th>
                        </tr>
                </table>
            </th>
        </tr>
        <c:forEach items="${yearList}" var="list" varStatus="index">
            <tr>
                <td style="text-align: center">${list.parentCategory}</td>
                <td style="text-align: center">
                    <table class="table table-striped">
                        <c:forEach items="${list.childItems}" var="child">
                            <tr>
                                <td style="text-align: center;width: 15%">${child.categoryName}</td>
                                <c:forEach items="${child.counts}" var="count">
                                    <td style="text-align: center">
                                        ${count}
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>