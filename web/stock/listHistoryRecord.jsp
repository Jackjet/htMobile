<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>历史库存查询</title>
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
	
	<link href="<c:url value="/"/>js/loadmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
	<script type='text/javascript' src='<c:url value="/"/>js/loadmask/jquery.loadmask.min.js'></script>
	
	<!-- 导出到excel -->
	<script type='text/javascript' src='<c:url value="/"/>js/htmlToExcel.js'></script>
	
	<!-- 按钮样式 -->
	<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/button.css" />
	<!-- 表单验证 -->
	<script src="<c:url value='/'/>js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/'/>js/Validform_v5.3.2/css/style.css" />
	<style>
		body{
			background-color:white;
		}
		#conditionTab{
			
		}
		#conditionTab td{
			padding-left:10px;
		}
		#hisReportTabContent{
			text-align:center;
		}
		#hisReportTab th,#hisReportTab td{
			background-color:#FFFFFF;
		}
		#hisReportTabContent th,#hisReportTabContent td{
			background-color:#FFFFFF;
		}
	</style>
	<script>
		$(function(){
			//$("#btn_sub").button();
			//$("#btn_clo").button();
			
			$("#hisReportForm").Validform({
				tiptype:3,
				btnSubmit:"#btn_sub",
				btnReset:"#btn_reset",
				showAllError:true,
				ajaxPost:true,
				beforeSubmit:function(curform){
					$("#right").mask("处理中，请稍等...");
				},
				callback:function(data){
					if($("#right").isMasked()){
					　$("#right").unmask();
					}
					$("#right").unmask();
					//alert(data._Orders);
					
					if(data.status == "y"){
						$.each(data._Instances, function(i, n) {
			               //alert(n.inOrderNo); 
			            });
			            mkTab(data._Instances);
					}
				}
			});  
			
			
		});
		
		function mkTab(orders){// 
			var returnHtml = "<table id=\"hisReportTabContent\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"black\" class=\"doc\">";
			returnHtml += "<tr><td colspan=\"8\" bgcolor=\"#FFFFFF\" align=\"center\"><h3>上港集团物流有限公司浦东分公司</h3>";
			returnHtml += "<h1>备件历史库存量查询</h1>";
			returnHtml += "节点日期："+$("#recordDate").val()+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>";//<tr><td>
			
			//returnHtml += "<table id=\"hisReportTab\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"black\" class=\"doc\">";
			returnHtml += "<tr><th nowrap=\"nowrap\">序号</th>";
			returnHtml += "<th nowrap=\"nowrap\">物资编码</th>";
			returnHtml += "<th nowrap=\"nowrap\">品名（品牌/规格/型号）</th>";
			returnHtml += "<th nowrap=\"nowrap\">&nbsp;单位&nbsp;</th>";
			//returnHtml += "<th nowrap=\"nowrap\">当前库存数量</th>";
			returnHtml += "<th nowrap=\"nowrap\">节点日库存数量</th>";
			returnHtml += "<th nowrap=\"nowrap\">单价</th>";
			returnHtml += "<th nowrap=\"nowrap\">节点日库存金额</th>";
			returnHtml += "<th nowrap=\"nowrap\">供应商</th></tr>";
			
			var allCount = 0;   //当前总量
			var allExCount = 0; //节点前总量
			var allAmount = 0;  //节点前总金额
			$.each(orders, function(i, n) {
				allCount += n.count;
				allExCount += n.itemCount;
				allAmount += (n.itemCount) * (n.itemPrice);
				//alert(n.inOrderNo); 
				//alert(n.items);
				returnHtml += "<tr><td align=center>"+(i+1)+"</td>";
	       		returnHtml += "<td align=center>&nbsp;"+n.itemCode+"&nbsp;</td>";
	       		returnHtml += "<td align=left>&nbsp;"+n.itemName+"</td>";
	       		returnHtml += "<td align=center>"+n.itemUnit+"</td>";
	       		//returnHtml += "<td align=\"center\">"+n.count+"</td>";
	       		returnHtml += "<td align=\"center\">"+n.itemCount+"</td>";
	       		returnHtml += "<td align=\"right\">"+n.itemPrice+"</td>";
	       		returnHtml += "<td align=\"right\">"+((n.itemCount) * (n.itemPrice)).toFixed(5)+"</td>";
	       		returnHtml += "<td align=left>【"+n.supplierCode+"】"+n.supplierName+"</td></tr>";
               
            });
            returnHtml += "<tr><th colspan=4>合计</th>";
            //returnHtml += "<th align=\"center\">"+allCount+"</th>";
            returnHtml += "<th align=\"center\">"+allExCount+"</th>";
            returnHtml += "<th colspan=2 align=\"right\">"+allAmount.toFixed(5)+"</th>";
            returnHtml += "<th></th></tr>";
			//returnHtml += "</table>";
			
			//returnHtml += "</td></tr>";
            
            returnHtml += "</table>";
            $("#hisReport").html(returnHtml);
		}
		
	    
	    //查询备件
	     function searchItems(inputId){
	     	var keyWord = $("#"+inputId).val();
	     	if(keyWord != "" && keyWord != null){
	     		if(keyWord.length == 1){
	     			alert("关键字长度应不少于2！");
	     			$("#"+inputId).focus();
	     		}else{
	     			$("#right").mask("搜索中，请稍等...");
		     		$.getJSON("/stock/itemInfor.htm?method=getItemsByTagName&range=in&itemTag="+encodeURI(keyWord),function(data) {
			           var options = "<option value=''>--选择备件--</option>";
			           //alert("11"+data._Items);
			           $.each(data._Items, function(i, n) {
			               //options += "<option value='"+n.supplier.supplierId+"'  class='"+n.restCount+"'>"+n.supplier.supplierName+"</option>";
			               options += "<option value='"+n.itemId+"' class='"+n.itemUnit+"|"+n.itemPrice+"|"+n.priceUnit+"'>【"+n.itemCode+"】"+n.itemName+"</option>";
			               //+"|"+n.inOrderItemId+"   
			           });   
			           //alert($("#itemId").html());
			           //alert(options);
			           $("#itemId").html(options);   
			           //$("#itemId").append(options);
			           $("#right").unmask();
			           $("#itemId").focus();
			        });
			        $("#right").unmask();
	     		}
	     		
	     	}
        	
		    $("#right").unmask();
	     }
	     
	     function rtnItems(id){
	     	//var nextInp = document.getElementById(next);
			var event = arguments.callee.caller.arguments[0] || window.event;
			if(event.keyCode == 13){//判断是否按了回车，enter的keycode代码是13
				searchItems(id);
			}
	     }
	</script>

