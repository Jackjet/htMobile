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
            $("#reportTime").datetimepicker({format: 'YYYY-MM-DD HH:mm:ss', locale: moment.locale('zh-cn')});
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
            txtArea.innerHTML = "<input type=file class=form-control name=" + nameStr + " ><br/>";
        }
    </script>
</head>
<body>
<div class="container">
    <div class="col-md-12 column" style="margin-top: 100px">
        <form class="form-horizontal" role="form"  action="/morning.htm?method=addUnion" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label class="col-sm-2 control-label">汇报人:</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" value="${_GLOBAL_USER.name}" name="reporterName"/>
                    <input type="hidden" class="form-control" value="${_GLOBAL_USER.userId}" name="reporterId"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">检查时间:</label>
                <div class="col-sm-2">
                    <input id="reportTime" class="form-control" name="reportTime" type="text"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">汇报类型:</label>
                <div class="col-sm-2">
                    <select id="category" name="category" class="selectpicker show-tick form-control"  data-width="98%" data-first-option="false" required data-live-search="true">
                        <option value="">--请选择--</option>
                        <option value="生产事故">生产事故</option>
                        <option value="联合检查">联合检查</option>
                    </select>
                </div>
            </div><div class="form-group">
                <label class="col-sm-2 control-label">汇报附件:</label>
                <div class="col-sm-4">
                    <input class="form-control" type="file" name="attach">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-4">
                    <table id="fileTable">

                    </table>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-4 col-sm-offset-2">
                    <input type="button" value="添加附件" class="btn btn-success"
                                onclick="addFile(fileTable);">
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
</body>
</html>
