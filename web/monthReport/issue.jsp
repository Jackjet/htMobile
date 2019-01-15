<%@ page contentType="text/html; charset=utf-8"%>
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
            $("#category option").click(function () {
                if($("#month").val()==''){
                    return;
                }
                $.ajax({
                    type:'post',
                    url:"${pageContext.request.contextPath}/monthReport.htm?method=showIssue",
                    data:{year:$("#year").val(),categoryId:$("#category").val(),month:$("#month").val()},
                    dataType:"JSON",
                    success:function (data) {
                        $("#content").val(data);
                    },
                    error:function (data) {
                        alert("后台出错")
                    }
                })
            });
            $("#month option").click(function () {
                if($("#category").val()==''){
                    return;
                }
                $.ajax({
                    type:'post',
                    url:"${pageContext.request.contextPath}/monthReport.htm?method=showIssue",
                    data:{year:$("#year").val(),categoryId:$("#category").val(),month:$("#month").val()},
                    dataType:"JSON",
                    success:function (data) {
                        $("#content").val(data);
                    },
                    error:function (data) {
                        alert("后台出错")
                    }
                })
            })
        })
    </script>
</head>
<body>
<div class="container">
    <div class="row clearfix" style="margin-top: 80px">
        <div class="col-md-12 column">
            <form class="form-horizontal" role="form" action="/monthReport.htm?method=saveIssue" method="post">
                <div class="form-group">
                    <label class="col-sm-2 control-label">年份:</label>
                    <div class="col-sm-2">
                        <input id="year" type="text" class="form-control" name="year" value="${_ThisYear}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">隐患项:</label>
                    <div class="col-sm-2">
                        <select id="category" name="categoryId" class="selectpicker show-tick form-control"  data-width="98%" data-first-option="false" required data-live-search="true">
                            <option value="">--请选择--</option>
                            <c:forEach var="category" items="${_allCategory}">
                                <option value="${category.categoryId}">${category.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">月份:</label>
                    <div class="col-sm-2">
                        <select name="month" class="selectpicker show-tick form-control"  data-width="98%" data-first-option="false" required data-live-search="true">
                            <option value="">--请选择--</option>
                            <option <c:if test="${_ThisMonth eq '1月'}">selected="selected"</c:if> value="1月">1月</option>
                            <option <c:if test="${_ThisMonth eq '2月'}">selected="selected"</c:if> value="2月">2月</option>
                            <option <c:if test="${_ThisMonth eq '3月'}">selected="selected"</c:if> value="3月">3月</option>
                            <option <c:if test="${_ThisMonth eq '4月'}">selected="selected"</c:if> value="4月">4月</option>
                            <option <c:if test="${_ThisMonth eq '5月'}">selected="selected"</c:if> value="5月">5月</option>
                            <option <c:if test="${_ThisMonth eq '6月'}">selected="selected"</c:if> value="6月">6月</option>
                            <option <c:if test="${_ThisMonth eq '7月'}">selected="selected"</c:if> value="7月">7月</option>
                            <option <c:if test="${_ThisMonth eq '8月'}">selected="selected"</c:if> value="8月">8月</option>
                            <option <c:if test="${_ThisMonth eq '9月'}">selected="selected"</c:if> value="9月">9月</option>
                            <option <c:if test="${_ThisMonth eq '10月'}">selected="selected"</c:if> value="10月">10月</option>
                            <option <c:if test="${_ThisMonth eq '11月'}">selected="selected"</c:if> value="11月">11月</option>
                            <option <c:if test="${_ThisMonth eq '12月'}">selected="selected"</c:if> value="12月">12月</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">突出问题:</label>
                    <div class="col-sm-6">
                        <textarea id="content" rows="8" class="form-control" name="content"></textarea>
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
