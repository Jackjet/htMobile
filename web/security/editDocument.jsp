<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
<html>
<head>
<title>新增文档</title>
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
<script type="text/javascript" src="<c:url value='/'/>js/upload_security.js"></script>
<script>
	$(function(){
		$(".widget").button();
		initUpload("${_AttachStrs}","fileQueue","uploadify","uploadAttachment","fileList","uploadDiv",$("#savePath").val());
		
		$("#documentInforForm").Validform({
			tiptype:3,
			btnSubmit:"#btn_sub",
			showAllError:true,
			beforeSubmit:function(curform){
			},
			callback:function(data){
				alert('信息编辑成功！');
				window.returnValue = "Y";
				window.opener.loadFiles(${_Category.categoryId});
				window.close();
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
						<form id="documentInforForm" name="documentInforForm" action="/security/document.htm?method=saveDocument" method="post" enctype="multipart/form-data">
							<input type="hidden" name="categoryId" value="${_Category.categoryId}"/>
							<input type="hidden" name="savePath" id="savePath" value="${_Category.fullPath}"/>
							<table width="800" border="0" cellpadding="0" cellspacing="0"
								class="doc">
								
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">目录：</span>&nbsp;&nbsp;</td>
									<td>
										${_Category.categoryName}
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
