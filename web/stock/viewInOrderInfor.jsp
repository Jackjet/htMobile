<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>查看订单信息</title>
	<script type="text/javascript" src="<c:url value="/"/>js/pngfix.js"></script>
	<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script>
	<script src="<c:url value="/"/>js/inc_javascript.js" type="text/javascript"></script>
	<script src="<c:url value="/"/>js/htmlToExcel.js" type="text/javascript"></script>
	
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
		function setInValid(inOrderId){
			if(confirm("确定要作废吗？")){
				var form = document.inOrderInforForm;
				form.action = "/stock/inOrderInfor.htm?method=setInValid";
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
			var form = document.inOrderInforForm;
			form.action = "/stock/inOrderInfor.htm?method=saveCheck";
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
  <div class="xinxi"><strong>查看订单信息</strong>&nbsp;&nbsp;&nbsp;&nbsp;
  	<strong><font color=red>
  		<c:if test="${_InOrderInfor.status == 0}">已入库</c:if>
  		<c:if test="${_InOrderInfor.status == 1}">作废，待审批</c:if>
  		<c:if test="${_InOrderInfor.status == 2}">已作废</c:if>
  		</font></strong></div><p>&nbsp;</p>
  <div class="news">
	<form:form commandName="inOrderInforVo" name="inOrderInforForm" id="inOrderInforForm" action="" method="post" enctype="multipart/form-data">
      <input type="hidden" name="inOrderId" value="${_InOrderInfor.inOrderId}"/>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#b4c5cd" class="doc">
            <tr>
              <td width="15%" nowrap="nowrap" align="right" bgcolor="#f5fafe"><span class="blues">订单流水号：</span>&nbsp;&nbsp;</td>
              <td width="85%" bgcolor="#FFFFFF"> &nbsp;&nbsp;${_InOrderInfor.inOrderId}</td>
            </tr> 
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">订单描述：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_InOrderInfor.inOrderNo}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">入库员：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_InOrderInfor.applier.person.personName}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">仓库：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_InOrderInfor.warehouse.houseName}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">采购人：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_InOrderInfor.buyer.personName}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">供应商：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_InOrderInfor.supplier.supplierName}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">到期日期：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_InOrderInfor.becomeDate}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">订单总金额（含税）：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_InOrderInfor.amount}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">订单总金额（去税）：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_InOrderInfor.pureAmount}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">价格单位：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_InOrderInfor.priceUnit}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">备注：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_InOrderInfor.memo}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">备件：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF" style="padding-left:10px;">&nbsp;&nbsp;
              	<div id="itemContent">
	              	<table id="itemsTab-real" width="90%" border="0" cellpadding="0" cellspacing="1" bgcolor="#b4c5cd" class="doc">
						<tr>
							<th>序号</th>
							<th colspan=2>备件</th>
							<th>单位</th>
							<!-- <th>申请数量</th>
							<th>采购数量</th> -->
							<th>入库数量</th>
							<th>单价（CNY）</th>
							<!--<th>到期日</th>-->
							<th>总金额</th>
						</tr>
						<c:set var="allAmount" value="0" />
						<c:set var="allMoneyAmount" value="0" />
						<c:forEach items="${_InOrderInfor.items}" var="item" varStatus="index">
							<tr>
								<td>${index.index+1}</td>
								<td>${item.itemInfor.itemCode}</td>
								<td style="text-align:left;">&nbsp;${item.itemInfor.itemName}</td>
								<td>${item.itemInfor.itemUnit}</td>
								<!-- <td>${item.applyCount}</td>
								<td>${item.buyCount}</td> -->
								<td>${item.acceptCount}<c:set var="allAmount" value="${allAmount+item.acceptCount}" /></td>
								<td>${item.inItemPrice}</td><!-- (${item.itemInfor.priceUnit}) -->
								<!--<td>${item.becomeDate}</td>-->
								<td style="text-align:right;">${item.amount}&nbsp;&nbsp;&nbsp;<c:set var="allMoneyAmount" value="${allMoneyAmount+item.amount}" /></td>
							</tr>
						</c:forEach>
						<tr>
							<td><b>合计</b></td>
							<td colspan=3 style="text-align:right;">&nbsp;</td>
							<td style="text-align:center;">${allAmount}</td>
							<td>&nbsp;</td>
							<td style="text-align:right;">${allMoneyAmount}&nbsp;&nbsp;&nbsp;</td><!-- ${_InOrderInfor.pureAmount} -->
						</tr>
					</table>
					<br/>
				</div>
              </td>
            </tr>
            
            <c:if test="${!empty _InOrderInfor.checkDate}">
            	<tr>
	              <td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">作废审批意见：</span>&nbsp;&nbsp;</td>
	              <td bgcolor="#FFFFFF">
	              	&nbsp;&nbsp;
	              	<font color=black><b>${_InOrderInfor.checker.person.personName}</b></font>（<font color=blue>${_InOrderInfor.checkDate}</font>）：
	              	<br/>&nbsp;&nbsp;
	              	<c:if test="${_InOrderInfor.passed == 1}">同意</c:if>
	              	<c:if test="${_InOrderInfor.passed == 0}">不同意</c:if>
	              	<br/>&nbsp;&nbsp;
	              	${_InOrderInfor.checkAdvice}
	              </td>
	            </tr>
            </c:if>
            
            <tbody id="checkArea" style="display:none;">
            	<tr>
            		<td align="right" nowrap="nowrap" bgcolor="#f5fafe"><span class="blues">审批意见：</span>&nbsp;&nbsp;</td>
	            	<td bgcolor="#FFFFFF">
	            		<br/>
	            		&nbsp;&nbsp;
	            		<form:radiobutton path="passed" value="1"/>同意&nbsp;&nbsp;
	            		<form:radiobutton path="passed" value="0"/>不同意
	            		<br/>&nbsp;&nbsp;
	            		<form:textarea path="checkAdvice" cols="50" rows="5" class="textarea"/></td>
	            </tr>
            </tbody>
            
            
          </table>
      <div id="buttons"><table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td height="12"></td>
                    <td height="12"></td>
                  </tr>
                 </table>
                 <table width="500" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <c:if test="${_InOrderInfor.status == 0 && empty _InOrderInfor.checkDate}">
                    	<td>
							<a href="javascript:void(0);" class="btn_blue" id="edit_btn" onclick="setInValid(${_InOrderInfor.inOrderId});"><span>作 废</span></a>
						</td>
                    </c:if>
                    
                    <c:if test="${_InOrderInfor.status == 3}">
                    	<td>
							<a href="<c:url value="/"/>stock/inOrderInfor.htm?method=edit&inOrderId=${_InOrderInfor.inOrderId}" class="btn_blue"><span>继续添加</span></a>
						</td>
						<td>
							<a href="<c:url value="/"/>stock/inOrderInfor.htm?method=saveEntry&inOrderId=${_InOrderInfor.inOrderId}" class="btn_blue"><span>提交入库</span></a>
						</td>
                    </c:if>
                    
                    <c:if test="${_InOrderInfor.status == 1 && _GLOBAL_USER.personId == _InOrderInfor.checker.personId}">
                    	<td>
							<a href="javascript:void(0);" class="btn_blue" id="setCheck_btn" onclick="display();"><span>审 批</span></a>
						</td>
						
						<td>
							<a href="javascript:void(0);" class="btn_blue" id="saveCheck_btn" style="display:none;" onclick="saveCheck();"><span>保存审批</span></a>
						</td>
                    </c:if>
                    
                    <td>
						<a href="javascript:void(0);" onclick="javascript:htmlToExcel(document.getElementById('itemsTab-real'),document.getElementById('itemContent'),0,0,'','ZZ','00000');" class="btn_blue"><span>导出备件到Excel</span></a>
					</td>
					
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

