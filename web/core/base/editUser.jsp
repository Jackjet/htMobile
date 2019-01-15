<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑系统用户</title>
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
	$(document).ready(function(){
		//加载部门及联动信息
		//$.loadDepartments_Persons("departmentId", null, "personId");
		
		//表单验证
		$("#systemUserForm").Validform({
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
				window.opener.loadTab('listUser.jsp?isvalid=true', '1');
			}
		});  
		
		//加载部门及联动信息
		$.loadDepartments_Persons("departmentId", null, "personId");
		
	});
	//部门,班组,用户下拉联动 
	//下拉数据初始化
	$.fn.selectInit = function(){return $(this).html("<option value=''>请选择</option>");};
	
	//加载部门及联动信息
	$.loadDepartments_Persons = function(departmentId, groupId, personId) {
		//加载部门数据
		$.getJSON("/core/organizeInfor.htm?method=getDepartments",function(data) {
			if (data != null) {
			    $.each(data._Departments, function(i, n) {
				    $("#"+departmentId).append("<option value='"+ n.organizeId + "'>" + n.organizeName + "</option>");
				});
			}
		});
		
		if ($("#"+groupId).html() != null) {
			//含下拉班组信息时,改变部门信息,联动班组信息
			$("#"+departmentId).bind("change",function(){
	           $("#"+groupId).selectInit();
	           var depSelectId = $("#"+departmentId+" option:selected").val();
	           var groupUrl = "/core/organizeInfor.htm?method=getGroups&departmentId=" + depSelectId;
	           $.loadGroups(groupUrl, groupId);
			});
				
			//含下拉班组信息时,改变班组信息,联动用户信息
			$("#"+groupId).bind("change",function(){
		       $("#"+personId).selectInit();
		       var groSelectId = $("#"+groupId+" option:selected").val(); 
		       var userUrl = "/core/personInfor.htm?method=getPersons&groupId=" + groSelectId;
		       $.loadPersons(userUrl, personId);
			});
		}else {
			//不含下拉班组信息时,改变部门信息,联动人员信息
			$("#"+departmentId).bind("change",function(){
	            $("#"+personId).selectInit();
	            var depSelectId = $("#"+departmentId+" option:selected").val();
	            var url = "/core/personInfor.htm?method=getPersons&departmentId=" + depSelectId;
	            $.loadPersons(url, personId);                    
			});
		}
	}
		 
	//获取班组信息
	$.loadGroups = function(url, groupId) {
		$.getJSON(url,function(data) {
			if (data != null) {
		 		$.each(data._Groups, function(i, n) {
			 		$("#"+groupId).append("<option value='"+ n.organizeId +"'>" + n.organizeName + "</option>");
			 	});
		 	}
		});
	}
		 
	//获取人员信息
	$.loadPersons = function(url, personId) {
		$.getJSON(url,function(data) {
			if (data != null) {
				$.each(data._Persons, function(i, n) {
					$("#"+personId).append("<option value='"+ n.personId + "'>" + n.personName + "</option>");
				});
			}
		});
	}
	
	//提交数据
	function submitData() {
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
						<strong>编辑系统用户</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form:form commandName="systemUserInforVo" id="systemUserForm"
							name="systemUserForm"
							action="/core/systemUserInfor.htm?method=save" method="post">

							<table width="800" border="0" cellpadding="0" cellspacing="0"
								class="doc">
								<tr>
									<td align="right" width="15%" nowrap="nowrap"><span class="blues">所属部门：</span>&nbsp;&nbsp;</td>
									<td width="85%"> 
										<c:choose>
											<c:when test="${empty _User}">
												<form:select path="departmentId" class="select">
													<form:option value="0">--选择部门--</form:option>
													<!--<c:forEach items="${_Departments}" var="department">
														<form:option value="${department.organizeId}">${department.organizeName}</form:option>
													</c:forEach>
												--></form:select>
											</c:when>
											<c:otherwise>${_User.person.department.organizeName}</c:otherwise>
										</c:choose> 
									</td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap"><span class="blues">人员：</span>&nbsp;&nbsp;</td>
									<td>
										<c:choose>
											<c:when test="${empty _User}">
												<form:select path="personId" class="select" datatype="*" nullmsg="请选择人员！" sucmsg="选择正确！">
													<form:option value="">--选择人员--</form:option>
													<c:forEach items="${_Persons}" var="person"
														varStatus="status">
														<form:option value="${person.personId}">${person.personName}</form:option>
													</c:forEach>
												</form:select>
											</c:when>
											<c:otherwise>${_User.person.personName}<form:hidden path="personId" />
											</c:otherwise>
										</c:choose> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">登录用户名：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="userName" size="20" class="textfield" ajaxurl="/core/systemUserInfor.htm?method=validate&personId=${param.rowId}" datatype="*3-100" sucmsg="填写正确！" errormsg="用户名至少3位！" nullmsg="请输入员工姓名！"/> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">登录密码：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="password" size="20" class="textfield"  datatype="*" sucmsg="填写正确！" nullmsg="请输入密码！"/> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">用户类型：</span>&nbsp;&nbsp;</td>
									<td>
										<form:select path="userType" class="select">
							            	<form:option value="0">普通用户</form:option>
							            	<form:option value="1">系统管理员</form:option>
							            	<form:option value="2">入库员</form:option>
							            	<form:option value="3">出库员</form:option>
							            	<form:option value="4">主管</form:option>
							        	</form:select>
									</td>
								</tr>
								<c:if test="${!empty _Roles}">
									<tr>
										<td nowrap="nowrap" align="right" valign="top"><span class="blues">用户角色：</span>&nbsp;&nbsp;</td>
										<td>
												<table style="color:black;">
										        	<tr>
										          	<c:set var="_TypeNum" value="0"/>
									      			<c:forEach var="role" items="${_Roles}" varStatus="index">
														<c:if test="${_TypeNum!=0 && _TypeNum%4==0}">
															</tr><tr>
														</c:if>
														<td width="16%" nowrap="nowrap">
															<c:choose>
																<c:when test="${role.roleName == '全体用户'}">
																	<form:checkbox path="roleIds" value="${role.roleId}" checked="checked" onclick="return false;"/> ${role.roleName}
																</c:when>
																<c:otherwise>
																	<form:checkbox path="roleIds" value="${role.roleId}"/> ${role.roleName}
																</c:otherwise>
															</c:choose>
														</td>
														<c:set var="_TypeNum" value="${_TypeNum+1}"/>
													</c:forEach>
													<c:forEach begin="${_TypeNum%4}" end="3">
														<td>&nbsp;</td>
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
											<a href="javascript:void(0);" class="btn_blue" id="btn_sub"><span>提  交</span></a>
										</td>
										<td>
											<a href="javascript:void(0);" class="btn_red" onclick="window.close();"><span>关 闭</span></a>
										</td>
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

	<!--网站底部部分-->
	<div id="footer">上海慧智计算机技术有限公司 技术支持</div>

</body>
</html>
