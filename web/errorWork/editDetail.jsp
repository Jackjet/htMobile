<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/inc/taglibs.jsp" %>
<!doctype html>
<head>
    <link href="/js/timepicker/css/lyz.calendar.css" rel="stylesheet" type="text/css"/>
    <script src="http://www.jq22.com/jquery/1.4.4/jquery.min.js"></script>
    <link href="/js/timepicker/css/lyz.calendar.css" rel="stylesheet" type="text/css"/>
    <script src="/js/timepicker/js/lyz.calendar.min.js" type="text/javascript"></script>
    <script>
        function eFile(isTable) {
            var index = isTable.rows.length;
            var nextRow = isTable.insertRow(index);
            var isText = nextRow.insertCell(0);
            var txtArea = nextRow.insertCell(1);
            index++;
            index = index.toString();
            var nameStr = "error_" + new Date().getMilliseconds();
            var txtStr = index + ".";
            isText.innerHTML = txtStr;
            txtArea.innerHTML = "<input type=file name=" + nameStr + " >";
            // index=1;
            // index++;
            // var html = "<input type='file' name='error'+index><br/>";
            // $("#ex").append(html)
        }

        function mFile(isTable) {
            var index = isTable.rows.length;
            var nextRow = isTable.insertRow(index);
            var isText = nextRow.insertCell(0);
            var txtArea = nextRow.insertCell(1);
            index++;
            index = index.toString();
            var nameStr = "memo_" + new Date().getMilliseconds();
            var txtStr = index + ".";
            isText.innerHTML = txtStr;
            txtArea.innerHTML = "<input type=file name=" + nameStr + " >";
            // index=1;
            // index++;
            // var html = "<input type='file' name='error'+index><br/>";
            // $("#ex").append(html)
        }
    </script>
</head>
<html>
<div style="padding-left: 30px">
    <form action="/errorWork.htm?method=saveErrorWork" method="post" enctype="multipart/form-data">
        <input type="hidden" name="errorWorkId" value="${errorWork.errorWorkId}">
        <table cellspacing="0" cellpadding="0" border="0" style="width: 90%">
            <tbody id="tblGrid">
            <tr style="height: 30px;">
                <td colspan="2">【动检详情】
                    <hr/>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">动检标题：</td>
                <td style="width: 70%">${errorWork.errorTitle}</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">动检类型：</td>
                <td style="width: 70%">${errorWork.errorType}</td>
            </tr>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">动检区域：</td>
                <td style="width: 70%">
                    <select name="areaId" style="height: 25px">
                        <c:forEach items="${_allArea}" var="area" varStatus="index">
                            <option
                                    <c:if test="${not empty errorWork.areaName && errorWork.areaName eq area.areaName }">selected="selected"</c:if>
                                    value=${area.areaId}>${area.areaName }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">异常情况：</td>
                <td style="width: 70%">
                    <textarea rows="10" cols="100" name="errorInfo">${errorWork.errorInfo}</textarea>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">异常附件：</td>
                <td style="width: 70%">
                    <c:forEach items="${errorWork.errorAttachs}" var="attach" varStatus="a">
                        <c:if test="${attach!=''}">
                            <a href="${attach}" target="_blank"><img title="点击查看大图" width="240" height="180"
                                                                     src="${attach}"></a><input type="checkbox"
                                                                                                name="eImage"
                                                                                                value="${attach}">选中删除
                        </c:if>
                    </c:forEach>
                    <table id="dyTable0"></table>
                    <input type="button" value="添加更多" onclick="eFile(dyTable0)">
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">责任部门：</td>
                <td style="width: 70%">
                    <select name="dutyDept1Name" style="height: 25px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${_UnionDepartments}" var="department" varStatus="index">
                            <option
                                    <c:if test="${not empty errorWork.dutyDept1Name && errorWork.dutyDept1Name eq department.departmentName }">selected="selected"</c:if>
                                    value=${department.departmentName }>${department.departmentName }</option>
                        </c:forEach>
                    </select>
                    <select name="dutyDept2Name" style="height: 25px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${_UnionDepartments}" var="department" varStatus="index">
                            <option
                                    <c:if test="${not empty errorWork.dutyDept2Name && errorWork.dutyDept2Name eq department.departmentName }">selected="selected"</c:if>
                                    value=${department.departmentName }>${department.departmentName }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">执行人：</td>
                <td style="width: 70%">${errorWork.executerName}</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">执行时间：</td>
                <td style="width: 70%">${errorWork.checkTime}</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">备注：</td>
                <td style="width: 70%">${errorWork.memo}</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">备注附件：</td>
                <td style="width: 70%">
                    <c:forEach items="${errorWork.memoAttachs}" var="attach" varStatus="a">
                        <c:if test="${attach!=''}">
                            <a href="${attach}" target="_blank"><img title="点击查看大图" width="240" height="180"
                                                                     src="${attach}"></a><input
                                type="checkbox" name="mImage" value="${attach}">选中删除
                        </c:if>
                    </c:forEach>
                    <table id="dyTable1"></table>
                    <input type="button" value="添加更多" onclick="mFile(dyTable1)">
                </td>
            </tr>
            <tr>
                <td></td>
                <td><br/><input type="submit" value="提交修改"></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<hr style="border:1px double;margin-bottom:0px"/>
<div id="footer" style="text-align:center;
				color:black;
				font-size:15px;padding-bottom: 0px">上海慧智计算机技术有限公司 技术支持
</div>
</html>