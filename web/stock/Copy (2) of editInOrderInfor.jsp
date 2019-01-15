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
	<script src="<c:url value="/"/>js/jquery.extend.remote.js" type="text/javascript"></script><!--jquery扩展包-->
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
		#itemsTab th ,#itemsTab-real th{
			 background-color:#f5fafe;
			 
		}
		#itemsTab td,#itemsTab-real td {
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
			$("#tabs").tabs();
			$("#inOrderInforForm").Validform({
				tiptype:3,
				btnSubmit:"#btn_sub",
				showAllError:true,
				beforeSubmit:function(curform){
					/*var orderNo = $("#inOrderNo").val();
					if(orderNo == "采购订单"){
						alert("请补全订单号！");
						$("#inOrderNo").focus();
						return false;
					}*/
					
					//判断是否添加了备件，如果没有，不能提交
					var hasItems = 0;
					$("#itemsTab-real .contentTr").each(function(i,n){
						hasItems = 1;
					});
					if(hasItems == 0){
						alert("请添加备件！");
						return false;
					}
				},
				callback:function(data){
					alert('信息编辑成功！');
					window.returnValue = "Y";
					//window.opener.location.reload();
					
					window.close();
				}
				
				
			});  
			
			$("#itemForm").Validform({
				tiptype:3,
				btnSubmit:"#addtr",
				showAllError:true,
				beforeSubmit:function(curform){
					addItem();
					return false;
				},
				callback:function(data){
					alert('信息编辑成功！');
				}
				
			});  
			
			
			//初始化附件上传
			//initUploadImg("${_PhotoAttachment}","fileQueue1","uploadify1",false,1,"uploadPhotoAttach","fileList1","photoAttachmentDiv");
			
			
			//自动完成
			$("#buyerId").combobox();    
			$("#houseId").combobox();
			$("#supplierId").combobox();
			$("#itemId").comboboxRemote();
			//$("#smallItemId").combobox();
			//$( "#itemId" ).toggle();
		});
	    
	    //根据所选备件显示详情
	    function displayDetail(id){
	    	var values=$("#"+id).find("option:selected").attr("class");
	    	//alert("123"+checkText);
	    	var itemUnit = values.split("|")[0];
	    	var itemPrice = values.split("|")[1];
	    	var priceUnit = values.split("|")[2];
	    	
	    	$("#itemUnit").text(itemUnit);
	    	$("#inItemPrice").val(itemPrice);
	    	$("#itemPriceUnit").text(priceUnit);
	    }
	    
	    //根据小类得到备件
	    function getItems(id){
	   		var smallItemId=$("#"+id).find("option:selected").attr("value");
	   		//alert(smallItemId);
	    	$.getJSON("/stock/itemInfor.htm?method=getItemsBySmall&smallItemId="+smallItemId,function(data) {
		           var options = "";
		           $.each(data._Items, function(i, n) {
		               //options += "<option value='"+n.supplier.supplierId+"'  class='"+n.restCount+"'>"+n.supplier.supplierName+"</option>";
		               options += "<option value='"+n.itemId+"' class='"+n.itemUnit+"|"+n.itemPrice+"|"+n.priceUnit+"|"+n.count+"'>【"+n.itemCode+"】"+n.itemName+"</option>";
		               //+"|"+n.inOrderItemId+"   
		           });   
		           $('#itemId').html(options);   
		        }   
		    );
	    }
	    
	    //增加备件
	    function addItem(){
	    	//if($("#itemForm").Validform()){
	    		var itemName=$("#itemId").children( ":selected" ).text();
		    	var itemId=$("#itemId").val();
		    	var itemUnit = $("#itemUnit").text();
		    	var inItemPrice = $("#inItemPrice").val();
		    	var itemPriceUnit = $("#itemPriceUnit").text();
		    	var applyCount = $("#applyCount").val();
		    	var buyCount = $("#buyCount").val();
		    	var acceptCount = $("#acceptCount").val();
		    	var becomeDate = $("#becomeDate").val();
		    	var itemAmount = $("#itemAmount").val();
		    	
		    	addtr("itemsTab",itemId,itemName,itemUnit,applyCount,buyCount,acceptCount,inItemPrice,itemPriceUnit,becomeDate,itemAmount);
		    	//addtr("itemsTab-real",itemId,itemName,itemUnit,applyCount,buyCount,acceptCount,itemPrice,itemPriceUnit,becomeDate,itemAmount);
		    	
		    	/*var itemName=$("#itemName").val("");
		    	var itemId=$("#itemId").val("");
		    	var itemUnit = $("#itemUnit").text("");
		    	var itemPrice = $("#itemPrice").text("");
		    	var itemPriceUnit = $("#itemPriceUnit").text("");
		    	var applyCount = $("#applyCount").val("1");
		    	var buyCount = $("#buyCount").val("1");
		    	var acceptCount = $("#acceptCount").val("1");
		    	var becomeDate = $("#becomeDate").val("");
		    	var itemAmount = $("#itemAmount").val("");*/
		    	
		    	$("#itemForm").Validform().resetForm();
		    	//alert($("#itemsTab-real .itemAmounts").val());
		    	//计算目前订单总金额
		    	var amount = 0;
		    	$("#itemsTab-real .itemAmounts").each(function(i,n){
		    		//alert($(this).val());
		    		amount += parseFloat($(this).val());
		    	});
		    	amount = amount.toFixed(4);
		    	$("#amount").val(amount);
	    	//}
	    	
	    }
	    
	    //同时删除两个表格中的行
	    function deleteTr(num){
	    	//alert("1:"+$("#itemsTabTr"+num).html());
	    	//alert("2:"+$("#itemsTab-realTr"+num).html());
	     	//$("#itemsTab-realTr"+num).remove();
	     	$(".itemsTabTr"+num).remove();
	     }
	     
	     //计算备件金额
	     function countAmount(id){
	     	var count = $("#"+id).val();
	     	var inItemPrice = $("#inItemPrice").val();
	     	
	     	var itemAmount = count * inItemPrice;
	     	//alert(itemAmount);
	     	$("#itemAmount").val(itemAmount);
	     }
	     
	     function setSameCount(id,id1,id2){
	     	$("#"+id1).val($("#"+id).val());
	     	$("#"+id2).val($("#"+id).val());
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
						<div id="tabs">
							<ul>
					    		<li><a href="#tabs-1">订单信息</a></li>
					    		<li><a href="#tabs-2">备件</a></li>
					  		</ul>
					  		<div id="tabs-1">
					  			<form:form commandName="inOrderInforVo" id="inOrderInforForm" action="/stock/inOrderInfor.htm?method=save" method="post">
									<form:hidden path="inOrderId"/>
									<table width="100%" border="0" cellpadding="0" cellspacing="0" class="doc" style="border:0px solid red;">
										<tr>
											<td width="15%" align="right" nowrap="nowrap"><span class="blues">订单描述：</span>&nbsp;&nbsp;</td>
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
						                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
						                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<font color="red">*</font>
											</td>
										</tr>
										
										<tr>
											<td align="right"><span class="blues">供应商：</span>&nbsp;&nbsp;</td>
											<td>
												<form:select path="supplierId" class="select" datatype="*" nullmsg="请选择供应商！" sucmsg="选择正确！">
						                      		<form:option value="">--选择供应商--</form:option>
						                      		<c:forEach items="${_Suppliers}" var="supplier">
														<form:option value="${supplier.supplierId}">${supplier.supplierName}</form:option>
													</c:forEach>
						                      	</form:select>
												<!--<font color="red">*</font>-->
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<font color="red">*</font>
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
												<form:input path="amount" size="20" class="textfield" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入订单总金额（含税）" />
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap" align="right"><span class="blues">订单总金额（去税）：</span>&nbsp;&nbsp;</td>
											<td>
												<form:input path="pureAmount" size="20" class="textfield" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" ignore="ignore" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入订单总金额（去税）" />
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
												<form:textarea path="memo" cols="80" rows="5" class="textarea"/>
												<br/><br/>
											</td>
										</tr>
		
										<tr>
											<td nowrap="nowrap" align="right"><span class="blues">备件：</span>&nbsp;&nbsp;</td>
											<td style="vertical-align:bottom;border:0px solid red;">
												<table id="itemsTab-real" width="90%" border="0" cellpadding="0" cellspacing="1" bgcolor="#b4c5cd" class="doc">
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
														<th>操作</th>
													</tr>
													<!--<tr id="tr0">
														<td></td>
														<td>
															<span id="itemName0"></span>
															<input type="hidden" name="itemId0" id="itemId0" />
														</td>
														<td>
															<span id="itemUnit0"></span>
														</td>
														<td>
															<input type="input" class="textfield" size="5" name="applyCount0" readonly="readonly" />
														</td>
														<td>
															<input type="input" class="textfield" size="5" name="buyCount0" readonly="readonly" />
														</td>
														<td>
															<input type="input" class="textfield" size="5" name="acceptCount0" readonly="readonly" />
														</td>
														<td>
															<span id="itemPrice0"></span>&nbsp;(<span id="priceUnit0"></span>)
														</td>
														<td>
															<input type="input" class="textfield" size="12" name="becomeDate0" readonly="true" />
														</td>
														<td>
															<input type="input" class="textfield" size="10" name="amount0" readonly="true" />
														</td>
													</tr>
													--><tbody></tbody>
												</table>
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
					  		<div id="tabs-2" style="text-align:center;">
					  			<form name="itemForm" id="itemForm" action="" method="post">
									<!-- <div style="width:60%;height:auto;border:1px solid silver;"> -->
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="doc" style="border:0px solid red;">
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
															<th>操作</th>
														</tr>
														<!--<tr id="tr0">
															<td></td>
															<td>
																<span id="itemName0"></span>
																<input type="hidden" name="itemId0" id="itemId0" />
															</td>
															<td>
																<span id="itemUnit0"></span>
															</td>
															<td>
																<input type="input" class="textfield" size="5" name="applyCount0" readonly="readonly" />
															</td>
															<td>
																<input type="input" class="textfield" size="5" name="buyCount0" readonly="readonly" />
															</td>
															<td>
																<input type="input" class="textfield" size="5" name="acceptCount0" readonly="readonly" />
															</td>
															<td>
																<span id="itemPrice0"></span>&nbsp;(<span id="priceUnit0"></span>)
															</td>
															<td>
																<input type="input" class="textfield" size="12" name="becomeDate0" readonly="true" />
															</td>
															<td>
																<input type="input" class="textfield" size="10" name="amount0" readonly="true" />
															</td>
														</tr>
														--><tbody></tbody>
													</table>
												</td>
											</tr>
											
											<tr>
												<td nowrap="nowrap" align="right"><span class="blues">添加备件：</span>&nbsp;&nbsp;</td>
												<td align="left">
													<br/>
													<div style="width:60%;height:auto;border:1px solid silver;">
														<table width="100%" border="1" cellpadding="0" cellspacing="5" class="doc" style="border:0px solid red;">
															<!--<tr>
																<td width="50%">&nbsp;&nbsp;备件小类：
																	<select name="smallItemId" id="smallItemId" class="select" onchange="getItems('smallItemId');">
											                      		<option value="">--选择备件小类--</option>
											                      		<c:forEach items="${_SmallItems}" var="item">
																			<option value="${item.itemId}">【${item.itemCode}】${item.itemName}</option>
																		</c:forEach>
											                      	</select>
																</td>
															</tr>
															--><tr>
																<td width="50%">&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件：
																	<select name="itemId" id="itemId" datatype="*" nullmsg="请选择备件！" sucmsg=" " onchange="displayDetail('itemId');" >
											                      		<option value="">--选择备件--</option>
											                      		<c:forEach items="${_Items}" var="item">
																			<option value="${item.itemId}" class="${item.itemUnit}|${item.itemPrice}|${item.priceUnit}">【${item.itemCode}】${item.itemName}</option>
																		</c:forEach>
											                      	</select>
											                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																</td>
															</tr>
															<tr>
																<td width="25%">
																	&nbsp;&nbsp;单位：<span id="itemUnit"></span>
																</td>
																<td>
																	单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：
																	<input type="input" class="textfield" size="10" onblur="countAmount('acceptCount');" id="inItemPrice" name="inItemPrice" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入单价" />
																	<!--<span id="itemPrice"></span>-->
																	 (<span id="itemPriceUnit"></span>)
																</td>
															</tr>
															<tr>
																<td>&nbsp;&nbsp;申请数量：<input type="input" class="textfield" onblur="countAmount('applyCount');setSameCount('applyCount','buyCount','acceptCount');" size="7" id="applyCount" name="applyCount" value="1" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入申请数量" /></td>
																<td>采购数量：<input type="input" class="textfield" size="7" onblur="countAmount('buyCount');setSameCount('buyCount','applyCount','acceptCount');" id="buyCount" name="buyCount" value="1" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入采购数量" /></td>
															</tr>
															<tr>
																<td>&nbsp;&nbsp;接收数量：<input type="input" class="textfield" size="7" onblur="countAmount('acceptCount');setSameCount('acceptCount','applyCount','buyCount');" id="acceptCount" name="acceptCount" value="1" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入接收数量" /></td>
																<td>到期日期：<input type="input" class="Wdate textfield" size="15" id="becomeDate" name="becomeDate" readonly="true" datatype="*" sucmsg="填写正确！" nullmsg="请选择到期日期！" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" /></td>
															</tr>
															<tr>
																<td colspan=2>&nbsp;&nbsp;总&nbsp;金&nbsp;额：<input type="input" class="textfield" size="10" onfocus="countAmount();" id="itemAmount" name="itemAmount" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入总金额" /></td>
															</tr>
															<tr>
																<td colspan=2>
																	<a href="javascript:void(0);" class="btn_blue" id="addtr" >
																		<span>增加备件</span>
																	</a>
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
										</table>
										
										<br/>
									<!-- </div> -->
								</form>
					  		</div>
					  	<div>
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
