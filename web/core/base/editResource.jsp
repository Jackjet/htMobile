<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑功能权限</title>
<script type="text/javascript" src="<c:url value="/"/>js/pngfix.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/edit.css" />
<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script> <!--jquery包-->
<script src="<c:url value="/"/>js/inc_javascript.js" type="text/javascript"></script>


<!-- 按钮样式 -->
<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/button.css" />

<!-- 表单验证 -->
<script src="<c:url value='/'/>js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/'/>js/Validform_v5.3.2/css/style.css" />
<script>
	$(function(){
		$("#virtualResourceForm").Validform({
			tiptype:3,
			btnSubmit:"#btn_sub",
			showAllError:true,
			beforeSubmit:function(curform){
			},
			callback:function(data){
				alert('信息编辑成功！');
				window.returnValue = "Y";
				window.close();
				//window.opener.location.reload();
				window.opener.loadTab('/core/virtualResource.htm?method=list&field=${param.field}', '${param.tabId}');
			}
		});  
		
		
		//勾选选中的角色
		var roleIds = document.getElementsByName('roleIds');
		<c:forEach var="roleId" items="${_RoleIds}">
			var tmpRoleId = '${roleId}';
			if (roleIds != null && roleIds.length > 0) {
				for (var i=0;i<roleIds.length;i++) {
					var roleId = roleIds[i];
					if (tmpRoleId == roleId.value) {
						roleId.checked = true;
					}
				}
			}
		</c:forEach>
		
	});
	
	//提交数据
	function submitData() {
		var form = document.virtualResourceForm;
		form.action = '<c:url value="/core/virtualResource.htm"/>?method=save';
		form.submit();
		alert('信息编辑成功！');
		window.returnValue = "refresh";
		window.close();
	}
</script>

</head>
<base target="_self" />
<body>
	<div id="header">
		<div class="logo">
			<img src="<c:url value="/"/>images/zhongtu.png" width="430"
				height="88" />
		</div>
	</div>
	<div id="wrap">

		<!--网站主题部分-->
		<div id="right">
			<div class="emil"></div>
			<div class="module">
				<div class="content">
					<div class="xinxi">
						<strong>编辑功能权限</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form id="virtualResourceForm" name="virtualResourceForm" action="/core/virtualResource.htm?method=save" method="post">
							<input type="hidden" name="resourceId" value="${_Resource.resourceId}"/>
							<input type="hidden" name="operationId" value="${_Operation.operationId}"/>
							<table width="800" border="0" cellpadding="0" cellspacing="0"
								class="doc" style="color:black;">
								<tr>
									<td width="15%" align="right" nowrap="nowrap"><span class="blues">模块名称：</span>&nbsp;&nbsp;</td>
									<td width="85%">
										${_Resource.resourceName}
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">功能描述：</span>&nbsp;&nbsp;</td>
									<td>
										${_Resource.description}
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">相关操作：</span>&nbsp;&nbsp;</td>
									<td>
										${_Operation.operationName}
									</td>
								</tr>
								<c:if test="${!empty _Roles}">  
									<tr>
										<td nowrap="nowrap" align="right" valign="top"><span class="blues">操作角色：</span>&nbsp;&nbsp;</td>
										<td>
											<%--<ul id="lis">
												<c:forEach var="role" items="${_Roles}">
													<li><input type="checkbox" name="roleIds" value="${role.roleId}"/> ${role.roleName}</li>
												</c:forEach>
											</ul>
											--%><table>
												<tr>
													<c:forEach var="role" items="${_Roles}" varStatus="index">
														<td nowrap="nowrap">
															<input type="checkbox" name="roleIds" value="${role.roleId}" id="check_${role.roleId}"/> <label for="check_${role.roleId}">${role.roleName}</label>
															&nbsp;&nbsp;
														</td>
														<c:if test="${(index.index+1) % 4 == 0 }">
															</tr><tr>
														</c:if>
													</c:forEach>
												</tr>
											</table>
										</td>
									</tr>
								</c:if>

							</table>
							<div id="buttons">
								<table width="300" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td height="12"></td>
										<td height="12"></td>
									</tr>
									<tr>
										<td>
											<a href="javascript:void(0);" class="btn_blue" id="btn_sub"><span>提 交</span></a>
										</td>
										<td>
											<a href="javascript:void(0);" class="btn_red" id="btn_clo" onclick="window.close();"><span>关 闭</span></a>
										</td>
									</tr>
									<tr>
										<td height="20"></td>
										<td height="20"></td>
									</tr>
								</table>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearit"></div>

	<!--网站底部部分-->
	<div id="footer">上海慧智计算机技术有限公司 技术支持</div>

</body>
</html>
