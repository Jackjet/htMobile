<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
<html>
<head>
<title>编辑费用</title>
	<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script> <!--jquery包-->
	<script src="<c:url value="/"/>js/inc_javascript.js" type="text/javascript"></script>
	
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/gkSecure/jquery-ui-1.9.2.custom.css" />
	<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
	
	<!-- 表单验证 -->
	<script src="<c:url value='/'/>js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/'/>js/Validform_v5.3.2/css/style.css" />
	<!-- 导出到excel -->
	<script type='text/javascript' src='<c:url value="/"/>js/expertToExcel.js'></script>
	
	
	<style>
		#footer {
			height: 20px;
			line-height: 20px;
			background-color: #112F53;
			border-top-width: 1px;
			border-top-style: solid;
			border-top-color: #20609d;
			text-align: center;
			color: white;
			clear: both;
			z-index: 99999;
			position: fixed;
			bottom: 0;
			left: 0;
			width: 100%;
			overflow: visible;
			font-size:15px;
		}
		body{
			font-family: 微软雅黑,Helvetica, Arial, sans-serif;
			margin:0 auto;
		}
		#wrap{
			margin:0 auto;
			text-align:center;
			width:90%;
			border:0px solid red;
		}
		#warp table{
			margin:0 auto;
		}
		td,th{
			background:white;
			text-align:center;
		}
		
		#wrap_head{
			text-align:center;
			padding-top:30px;
			padding-bottom:20px;
		}
		
		#wrap input{
			border:0;
			width:100%;
			color:blue;
			text-align:center;
			font-size:14px;
		}
		
		a{
			color:blue;
			outline:none;
			text-decoration:none;
		}
	</style>
	
	<script>
		$(function(){
			
			
			
			$(".widget").button();
			
			$("#costForm").Validform({
				tiptype:3,
				btnSubmit:"#btn_sub",
				showAllError:true,
				beforeSubmit:function(curform){
				},
				callback:function(data){
					alert('信息编辑成功！');
					//window.location.reload();
					//window.returnValue = "Y";
					//window.close();
					//window.opener.location.reload();
					//window.location.href="/security/document.htm?method=view&rowId="+rowId;
				}
			});  
			

			$("#wrap td input").attr("onkeypress","return event.keyCode>=48&&event.keyCode<=57||event.keyCode==46");
			$("#wrap td input").attr("onpaste","return !clipboardData.getData('text').match(/\D/)");
			$("#wrap td input").attr("ondragenter","return false");
			$("#wrap td input").css("ime-mode","Disabled");
			
			//设置值 
			//alert('aa');
			//initValue();
		});
		
		//提交数据
		function submitData() {
			alert('保存成功');
			//window.returnValue = "Y";
			//window.close();
		}
		
		function initValue(){
			for(var i=1;i<12;i++){
				for(var j = 1;j<13;j++){
					var tmpkey = "v"+i+"_"+j;
				}
			}
		}
	</script>

