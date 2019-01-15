<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑入库单</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/edit.css" />
	<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script><!--jquery包-->
	<script src="<c:url value="/"/>js/inc_javascript.js" type="text/javascript"></script>
	<script src="<c:url value='/js'/>/additem.js"></script>
	
	<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/cupertino/jquery-ui-1.9.2.custom.css" />
	<script src="<c:url value="/"/>js/jquery.extend.js" type="text/javascript"></script><!--jquery扩展包-->
	<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/jquery.extend.css" />
	
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
	
	<!-- autoComplete
	<script type="text/javascript" src="<c:url value="/"/>js/jquery.autocomplete.js"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/"/>js/jquery.autocomplete.css" /> -->
	<style>
		#itemsTab{
			
		}
		#itemsTab th {
			 background-color:#f5fafe;
			 
		}
		#itemsTab td {
			 background-color:white;
			 padding-left:0px;
			 text-align:center;
		}
		#addTr{
			position:relative;
			top:2px;
			left:2px;
		}
	</style>
	<script>
		
		$(function(){
			$("#inOrderInforForm").Validform({
				tiptype:3,
				btnSubmit:"#btn_sub",
				showAllError:true,
				beforeSubmit:function(curform){
					//alert($("#buyerId").val());
					var orderNo = $("#inOrderNo").val().trim();
					if(orderNo == "采购订单"){
						alert("请补全订单号！");
						$("#inOrderNo").focus();
						return false;
					}
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
			
			
			//自动完成
			$("#buyerId").combobox();    
			$("#houseId").combobox();
			$("#supplierId").combobox();
			$("#itemIds0").combobox();
			
			//$( "#itemIds" ).toggle();
			//$( "#toggle" ).click(function() {      $( "#combobox" ).toggle();    });
			
			//$("#itemIds").on( "autocompleteselect", function( event, ui ) {
			//	alert(ui.value);
			//});
		});
		//自动完成
		function autoGetPersons(){
	    	$('#selBuyer').flushCache();
	       	$.getJSON("/core/personInfor.htm?method=getPersons" , function(data){
	        	//alert(data._Persons);
	       		$('#selBuyer').autocomplete(data._Persons, {
	                max: 10,    //列表里的条目数
	                minChars: 0,    //自动完成激活之前填入的最小字符
	                width: 300,     //提示的宽度，溢出隐藏
	                scrollHeight: 300,   //提示的高度，溢出显示滚动条
	                matchContains: true,    //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
	                selectFirst: true,   //自动选中第一个
	                autoFill: false,    //自动填充
	                formatItem: function(row, i, max) {
	                	var returnName = row.personName;
	                    return returnName+"  --部门："+row.department.organizeName;
	                },
	                formatMatch: function(row, i, max) {
	                    return row.personId+row.personName;
	                },
	                formatResult: function(row) {
	                    return row.personName;
	                }
	            }).result(function(event, row, formatted) {
	                $("#buyerId").val(row.personId);
	            });
	       	});
	    }
	    
	    //根据所选备件显示详情
	    function displayDetail(id,num){
	    	var values=$("#"+id).find("option:selected").attr("class");
	    	//alert("123"+checkText);
	    	var itemUnit = values.split("|")[0];
	    	var itemPrice = values.split("|")[1];
	    	var priceUnit = values.split("|")[2];
	    	
	    	$("#itemUnit"+num).text(itemUnit);
	    	$("#itemPrice"+num).text(itemPrice);
	    	$("#priceUnit"+num).text(priceUnit);
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
						<strong>编辑入库单</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<form:form commandName="inOrderInforVo" id="inOrderInforForm" action="/stock/inOrderInfor.htm?method=save" method="post">
							<form:hidden path="inOrderId"/>
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="doc" style="border:0px solid red;">
								<tr>
									<td width="15%" align="right" nowrap="nowrap"><span class="blues">订单号：</span>&nbsp;&nbsp;</td>
									<td width="85%">
										<c:if test="${_IsNew}">
											<form:input path="inOrderNo" size="31" value="采购订单" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入订单号！"/>
										</c:if>
										<c:if test="${!_IsNew}">
											<form:input path="inOrderNo" size="20" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入订单号！"/>
										</c:if>
										 &nbsp;
										<font color="red">*</font>
									</td>
								</tr>
								
								<tr>
									<td align="right"><span class="blues">仓库：</span>&nbsp;&nbsp;</td>
									<td>
										<form:select path="houseId" class="select" datatype="*" nullmsg="请选择仓库！" sucmsg="选择正确！">
				                      		<form:option value="">--选择仓库--</form:option>
				                      		<c:forEach items="${_WareHouses}" var="house">
												<form:option value="${house.houseId}">${house.houseName}</form:option>
											</c:forEach>
				                      	</form:select>
				                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<font color="red">*</font>
									</td>
								</tr>
								
								<tr>
									<td align="right"><span class="blues">采购人：</span>&nbsp;&nbsp;</td>
									<td>
										<form:select path="buyerId" class="select" datatype="*" nullmsg="请选择采购人！" sucmsg="选择正确！">
				                      		<form:option value="">--选择采购人--</form:option>
				                      		<c:forEach items="${_Persons}" var="person">
												<form:option value="${person.personId}">${person.personName}</form:option>
											</c:forEach>
				                      	</form:select>
				                      	<!--<input type="input" id="selBuyer" name="selBuyer" class="textfield"/>-->
				                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<font color="red">*</font>
									</td>
								</tr>
								
								<tr>
									<td align="right"><span class="blues">供应商：</span>&nbsp;&nbsp;</td>
									<td>
										<form:select path="supplierId" class="select" datatype="*" ignore="ignore" nullmsg="请选择供应商！" sucmsg="选择正确！">
				                      		<form:option value="">--选择供应商--</form:option>
				                      		<c:forEach items="${_Suppliers}" var="supplier">
												<form:option value="${supplier.supplierId}">${supplier.supplierName}</form:option>
											</c:forEach>
				                      	</form:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<!--<font color="red">*</font>-->
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">到期日期：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="becomeDateStr" size="20" class="textfield" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${_BecomeDate}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">订单总金额（含税）：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="amount" size="20" class="textfield" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" ignore="ignore" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入单价" />
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">订单总金额（去税）：</span>&nbsp;&nbsp;</td>
									<td>
										<form:input path="pureAmount" size="20" class="textfield" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" ignore="ignore" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入单价" />
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
								
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">备注：</span>&nbsp;&nbsp;</td>
									<td>
										<form:textarea path="memo" cols="50" rows="5" class="textarea"/>
										<br/><br/>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" align="right"><span class="blues">备件：</span>&nbsp;&nbsp;</td>
									<td style="vertical-align:bottom;border:0px solid red;">
										<table id="itemsTab" width="90%" border="0" cellpadding="0" cellspacing="1" bgcolor="#b4c5cd" class="doc">
											<tr>
												<th>序号</th>
												<th style="width:100px;">备件</th>
												<th>单位</th>
												<th>申请数量</th>
												<th>采购数量</th>
												<th>接收数量</th>
												<th>单价</th>
												<th>到期日</th>
												<th>总金额</th>
											</tr>
											<tr id="tr0">
												<td></td>
												<td width="20%">
													<select name="itemIds0" id="itemIds0" datatype="*" nullmsg="请选择备件！" sucmsg=" " onchange="displayDetail('itemIds0','0');" >
							                      		<option value="">--选择备件--</option>
							                      		<c:forEach items="${_Items}" var="item">
															<option value="${item.itemId}" class="${item.itemUnit}|${item.itemPrice}|${item.priceUnit}">【${item.itemCode}】${item.itemName}</option>
														</c:forEach>
							                      	</select>
												</td>
												<td>
													<!--<input type="input" class="textfield" size="5" />-->
													<span id="itemUnit0"></span>
												</td>
												<td>
													<input type="input" class="textfield" size="5" name="buyCount0" value="1" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg=" " errormsg="请输入正确格式" nullmsg="请输入申请数量" />
												</td>
												<td>
													<input type="input" class="textfield" size="5" name="applyCount0" value="1" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg=" " errormsg="请输入正确格式" nullmsg="请输入采购数量" />
												</td>
												<td>
													<input type="input" class="textfield" size="5" name="acceptCount0" value="1" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg=" " errormsg="请输入正确格式" nullmsg="请输入接收数量" />
												</td>
												<td>
													<span id="itemPrice0"></span>&nbsp;(<span id="priceUnit0"></span>)
												</td>
												<td>
													<input type="input" class="Wdate textfield" size="12" name="becomeDate0" readonly="true" datatype="*" sucmsg=" " nullmsg="请选择到期日期！" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
												</td>
												<td>
													<input type="input" class="textfield" size="8" name="acceptCount0" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg=" " errormsg="请输入正确格式" nullmsg="请输入总金额" />
												</td>
											</tr>
											<tbody></tbody>
										</table>
										<br/>
										<a href="javascript:void(0);" class="btn_blue" id="addtr" onclick="addtr('itemsBody');">
											<span>增加一行</span>
										</a>
										<span id="itemsBody"></span>
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
