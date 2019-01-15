<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑岗位信息</title>
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
	$(function(){
		//表单验证
		$("#structureForm").Validform({
			tiptype:3,
			btnSubmit:"#btn_sub",
			showAllError:true,
			beforeSubmit:function(curform){
			},
			callback:function(data){
				alert('信息编辑成功！');
				window.returnValue = "Y";
				window.close();
				window.opener.loadTab('/core/structureInfor.htm?method=list', '5');
			}
		}); 
		
	});
</script>
<script>
	//提交数据
	function submitData() {
		alert('信息编辑成功！');
		window.returnValue = "refresh";
		window.close();
		window.opener.location.reload();
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
						<strong>编辑岗位信息</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form id="structureForm" action="/core/structureInfor.htm?method=save" method="post">
							<input type="hidden" name="structureId" value="${_Structure.structureId}"/>

							<table width="800" border="0" cellpadding="0" cellspacing="0"
								class="doc">
								<tr>
									<td width="15%" align="right" nowrap="nowrap"><span class="blues">岗位名称：</span>&nbsp;&nbsp;</td>
									<td width="85%">
										<input type="text" id="structureName" name="structureName" size="20" value="${_Structure.structureName}" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入岗位名称！" /> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">排序编号：</span>&nbsp;&nbsp;</td>
									<td>
										<input type="text" id="orderNo" name="orderNo" size="20" value="${_Structure.orderNo}" class="textfield" datatype="n" sucmsg="填写正确！" errormsg="请输入数字格式!" nullmsg="请输入排序编号！" /> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">所属岗位：</span>&nbsp;&nbsp;</td>
									<td>
										<select name="parentId" class="select">
											<c:forEach items="${requestScope._TreeStructures}" var="structure" varStatus="status"> 
												<c:choose>
													<c:when test="${_Structure.parent.structureId == structure.structureId}">
														<option value="${structure.structureId}" selected="selected">
													</c:when>
													<c:otherwise>
														<option value="${structure.structureId}">
													</c:otherwise>
												</c:choose>
													<c:forEach begin="0" end="${structure.layer}">&nbsp;</c:forEach>
													<c:if test="${structure.layer==1}"><b>+</b></c:if><c:if test="${structure.layer==2}"><b>-</b></c:if>${structure.structureName}				
												</option>
											</c:forEach>
								        </select> 
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
