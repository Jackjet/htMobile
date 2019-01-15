<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        $(function () {
            $("#handTime").datetimepicker({format: 'YYYY-MM-DD HH:mm:ss', locale: moment.locale('zh-cn')});
        });

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
    </script>
</head>
<body>
<div class="container">
    <div class="row clearfix" style="margin-top: 80px">
        <div class="col-md-12 column">
            <form class="form-horizontal" role="form" action="/handOver.htm?method=saveChange" method="post"
                  enctype="multipart/form-data">
                <div class="form-group">
                    <label class="col-sm-2 control-label">标题:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="title" value="${handDetail.title}"/>
                        <input type="hidden" class="form-control" name="handId" value="${handDetail.handId}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">交班内容:</label>
                    <div class="col-sm-8">
                        <textarea rows="5" class="form-control" name="content">${handDetail.content}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-4 col-sm-offset-2">
                        <c:if test="${handDetail.attaches != ''}">
                            <c:forEach items="${handDetail.attaches}" var="attach" varStatus="a">
                                <c:if test="${attach!=''}">
                                    <a href="${attach}" target="_blank"><img style="padding-top: 10px" title="点击查看大图"
                                                                             width="180"
                                                                             height="120"
                                                                             src="${attach}"></a>
                                    <input type="checkbox" name="image" value="${attach}">选中删除
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-3 col-sm-offset-2">
                        <table id="fileTable">

                        </table>
                        <div>
                            <br/><input type="button" value="添加附件" class="btn btn-success"
                                        onclick="addFile(fileTable);">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">提交人:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="handerName" value="${handDetail.handerName}"/>
                        <input type="hidden" class="form-control" name="handerId" value="${handDetail.handerId}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">提交时间:</label>
                    <div class="col-sm-4">
                        <input type="text" id="handTime" class="form-control" name="handTime"
                               value="${handDetail.handTime}"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">保存</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
