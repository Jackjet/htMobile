<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>查看出库单信息</title>
	<script type="text/javascript" src="<c:url value="/"/>js/pngfix.js"></script>
	<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script>
	<script src="<c:url value="/"/>js/inc_javascript.js" type="text/javascript"></script>
	
	<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/cupertino/jquery-ui-1.9.2.custom.css" />
	
	<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/view.css" />
	<!-- 按钮样式 -->
	<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/button.css" />
	
	<style>
		#itemsTab-real th{
			 background-color:#f5fafe;
			 
		}
		#itemsTab-real td {
			 background-color:white;
			 padding-left:0px;
			 text-align:center;
		}
	</style>
	
	<script type="text/javascript">
	
		
		//保存会议纪要
		function save(){
			//var path;
			var form = document.meetInforForm;
			form.action = "<c:url value='/meeting/meetInfor.htm'/>?method=saveView";
			form.submit();
			alert("保存成功！");
			window.returnValue = "Y";
			window.close();	
		}
		//修改会议纪要
		
		function edit(meetId){
			//var path;
			var form = document.meetInforForm;
			form.action = "<c:url value='/meeting/meetInfor.htm'/>?method=edit&meetId="+meetId;
			//form.submit();
			window.name = "__self";
			window.open(form.action, "__self"); //注意是2个下划线 	
			//path = "<c:url value='/meeting/meetInfor.htm'/>?method=saveView&meetId="+meetId;
			//window.location.href = path;		
		}
		
		//作废
		function setInValid(outOrderId){
			if(confirm("确定要作废吗？")){
				var form = document.outOrderInforForm;
				form.action = "/stock/outOrderInfor.htm?method=setInValid";
				form.submit();
				alert("已提交审批！");
				window.returnValue = "Y";
				window.close();	
			}
		}
		
		//显示审批框
		function display() {
			
			
			$("#checkArea").css("display","");
			$("#setCheck_btn").css("display","none");
			$("#saveCheck_btn").css("display","");
		}
		
		//保存审批
		function saveCheck(){
			var form = document.outOrderInforForm;
			form.action = "/stock/outOrderInfor.htm?method=saveCheck";
			form.submit();
			alert("审批成功！");
			window.returnValue = "Y";
			window.close();	
		}
		
		//退回
		function doBack(rowId){
			if(confirm("确定执行退回申请吗？")){
				$.ajax({
					url: '/stock/outOrderInfor.htm?method=doBack&outOrderId='+rowId,
					cache: false,
					type: "GET",
					//dataType : "json",
					async: true,
		            cache: false,
					beforeSend: function (xhr) {
					},
					complete : function(req, msg) {
					},
					success : function (msg) {
						if(msg == 'success'){
							alert("申请成功！");
							window.location.reload();
						}
						if(msg == 'fail'){
							alert("申请失败，请重试！");
						}
						//$mygrid.trigger("reloadGrid");
						window.location.reload();
					}
				});
			}
			//window.open("/stock/outOrderInfor.htm?method=view&rowId="+rowId, "_blank");
		}
		
		//显示退回审批框
		function display_back() {
			
			$("#checkArea_back").css("display","");
			$("#setCheckBack_btn").css("display","none");
			$("#saveCheckBack_btn").css("display","");
		}
		
		//保存审批
		function saveCheckBack(){
			var form = document.outOrderInforForm;
			form.action = "/stock/outOrderInfor.htm?method=saveCheckBack";
			form.submit();
			alert("审批成功！");
			window.returnValue = "Y";
			window.close();	
		}
	</script>
</head>
<base target="_self"/>
<body>
<div id="header">
  <div class="logo"><img src="<c:url value="/"/>images/zhongtu.png" width="430" height="88" /></div>
</div>
<div id="wrap" >

