<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
<html>
<head>
	<meta charset="utf-8">
		<title>上海海通国际汽车码头安监平台</title>
		<!-- <script
			  src="https://code.jquery.com/jquery-1.12.4.js"
			  integrity="sha256-Qw82+bXyGq6MydymqBxNPYTaUXXq7c8v3CwiYwLLNXU="
			  crossorigin="anonymous"></script> -->
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
					//$.cookie("passWord", passWord, { expires: 365 }); // 存储一个带365天期限的 cookie
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
					//$("#password").val($.cookie("passWord"));
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
				width:400px;
				height:280px;
				border:0px solid red;
				background:url(/images/login-center.png) no-repeat;
				background-size:100% 100%;
				position:relative;
				top:25%;
				margin:0 auto;
			}
			
			.input{
				height:30px;
				width:35%;
				background:transparent;
				font-size:20px;
				color:white;
				vertical-align:bottom;
				border:0px;	
				text-align:center;
			}
			#userName{
				position:relative;
				top:69px;
				left:120px;
				
			}
			#password{
				position:relative;
				top:89px;
				left:120px;
				
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
		</style>
	</head>

	<body id="bg">
		<div id="main">
			
				<table border="0" width="100%;" height="100%" cellpadding=0 cellspacing=0>
					<tr style="height:20%;">
						<td align="center" colspan="3"><!--  style="background:url(/images/titleLogo.png) no-repeat center;" -->
							<img src="/images/headtitle.png" width="500" height="35" />
						</td>
					</tr>
					<tr>
						<td width="35%;" align="left" style="padding-left:50px;"><!--  style="background:url(/images/login-left.png) no-repeat 20%;" -->
							<img src="/images/login-left.png" width="150" height="150" />
						</td>
						<td>
							<div id="mainDiv">
								<form method="post" name="loginForm" id="loginForm" action="/login.htm">
									<input type="text" class="input" placeholder="" name="userName" id="userName" /><br/>
									<input type="password" class="input" placeholder="" name="password" id="password" /><br/>
									<a  onclick="saveUserInfo();" class="btn">登 录</a>
								</form>
							</div>
							
						</td>
						<td align="right" width="35%;" style="padding-right:50px;"><!--  style="background:url(/images/login-right.png) no-repeat 75%;" -->
							<img src="/images/login-right.png" width="150" height="150" />
						</td>
					</tr>
					<tr style="height:30%">
						<td colspan="3"></td>
					</tr>
				</table>
			
		</div>
	</body>
</html>
