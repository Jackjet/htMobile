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
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css"
          rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js"></script>
    <script>
        $(document).ready(function () {
            // 验证表单的规则
            $('#defaultForm').bootstrapValidator({
                fields: {
                    title: {
                        validators: {
                            notEmpty: {
                                message: '项目名称不能为空'
                            }
                        }
                    },
                    personCount: {
                        validators: {
                            notEmpty: {
                                message: '出勤人数为整数'
                            },
                        }
                    },
                    departmentName: {
                        validators: {
                            notEmpty: {
                                message: '培训部门不能为空'
                            },
                        }
                    },
                    trainerName: {
                        validators: {
                            notEmpty: {
                                message: '培训师不能为空'
                            },
                        }
                    },
                }

            });
            // 重置表单验证
            $('#resetBtn').click(function () {
                $('#defaultForm').data('bootstrapValidator').resetForm(true);
            });
        });

        $(function () {
            $("#trainTime").datetimepicker({format: 'YYYY-MM-DD HH:mm:ss', locale: moment.locale('zh-cn')});
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
            <form id="defaultForm" class="form-horizontal" role="form" action="/train.htm?method=addTrain"
                  method="post"
                  enctype="multipart/form-data">
                <div class="form-group">
                    <label class="col-sm-2 control-label" style="letter-spacing: 10px">培训项目:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="title" value="${trainVo.title}"/>
                        <input type="hidden" name="trainId" value="${trainVo.trainId}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" style="letter-spacing: 10px">培训类型:</label>
                    <div class="col-sm-2">
                        <select name="categoryId" class="selectpicker show-tick form-control" data-width="98%"
                                data-first-option="false" required data-live-search="true">
                            <option value="">--请选择--</option>
                            <c:forEach var="category" items="${_TrainCategories}">
                                <option <c:if test="${not empty trainVo.categoryId && trainVo.categoryId == category.categoryId}">selected="selected"</c:if>
                                        value=${category.categoryId}>${category.categoryName }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" style="letter-spacing: 10px">培训部门:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" name="departmentName" value="${trainVo.departmentName}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" style="letter-spacing: 10px">所属单位:</label>
                    <div class="col-sm-2">
                        <select name="departmentNum" class="selectpicker show-tick form-control" data-width="98%"
                                data-first-option="false" required data-live-search="true">
                            <option value="">--请选择--</option>
                            <option <c:if test="${trainVo.departmentNum==1}">selected="selected"</c:if> value="1">--技劳堆场--</option>
                            <option <c:if test="${trainVo.departmentNum==2}">selected="selected"</c:if> value="2">--东雷公司--</option>
                            <option <c:if test="${trainVo.departmentNum==3}">selected="selected"</c:if> value="3">--整车物流--</option>
                            <option <c:if test="${trainVo.departmentNum==4}">selected="selected"</c:if> value="4">--驻外网点--</option>
                            <option <c:if test="${trainVo.departmentNum==5}">selected="selected"</c:if> value="5">--驻港单位--</option>
                            <option <c:if test="${trainVo.departmentNum==6}">selected="selected"</c:if> value="6">--其他--</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" style="letter-spacing: 10px">出勤人次:</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" name="personCount" value="${trainVo.personCount}"
                               onkeyup="value=value.match(/^-?[0-9]\d*$/)||value.match(/-?/)"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" style="letter-spacing: 10px">培训师:</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" name="trainerName" value="${trainVo.trainerName}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" style="letter-spacing: 10px">培训时间:</label>
                    <div class="col-sm-4">
                        <input type="text" id="trainTime"  class="form-control" name="trainTime" value="${trainVo.trainTime}"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-4 col-sm-offset-2">
                        <c:if test="${trainVo.attaches != ''}">
                            <c:forEach items="${trainVo.attaches}" var="attach" varStatus="a">
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
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">提交</button>
                        <button type="button" class="btn btn-info" id="resetBtn">重置</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
