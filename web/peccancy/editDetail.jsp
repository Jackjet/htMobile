<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/inc/taglibs.jsp" %>
<!doctype html>
<head>
    <link href="/js/timepicker/css/lyz.calendar.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
    <script>
        function ruleFile(isTable) {
            var index = isTable.rows.length;
            var nextRow = isTable.insertRow(index);
            var isText = nextRow.insertCell(0);
            var txtArea = nextRow.insertCell(1);
            index++;
            index = index.toString();
            var nameStr = "rule_" + new Date().getMilliseconds();
            var txtStr = index + ".";
            isText.innerHTML = txtStr;
            txtArea.innerHTML = "<input type=file name=" + nameStr + " >";
        }

        function reformFile(isTable) {
            var index = isTable.rows.length;
            var nextRow = isTable.insertRow(index);
            var isText = nextRow.insertCell(0);
            var txtArea = nextRow.insertCell(1);
            index++;
            index = index.toString();
            var nameStr = "reform_" + new Date().getMilliseconds();
            var txtStr = index + ".";
            isText.innerHTML = txtStr;
            txtArea.innerHTML = "<input type=file name=" + nameStr + " >";
        }
    </script>
</head>
<html>
<div style="padding-left: 30px">
    <form action="/peccancy.htm?method=savePeccancy" method="post" enctype="multipart/form-data">
        <input type="hidden" name="peccancyId" value="${peccancyDetail.peccancyId}">
        <table cellspacing="0" cellpadding="0" border="0" style="width: 90%">
            <tbody id="tblGrid">
            <tr style="height: 30px;">
                <td colspan="2">【违章详情】
                    <hr/>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">违章类型：</td>
                <td style="width: 70%">
                    <select name="peccancyTypeId" style="height: 25px">
                        <c:forEach items="${_allCategory}" var="categorys" varStatus="index">
                            <option
                                    <c:if test="${not empty peccancyDetail.peccancyTypeName && peccancyDetail.peccancyTypeName eq categorys.categoryName }">selected="selected"</c:if>
                                    value=${categorys.categoryId}>${categorys.categoryName }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">违章描述：</td>
                <td style="width: 70%">
                    <textarea rows="5" cols="80" name="content">${peccancyDetail.content}</textarea>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">责任部门：</td>
                <td style="width: 70%">
                    <select name="dutyDeptName" style="height: 25px">
                        <c:forEach items="${_Departments}" var="department" varStatus="index">
                            <option
                                    <c:if test="${not empty peccancyDetail.dutyDeptName && peccancyDetail.dutyDeptName eq department.departmentName }">selected="selected"</c:if>
                                    value=${department.departmentName}>${department.departmentName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">责任人：</td>
                <td style="width: 70%">${peccancyDetail.dutyPersonName}</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">备注：</td>
                <td style="width: 70%">${peccancyDetail.remark}</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">发现人：</td>
                <td style="width: 70%">${peccancyDetail.offenderName}</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">发现时间：</td>
                <td style="width: 70%">${peccancyDetail.findTime}</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">违章附件：</td>
                <td style="width: 70%">
                    <c:forEach items="${peccancyDetail.ruleAttaches}" var="attach" varStatus="a">
                        <c:if test="${attach!=''}">
                            <a href="${attach}" target="_blank"><img title="点击查看大图" width="120" height="90"
                                                                     src="${attach}"></a><input
                                type="checkbox" name="ruleImage" value="${attach}">选中删除
                        </c:if>
                    </c:forEach>
                    <table id="dyTable0"></table>
                    <input type="button" value="添加更多" onclick="ruleFile(dyTable0)">
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">违章条例：</td>
                <td style="width: 70%">${peccancyDetail.peccancyRules}</td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">晨会汇报：</td>
                <td style="width: 70%">
                    <select name="morningReport">
                        <option value="">-请选择-</option>
                        <option <c:if test="${peccancyDetail.morningReport}">selected="selected"</c:if> value="true">-是-</option>
                        <option <c:if test="${!peccancyDetail.morningReport}">selected="selected"</c:if> value="false">-否-</option>
                    </select>
                </td>
            </tr>
            <tr style="height: 30px;">
                <td style="width:60px">整改附件：</td>
                <td style="width: 70%">
                    <c:forEach items="${peccancyDetail.reformAttaches}" var="attach" varStatus="a">
                        <c:if test="${attach!=''}">
                            <a href="${attach}" target="_blank"><img title="点击查看大图" width="120" height="90"
                                                                     src="${attach}"></a><input
                                type="checkbox" name="reformImage" value="${attach}">选中删除
                        </c:if>
                    </c:forEach>
                    <table id="dyTable1"></table>
                    <input type="button" value="添加更多" onclick="reformFile(dyTable1)">
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