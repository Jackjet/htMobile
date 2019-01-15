<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>编辑出库单</title>
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
			//$("#tabs").tabs();
			$("#outOrderInforForm").Validform({
				tiptype:3,
				btnSubmit:"#btn_sub",
				showAllError:true,
				beforeSubmit:function(curform){
					//alert($("#buyerId").val());
					var amount = $("#amount").val();
					if(amount != ""){
						if(parseFloat(amount) >= 1000){
							alert("总金额超过1000，将提交主管审批！");
						}
					}
					
					var count = $("#sendCount").val();
			     	//判断数量是否超过可用数量 
			     	var canUseCount = $("#canUseCountDisplay").text();
			     	if(parseFloat(count) > parseFloat(canUseCount)){
			     		alert("发放数量超过了可用数量！");
			     		$("#sendCount").focus();
			     		return false;
			     	}
					
				},
				callback:function(data){
					alert('信息编辑成功！');
					window.returnValue = "Y";
					
					window.close();
				}
			});
			
			
			//自动完成
			$("#typeId").combobox();    
			$("#houseId").combobox();
			$("#costId").combobox();
			$("#deviceId").combobox();
			$("#itemId").combobox();
			$("#departmentId").combobox();
			$("#applierId").combobox();
			$("#supplierId").combobox();
			
			//$("#deviceId").toggle();
		});
	    
	    function split( val ) {      
			return val.split( /,\s*/ );    
		}    
		
		function extractLast( term ) {      
			return split( term ).pop();    
		}  
	    
	    //根据所选备件显示详情
	    function displayDetail(id){
	    	var values=$("#"+id).find("option:selected").attr("class");
	    	//alert("123"+checkText);
	    	var itemUnit = values.split("|")[0];
	    	var itemPrice = values.split("|")[1];
	    	var priceUnit = values.split("|")[2];
	    	var itemCount = values.split("|")[3];
	    	
	    	$("#itemUnit").text(itemUnit);
	    	$("#outItemPrice").val(itemPrice);
	    	$("#itemPriceUnit").text(priceUnit);
	    	$("#itemCountDisplay").text(itemCount);
	    	
	    	//找出该备件有库存的供应商
	    	
	    	
	    	$.getJSON("/stock/supplierInfor.htm?method=getOutSuppliers&itemId="+$("#"+id).val(),function(data) {
		           var options = "";
		           $.each(data._SupplierItems, function(i, n) {
		               options += "<option value='"+n.supplier.supplierId+"'  class='"+n.restCount+"'>"+n.supplier.supplierName+"</option>";
		               //+"|"+n.inOrderItemId+"   
		           });   
		           $('#supplierId').html(options);   
		        }   
		    );
	    }
	    
	    //根据所选供应商显示剩余数量
	    function restCount(id){
	    	var values=$("#"+id).find("option:selected").attr("class");
	    	//var canUseCount = values.split("|")[0];
	    	//var inOrderItemId = values.split("|")[1];
	    	
	    	$("#canUseCountDisplay").text(values);
	    	//$("#inOrderItemId").val(inOrderItemId);
	    }
	    
	    //显示车号输入
	    function setVehicles(id){
	    	//alert(id);
	    	var deviceVal=$("#"+id).find("option:selected").text();
			//alert(deviceVal);
	    	//如果是F，则显示车号
	    	if(deviceVal == "F"){
				$("#vehicleTr").css("display","");	
				
				var deviceArray = new Array();
				<c:forEach var="device" items="${_Devices}" varStatus="index">
					var deviceName = '${device.deviceName}';
					deviceArray[${index.index}] = deviceName;
				</c:forEach>
				  
				// don't navigate away from the field on tab when selecting an item      
				$("#vehicles").bind( "keydown", function( event ) {        
					if ( event.keyCode === $.ui.keyCode.TAB && $( this ).autocomplete( "instance" ).menu.active ) {          
						event.preventDefault();        
					}      
				}).autocomplete({        
					minLength: 0,        
					source: function( request, response ) {          
						// delegate back to autocomplete, but extract the last term          
						response( $.ui.autocomplete.filter(deviceArray, extractLast( request.term ) ) );        
					},        
					focus: function() {          
						// prevent value inserted on focus          
						return false;        
					},        
					select: function( event, ui ) {          
						var terms = split( this.value );          
						// remove the current input          
						terms.pop();          
						// add the selected item          
						terms.push( ui.item.value );          
						// add placeholder to get the comma-and-space at the end          
						terms.push( "" );          
						this.value = terms.join( "," );          
						return false;        
					}      
				});    	
	    	}else{
	    		$("#vehicleTr").css("display","none");	  
	    		$("#vehicles").val("");
	    	}
	    	
	    }
	    
	    
	     
	     //计算备件金额
	     function countAmount(){
	     
	     	var count = $("#sendCount").val();
	     	//判断数量是否超过可用数量 
	     	var canUseCount = $("#canUseCountDisplay").text();
	     	if(parseFloat(count) > parseFloat(canUseCount)){
	     		alert("发放数量超过了可用数量！");
	     	}else{
	     		var itemPrice = $("#outItemPrice").val();
	     	
		     	var itemAmount = count * itemPrice;
		     	//alert(itemAmount);
		     	$("#amount").val(itemAmount);
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
						<strong>编辑出库单</strong>
					</div>
					<p>&nbsp;</p>
					<div class="news">
						<!--<div id="tabs">
							<ul>
					    		<li><a href="#tabs-1">订单信息</a></li>
					    		<li><a href="#tabs-2">备件</a></li>
					  		</ul>
					  		<div id="tabs-1">-->
					  			<form:form commandName="outOrderInforVo" id="outOrderInforForm" action="/stock/outOrderInfor.htm?method=save" method="post">
									<form:hidden path="outOrderId"/>
									<table width="100%" border="0" cellpadding="0" cellspacing="0" class="doc" style="border:0px solid red;">
										<tr>
											<td width="15%" align="right" nowrap="nowrap"><span class="blues">出库单号：</span>&nbsp;&nbsp;</td>
											<td width="30%" colspan=3>
												<form:input path="outOrderNo" size="20" class="textfield" datatype="*" sucmsg="填写正确！" nullmsg="请输入订单号！"/>
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
											<td align="right"><span class="blues">分类：</span>&nbsp;&nbsp;</td>
											<td>
												<form:select path="typeId" class="select" ignore="ignore" datatype="*" nullmsg="请选择分类！" sucmsg="选择正确！">
						                      		<form:option value="">--选择分类--</form:option>
						                      		<c:forEach items="${_Types}" var="type">
														<form:option value="${type.typeId}">${type.typeName}</form:option>
													</c:forEach>
						                      	</form:select>
						                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<!--<font color="red">*</font>
											--></td>
										</tr>
										
										<tr>
											<td align="right"><span class="blues">设备：</span>&nbsp;&nbsp;</td>
											<td>
												<form:select path="deviceId" class="select" datatype="*" nullmsg="请选择设备！" sucmsg="选择正确！" onchange="setVehicles('deviceId');">
						                      		<form:option value="">--选择设备--</form:option>
						                      		<c:forEach items="${_Devices}" var="device">
														<form:option value="${device.deviceId}">${device.deviceName}</form:option>
													</c:forEach>
						                      	</form:select>
						                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<font color="red">*</font>
											</td>
											<td align="right"><span class="blues">材料成本：</span>&nbsp;&nbsp;</td>
											<td>
												<form:select path="costId" class="select" ignore="ignore" datatype="*" nullmsg="请选择材料成本！" sucmsg="选择正确！">
						                      		<form:option value="">--选择材料成本--</form:option>
						                      		<c:forEach items="${_Costs}" var="cost">
														<form:option value="${cost.costId}">${cost.costName}</form:option>
													</c:forEach>
						                      	</form:select>
						                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
										
										<tr style="display:none;" id="vehicleTr">
											<td align="right"><span class="blues">车号：</span>&nbsp;&nbsp;</td>
											<td colspan=3>
												<form:input path="vehicles" size="100" class="textfield"/>
											</td>
										</tr>
										
										<tr>
											<td align="right"><span class="blues">备件：</span>&nbsp;&nbsp;</td>
											<td>
												<form:select path="itemId" class="select" datatype="*" nullmsg="请选择备件！" sucmsg="选择正确！" onchange="displayDetail('itemId');">
						                      		<form:option value="">--选择备件--</form:option>
						                      		<c:forEach items="${_Items}" var="item">
														<form:option value="${item.itemId}" class="${item.itemUnit}|${item.itemPrice}|${item.priceUnit}|${item.count}">【${item.itemCode}】${item.itemName}</form:option>
													</c:forEach>
						                      	</form:select>
						                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<font color="red">*</font>
											</td>
											<td align="right" width="15%"><span class="blues">备件数量：</span>&nbsp;&nbsp;</td>
											<td width="40%">
												<span id="itemCountDisplay"></span>
											</td>
										</tr>
										
										<tr>
											<td align="right"><span class="blues">单价：</span>&nbsp;&nbsp;</td>
											<td colspan=3>
												<!--<span id="itemPrice"></span>-->
												<form:input path="outItemPrice" size="20" class="textfield" onblur="countAmount();" readonly="readonly" />
												 (<span id="itemPriceUnit"></span>)
											</td>
										</tr>
										
										<tr>
											<td align="right"><span class="blues">供应商：</span>&nbsp;&nbsp;</td>
											<td>
												<form:select path="supplierId" class="select" datatype="*" nullmsg="请选择供应商！" sucmsg="选择正确！" onchange="restCount('supplierId');">
						                      		<form:option value="">--选择供应商--</form:option>
						                      		
						                      	</form:select>
												<!--<font color="red">*</font>-->
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<font color="red">*</font>
											</td>
											<td align="right"><span class="blues">可用数量：</span>&nbsp;&nbsp;</td>
											<td>
												<span id="canUseCountDisplay"></span>
												<!--<input type="hidden" id="inOrderItemId" name="inOrderItemId" />-->
											</td>
											
										</tr>
										
										<tr>
											<td align="right"><span class="blues">发放数量：</span>&nbsp;&nbsp;</td>
											<td>
												<form:input path="sendCount" size="20" class="textfield" onblur="countAmount();" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入数量" />
												<font color="red">*</font>
											</td>
											<td align="right" width="15%"><span class="blues">领料部门：</span>&nbsp;&nbsp;</td>
											<td width="40%">
												<form:select path="departmentId" class="select" datatype="*" nullmsg="请选择领料部门！" sucmsg="选择正确！">
						                      		<form:option value="">--选择领料部门--</form:option>
						                      		<c:forEach items="${_Departments}" var="department">
														<form:option value="${department.organizeId}">${department.organizeName}</form:option>
													</c:forEach>
						                      	</form:select>
						                      	<!--<input type="input" id="selBuyer" name="selBuyer" class="textfield"/>-->
						                      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<td nowrap="nowrap" align="right"><span class="blues">总金额：</span>&nbsp;&nbsp;</td>
											<td>
												<form:input path="amount" size="20" class="textfield" onfocus="countAmount();" datatype="*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/" sucmsg="填写正确！" errormsg="请输入正确格式" nullmsg="请输入总金额" />
												<font color="red">*</font>
											</td>
											
											<td align="right"><span class="blues">领料人：</span>&nbsp;&nbsp;</td>
											<td>
												<form:select path="applierId" class="select" datatype="*" nullmsg="请选择采购人！" sucmsg="选择正确！">
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
											<td nowrap="nowrap" align="right"><span class="blues">发放日期：</span>&nbsp;&nbsp;</td>
											<td colspan=3>
												<form:input path="sendDateStr" size="20" class="textfield Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  datatype="*" sucmsg="填写正确！" nullmsg="请输入发放日期！" readonly="readonly"/>
												<font color="red">*</font>
											</td>
										</tr>
										
										<tr>
											<td nowrap="nowrap" align="right"><span class="blues">备注：</span>&nbsp;&nbsp;</td>
											<td>
												<form:textarea path="memo" cols="50" rows="5" class="textarea"/>
												<br/><br/>
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
