<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/jquery.bootstrapvalidator/0.5.3/css/bootstrapValidator.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery.bootstrapvalidator/0.5.3/js/bootstrapValidator.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            // 验证表单的规则
            $('#defaultForm').bootstrapValidator({
                fields: {
                    name: {
                        validators: {
                            notEmpty: {
                                message: '姓名不能为空'
                            }
                        }
                    },
                    userName: {
                        validators: {
                            notEmpty: {
                                message: '请输入用户名'
                            },
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: '请输入密码'
                            },
                        }
                    },
                }
            });
            // 重置表单验证
            $('#resetBtn').click(function() {
                $('#defaultForm').data('bootstrapValidator').resetForm(true);
            });
        });
    </script>
</head>
<body>
<div class="container">
    <div class="row clearfix" style="margin-top: 80px">
        <div class="col-md-12 column">
            <form id="defaultForm" class="form-horizontal" role="form" action="/core/user.htm?method=save" method="post"
                  enctype="multipart/form-data">
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名:</label>
                    <div class="col-sm-2">
                        <input type="hidden" name="xId" value="${user.xId}">
                        <input type="hidden" name="userId" value="${user.userId}">
                        <input type="text" class="form-control" name="userName" value="${user.userName}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">登录密码:</label>
                    <div class="col-sm-2">
                        <input type="password" class="form-control" name="password" value="${user.password}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">姓名:</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" name="name" value="${user.name}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">性别:</label>
                    <div class="col-sm-1">
                        <select name="sex" class="selectpicker show-tick form-control" data-width="98%"
                                data-first-option="false" required data-live-search="true">
                            <option <c:if test="${not empty user.sex && user.sex==0 }">selected="selected"</c:if> value='0'>男</option>
                            <option <c:if test="${not empty user.sex && user.sex==1 }">selected="selected"</c:if> value='1'>女</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">所属部门:</label>
                    <div class="col-sm-2">
                        <select name="departmentId" class="selectpicker show-tick form-control"  data-width="98%" data-first-option="false" required data-live-search="true">
                            <option value="">--选择部门--</option>
                            <c:forEach items="${_AllDepartments}" var="department" varStatus="index">
                                <option
                                        <c:if test="${not empty user.departmentId && user.departmentId eq department.departmentId }">selected="selected"</c:if>
                                        value=${department.departmentId}>${department.departmentName }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户类型:</label>
                    <div class="col-sm-2">
                        <select name="userType" class="selectpicker show-tick form-control"  data-width="98%" data-first-option="false" required data-live-search="true">
                            <option value="">--选择类型--</option>
                                <option <c:if test="${not empty user.userType && user.userType==99 }">selected="selected"</c:if> value="99">超级管理员</option>
                                <option <c:if test="${not empty user.userType && user.userType==11 }">selected="selected"</c:if>  value="11">系统管理员</option>
                                <option <c:if test="${not empty user.userType && user.userType>5&&user.userType<11 }">selected="selected"</c:if>  value="6">管理人员</option>
                                <option <c:if test="${not empty user.userType && user.userType>0&&user.userType<6 }">selected="selected"</c:if>  value="1">普通用户</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">检查权限:</label>
                    <div class="col-sm-1">
                        <select name="reformFinder" class="selectpicker show-tick form-control" data-width="98%"
                                data-first-option="false" required data-live-search="true">
                            <option value="">--</option>
                            <option <c:if test="${not empty user.reformFinder && !user.reformFinder }">selected="selected"</c:if> value="false">无</option>
                            <option <c:if test="${not empty user.reformFinder && user.reformFinder }">selected="selected"</c:if> value="true">有</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">web登录权限:</label>
                    <div class="col-sm-1">
                        <select name="webLogin" class="selectpicker show-tick form-control" data-width="98%"
                                data-first-option="false" required data-live-search="true">
                            <option <c:if test="${not empty user.webLogin && !user.webLogin }">selected="selected"</c:if> value="false">无</option>
                            <option <c:if test="${not empty user.webLogin && user.webLogin }">selected="selected"</c:if> value="true">有</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">文档添加权限:</label>
                    <div class="col-sm-1">
                        <select name="docAdd" class="selectpicker show-tick form-control" data-width="98%"
                                data-first-option="false" required data-live-search="true">
                            <option <c:if test="${not empty user.docAdd && !user.docAdd }">selected="selected"</c:if> value="false">无</option>
                            <option <c:if test="${not empty user.docAdd && user.docAdd }">selected="selected"</c:if> value="true">有</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">文档删除权限:</label>
                    <div class="col-sm-1">
                        <select name="docDelete" class="selectpicker show-tick form-control" data-width="98%"
                                data-first-option="false" required data-live-search="true">
                            <option <c:if test="${not empty user.docDelete && !user.docDelete }">selected="selected"</c:if> value='false'>无</option>
                            <option <c:if test="${not empty user.docDelete && user.docDelete }">selected="selected"</c:if> value="true">有</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">保存</button>
                        <button type="button" class="btn btn-info" id="resetBtn">重置</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
