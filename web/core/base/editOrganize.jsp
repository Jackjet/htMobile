<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑部门[班组]信息
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</title>
<script type="text/javascript" src="<c:url value="/"/>js/pngfix.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/edit.css" />
<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="<c:url value="/"/>js/inc_javascript.js"
	type="text/javascript"></script>

<!-- 按钮样式 -->
<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/button.css" />
<script src="<c:url value='/'/>js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/'/>js/Validform_v5.3.2/css/style.css" />
<script>
	//验证
	$(document).ready(function(){
		showDirAndSupInfor();
		
		//表单验证
		$("#organizeForm").Validform({
			tiptype:3,
			btnSubmit:"#btn_sub",
			showAllError:true,
			beforeSubmit:function(curform){
			},
			callback:function(data){
				alert('信息编辑成功！');
				window.returnValue = "Y";
				window.close();
				window.opener.loadTab('/core/organizeInfor.htm?method=list', '4');
			}
		}); 
	});
	
	//提交数据
	var form = document.getElementById('organizeForm');
	function submitData() {
		alert('信息编辑成功！');
		window.returnValue = "refresh";
		window.close();
	}
	
	//显示或隐藏董事、监事信息
	function showDirAndSupInfor() {
		var levelId = document.getElementById('levelId');
		var directors = document.getElementById('directorsTr');
		var supervisors = document.getElementById('supervisorsTr');
		if (levelId.value == '4') {
			directors.style.display = '';
			supervisors.style.display = '';
		}else {
			directors.style.display = 'none';
			supervisors.style.display = 'none';
		}
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
						<strong>编辑部门[班组]信息</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form id="organizeForm" action="/core/organizeInfor.htm?method=save" method="post">
							<input type="hidden" name="organizeId" value="${_Organize.organizeId}"/>

							<table width="800" border="0" cellpadding="0" cellspacing="0"
								class="doc">
								<tr>
									<td width="15%" align="right" nowrap="nowrap"><span class="blues">部门[班组]名称：</span>&nbsp;&nbsp;</td>
									<td width="85%">
										<input type="text" id="organizeName" name="organizeName" size="20" value="${_Organize.organizeName}" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入部门[班组]名称！" /> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap"><span class="blues">部门简称：</span>&nbsp;&nbsp;</td>
									<td>
										<input type="text" id="shortName" name="shortName" size="20" value="${_Organize.shortName}" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入部门简称！" /> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap"><span class="blues">编号：</span>&nbsp;&nbsp;</td>
									<td>
										<input type="text" id="organizeCode" name="organizeCode" size="20" value="${_Organize.organizeCode}" class="textfield" /> 
									</td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap"><span class="blues">组织层级：${_Organize.levelId}</span>&nbsp;&nbsp;</td>
									<td>
										<select name="levelId" id="levelId" class="select"><!--  onchange="showDirAndSupInfor();" -->
											<option value="1">部门</option>
											<option value="2">班组</option>
											<option value="3">分公司</option>
											<option value="4">投资公司</option>
										</select>
										<script language="javaScript">
											var form = document.getElementById('organizeForm');
											getOptsValue(form.levelId,"${_Organize.levelId}");
										</script>
									</td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap"><span class="blues">排序编号：</span>&nbsp;&nbsp;</td>
									<td>
										<input type="text" id="orderNo" name="orderNo" size="20" value="${_Organize.orderNo}" class="textfield"  datatype="n" sucmsg="填写正确！" errormsg="请输入数字格式!" nullmsg="请输入排序编号！"/> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap"><span class="blues">所属部门[公司]：</span>&nbsp;&nbsp;</td>
									<td>
										<select name="parentId" class="select">
											<c:forEach items="${requestScope._TreeOrganizes}" var="organize"> 
												<c:choose>
													<c:when test="${_Organize.parent.organizeId == organize.organizeId}">
														<option value="${organize.organizeId}" selected="selected">
													</c:when>
													<c:otherwise>
														<option value="${organize.organizeId}">
													</c:otherwise>
												</c:choose>
													<c:forEach begin="0" end="${organize.layer}">&nbsp;</c:forEach>
													<c:if test="${organize.layer==1}"><b>+</b></c:if><c:if test="${organize.layer==2}"><b>-</b></c:if>${organize.organizeName}				
												</option>
											</c:forEach>
								        </select>
									</td>
								</tr>
								<tr>
									<td align="right" nowrap="nowrap"><span class="blues">部门经理[主管]：</span>&nbsp;&nbsp;</td>
									<td>
										<select name="directorId" class="select">
											<option value="">--请选择人员--</option>
											<c:forEach items="${requestScope._Persons}" var="person"> 
												<c:choose>
													<c:when test="${_Organize.director.personId == person.personId}">
														<option value="${person.personId}" selected="selected">
													</c:when>
													<c:otherwise>
														<option value="${person.personId}">
													</c:otherwise>
												</c:choose>
													${person.personName}		
												</option>
											</c:forEach>
								        </select>
									</td>
								</tr>
								<tr style="display: none;" id="directorsTr">
									<td align="right" nowrap="nowrap"><span class="blues">董事信息：</span>&nbsp;&nbsp;</td>
									<td>
										<textarea id="directors" name="directors" cols="25" rows="2" class="textarea">${_DirAndSup.directors}</textarea>
									</td>
								</tr>
								<tr style="display: none;" id="supervisorsTr">
									<td align="right" nowrap="nowrap"><span class="blues">监事信息：</span>&nbsp;&nbsp;</td>
									<td>
										<textarea id="supervisors" name="supervisors" cols="25" rows="2" class="textarea">${_DirAndSup.supervisors}</textarea>
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
											<a href="javascript:void(0);" class="btn_red" onclick="window.close();"><span>关 闭</span></a>
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
