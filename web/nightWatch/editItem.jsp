<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
<html>
<head>
<title>编辑小项</title>
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
	<script>
	
		$(function(){
			$(".widget").button();
			//表单验证
			$("#nwItemForm").Validform({
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
<!--<body onload="init();">-->
<body>
	<div id="wrap">

		<!--网站主题部分-->
		<div id="right">
			<div class="emil"></div>
			<div class="module">
				<div class="content">
					<div class="xinxi">
						<strong>编辑小项</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form:form commandName="nwItemVo" id="nwItemForm" name="nwItemForm" action="/nightWatch/item.htm?method=save" method="post">
							<form:hidden path="xId"/>  
							<table width="800" border="0" cellpadding="0" cellspacing="0"
								class="doc">
								<tr>
									<td width="15%" align="right" nowrap="nowrap"><span class="blues">小项名称：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="itemName" size="20" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入小项名称！" /> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">小项编号：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="itemCode" size="20" class="textfield"/> 
									</td>
								</tr>
								
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">显示次序：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="orderNo" size="20" class="textfield" datatype="n" sucmsg="填写正确！" errormsg="请输入数字类型！" nullmsg="请输入显示次序！" /> 
										<font color="red">*</font>
									</td>
								</tr>
								
								
							<div id="buttons">
								<table width="300" border="0" align="center" cellpadding="0"
									cellspacing="0">
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
