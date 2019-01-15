<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
<html>
<head>
<title>编辑大项</title>
	<script type="text/javascript" src="<c:url value="/"/>js/pngfix.js"></script>
	
	<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="../js/jquery-ui-1.9.2.custom/js/jquery-1.8.3.js" type="text/javascript"></script> <!--jquery包-->
	<script src="<c:url value="/"/>js/inc_javascript.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/edit.css" />
	
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/gkSecure/jquery-ui-1.9.2.custom.css" />
	<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
	
	<!-- 表单验证 -->
	<script type="text/javascript" src="../js/Validform_v5.3.2/Validform_v5.3.2.js"></script>
	<link rel="stylesheet" type="text/css" href="../js/Validform_v5.3.2/css/style.css" />
	<script>
		$(function(){
			$(".widget").button();
			//表单验证
			$(".bigCategoryForm").Validform({
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
				}
			});  
			
			
		});
		
		//提交数据
		function submitData() {
			alert('信息编辑成功');
			window.returnValue = "Y";
			window.close();
		}		
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
						<strong>编辑大项</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form:form commandName="bigCategoryVo" cssClass="bigCategoryForm"  name="bigCategoryForm" action="/patrol/bigCategory.htm?method=save" method="post">
							<form:hidden path="xId"/>   
							<table width="800" border="0" cellpadding="0" cellspacing="0" class="doc">
								<tr>
									<td width="15%" align="right" nowrap="nowrap"><span class="blues">大项名称：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="categoryName" size="20" cssClass="textfield" /> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">大项编号：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="categoryCode" size="20"  cssClass="textfield"/> 
									</td>
								</tr>
								
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">显示次序：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="orderNo" size="20" cssClass="textfield"/> 
										<font color="red">*</font>
									</td>
								</tr>	
							</table>
							<div id="buttons">
								<table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
									<tr>
										<td height="12"></td>
										<td height="12"></td>
									</tr>
									<tr>
										<td>
											<a id="btn_sub" href="javascript:void(0);" class="widget"><span>提 交</span></a>
										</td>
										<td>
											<a href="javascript:void(0);" class="widget" onclick="window.close();"><span>关 闭</span></a>
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
