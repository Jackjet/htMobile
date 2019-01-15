<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglibs.jsp"%>
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
            $(".time").datetimepicker({format: 'YYYY-MM-DD HH:mm:ss', locale: moment.locale('zh-cn')});
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
            <form class="form-horizontal" role="form" action="/reform.htm?method=addReform" method="post"
                  enctype="multipart/form-data">
                <div class="form-group">
                    <label class="col-sm-2 control-label">隐患标题:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="errorTitle"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">隐患区域:</label>
                    <div class="col-sm-4">
                        <select name="areaId" class="selectpicker show-tick form-control"  data-width="98%" data-first-option="false" title='责任部门' required data-live-search="true">
                            <option value="">--请选择--</option>
                            <c:forEach var="area" items="${_allArea}">
                                <option value="${area.areaId}">${area.areaName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">隐患类型:</label>
                    <div class="col-sm-4">
                        <select name="errorTypeId" class="selectpicker show-tick form-control"  data-width="98%" data-first-option="false" required data-live-search="true">
                            <option value="">--请选择--</option>
                            <c:forEach var="category" items="${_allCategory}">
                                <option value="${category.categoryId}">${category.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">责任部门:</label>
                    <div class="col-sm-4">
                        <select name="dutyDeptName" class="selectpicker show-tick form-control"  data-width="98%" data-first-option="false" title='责任部门' required data-live-search="true">
                            <option value="">--请选择--</option>
                            <c:forEach var="dept" items="${_Departments}">
                                <option value="${dept.departmentName}">${dept.departmentName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">发现时间:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control time" name="findTime"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">整改时间:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control time" name="reformTime"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">完成时间:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control time" name="finishTime"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">隐患内容:</label>
                    <div class="col-sm-8">
                        <textarea rows="5" class="form-control " name="errorInfo"></textarea>
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
                    <label class="col-sm-2 control-label">发现人:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="findUserName" value="${_GLOBAL_USER.name}"/>
                        <input type="hidden" class="form-control" name="findUserId" value="${_GLOBAL_USER.userId}"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
