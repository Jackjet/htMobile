<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/inc/taglibs.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/moment.js/2.18.1/moment-with-locales.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
    <script>
        function addLine() {
            var html = "<tr>\n" +
                "                                <td style=\"width:30%;vertical-align: center\"><input style=\"height: 50px\" class=\"form-control\" name=\"content\"></td>\n" +
                "                                <td style=\"width:70%;vertical-align: center\"><textarea style=\"margin-left: 40px;margin-top: 10px\" class=\"form-control\" rows=\"5\" name=\"problem\"></textarea></td>\n" +
                "                            </tr>";
            $("#myTable").append(html);
        }

        function addFile(isTable) {
            var index = isTable.rows.length;
            var nextRow = isTable.insertRow(index);
            var isText = nextRow.insertCell(0);
            var txtArea = nextRow.insertCell(1);
            index++;
            index = index.toString();
            var nameStr = "maintain" + index;
            var txtStr = index + ".";
            isText.innerHTML = txtStr;
            txtArea.innerHTML = "<input type=file class=form-control name=" + nameStr + " >";
        }

        function showDetail(month) {
            window.open("${pageContext.request.contextPath}/monthReport.htm?method=editCheckFocus&month=" + month, "_self");
        }

        $(function () {
            $("#recordTime").datetimepicker({format: 'YYYY-MM', locale: moment.locale('zh-cn')});
        });
    </script>
</head>
<body>
<div class="container">
    <div class="row clearfix" style="margin-top: 80px">
        <div class="col-md-12 column">
            <form class="form-horizontal" role="form" action="/monthReport.htm?method=safeCheckFocus" method="post"
                  enctype="multipart/form-data">
                <div class="form-group">
                    <label class="col-sm-2 control-label" style="letter-spacing: 10px;font-size: large">时间:</label>
                    <div class="col-sm-4">
                        <input type="text" id="recordTime" class="form-control" name="time" value="${time}"
                               onblur="showDetail(this.value)"/>
                        <input type="hidden" class="form-control" name="focusId" value="${checkFocus.focusId}"/>
                    </div>
                </div>
                <div class="form-group">
                    <table id="myTable" class="col-sm-offset-1">
                        <thead>
                        <tr>
                            <th style="width:30%;text-align:center;vertical-align: center">项目</th>
                            <th style="width:70%;text-align:center;vertical-align: center">检查内容及要求</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${checkFocus.records}" var="record">
                            <tr>
                                <td style="width:30%;vertical-align: center"><input style="height: 50px"
                                                                                    class="form-control" name="content"
                                                                                    value="${record.key}"></td>
                                <td style="width:70%;vertical-align: center"><textarea
                                        style="margin-left: 40px;margin-top: 10px" rows="5" class="form-control"
                                        name="problem">${record.value}</textarea>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${checkFocus==null}">
                            <tr>
                                <td style="width:30%;vertical-align: center"><input style="height: 50px"
                                                                                    class="form-control" name="content">
                                </td>
                                <td style="width:70%;vertical-align: center"><textarea
                                        style="margin-left: 40px;margin-top: 10px" rows="5" class="form-control"
                                        name="problem"></textarea></td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                    <div class="col-sm-offset-7 col-sm-1">
                        <input style="margin-top: 10px" type="button" value="添加一行" class="btn btn-success"
                               onclick="addLine();">
                    </div>
                    <div class="form-group">
                        <c:if test="${checkFocus.attaches!=null}">
                            <div style="margin-top: 30px" class="col-sm-offset-1 col-sm-12">
                                <c:forEach items="${checkFocus.attaches}" var="attach" varStatus="a">
                                    <a href="${attach}" target="_blank"><img title="点击查看大图" width="180" height="150"
                                                                             src="${attach}"></a>
                                    <input type="checkbox" name="image" value="${attach}">点击删除
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                    <div class="col-sm-offset-1 col-sm-8">
                        <table id="fileTable">

                        </table>
                        <div>
                            <br/><input type="button" value="添加附件" class="btn btn-success"
                                        onclick="addFile(fileTable);">
                        </div>
                    </div>


                </div>
                <div class="form-group">
                    <div class="col-sm-offset-8 col-sm-2">
                        <button type="submit" class="btn btn-default">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