<!--网站主题部分-->
<div id="right">
  <div class="emil"></div>
  <div class="module">
    <div class="content">
  <div class="xinxi"><strong>查看出库单信息</strong>&nbsp;&nbsp;&nbsp;&nbsp;
  	<strong><font color=red>
  		<c:if test="${_OutOrderInfor.status == 0}">审批未通过</c:if>
  		<c:if test="${_OutOrderInfor.status == 1}">金额高于1000，待审批</c:if>
  		<c:if test="${_OutOrderInfor.status == 2}">已出库</c:if>
  		<c:if test="${_OutOrderInfor.status == 3}">退回，待审批</c:if>
  		<c:if test="${_OutOrderInfor.status == 4}">已退回</c:if>
  		</font></strong></div><p>&nbsp;</p>
  <div class="news">
	<form:form commandName="outOrderInforVo" name="outOrderInforForm" id="outOrderInforForm" action="" method="post" enctype="multipart/form-data">
      <input type="hidden" name="outOrderId" value="${_OutOrderInfor.outOrderId}"/>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#b4c5cd" class="doc">
            <tr>
              <td width="15%" nowrap="nowrap" align="right" bgcolor="#f5fafe"><span class="blues">出库单号：</span>&nbsp;&nbsp;</td>
              <td width="30%" bgcolor="#FFFFFF" colspan=3> &nbsp;&nbsp;${_OutOrderInfor.outOrderNo}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">出库员：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.sender.person.personName}</td>
              <td width="15%" align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">供应商：</span>&nbsp;&nbsp;</td>
              <td width="40%" bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.supplier.supplierName}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">仓库：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.warehouse.houseName}</td>
              <td width="15%" align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">分类：</span>&nbsp;&nbsp;</td>
              <td width="40%" bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.outOrderType.typeName}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">设备：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.outOrderDevice.deviceName}【${_OutOrderInfor.outOrderDevice.deviceCode}】</td>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">材料成本：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.outOrderCost.costName}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">备件：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.itemInfor.itemName}</td>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">车号：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.vehicles}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">单价：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.itemInfor.itemPrice}(${_OutOrderInfor.itemInfor.priceUnit})</td>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">领料部门：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.department.organizeName}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">发放数量：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.sendCount}</td>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">领料人：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.applier.personName}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">总金额：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.amount}</td>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">发放日期：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_OutOrderInfor.sendDate}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">备注：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF" colspan=3>&nbsp;&nbsp;${_OutOrderInfor.memo}</td>
            </tr>
            
            <c:if test="${!empty _OutOrderInfor.checkDate}">
            	<tr>
	              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">审批意见：</span>&nbsp;&nbsp;</td>
	              <td bgcolor="#FFFFFF" colspan=3>
	              	&nbsp;&nbsp;
	              	<font color=black><b>${_OutOrderInfor.checker.person.personName}</b></font>（<font color=blue>${_OutOrderInfor.checkDate}</font>）：
	              	<br/>&nbsp;&nbsp;
	              	<c:if test="${_OutOrderInfor.passed == 1}">同意</c:if>
	              	<c:if test="${_OutOrderInfor.passed == 0}">不同意</c:if>
	              	<br/>&nbsp;&nbsp;
	              	${_OutOrderInfor.checkAdvice}
	              </td>
	            </tr>
            </c:if>
            
            <c:if test="${!empty _OutOrderInfor.backCheckDate}">
            	<tr>
	              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">退回审批意见：</span>&nbsp;&nbsp;</td>
	              <td bgcolor="#FFFFFF" colspan=3>
	              	&nbsp;&nbsp;
	              	<font color=black><b>${_OutOrderInfor.checker.person.personName}</b></font>（<font color=blue>${_OutOrderInfor.backCheckDate}</font>）：
	              	<br/>&nbsp;&nbsp;
	              	<c:if test="${_OutOrderInfor.backPassed == 1}">同意</c:if>
	              	<c:if test="${_OutOrderInfor.backPassed == 0}">不同意</c:if>
	              </td>
	            </tr>
            </c:if>
            
            <tbody id="checkArea" style="display:none;">
            	<tr>
            		<td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">审批意见：</span>&nbsp;&nbsp;</td>
	            	<td bgcolor="#FFFFFF" colspan=3>
	            		<br/>
	            		&nbsp;&nbsp;
	            		<form:radiobutton path="passed" value="1"/>同意&nbsp;&nbsp;
	            		<form:radiobutton path="passed" value="0"/>不同意
	            		<br/>&nbsp;&nbsp;
	            		<form:textarea path="checkAdvice" cols="50" rows="5" class="textarea"/></td>
	            </tr>
            </tbody>
            
            <tbody id="checkArea_back" style="display:none;">
            	<tr>
            		<td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">退回审批意见：</span>&nbsp;&nbsp;</td>
	            	<td bgcolor="#FFFFFF" colspan=3>
	            		<br/>
	            		&nbsp;&nbsp;
	            		<form:radiobutton path="backPassed" value="1"/>同意&nbsp;&nbsp;
	            		<form:radiobutton path="backPassed" value="0"/>不同意
	            </tr>
            </tbody>
            
          </table>
      <div id="buttons"><table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td height="12"></td>
                    <td height="12"></td>
                  </tr>
                 </table>
                 <table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <c:if test="${_OutOrderInfor.status == 0 && empty _OutOrderInfor.checkDate}">
                    	<td>
							<a href="javascript:void(0);" class="btn_blue" id="edit_btn" onclick="setInValid(${_OutOrderInfor.outOrderId});"><span>作 废</span></a>
						</td>
                    </c:if>
                    
                    <c:if test="${_OutOrderInfor.status == 2 && _GLOBAL_USER.personId ==  _OutOrderInfor.sender.personId}">
                    	<td>
							<a href="javascript:void(0);" class="btn_blue" id="back_btn" onclick="doBack(${_OutOrderInfor.outOrderId});"><span>退 回</span></a>
						</td>
                    </c:if>
                    
                    <c:if test="${_OutOrderInfor.status == 1 && _GLOBAL_USER.personId == _OutOrderInfor.checker.personId}">
                    	<td>
							<a href="javascript:void(0);" class="btn_blue" id="setCheck_btn" onclick="display();"><span>审 批</span></a>
						</td>
						
						<td>
							<a href="javascript:void(0);" class="btn_blue" id="saveCheck_btn" style="display:none;" onclick="saveCheck();"><span>保存审批</span></a>
						</td>
                    </c:if>
                    
                    <c:if test="${_OutOrderInfor.status == 3 && _GLOBAL_USER.personId == _OutOrderInfor.checker.personId}">
                    	<td>
							<a href="javascript:void(0);" class="btn_blue" id="setCheckBack_btn" onclick="display_back();"><span>退回审批</span></a>
						</td>
						
						<td>
							<a href="javascript:void(0);" class="btn_blue" id="saveCheckBack_btn" style="display:none;" onclick="saveCheckBack();"><span>保存退回审批</span></a>
						</td>
                    </c:if>
					
                    <td>
                       <a href="javascript:void(0);" class="btn_red" onclick="window.close();"><span>关 闭</span></a>
                    </td>
                  </tr>
          </table>
          <table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td height="15" style="border:0px solid red;">&nbsp;</td>
	            <td height="15"></td>
	          </tr>
          </table>
          <br/>
          </div>
          
          </form:form>
  </div>
    </div>
  </div>
</div>
</div><div class="clearit"></div>

<!--网站底部部分-->
<div id="footer">上海慧智计算机技术有限公司 技术支持</div>

</body>
</html>

