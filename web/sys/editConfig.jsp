<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0049)http://oa.sohuu.com:81/general/person_info/theme/ -->
<html>
	<head>
		<title>界面主题</title>
		<link rel=stylesheet type=text/css href="<c:url value='/'/>css/configStyle.css">
		<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script> <!--jquery包-->
		
		<!--  Uploadify -->
		<link href="<c:url value='/'/>js/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<c:url value='/'/>js/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="<c:url value='/'/>js/uploadify/swfobject.js"></script>
		<script type="text/javascript" src="<c:url value='/'/>js/upload.js"></script>
		
		
		<script>
			$(document).ready(function() {
				//初始化附件上传
				initUploadImg("${_LogoPic}","fileQueue1","uploadify1",false,1,"logoPath","fileList1","logoPicDiv");
				initUploadImg("${_TopPic}","fileQueue2","uploadify2",false,1,"picPath","fileList2","topPicDiv");
				initUpload("${_ContactExcel}","fileQueue3","uploadify3","contactExcel","fileList3","contactExcelDiv");
			});

			
			
			function submitForm(){
				var form = document.basicInforForm;
				//form.action = "/sys/config.htm?method=save&logoPath=";
				form.submit();
				parent.parent.location.reload();
			}

		</script>
	</head>
	<body class=bodycolor topMargin=5>
		<table class=TableBlock cellPadding=2 width=90% align=center>
			<form:form commandName="basicInforVo" name="basicInforForm" id="basicInforForm" action="/sys/config.htm?method=save" method="post" enctype="multipart/form-data">
				<tbody>
					<tr>
						<td class=TableHeader colSpan=2>界面主题与名称</td>
					</tr>
					<tr>
						<td class=TableData noWrap><b>系统名称：</b></td>
						<td class=TableData>
							<form:input path="systemName" class="SmallInput" size="30" />
							<font color="gray">为空时，则默认为“慧智网络智能办公系统”</font>
						</td>
					<tr>
					<tr>
						<td class=TableData width="20%" noWrap><b>是否显示天气预报：</b>
						</td>
						<td class=TableData>
							<form:select path="weather" class="SmallSelect">
								<form:option value="true">是</form:option>
								<form:option value="false">否</form:option>
							</form:select>
						</td>
					</tr>
					<tr>
						<td class=TableData width="20%" noWrap><B>左边默认打开菜单：</B>
						</td>
						<td class=TableData>
							<form:select path="openMenuId" class="SmallSelect">
								<form:option value="0">--无--</form:option>
								<c:forEach items="${_AllMenuList}" var="menu">
									<c:if test="${menu.layer == '1' && menu.menuPosition == '0'}">
										<form:option value="${menu.resourceId}">${menu.resourceName}</form:option>
									</c:if>
								</c:forEach>
								<%--<form:option value="1">个人办公</form:option>
								<form:option value="2">工作流</form:option>
								<form:option value="3">日常办公</form:option>
								<form:option value="4">投资企业</form:option>
								<form:option value="5">供应商管理</form:option>
								<form:option value="6">常用资料</form:option>
								<form:option value="7">信息发布</form:option>
								<form:option value="8">文档大全</form:option>
								<form:option value="9">系统维护</form:option>
							--%></form:select>
						</td>
					</tr>
					<tr>
						<td class=TableData width="20%" noWrap><b>LOGO图片：</b>
						</td>
						<td class=TableData>
							<div id="logoPicDiv"></div>
							<input type="hidden" name="logoPath" id="logoPath" />
							<%--<form:hidden path="logoPath" id="logoPath"/>
						--%></td>
					</tr>
					<tr>
						<td class=TableData width="20%" noWrap><b>顶部图片：</b>
						</td>
						<td class=TableData>
							<div id="topPicDiv"></div>
							<input type="hidden" name="picPath" id="picPath" />
							<%--<form:hidden path="picPath" id="picPath"/>
						--%></td>
					</tr>
					<tr>
						<td class=TableHeader colSpan=2>短信通知</td>
					</tr>
					<tr>
						<td class=TableData width="20%" noWrap><b>会议短信通知：</b>
						</td>
						<td class=TableData>
							<form:select path="meetingMessage" class="SmallSelect">
								<form:option value="true">是</form:option>
								<form:option value="false">否</form:option>
							</form:select>
						</td>
					</tr>
					<tr>
						<td class=TableData width="20%" noWrap><b>订车短信通知：</b>
						</td>
						<td class=TableData>
							<form:select path="carMessage" class="SmallSelect">
								<form:option value="true">是</form:option>
								<form:option value="false">否</form:option>
							</form:select>
						</td>
					</tr>
					
					<tr>
						<td class=TableHeader colSpan=2>基本资料</td>
					</tr>
					<tr>
						<td class=TableData width="20%" noWrap><b>最新通讯录：</b>
						</td>
						<td class=TableData>
							<div id="contactExcelDiv"></div>
							<input type="hidden" name="contactExcel" id="contactExcel" />
							<font color="gray">请上传Excel格式的文件，如果已存在，请先将原文件删除！</font>
						</td>
					</tr>
					
					
					<tr class=TableControl align=middle>
						<td colSpan=2 noWrap><input class=BigButton value="保存设置并生效"
							type=button onclick="submitForm();"/>&nbsp;&nbsp;</td>
					</tr>
				</form:form>
			</tbody>
		</table>
		<br>
	</body>
</html>
