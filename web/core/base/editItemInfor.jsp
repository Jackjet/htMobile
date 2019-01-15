<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑物品信息</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/edit.css" />
<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script><!--jquery包-->
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
		$("#itemInforForm").Validform({
			tiptype:3,
			btnSubmit:"#btn_sub",
			showAllError:true,
			beforeSubmit:function(curform){
			},
			callback:function(data){
				alert('信息编辑成功！');
				window.returnValue = "Y";
				//window.opener.location.reload();
				if("${_Layer}" == "1"){
					window.opener.loadTab('listItemInfor.jsp?layer=1', '10');
				}
				if("${_Layer}" == "2"){
					window.opener.loadTab('listItemInfor.jsp?layer=2', '101');
				}
				
				window.close();
			}
		});  
		
		//初始化附件上传
		//initUploadImg("${_PhotoAttachment}","fileQueue1","uploadify1",false,1,"uploadPhotoAttach","fileList1","photoAttachmentDiv");
	});
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
						<strong>编辑物品信息</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form:form commandName="itemInforVo" id="itemInforForm" action="/stock/itemInfor.htm?method=save" method="post">
							<form:hidden path="itemId"/>
							<form:hidden path="layer"/>
							<table width="800" border="0" cellpadding="0" cellspacing="0"
								class="doc">
								<tr>
									<td width="15%" align="right" nowrap="nowrap"><span class="blues">物品名称：</span>&nbsp;&nbsp;</td>
									<td width="85%">
										<form:input path="itemName" size="20" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入物品名称！"/> 
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">物品编码：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="itemCode" size="20" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入物品编码！" />
										<font color="red">*</font> 
									</td>
								</tr>
								
								<c:if test="${_Layer == 2}">
									<tr>
										<td align="right"><span class="blues">所属大类：</span>&nbsp;&nbsp;</td>
										<td>
											<form:select path="parentId" class="select" datatype="*" nullmsg="请选择大类！" sucmsg="选择正确！">
					                      		<form:option value="">--选择大类--</form:option>
					                      		<c:forEach items="${_BigCategorys}" var="item">
													<form:option value="${item.itemId}">【${item.itemCode}】${item.itemName}</form:option>
												</c:forEach>
					                      	</form:select>
											<font color="red">*</font>
										</td>
									</tr>
								</c:if>
								
								<c:if test="${_Layer == 3}">
									<tr>
										<td align="right"><span class="blues">所属小类：</span>&nbsp;&nbsp;</td>
										<td>
											<form:select path="parentId" class="select" datatype="*" nullmsg="请选择小类！" sucmsg="选择正确！">
					                      		<form:option value="">--选择小类--</form:option>
					                      		<c:forEach items="${_SmallItems}" var="item">
													<form:option value="${item.itemId}">【${item.itemCode}】${item.itemName}</form:option>
												</c:forEach>
					                      	</form:select>
											<font color="red">*</font>
										</td>
									</tr>
								</c:if>
								
								<!--<tr>
									<td align="right"><span class="blues">供应商：</span>&nbsp;&nbsp;</td>
									<td>
										<form:select path="supplierId" class="select" datatype="*" ignore="ignore" nullmsg="请选择供应商！" sucmsg="选择正确！">
				                      		<form:option value="">--选择供应商--</form:option>
				                      		<c:forEach items="${_Suppliers}" var="supplier">
												<form:option value="${supplier.supplierId}">${supplier.supplierName}</form:option>
											</c:forEach>
				                      	</form:select>
										<font color="red">*</font>
									</td>
								</tr>
								--><tr>
									<td nowrap="nowrap" align="right"><span class="blues">单位：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="itemUnit" size="20" class="textfield" />
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">规格/型号：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="itemModel" size="20" class="textfield" />
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">批号：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="batchNo" size="30" class="textfield" />
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">品牌：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="itemBrand" size="20" class="textfield" />
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">生产厂家：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="factory" size="20" class="textfield" />
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">单价：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="itemPrice" size="20" class="textfield" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" ignore="ignore" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入单价" />
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">价格单位：</span>&nbsp;&nbsp;</td>
									<td>
										<form:select path="priceUnit" class="select">
				                      		<form:option value="">无</form:option>
				                      		<form:option value="CNY">CNY</form:option>
				                      		<form:option value="US$">US$</form:option>
				                      	</form:select> 
									</td>
								</tr>
								
								<c:if test="${_Layer == 3}">
									<tr>
										<td nowrap="nowrap" align="right"><span class="blues">库存量：</span>&nbsp;&nbsp;</td>
										<td>
											<c:if test="${_IsNew}">
												<form:input path="count" size="20" class="textfield" datatype="n" sucmsg="填写正确！" errormsg="请输入数字格式" nullmsg="请输入库存量"/> 
												<font color="red">*</font>
											</c:if>
											<c:if test="${!_IsNew}">
												<input type="hidden" name="count" value="${_Count}" />
												<span>${_Count}</span>
											</c:if>
										</td>
									</tr>
								</c:if>
								
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">显示次序：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="displayOrder" size="20" class="textfield" datatype="n" ignore="ignore" sucmsg="填写正确！" errormsg="请输入数字格式" nullmsg="请输入显示次序" />
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