</head>
<base target="_self" />
<body>
	<%--<div id="header">
		<div class="logo">
			<img src="<c:url value="/"/>images/zhongtu.png" width="430"
				height="88" />
		</div>
	</div>
	--%><div>

		<!--网站主题部分-->
		<div id="right" style="margin-left:-1px;border:0px solid red;overflow-x:auto;">
			<div class="emil"></div>
			<div class="module">
				<div class="content">
					<div class="xinxi">
						<strong>历史库存查询</strong>
					</div>
					<!--<p>&nbsp;</p>-->
					<div class="news" style="height:100%;background-color:#cccccc;border-bottom:1px solid black;">
						<form id="hisReportForm" name="hisReportForm" action="/stock/historyCountRecord.htm?method=getHistoryReport" method="post">
							<table width="80%" border="0" cellpadding="0" cellspacing="0" class="doc" id="conditionTab" style="border:0px solid red;">
								<tr>
									<!-- <td align="right" nowrap="nowrap"><span class="blues">备件：</span>&nbsp;&nbsp;</td>
									<td nowrap="nowrap" align="left">
				                      	<input class="textfield" id="searchWord" size="15" onkeydown="rtnItems('searchWord');" onblur="searchItems('searchWord');"/>
										<img src="<c:url value="/"/>images/ssky.png" onclick="searchItems('searchWord');" align="absmiddle" height="27" border=0 style="cursor:pointer;margin-left:-7px;"/>
										<select name="itemId" id="itemId" class="select" ignore="ignore" datatype="*" nullmsg="请选择备件！" sucmsg="选择正确！" >
				                      		<option value="">--选择备件--</option>
				                      	</select>
									</td> -->
									
									
									<td align="left" nowrap="nowrap"><span class="blues">备件编码：</span>&nbsp;&nbsp;</td>
									<td nowrap="nowrap">
				                      	<input name="itemCode" id="itemCode" size="20" class="textfield" />
									</td>
									
									
									
									<td align="left" nowrap="nowrap"><span class="blues">备件名称：</span>&nbsp;&nbsp;</td>
									<td nowrap="nowrap">
				                      	<input name="itemName" id="itemName" size="20" class="textfield" />
									</td>
									
								</tr>
								<tr>
									<td align="left" nowrap="nowrap"><span class="blues">节点日期：</span>&nbsp;&nbsp;</td>
									<td nowrap="nowrap" align="left">
										<input name="recordDate" id="recordDate" size="12" class="textfield Wdate" datatype="*" sucmsg="填写正确！" nullmsg="请输入节点日期！" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
									</td>
								</tr>
								
								<tr><td height=30></td></tr>
								
								<tr>
									<td align="right" colspan=3 style="border:0px solid red;">
										<a href="javascript:void(0);" class="btn_blue" id="btn_sub" style="margin-right:50px;"><span>查  询</span></a>&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="javascript:void(0);" class="btn_red" id="btn_reset" style="margin-right:50px;"><span>重  填</span></a>&nbsp;&nbsp;&nbsp;&nbsp;
									<!-- <td align="center">
										<a href="/stock/historyCountRecord.htm?method=setHisRecord" target="_blank" class="btn_red" id="btn_reset"><span>手动备份</span></a>
									</td> -->
										<!--<a href="javascript:void(0);" id="btn_excel">
											<img src="<c:url value="/"/>images/search/excel.gif" border=0/>
										</a>-->
										<a href="javascript:void(0);" onclick="javascript:htmlToExcel(document.getElementById('hisReportTabContent'),document.getElementById('hisReport'),3,1,'yyyy-MM-dd','ZZ','00000');" class="btn_blue" id="btn_excel"><span>导出到Excel</span></a>
									</td>
								</tr>
							</table>
							<br/>
						</form>
					</div>
					<br/>
					<div id="hisReport" style="border:0px solid red;width:100%;">
						
					</div>
					<div id="activity_pane2"></div>
					<br/>
				</div>
			</div>
		</div>
	</div>
	<div class="clearit"></div>

	<!--网站底部部分-->
	<%--<div id="footer">上海慧智计算机技术有限公司 技术支持</div>

--%></body>
</html>
