<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑个人信息</title>
<script type="text/javascript" src="<c:url value="/"/>js/pngfix.js"></script>
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

<!--  Uploadify -->
<link href="<c:url value='/'/>js/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value='/'/>js/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<c:url value='/'/>js/uploadify/swfobject.js"></script>
<script type="text/javascript" src="<c:url value='/'/>js/upload.js"></script>
<script>
	$(function(){
		//$("#btn_sub").button();
		//$("#btn_clo").button();
		
		$("#personForm").Validform({
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
		
		//初始化附件上传
		initUploadImg("${_PhotoAttachment}","fileQueue1","uploadify1",false,1,"uploadPhotoAttach","fileList1","photoAttachmentDiv");
		
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
						<strong>编辑个人信息</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form:form commandName="personInforVo" id="personForm" action="/core/personInfor.htm?method=savePersonalInfor" method="post">
							<form:hidden path="personId"/>
							<form:hidden path="personName"/>
							<form:hidden path="personNo"/>
							<form:hidden path="positionLayer"/>
							<form:hidden path="deleted"/>
							<table width="800" border="0" cellpadding="0" cellspacing="0"
								class="doc">
								<tr>
									<td width="15%" align="right" nowrap="nowrap"><span class="blues">姓名：</span>&nbsp;&nbsp;</td>
									<td width="85%">
											${_PersonInfor.personName} 
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">工号：</span>&nbsp;&nbsp;</td>
									<td>
											${_PersonInfor.personNo}
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">性别：</span>&nbsp;&nbsp;</td>
									<td>
										<form:select path="gender" class="select">
				                      		<form:option value="0">男</form:option>
				                      		<form:option value="1">女</form:option>
				                      	</form:select> 
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">照片：</span>&nbsp;&nbsp;</td>
									<td>
										<div id="photoAttachmentDiv"></div>
										<input type="hidden" name="uploadPhotoAttach" id="uploadPhotoAttach" />
									</td>
								</tr>
								<tr>
									<td align="right"><span class="blues">所属部门：</span>&nbsp;&nbsp;</td>
									<td>
										${_PersonInfor.department.organizeName}
									</td>
								</tr>
								<tr>
									<td align="right"><span class="blues">所属班组：</span>&nbsp;&nbsp;</td>
									<td>
										${_PersonInfor.group.organizeName}
									</td>
								</tr>
								<tr>
									<td align="right"><span class="blues">岗位：</span>&nbsp;&nbsp;</td>
									<td>
										${_PersonInfor.structure.structureName}
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">手机：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="mobile" size="20" class="textfield" datatype="m" ignore="ignore" sucmsg="填写正确！" errormsg="请输入正确格式的手机号码，可以为空！" /> 
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">邮箱：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="email" size="20" class="textfield" ajaxurl="/core/personInfor.htm?method=valPersonEmail&personId=${_PersonInfor.personId}" datatype="e" ignore="ignore" sucmsg="填写正确！" errormsg="请输入正确格式的邮箱，可以为空！" /> 
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">邮箱密码：</span>&nbsp;&nbsp;</td>
									<td>
										<input type="password" size="21" name="emailPassword" id="emailPassword" value="${_PersonInfor.emailPassword}" class="textfield" datatype="*6-100" ignore="ignore" sucmsg="填写正确！" errormsg="邮箱密码至少为6位，可以为空！" /> 
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">出生日期：</span>&nbsp;&nbsp;</td>
									<td>
										<input name="birthday" id="birthday" size="20" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${_Birthday}" readonly="readonly" class="textfield" /> 
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">办公室地址：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="officeAddress" size="60" class="textfield" />  
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">办公室电话：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="officePhone" size="60" class="textfield"/>  <!--  ignore="ignore" datatype="(1(3|5|8)\d{9})|((0(\d{3}|\d{2})-)?(\d{8}|\d{7}))\s" sucmsg="填写正确！" errormsg="请输入正确的格式，如：021-888888，可以为空！"  -->
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">办公室邮编：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="officeCode" size="60" class="textfield" />  
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">家庭地址：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="homeAddress" size="60" class="textfield" />  
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">家庭电话：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="homePhone" size="60" class="textfield" />  
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">家庭邮编：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="postCode" size="60" class="textfield" />  
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">备注：</span>&nbsp;&nbsp;</td>
									<td>
										<form:textarea path="memo" cols="50" rows="5" class="textarea"/>
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

	<!--网站底部部分-->
	<%--<div id="footer">上海慧智计算机技术有限公司 技术支持</div>

--%></body>
</html>
