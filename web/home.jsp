<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
<html>
<head>
	<meta charset="utf-8">
		<title>上海海通国际汽车码头安监平台</title>
		<SCRIPT type=text/javascript src="/js/jquery-1.9.1.js"></SCRIPT>
		<SCRIPT type=text/javascript src="/js/jquery.cookie.js"></SCRIPT>
		
		<script src="/js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script><!--jquery ui-->
		<link rel="stylesheet" type="text/css" media="screen" href="/js/jquery-ui-1.9.2.custom/css/gkSecure/jquery-ui-1.9.2.custom.css" />
		<script type="text/javascript">
			
			
			
			//保存用户信息
			function saveUserInfo() {
				//var isChecked = document.getElementById("rmbUser").checked; 
				//if ($("#rmbUser").attr("checked") == true) {
				//if(isChecked){
					var userName = $("#userName").val();
					var passWord = $("#password").val();
					//$.cookie("rmbUser", "true", { expires: 365 }); // 存储一个带365天期限的 cookie
					$.cookie("userName", userName, { expires: 365 }); // 存储一个带365天期限的 cookie
					$.cookie("passWord", passWord, { expires: 365 }); // 存储一个带365天期限的 cookie
				//} else {
				//	$.cookie("rmbUser", "false", { expires: -1 });
				//	$.cookie("userName", '', { expires: -1 });
				//	$.cookie("passWord", '', { expires: -1 });
				//}
				//alert(userName != null && passWord != null);
				if(userName != null && passWord != null && userName != "" && passWord != ""){
					document.loginForm.submit();
				}
			};
			
			$(function(){
				jQuery.ajaxSetup({async:false});
				$("#main").css("height",document.documentElement.clientHeight);
				
				//初始化页面时验证是否记住了密码
				//if ($.cookie("rmbUser") == "true") {
					//$("#rmbUser").attr("checked", true);
					$("#userName").val($.cookie("userName"));
					$("#password").val($.cookie("passWord"));
				//}else{
					$("#userName").focus();
				//}
				
				//注册回车事件
				document.onkeydown=function(event){
	            	var e = event || window.event || arguments.callee.caller.arguments[0];
	            	if(e && e.keyCode==13){ // enter 键
	            		if (document.activeElement.type == "text" || document.activeElement.type == "password") {
		                	//要做的事情
		                	saveUserInfo();  
	            		}
	            	}
	            };
			});
			
			//window.document.onkeydown = disableEnterKey;
		</script>
		
		<style>
			body{
				margin:0 auto;
				font-family:微软雅黑,黑体;
				background:#111B3E;
				overflow:hidden;
				background:url(/images/login-bg.png) no-repeat 100%;
				background-size:100% 100%;
			}
			#main{
				margin:0 auto;
				
			}
			#mainDiv{
				width:1150px;
				height:486px;
				border:0px solid red;
				background:url(/images/group.png) center top no-repeat;
				//background-size:100% 100%;
				margin:0 auto;
				text-align:center;
				padding-top:120px;
				
				
			}
			.mainTitle{
				color:#0fd8fe;
				font-size:30px;
			}
			
			.mainCount{
				color:white;
				font-size:75px;
			}
			
			.cell-aqtr{
				width:90px;
				height:58px;
				border:0px solid red;
				position:relative;
				top:-28%;
				right:-2%;
				color:#db9f41;
				font-size:25px;
				padding-top:7px;
			}
			
			.cell-yh{
				width:90px;
				height:58px;
				border:0px solid red;
				position:relative;
				top:-23%;
				left:76%;
				color:#db9f41;
				font-size:25px;
				padding-top:7px;
				margin-right:50px;
			}
			
			
			a{
				outline:none;
				color:white;
				text-decoration:none;
				cursor:pointer;
			}
			.btn{
				color:white;
				font-size:18px;
				position:relative;
				top:120px;
				left:86px;
				border:0px solid red;
				width:100px;
				height:30px;
				padding:8px 93px;
			}
			
			#goto_btn{
				position:absolute;
				top:10px;
				right:10px;
				height:30px;
				width:120px;
				border:0px solid red;
				color:white;
				font-size:14px;
			}
			
		</style>
	</head>

	<body id="bg">
		<div id="main">
				<div id="goto_btn">
					<a href="indexHome.jsp">
						<img src="/images/12.gif" border=0 width="40" height="15" align="absmiddle" />&nbsp;&nbsp;进入首页
					</a>
				</div>
				<table border="0" width="100%;" height="100%" cellpadding=0 cellspacing=0>
					<tr style="height:35%;">
						<td align="center">
							<br/><br/><br/>
							<img src="/images/headtitle.png" width="500" height="35" />
							<br/><br/>
						</td>
					</tr>
					<tr>
						<td>
							<div id="mainDiv">
								<%--<img src="/images/transparent.png" width="1150" height="486" border=1 />
								--%><span class="mainTitle"><span>${_ThisYear}</span>年<br/>重点危险节点检查</span><br/><br/><br/>
								<span class="mainCount" id="yearCount">${_YearCount}</span>
								<div class="cell-aqtr">

								</div>
								<div class="cell-yh">
									${_YearErrorCount}
								</div>
								
								
								
								<%--<div class="cell-kjcx-a" style="border:1px solid red; width:5.5%;height:8%;position:relative;top:25%;left:13%;"></div>
								<div class="cell-aqtr-a" style="border:1px solid red; width:5.5%;height:4%;position:fixed;top:47%;left:10%;"></div>
								<div class="cell-zbss-a" style="border:1px solid red; width:5.5%;height:4%;position:fixed;top:73%;left:11%;"></div>
								
								<div class="cell-gljg-a" style="border:1px solid red; width:5.5%;height:8%;position:fixed;top:41%;left:21.5%;"></div>
								<div class="cell-aqzr-a" style="border:1px solid red; width:5.5%;height:8%;position:fixed;top:60%;left:21.5%;"></div>
								<div class="cell-dwjs-a" style="border:1px solid red; width:6%;height:8%;position:fixed;top:81%;left:20.5%;"></div>
								
								<div class="cell-aqmb-a" style="border:1px solid red; width:6%;height:4%;position:fixed;top:34%;left:29%;"></div>
								<div class="cell-fgaq-a" style="border:1px solid red; width:8%;height:8%;position:fixed;top:70%;left:28%;"></div>
								
								
								<div class="cell-zygl-a" style="border:1px solid red; width:6%;height:4%;position:fixed;top:33.5%;left:65%;"></div>
								<div class="cell-zyjk-a" style="border:1px solid red; width:6%;height:4%;position:fixed;top:70.5%;left:65%;"></div>
							--%></div>
							<%--<img src="/images/transparent.png" width="1150" height="486" border=5  style="position:relative;top:-486px;left:0;"/>
						--%></td>
					</tr>
				</table>
			
		</div>
	</body>
</html>
