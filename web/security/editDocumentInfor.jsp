<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
<html>
<head>
<title>编辑文档信息</title>
<script type="text/javascript" src="<c:url value="/"/>js/pngfix.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/edit.css" />
<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script> <!--jquery包-->
<script src="<c:url value="/"/>js/inc_javascript.js" type="text/javascript"></script>

	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/gkSecure/jquery-ui-1.9.2.custom.css" />
	<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
	
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
		$(".widget").button();
		initUpload("${_AttachStrs}","fileQueue","uploadify","uploadAttachment","fileList","uploadDiv");
		
		$("#documentInforForm").Validform({
			tiptype:3,
			btnSubmit:"#btn_sub",
			showAllError:true,
			beforeSubmit:function(curform){
			},
			callback:function(data){
				alert('信息编辑成功！');
				window.returnValue = "Y";
				window.close();
				window.opener.location.reload();
				//window.location.href="/security/document.htm?method=view&rowId="+rowId;
			}
		});  
		
	});
</script>

</head>
<base target="_self" />
<body>
	<div id="wrap">

		<!--网站主题部分-->
		<div id="right">
			<div class="emil"></div>
			<div class="module">
				<div class="content">
					<div class="xinxi">
						<strong>编辑文档信息</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form:form commandName="documentInforVo" id="documentInforForm" name="documentInforForm" action="/security/document.htm?method=save" method="post" enctype="multipart/form-data">
							<form:hidden path="documentId"/>
							<table width="800" border="0" cellpadding="0" cellspacing="0"
								class="doc">
								<tr>
									<td width="15%" align="right" nowrap="nowrap"><span class="blues">标题：</span>&nbsp;&nbsp;</td>
									<td width="85%">
										<form:input path="documentTitle" size="70" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入标题！" />
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">关键字：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="keyWord" size="20" class="textfield" /> 
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">分类：</span>&nbsp;&nbsp;</td>
									<td>
										<form:select path="categoryId" class="select" datatype="*" sucmsg="选择正确！" nullmsg="请选择分类！">
									       <form:option value="">--选择分类--</form:option>
									       <c:forEach items="${_TREE}" var="category">
									           <form:option value="${category.categoryId}"><c:forEach begin="0" end="${category.layer}">&nbsp;</c:forEach><c:if test="${category.layer==1}"><b>+</b></c:if><c:if test="${category.layer==2}"><b>-</b></c:if>${category.categoryName}</form:option>
									       </c:forEach>
									    </form:select> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">附件：</span>&nbsp;&nbsp;</td>
									<td>
										<div id="uploadDiv"></div>
										<input type="hidden" name="uploadAttachment" id="uploadAttachment" />
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
											<a href="javascript:void(0);" class="widget" id="btn_sub"><span>提 交</span></a>
										</td>
										<td>
											<a href="javascript:void(0);" class="widget" onclick="window.close();"><span>关 闭</span></a>
										</td>
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
