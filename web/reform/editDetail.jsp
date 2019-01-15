<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/inc/taglibs.jsp" %>
<!doctype html>
<head>
    <link href="/js/timepicker/css/lyz.calendar.css" rel="stylesheet" type="text/css"/>
    <script src="http://www.jq22.com/jquery/1.4.4/jquery.min.js"></script>
    <link href="/js/timepicker/css/lyz.calendar.css" rel="stylesheet" type="text/css"/>
    <script src="/js/timepicker/js/lyz.calendar.min.js" type="text/javascript"></script>
    <script>
        $(function () {
            $("#reformTime").calendar();
        });

        function addfile(isTable) {
            var index = isTable.rows.length;
            var nextRow = isTable.insertRow(index);
            var isText = nextRow.insertCell(0);
            var txtArea = nextRow.insertCell(1);
            index++;
            index = index.toString();
            var nameStr = "maintain" + index;
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
    <form action="/reform.htm?method=saveReform" method="post" enctype="multipart/form-data">
        <input type="hidden" name="reformId" value="${reformDetail.reformId}">
        <table cellspacing="0" cellpadding="0" border="0" style="width: 90%">
            <tbody id="tblGrid">
            <tr style="height: 30px;">
                <td colspan="2">【详情编辑】
                    <hr/>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">隐患类型：</td>
                <td style="width: 70%">
                    <select name="errorTypeId" style="height: 25px">
                        <c:forEach items="${_allCategory}" var="categorys" varStatus="index">
                            <option
                                    <c:if test="${not empty reformDetail.errorType && reformDetail.errorType eq categorys.categoryName }">selected="selected"</c:if>
                                    value=${categorys.categoryId}>${categorys.categoryName }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">异常情况：</td>
                <td style="width: 70%">
                    <textarea rows="10" cols="100" name="errorInfo">${reformDetail.errorInfo}</textarea>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">责任部门：</td>
                <td style="width: 70%">
                    <select name="dutyDeptName" style="height: 25px">
                        <c:forEach items="${_Departments}" var="department" varStatus="index">
                            <option
                                    <c:if test="${not empty reformDetail.dutyDeptName && reformDetail.dutyDeptName eq department.departmentName }">selected="selected"</c:if>
                                    value=${department.departmentName }>${department.departmentName }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">发现人：</td>
                <td style="width: 70%">${reformDetail.findUserName}</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">发现时间：</td>
                <td style="width: 70%">${reformDetail.findTime}</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">隐患区域：</td>
                <td style="width: 70%">
                    <select name="areaId" style="height: 25px">
                        <c:forEach items="${_allArea}" var="area" varStatus="index">
                            <option
                                    <c:if test="${not empty reformDetail.areaName && reformDetail.areaName eq area.areaName }">selected="selected"</c:if>
                                    value=${area.areaId}>${area.areaName }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">整改期限：</td>
                <td style="width: 70%"><input id="reformTime" name="reformTime" value="${reformDetail.reformTime}"></td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">晨会汇报：</td>
                <td style="width: 70%">
                    <select name="morningReport">
                        <option value="">-请选择-</option>
                        <option <c:if test="${reformDetail.morningReport}">selected="selected"</c:if> value="true">-是-</option>
                        <option <c:if test="${!reformDetail.morningReport}">selected="selected"</c:if> value="false">-否-</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">异常附件：</td>
                <td style="width: 70%">
                    <c:forEach items="${reformDetail.errorAttachs}" var="attach" varStatus="a">
                        <c:if test="${attach!=''}">
                            <a href="${attach}" target="_blank"><img title="点击查看大图" width="240" height="180"
                                                                     src="${attach}"></a>
                            <input type="checkbox" name="image" value="${attach}">选中删除
                        </c:if>
                    </c:forEach>
                    <table id="dyTable"></table>
                    <input type="button" value="添加更多" onclick="addfile(dyTable)">
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