</head>
<base target="_self" />
<body>
	<div id="wrap">
		<form method="post" name="costForm" id="costForm" action="/security/cost.htm?method=saveZc">
			<%--<div id="wrap_head">
				<table width="100%" border=0>
					<tr>
						<td align="left" width="25%">
							<a href="/security/cost.htm?method=editZc&tag=0&currentYear=${_Year}"><<前一年</a>
						</td>
						<td align="center">
							<span style="font-size:18px;font-weight:bold;">${_Year}年度安全费用实际支出</span>
							<input type="hidden" name="dataYear" value="${_Year}" />
						</td>
						<td align="right" width="25%">
							<c:if test="${_Year != _RealYear }">
								<a href="/security/cost.htm?method=editZc&tag=1&currentYear=${_Year}">后一年>></a>
							</c:if>
							<c:if test="${_Year == _RealYear }">
								&nbsp;
							</c:if>
						</td>
					</tr>
				</table>
			</div>
			--%><table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="silver">
				<tr>
					<th colspan="13" height="80">
						<a href="/security/cost.htm?method=editZc&tag=0&currentYear=${_Year}"><<前一年</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span style="font-size:18px;font-weight:bold;">${_Year}年度安全费用实际支出</span>
						<input type="hidden" name="dataYear" value="${_Year}" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="/security/cost.htm?method=editZc&tag=1&currentYear=${_Year}">后一年>></a>
					</th>
					
				</tr>
	            <tr>
	              <th width="16%" nowrap="nowrap">&nbsp;</th>
	              <td width="7%" align="center">1月</td>
	              <td width="7%" align="center">2月</td>
	              <td width="7%" align="center">3月</td>
	              <td width="7%" align="center">4月</td>
	              <td width="7%" align="center">5月</td>
	              <td width="7%" align="center">6月</td>
	              <td width="7%" align="center">7月</td>
	              <td width="7%" align="center">8月</td>
	              <td width="7%" align="center">9月</td>
	              <td width="7%" align="center">10月</td>
	              <td width="7%" align="center">11月</td>
	              <td width="7%" align="center">12月</td>
	            </tr>
	            
	            <tr>
	              <td width="16%" nowrap="nowrap">安全设施设备安装维护</td>
	              <td width="7%" align="center"><input type="text" name="1_1" id="1_1" value="${v1_1}" /></td>
	              <td width="7%" align="center"><input type="text" name="1_2" id="1_2" value="${v1_2}"  /></td>
	              <td width="7%" align="center"><input type="text" name="1_3" id="1_3" value="${v1_3}"  /></td>
	              <td width="7%" align="center"><input type="text" name="1_4" id="1_4" value="${v1_4}" /></td>
	              <td width="7%" align="center"><input type="text" name="1_5" id="1_5" value="${v1_5}" /></td>
	              <td width="7%" align="center"><input type="text" name="1_6" id="1_6" value="${v1_6}" /></td>
	              <td width="7%" align="center"><input type="text" name="1_7" id="1_7" value="${v1_7}" /></td>
	              <td width="7%" align="center"><input type="text" name="1_8" id="1_8" value="${v1_8}" /></td>
	              <td width="7%" align="center"><input type="text" name="1_9" id="1_9" value="${v1_9}" /></td>
	              <td width="7%" align="center"><input type="text" name="1_10" id="1_10" value="${v1_10}" /></td>
	              <td width="7%" align="center"><input type="text" name="1_11" id="1_11" value="${v1_11}" /></td>
	              <td width="7%" align="center"><input type="text" name="1_12" id="1_12" value="${v1_12}" /></td>
	            </tr>
	            
	            
	            <tr>
	              <td width="16%" nowrap="nowrap">应急救援</td>
	              <td width="7%" align="center"><input type="text" name="2_1" id="2_1" value="${v2_1}" /></td>
	              <td width="7%" align="center"><input type="text" name="2_2" id="2_2" value="${v2_2}" /></td>
	              <td width="7%" align="center"><input type="text" name="2_3" id="2_3" value="${v2_3}" /></td>
	              <td width="7%" align="center"><input type="text" name="2_4" id="2_4" value="${v2_4}" /></td>
	              <td width="7%" align="center"><input type="text" name="2_5" id="2_5" value="${v2_5}" /></td>
	              <td width="7%" align="center"><input type="text" name="2_6" id="2_6" value="${v2_6}" /></td>
	              <td width="7%" align="center"><input type="text" name="2_7" id="2_7" value="${v2_7}" /></td>
	              <td width="7%" align="center"><input type="text" name="2_8" id="2_8" value="${v2_8}" /></td>
	              <td width="7%" align="center"><input type="text" name="2_9" id="2_9" value="${v2_9}" /></td>
	              <td width="7%" align="center"><input type="text" name="2_10" id="2_10" value="${v2_10}" /></td>
	              <td width="7%" align="center"><input type="text" name="2_11" id="2_11" value="${v2_12}" /></td>
	              <td width="7%" align="center"><input type="text" name="2_12" id="2_12" value="${v2_12}" /></td>
	            </tr>
	            
	            
	            <tr>
	              <td width="16%" nowrap="nowrap">隐患整改</td>
	              <td width="7%" align="center"><input type="text" name="3_1" id="3_1" value="${v3_1}" /></td>
	              <td width="7%" align="center"><input type="text" name="3_2" id="3_2" value="${v3_2}" /></td>
	              <td width="7%" align="center"><input type="text" name="3_3" id="3_3" value="${v3_3}" /></td>
	              <td width="7%" align="center"><input type="text" name="3_4" id="3_4" value="${v3_4}" /></td>
	              <td width="7%" align="center"><input type="text" name="3_5" id="3_5" value="${v3_5}" /></td>
	              <td width="7%" align="center"><input type="text" name="3_6" id="3_6" value="${v3_6}" /></td>
	              <td width="7%" align="center"><input type="text" name="3_7" id="3_7" value="${v3_7}" /></td>
	              <td width="7%" align="center"><input type="text" name="3_8" id="3_8" value="${v3_8}" /></td>
	              <td width="7%" align="center"><input type="text" name="3_9" id="3_9" value="${v3_9}" /></td>
	              <td width="7%" align="center"><input type="text" name="3_10" id="3_10" value="${v3_10}" /></td>
	              <td width="7%" align="center"><input type="text" name="3_11" id="3_11" value="${v3_11}" /></td>
	              <td width="7%" align="center"><input type="text" name="3_12" id="3_12" value="${v3_12}" /></td>
	            </tr>
	            
	            
	            <tr>
	              <td width="16%" nowrap="nowrap">劳动防护</td>
	              <td width="7%" align="center"><input type="text" name="4_1" id="4_1" value="${v4_1}" /></td>
	              <td width="7%" align="center"><input type="text" name="4_2" id="4_2" value="${v4_2}" /></td>
	              <td width="7%" align="center"><input type="text" name="4_3" id="4_3" value="${v4_3}" /></td>
	              <td width="7%" align="center"><input type="text" name="4_4" id="4_4" value="${v4_4}" /></td>
	              <td width="7%" align="center"><input type="text" name="4_5" id="4_5" value="${v4_5}" /></td>
	              <td width="7%" align="center"><input type="text" name="4_6" id="4_6" value="${v4_6}" /></td>
	              <td width="7%" align="center"><input type="text" name="4_7" id="4_7" value="${v4_7}" /></td>
	              <td width="7%" align="center"><input type="text" name="4_8" id="4_8" value="${v4_8}" /></td>
	              <td width="7%" align="center"><input type="text" name="4_9" id="4_9" value="${v4_9}" /></td>
	              <td width="7%" align="center"><input type="text" name="4_10" id="4_10" value="${v4_10}" /></td>
	              <td width="7%" align="center"><input type="text" name="4_11" id="4_11" value="${v4_11}" /></td>
	              <td width="7%" align="center"><input type="text" name="4_12" id="4_12" value="${v4_12}" /></td>
	            </tr>
	            
	            
	            <tr>
	              <td width="16%" nowrap="nowrap">安全教育和培训</td>
	              <td width="7%" align="center"><input type="text" name="5_1" id="5_1" value="${v5_1}" /></td>
	              <td width="7%" align="center"><input type="text" name="5_2" id="5_2" value="${v5_2}" /></td>
	              <td width="7%" align="center"><input type="text" name="5_3" id="5_3" value="${v5_3}" /></td>
	              <td width="7%" align="center"><input type="text" name="5_4" id="5_4" value="${v5_4}" /></td>
	              <td width="7%" align="center"><input type="text" name="5_5" id="5_5" value="${v5_5}" /></td>
	              <td width="7%" align="center"><input type="text" name="5_6" id="5_6" value="${v5_6}" /></td>
	              <td width="7%" align="center"><input type="text" name="5_7" id="5_7" value="${v5_7}" /></td>
	              <td width="7%" align="center"><input type="text" name="5_8" id="5_8" value="${v5_8}" /></td>
	              <td width="7%" align="center"><input type="text" name="5_9" id="5_9" value="${v5_9}" /></td>
	              <td width="7%" align="center"><input type="text" name="5_10" id="5_10" value="${v5_10}" /></td>
	              <td width="7%" align="center"><input type="text" name="5_11" id="5_11" value="${v5_11}" /></td>
	              <td width="7%" align="center"><input type="text" name="5_12" id="5_12" value="${v5_12}" /></td>
	            </tr>
	            
	            
	            <tr>
	              <td width="16%" nowrap="nowrap">安全设施设备检测</td>
	              <td width="7%" align="center"><input type="text" name="6_1" id="6_1" value="${v6_1}" /></td>
	              <td width="7%" align="center"><input type="text" name="6_2" id="6_2" value="${v6_2}" /></td>
	              <td width="7%" align="center"><input type="text" name="6_3" id="6_3" value="${v6_3}" /></td>
	              <td width="7%" align="center"><input type="text" name="6_4" id="6_4" value="${v6_4}" /></td>
	              <td width="7%" align="center"><input type="text" name="6_5" id="6_5" value="${v6_5}" /></td>
	              <td width="7%" align="center"><input type="text" name="6_6" id="6_6" value="${v6_6}" /></td>
	              <td width="7%" align="center"><input type="text" name="6_7" id="6_7" value="${v6_7}" /></td>
	              <td width="7%" align="center"><input type="text" name="6_8" id="6_8" value="${v6_8}" /></td>
	              <td width="7%" align="center"><input type="text" name="6_9" id="6_9" value="${v6_9}" /></td>
	              <td width="7%" align="center"><input type="text" name="6_10" id="6_10" value="${v6_10}" /></td>
	              <td width="7%" align="center"><input type="text" name="6_11" id="6_11" value="${v6_11}" /></td>
	              <td width="7%" align="center"><input type="text" name="6_12" id="6_12" value="${v6_12}" /></td>
	            </tr>
	            
	            
	            <tr>
	              <td width="16%" nowrap="nowrap">其他直接相关支出</td>
	              <td width="7%" align="center"><input type="text" name="7_1" id="7_1" value="${v7_1}" /></td>
	              <td width="7%" align="center"><input type="text" name="7_2" id="7_2" value="${v7_2}" /></td>
	              <td width="7%" align="center"><input type="text" name="7_3" id="7_3" value="${v7_3}" /></td>
	              <td width="7%" align="center"><input type="text" name="7_4" id="7_4" value="${v7_4}" /></td>
	              <td width="7%" align="center"><input type="text" name="7_5" id="7_5" value="${v7_5}" /></td>
	              <td width="7%" align="center"><input type="text" name="7_6" id="7_6" value="${v7_6}" /></td>
	              <td width="7%" align="center"><input type="text" name="7_7" id="7_7" value="${v7_7}" /></td>
	              <td width="7%" align="center"><input type="text" name="7_8" id="7_8" value="${v7_8}" /></td>
	              <td width="7%" align="center"><input type="text" name="7_9" id="7_9" value="${v7_9}" /></td>
	              <td width="7%" align="center"><input type="text" name="7_10" id="7_10" value="${v7_10}" /></td>
	              <td width="7%" align="center"><input type="text" name="7_11" id="7_11" value="${v7_11}" /></td>
	              <td width="7%" align="center"><input type="text" name="7_12" id="7_12" value="${v7_12}" /></td>
	            </tr>
	            
	            <tr>
	              <td width="16%" nowrap="nowrap">购置靠泊专用设备等支出</td>
	              <td width="7%" align="center"><input type="text" name="8_1" id="8_1" value="${v8_1}" /></td>
	              <td width="7%" align="center"><input type="text" name="8_2" id="8_2" value="${v8_2}" /></td>
	              <td width="7%" align="center"><input type="text" name="8_3" id="8_3" value="${v8_3}" /></td>
	              <td width="7%" align="center"><input type="text" name="8_4" id="8_4" value="${v8_4}" /></td>
	              <td width="7%" align="center"><input type="text" name="8_5" id="8_5" value="${v8_5}" /></td>
	              <td width="7%" align="center"><input type="text" name="8_6" id="8_6" value="${v8_6}" /></td>
	              <td width="7%" align="center"><input type="text" name="8_7" id="8_7" value="${v8_7}" /></td>
	              <td width="7%" align="center"><input type="text" name="8_8" id="8_8" value="${v8_8}" /></td>
	              <td width="7%" align="center"><input type="text" name="8_9" id="8_9" value="${v8_9}" /></td>
	              <td width="7%" align="center"><input type="text" name="8_10" id="8_10" value="${v8_10}" /></td>
	              <td width="7%" align="center"><input type="text" name="8_11" id="8_11" value="${v8_11}" /></td>
	              <td width="7%" align="center"><input type="text" name="8_12" id="8_12" value="${v8_12}" /></td>
	            </tr>
	            
	            
	            
	            
	            <tr>
	              <td width="16%" nowrap="nowrap">安全生产标准化建设支出</td>
	              <td width="7%" align="center"><input type="text" name="9_1" id="9_1" value="${v9_1}" /></td>
	              <td width="7%" align="center"><input type="text" name="9_2" id="9_2" value="${v9_2}" /></td>
	              <td width="7%" align="center"><input type="text" name="9_3" id="9_3" value="${v9_3}" /></td>
	              <td width="7%" align="center"><input type="text" name="9_4" id="9_4" value="${v9_4}" /></td>
	              <td width="7%" align="center"><input type="text" name="9_5" id="9_5" value="${v9_5}" /></td>
	              <td width="7%" align="center"><input type="text" name="9_6" id="9_6" value="${v9_6}" /></td>
	              <td width="7%" align="center"><input type="text" name="9_7" id="9_7" value="${v9_7}" /></td>
	              <td width="7%" align="center"><input type="text" name="9_8" id="9_8" value="${v9_8}" /></td>
	              <td width="7%" align="center"><input type="text" name="9_9" id="9_9" value="${v9_9}" /></td>
	              <td width="7%" align="center"><input type="text" name="9_10" id="9_10" value="${v9_10}" /></td>
	              <td width="7%" align="center"><input type="text" name="9_11" id="9_11" value="${v9_11}" /></td>
	              <td width="7%" align="center"><input type="text" name="9_12" id="9_12" value="${v9_12}" /></td>
	            </tr>
	            
	            
	            
	            
	            <tr>
	              <td width="16%" nowrap="nowrap">安全生产推广应用支出</td>
	              <td width="7%" align="center"><input type="text" name="10_1" id="10_1" value="${v10_1}" /></td>
	              <td width="7%" align="center"><input type="text" name="10_2" id="10_2" value="${v10_2}" /></td>
	              <td width="7%" align="center"><input type="text" name="10_3" id="10_3" value="${v10_3}" /></td>
	              <td width="7%" align="center"><input type="text" name="10_4" id="10_4" value="${v10_4}" /></td>
	              <td width="7%" align="center"><input type="text" name="10_5" id="10_5" value="${v10_5}" /></td>
	              <td width="7%" align="center"><input type="text" name="10_6" id="10_6" value="${v10_6}" /></td>
	              <td width="7%" align="center"><input type="text" name="10_7" id="10_7" value="${v10_7}" /></td>
	              <td width="7%" align="center"><input type="text" name="10_8" id="10_8" value="${v10_8}" /></td>
	              <td width="7%" align="center"><input type="text" name="10_9" id="10_9" value="${v10_9}" /></td>
	              <td width="7%" align="center"><input type="text" name="10_10" id="10_10" value="${v10_10}" /></td>
	              <td width="7%" align="center"><input type="text" name="10_11" id="10_11" value="${v10_11}" /></td>
	              <td width="7%" align="center"><input type="text" name="10_12" id="10_12" value="${v10_12}" /></td>
	            </tr>
	            
	            
	            
	            
	            <tr>
	              <td width="16%" nowrap="nowrap">专职安全管理人员薪酬</td>
	              <td width="7%" align="center"><input type="text" name="11_1" id="11_1" value="${v11_1}" /></td>
	              <td width="7%" align="center"><input type="text" name="11_2" id="11_2" value="${v11_2}" /></td>
	              <td width="7%" align="center"><input type="text" name="11_3" id="11_3" value="${v11_3}" /></td>
	              <td width="7%" align="center"><input type="text" name="11_4" id="11_4" value="${v11_4}" /></td>
	              <td width="7%" align="center"><input type="text" name="11_5" id="11_5" value="${v11_5}" /></td>
	              <td width="7%" align="center"><input type="text" name="11_6" id="11_6" value="${v11_6}" /></td>
	              <td width="7%" align="center"><input type="text" name="11_7" id="11_7" value="${v11_7}" /></td>
	              <td width="7%" align="center"><input type="text" name="11_8" id="11_8" value="${v11_8}" /></td>
	              <td width="7%" align="center"><input type="text" name="11_9" id="11_9" value="${v11_9}" /></td>
	              <td width="7%" align="center"><input type="text" name="11_10" id="11_10" value="${v11_10}" /></td>
	              <td width="7%" align="center"><input type="text" name="11_11" id="11_11" value="${v11_11}" /></td>
	              <td width="7%" align="center"><input type="text" name="11_12" id="11_12" value="${v11_12}" /></td>
	            </tr>
	            
		        
	         </table>
	         
	         <div style="display:none;">
	         	<table id="dataTab" width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="silver">
			
					<tr>
						<th colspan="13" height="80">
							<span style="font-size:18px;font-weight:bold;">${_Year}年度安全费用实际支出</span>
						</th>
						
					</tr>
		            <tr>
		              <th width="16%" nowrap="nowrap">&nbsp;</th>
		              <td width="7%" align="center">1月</td>
		              <td width="7%" align="center">2月</td>
		              <td width="7%" align="center">3月</td>
		              <td width="7%" align="center">4月</td>
		              <td width="7%" align="center">5月</td>
		              <td width="7%" align="center">6月</td>
		              <td width="7%" align="center">7月</td>
		              <td width="7%" align="center">8月</td>
		              <td width="7%" align="center">9月</td>
		              <td width="7%" align="center">10月</td>
		              <td width="7%" align="center">11月</td>
		              <td width="7%" align="center">12月</td>
		            </tr>
		            
		            <tr>
		              <td width="16%" nowrap="nowrap">安全设施设备安装维护</td>
		              <td width="7%" align="center">${v1_1}</td>
		              <td width="7%" align="center">${v1_2}</td>
		              <td width="7%" align="center">${v1_3}</td>
		              <td width="7%" align="center">${v1_4}</td>
		              <td width="7%" align="center">${v1_5}</td>
		              <td width="7%" align="center">${v1_6}</td>
		              <td width="7%" align="center">${v1_7}</td>
		              <td width="7%" align="center">${v1_8}</td>
		              <td width="7%" align="center">${v1_9}</td>
		              <td width="7%" align="center">${v1_10}</td>
		              <td width="7%" align="center">${v1_11}</td>
		              <td width="7%" align="center">${v1_12}</td>
		            </tr>
		            
		            
		            <tr>
		              <td width="16%" nowrap="nowrap">应急救援</td>
		              <td width="7%" align="center">${v2_1}</td>
		              <td width="7%" align="center">${v2_2}</td>
		              <td width="7%" align="center">${v2_3}</td>
		              <td width="7%" align="center">${v2_4}</td>
		              <td width="7%" align="center">${v2_5}</td>
		              <td width="7%" align="center">${v2_6}</td>
		              <td width="7%" align="center">${v2_7}</td>
		              <td width="7%" align="center">${v2_8}</td>
		              <td width="7%" align="center">${v2_9}</td>
		              <td width="7%" align="center">${v2_10}</td>
		              <td width="7%" align="center">${v2_11}</td>
		              <td width="7%" align="center">${v2_12}</td>
		            </tr>
		            
		            
		            <tr>
		              <td width="16%" nowrap="nowrap">隐患整改</td>
		              <td width="7%" align="center">${v3_1}</td>
		              <td width="7%" align="center">${v3_2}</td>
		              <td width="7%" align="center">${v3_3}</td>
		              <td width="7%" align="center">${v3_4}</td>
		              <td width="7%" align="center">${v3_5}</td>
		              <td width="7%" align="center">${v3_6}</td>
		              <td width="7%" align="center">${v3_7}</td>
		              <td width="7%" align="center">${v3_8}</td>
		              <td width="7%" align="center">${v3_9}</td>
		              <td width="7%" align="center">${v3_10}</td>
		              <td width="7%" align="center">${v3_11}</td>
		              <td width="7%" align="center">${v3_12}</td>
		            </tr>
		            
		            
		            <tr>
		              <td width="16%" nowrap="nowrap">劳动防护</td>
		              <td width="7%" align="center">${v4_1}</td>
		              <td width="7%" align="center">${v4_2}</td>
		              <td width="7%" align="center">${v4_3}</td>
		              <td width="7%" align="center">${v4_4}</td>
		              <td width="7%" align="center">${v4_5}</td>
		              <td width="7%" align="center">${v4_6}</td>
		              <td width="7%" align="center">${v4_7}</td>
		              <td width="7%" align="center">${v4_8}</td>
		              <td width="7%" align="center">${v4_9}</td>
		              <td width="7%" align="center">${v4_10}</td>
		              <td width="7%" align="center">${v4_11}</td>
		              <td width="7%" align="center">${v4_12}</td>
		            </tr>
		            
		            
		            <tr>
		              <td width="16%" nowrap="nowrap">安全教育和培训</td>
		              <td width="7%" align="center">${v5_1}</td>
		              <td width="7%" align="center">${v5_2}</td>
		              <td width="7%" align="center">${v5_3}</td>
		              <td width="7%" align="center">${v5_4}</td>
		              <td width="7%" align="center">${v5_5}</td>
		              <td width="7%" align="center">${v5_6}</td>
		              <td width="7%" align="center">${v5_7}</td>
		              <td width="7%" align="center">${v5_8}</td>
		              <td width="7%" align="center">${v5_9}</td>
		              <td width="7%" align="center">${v5_10}</td>
		              <td width="7%" align="center">${v5_11}</td>
		              <td width="7%" align="center">${v5_12}</td>
		            </tr>
		            
		            
		            <tr>
		              <td width="16%" nowrap="nowrap">安全设施设备检测</td>
		              <td width="7%" align="center">${v6_1}</td>
		              <td width="7%" align="center">${v6_2}</td>
		              <td width="7%" align="center">${v6_3}</td>
		              <td width="7%" align="center">${v6_4}</td>
		              <td width="7%" align="center">${v6_5}</td>
		              <td width="7%" align="center">${v6_6}</td>
		              <td width="7%" align="center">${v6_7}</td>
		              <td width="7%" align="center">${v6_8}</td>
		              <td width="7%" align="center">${v6_9}</td>
		              <td width="7%" align="center">${v6_10}</td>
		              <td width="7%" align="center">${v6_11}</td>
		              <td width="7%" align="center">${v6_12}</td>
		            </tr>
		            
		            
		            <tr>
		              <td width="16%" nowrap="nowrap">其他直接相关支出</td>
		              <td width="7%" align="center">${v7_1}</td>
		              <td width="7%" align="center">${v7_2}</td>
		              <td width="7%" align="center">${v7_3}</td>
		              <td width="7%" align="center">${v7_4}</td>
		              <td width="7%" align="center">${v7_5}</td>
		              <td width="7%" align="center">${v7_6}</td>
		              <td width="7%" align="center">${v7_7}</td>
		              <td width="7%" align="center">${v7_8}</td>
		              <td width="7%" align="center">${v7_9}</td>
		              <td width="7%" align="center">${v7_10}</td>
		              <td width="7%" align="center">${v7_11}</td>
		              <td width="7%" align="center">${v7_12}</td>
		            </tr>
		            
		            
		            
		            
		            <tr>
		              <td width="16%" nowrap="nowrap">购置靠泊专用设备等支出</td>
		              <td width="7%" align="center">${v8_1}</td>
		              <td width="7%" align="center">${v8_2}</td>
		              <td width="7%" align="center">${v8_3}</td>
		              <td width="7%" align="center">${v8_4}</td>
		              <td width="7%" align="center">${v8_5}</td>
		              <td width="7%" align="center">${v8_6}</td>
		              <td width="7%" align="center">${v8_7}</td>
		              <td width="7%" align="center">${v8_8}</td>
		              <td width="7%" align="center">${v8_9}</td>
		              <td width="7%" align="center">${v8_10}</td>
		              <td width="7%" align="center">${v8_11}</td>
		              <td width="7%" align="center">${v8_12}</td>
		            </tr>
		            
		            
		            
		            
		            <tr>
		              <td width="16%" nowrap="nowrap">安全生产标准化建设支出</td>
		              <td width="7%" align="center">${v9_1}</td>
		              <td width="7%" align="center">${v9_2}</td>
		              <td width="7%" align="center">${v9_3}</td>
		              <td width="7%" align="center">${v9_4}</td>
		              <td width="7%" align="center">${v9_5}</td>
		              <td width="7%" align="center">${v9_6}</td>
		              <td width="7%" align="center">${v9_7}</td>
		              <td width="7%" align="center">${v9_8}</td>
		              <td width="7%" align="center">${v9_9}</td>
		              <td width="7%" align="center">${v9_10}</td>
		              <td width="7%" align="center">${v9_11}</td>
		              <td width="7%" align="center">${v9_12}</td>
		            </tr>
		            
		            
		            
		            
		            <tr>
		              <td width="16%" nowrap="nowrap">安全生产推广应用支出</td>
		              <td width="7%" align="center">${v10_1}</td>
		              <td width="7%" align="center">${v10_2}</td>
		              <td width="7%" align="center">${v10_3}</td>
		              <td width="7%" align="center">${v10_4}</td>
		              <td width="7%" align="center">${v10_5}</td>
		              <td width="7%" align="center">${v10_6}</td>
		              <td width="7%" align="center">${v10_7}</td>
		              <td width="7%" align="center">${v10_8}</td>
		              <td width="7%" align="center">${v10_9}</td>
		              <td width="7%" align="center">${v10_10}</td>
		              <td width="7%" align="center">${v10_11}</td>
		              <td width="7%" align="center">${v10_12}</td>
		            </tr>
		            
		            
		            
		            
		            <tr>
		              <td width="16%" nowrap="nowrap">专职安全管理人员薪酬</td>
		              <td width="7%" align="center">${v11_1}</td>
		              <td width="7%" align="center">${v11_2}</td>
		              <td width="7%" align="center">${v11_3}</td>
		              <td width="7%" align="center">${v11_4}</td>
		              <td width="7%" align="center">${v11_5}</td>
		              <td width="7%" align="center">${v11_6}</td>
		              <td width="7%" align="center">${v11_7}</td>
		              <td width="7%" align="center">${v11_8}</td>
		              <td width="7%" align="center">${v11_9}</td>
		              <td width="7%" align="center">${v11_10}</td>
		              <td width="7%" align="center">${v11_11}</td>
		              <td width="7%" align="center">${v11_12}</td>
		            </tr>
		            
			        
		         </table>
	         </div>
	         
	         <div id="wrap_foot" style="text-align:left;padding:20px;">
				<a id="btn_sub" href="javascript:void(0);" class="widget"><span>保 存</span></a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0);" class="widget" onclick="window.close();"><span>关 闭</span></a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0);" class="widget" onclick="javascript:toExcel('dataTab','年度费用支出');"><span>导出Excel</span></a>
			</div>
	        </div>
		</form>

	<!--网站底部部分-->
	<div id="footer">上海慧智计算机技术有限公司 技术支持</div>

</body>
</html>
