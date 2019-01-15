<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑角色信息</title>
<script type="text/javascript" src="<c:url value="/"/>js/pngfix.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/edit.css" />
<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="<c:url value="/"/>js/inc_javascript.js" type="text/javascript"></script>

<!-- 按钮样式 -->
<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/button.css" />
<!-- 表单验证 -->
<script src="<c:url value='/'/>js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/'/>js/Validform_v5.3.2/css/style.css" />
<script>
	$(function(){
		//表单验证
		$("#roleInforForm").Validform({
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
				window.opener.loadTab('listRole.jsp', '3');
			}
		}); 
		
		//勾选选中的用户
		var personIds = document.getElementsByName('personIds');
		<c:forEach var="personId" items="${_PersonIds}">
			var tmpPersonId = '${personId}';
			if (personIds != null && personIds.length > 0) {
				for (var i=0;i<personIds.length;i++) {
					var personId = personIds[i];
					if (tmpPersonId == personId.value) {
						personId.checked = true;
					}
				}
			}
		</c:forEach>
	});
</script>
<script>
	//提交数据
	function submitData() {
		alert('信息编辑成功！');
		window.returnValue = "refresh";
		window.close();
	}
	
	var i=0,number;
	//全选操作
	function selectUserId(checkbox,organizeId){
		var isChecked = false;
		if(checkbox.checked){
			isChecked = true;
		}
		var obj;
		obj = document.roleInforForm.personIds;		
		if(obj!=null){
			if(obj.length==null){
				//只有一个,则只需要判断该用户是不是这个分类
				<c:forEach var="user" items="${_Users}" varStatus="index">
					var tempOrganizeId = '${user.person.department.organizeId}';
					if(organizeId==tempOrganizeId){
						obj.checked = isChecked;
					}
				</c:forEach>
				
				<c:forEach var="user" items="${_OtherUsers}" varStatus="index">
					var tempOrganizeId = '${user.person.department.organizeId}';
						
					if(organizeId==tempOrganizeId){
						obj.checked = isChecked;
					}
				</c:forEach>
			}else{
				//多个用户
				var personNum;
				personNum = 0;
				var personNum = obj.length;			
				for(var k = 0; k<personNum;k++){
					var userId;
					personId = obj[k].value;
					<c:forEach var="user" items="${_Users}" varStatus="index">
						var tempOrganizeId = '${user.person.department.organizeId}';
						var tempPersonId = '${user.person.personId}';
							
						if(organizeId==tempOrganizeId && tempPersonId==personId){
							obj[k].checked = isChecked;
						}
					</c:forEach>
						
					<c:forEach var="user" items="${_OtherUsers}" varStatus="index">
						var tempOrganizeId = '${user.person.department.organizeId}';
						var tempPersonId = '${user.person.personId}';
							
						if(organizeId==tempOrganizeId && tempPersonId==personId){
							obj[k].checked = isChecked;
						}
					</c:forEach>
				}
			}				
		}
	}
	//显示或隐藏用户信息
	function hdUsers() {
		var form = document.roleInforForm;
		var roleType = form.roleType.value;
		var users = document.getElementById('users');
		
		if (roleType == 0) {
			users.style.display = '';
		}else {
			users.style.display = 'none';
		}
	}
</script>

</head>
<base target="_self" />
<body onload="hdUsers();">
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
						<strong>编辑角色</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form:form commandName="roleInforVo" id="roleInforForm" name="roleInforForm" action="/core/roleInfor.htm?method=save" method="post">
							<form:hidden path="roleId"/>
							<table width="800" border="0" cellpadding="0" cellspacing="0"
								class="doc">
								<tr>
									<td width="15%" align="right" nowrap="nowrap"><span class="blues">角色名称：</span>&nbsp;&nbsp;</td>
									<td width="85%">
										<form:input path="roleName" size="20" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入角色名！" /> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">可否删除：</span>&nbsp;&nbsp;</td>
									<td>
										<form:select path="fixed" class="select">
										  <form:option value="false">可以</form:option>
										  <form:option value="true">不可以</form:option>
									   	</form:select>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">角色类型：</span>&nbsp;&nbsp;</td>
									<td>
										<form:select path="roleType" class="select" onchange="hdUsers();">
										  <form:option value="0">自定义</form:option>
										  <form:option value="1">全体用户</form:option>
									   	</form:select>
									</td>
								</tr>
								
								<script language="javaScript">
									//定义一个数组，记录各个数据点击的次数
									var clickTimes =new Array();	
								</script>	
								
								<c:if test="${!empty _Users}">
									<tr id="users">
										<td nowrap="nowrap" align="right" valign="top"><span class="blues">包含用户：</span>&nbsp;&nbsp;</td>
										<td>
											<table width="100%" style="color:black;">
												<c:set var="_Num" value="0"/>
												<c:forEach var="department" items="${_Departments}">
									                <tr height="33">
									                   <td valign="bottom" width="100%" style="border-bottom:1px dotted #888888;font-size:10pt" colspan="2">
									                     
														  <script language="javaScript">
															clickTimes[${_Num}] = 0;							
														  </script>	
									                        ${department.organizeName}&nbsp;	                            				
															<span onclick="show_list('${_Num}')">
																<img name="img" src="<c:url value="${'/images'}"/>/xpexpand3_s.gif" style="margin-top:0px;margin-bottom:0px;"/>
															</span>
														</td>
										      		</tr>
															
													<tr name="tr" style="display:none;padding-top:10px;">
														<td width="9%" align="right" valign="top">
										      				<input type="checkbox" onclick="selectUserId(this,'${department.organizeId}')"/>全选
										      			</td>
										      			<td style="padding-left:10px;" width="92%">
															<table>
										      					<tr>
										      						<c:set var="_TypeNum" value="0"/>
										      						<c:forEach var="user" items="${_Users}" varStatus="index">
										      							<c:if test="${user.person.department.organizeId==department.organizeId}">
																			<c:if test="${_TypeNum!=0 && _TypeNum%5==0}">
																				</tr><tr>
																			</c:if>
																			<td valign="top" nowrap="nowrap">
																				<input type="checkbox" name="personIds" value="${user.personId}"/> ${user.person.personName}
																				&nbsp;&nbsp;
																			</td>				
																			<c:set var="_TypeNum" value="${_TypeNum+1}"/>
																		</c:if>																			
																	</c:forEach>
																	<c:forEach begin="${_TypeNum%5}" end="4">
																		<td>&nbsp;</td>
																	</c:forEach>		      											
																</tr>
																
																<tr>
										      						<c:set var="_TypeNum" value="0"/>
										      						<c:forEach var="user" items="${_OtherUsers}" varStatus="index">
										      							<c:if test="${user.person.department.organizeId==department.organizeId}">
																			<c:if test="${_TypeNum!=0 && _TypeNum%5==0}">
																				</tr><tr>
																			</c:if>
																			<td valign="top" nowrap="nowrap">
																				<input type="checkbox" name="personIds" value="${user.personId}"/> ${user.person.personName}
																				&nbsp;&nbsp;
																			</td>					
																			<c:set var="_TypeNum" value="${_TypeNum+1}"/>
																		</c:if>																			
																	</c:forEach>
																	<c:forEach begin="${_TypeNum%5}" end="4">
																		<td>&nbsp;</td>
																	</c:forEach>		      											
																</tr>
															</table>
														</td>
										      		</tr>
									                <c:set var="_Num" value="${_Num+1}"/>
												</c:forEach>	
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
