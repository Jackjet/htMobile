<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title>月度异常项报告</title>
		<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/jquery-1.9.0.min.js" type="text/javascript"></script>
		<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
		<script src="<c:url value="/"/>js/commonFunction.js"></script>
		<link href="/css/all.css" rel="stylesheet" type="text/css" />
		
		
		<style>
			a{
				outline:none;
			}
			body{
				font-family:"microsoft yahei",黑体;
				font-size:15px;
				overflow-x:auto;
				overflow-y:auto;
			}
			#mainContent{
				margin: 0 auto;
				width:100%;
				border:0px solid red;	
			}
			#mainTab{
				width:100%;
				text-align:center;
			}
			
			#topReport td,#topReport th{
				background:white;
			}
			
			#topReport{
				width:90%;
				border:0px solid lightGray;
				margin:0 auto;
			}
			
			
			
			.blueFont{
				color:#144d9d;
			}
			
			.countFont{
				color:#144d9d;
				font-weight:normal;
				font-size:15px;
			}
			
			#bottomList tr{
				height:45px;
			}
			
			.hasBottomBoder{
				border-bottom:1px solid lightGray;
			}
		</style>
		<script>
			//显示上传图片大图
			function showBigPic(bigID,realPath){
				//alert(bigID+"---"+realPath);
				$("#"+bigID).html("<img style='border:0px solid gray;' src='" + realPath + "' >").show();
			}
			function hidBigPic(bigID){
				 $("#"+bigID).hide("slow");
			}
		</script>
		
	</head>
	
	<body>
		<div id="mainContent">
			<table id="mainTab" border=0 cellspacing=0 cellpadding=0>
				<tr>
					<td valign="top">
						<div id="topReport">
							<div><br/>
								<span style="font-size:20px;"><span id="dataYear">${dataYear}</span>年${dataMonth}月隐患异常情况汇总</span><br/><br/>
							</div>
							<div>
								<table border=0 width="100%" style="margin:0 auto;" cellspacing=1 cellpadding=5 bgcolor="gray">
									<tr>
										<th></th>
										<c:forEach items="${categories}" var="category">
											<th nowrap>${category.categoryName}</th>
										</c:forEach>
										<th nowrap>合计</th>
									</tr>
									<c:forEach items="${resultReport}" var="report">
										<tr>
											<c:forEach items="${report}" var="mymap" > 
												<td nowrap>${mymap.key}</td>
												<c:forEach items="${mymap.value}" var="eCount">
													<td class="countFont">${eCount }</td>
												</c:forEach>
											</c:forEach> 
											
										</tr>
									</c:forEach>
								</table>
								<br/>
							</div>
							
						</div>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<div id="bottomList">
							<table border=0 cellspacing=0 cellpadding=0 width="100%">
								<tr style="background:#eeeeee;height:35px;">
									<th width="60">&nbsp;</th>
									<th>隐患类型</th>
									<th>异常情况</th>
									<th>图片</th>
									<th>责任部门</th>
									<th>检查人</th>
									<th>检查时间</th>
									<th width="30">&nbsp;</th>
								</tr>
								<c:forEach items="${details}" var="detail" varStatus="index">
									<tr>
										<td>${index.index+1}</td>
										<td class="hasBottomBoder"><b>${detail.errorType}</b></td>
										<td class="hasBottomBoder" align=left>${detail.errorInfo}</td>
										<td class="hasBottomBoder">
											<c:forEach items="${detail.errorAttachs}" var="attach" varStatus="img">
												<div id="big_${img.index}" style='position:absolute;display:none;z-index:999;'></div>
												<a href="<c:url value="/"/>${attach}" target="_blank">
													<img border=0 title="点击查看大图" src="<c:url value="/"/>${attach}" width="35" height="35" />
												</a>
												
											</c:forEach>
										</td>
										<td class="hasBottomBoder">${detail.dutyDeptName}</td>
										<td class="hasBottomBoder">${detail.findUserName}</td>
										<td class="hasBottomBoder">${detail.findTime}</td>
										<td>&nbsp;</td>
									</tr>
								</c:forEach>
							</table>
							<br/>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>

