<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>修改密码</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/edit.css" />
<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script><!--jquery包-->
<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/cupertino/jquery-ui-1.9.2.custom.css" />
<script src="<c:url value="/"/>js/inc_javascript.js" type="text/javascript"></script>

<!-- 按钮样式 -->
<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/button.css" />

<!-- 表单验证 -->
<script src="<c:url value='/'/>js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/'/>js/Validform_v5.3.2/css/style.css" />
<script>
	$(function(){
		//$("#btn_sub").button();
		//$("#btn_clo").button();
		var height = document.documentElement.clientHeight;
		$("#right").css("height",height+"px");
		
		$("#systemUserForm").Validform({
			tiptype:3,
			btnSubmit:"#btn_sub",
			showAllError:true,
			beforeSubmit:function(curform){
			},
			callback:function(data){
				alert('信息编辑成功！');
				//window.returnValue = "Y";
				//window.close();
				window.location.reload();
			}
		});  
		
	});
</script>

</head>
<base target="_self" />
<body>
	<%--<div id="header">
		<div class="logo">
			<img src="<c:url value="/"/>images/zhongtu.png" width="430"
				height="88" />
		</div>
	</div>
	--%><div>

		<!--网站主题部分-->
		<div id="right" style="margin-left:-1px;border:0px solid red;">
			<div class="emil"></div>
			<div class="module">
				<div class="content">
					<div class="xinxi">
						<strong>修改密码</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form:form commandName="systemUserInforVo" id="systemUserForm" name="systemUserForm" action="/core/systemUserInfor.htm?method=savePassword" method="post">
							<form:hidden path="personId"/>
							<table width="800" border="0" cellpadding="0" cellspacing="0"
								class="doc">
								<c:if test="${! empty _User.person.department}">
									<tr>
										<td width="15%" align="right" nowrap="nowrap"><span class="blues">所属部门：</span>&nbsp;&nbsp;</td>
										<td width="85%">
											${_User.person.department.organizeName} 
										</td>
									</tr>
								</c:if>
								<c:if test="${! empty _User.person.group}">
									<tr>
										<td nowrap="nowrap" align="right"><span class="blues">所属班组：</span>&nbsp;&nbsp;</td>
										<td>
											${_User.person.group.organizeName}
										</td>
									</tr>
								</c:if>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">人员：</span>&nbsp;&nbsp;</td>
									<td>
										${_User.person.personName}
									</td>
								</tr>
								<tr>
									<td align="right"><span class="blues">用户名：</span>&nbsp;&nbsp;</td>
									<td>
										${_User.userName}
									</td>
								</tr>
								<tr>
									<td align="right"><span class="blues">原始密码：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="oldPassword" size="20" class="textfield" ajaxurl="/core/systemUserInfor.htm?method=validateOldPas" datatype="*" sucmsg="填写正确！" nullmsg="请输入原始密码！"/>
									</td>
								</tr>
								<tr>
									<td align="right"><span class="blues">新密码：</span>&nbsp;&nbsp;</td>
									<td>
										<form:password path="password" size="20" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入新密码！"/>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">确认新密码：</span>&nbsp;&nbsp;</td>
									<td>
										<form:password path="rePassword" size="20" class="textfield" datatype="*" sucmsg="填写正确！" recheck="password" nullmsg="请再次输入新密码！" errormsg="两次输入密码不一致！"/> 
									</td>
								</tr>

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
											<a href="javascript:void(0);" class="btn_blue" id="btn_sub"><span>提  交</span></a>
										</td>
										<td>
											<%--<a href="javascript:void(0);" class="btn_red" onclick="window.close();"><span>关 闭</span></a>
										--%></td>
									</tr>
									<tr>
										<td height="20"></td>
										<td height="20"></td>
									</tr>
								</table>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearit"></div>

	<!--网站底部部分
	<div id="footer">上海慧智计算机技术有限公司 技术支持</div>-->

</body>
</html>
