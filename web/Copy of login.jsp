<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
<html>
<head>
	<meta charset="utf-8">
		<title>上海港国际客运中心安监平台</title>
		<link href="/css/login.css" rel="stylesheet" type="text/css" />
		<SCRIPT type=text/javascript src="/js/jquery-1.9.1.js"></SCRIPT>
		<SCRIPT type=text/javascript src="/js/jquery.cookie.js"></SCRIPT>
		
		<script src="/js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script><!--jquery ui-->
		<link rel="stylesheet" type="text/css" media="screen" href="/js/jquery-ui-1.9.2.custom/css/cupertino/jquery-ui-1.9.2.custom.css" />
		<script type="text/javascript">
			
			
			
			
			//初始化页面时验证是否记住了密码
			$(document).ready(function() {
				/*$("#installTip").dialog({
					autoOpen:false,
					position:"top"
				});
				try {  
				    var ax = new ActiveXObject("iWebOffice2006.HandWriteCtrl");  
				    //alert("已安装");  
				} catch(e) {  
				   //alert("尚未安装在线批注插件，请下载安装！"); 
				   //$("#installTip").dialog("open"); 
				} */ 
			
			
				if ($.cookie("rmbUser") == "true") {
					$("#rmbUser").attr("checked", true);
					$("#userName").val($.cookie("userName"));
					$("#password").val($.cookie("passWord"));
				}else{
					$("#userName").focus();
				}
				
				//注册回车事件
				document.onkeydown=function(event){
	            	var e = event || window.event || arguments.callee.caller.arguments[0];
	            	if(e && e.keyCode==13){ // enter 键
	                	//要做的事情
	                	saveUserInfo();  
	            	}
	            };
			});
			//保存用户信息
			function saveUserInfo() {
				var isChecked = document.getElementById("rmbUser").checked; 
				//if ($("#rmbUser").attr("checked") == true) {
				if(isChecked){
					var userName = $("#userName").val();
					var passWord = $("#password").val();
					$.cookie("rmbUser", "true", { expires: 365 }); // 存储一个带365天期限的 cookie
					$.cookie("userName", userName, { expires: 365 }); // 存储一个带365天期限的 cookie
					$.cookie("passWord", passWord, { expires: 365 }); // 存储一个带365天期限的 cookie
				} else {
					$.cookie("rmbUser", "false", { expires: -1 });
					$.cookie("userName", '', { expires: -1 });
					$.cookie("passWord", '', { expires: -1 });
				}
				
				document.actForm.submit();
			};
		</script>
	</head>

	<body id="bg">
		<div id="main">
			<div class="login">
				<h2> &nbsp;&nbsp; 欢迎使用</h2>
				<div class="logink">
					<div class="clogo">
						<img src="/images/sipgllogo.png" width="84" height="78" />
					</div>
					<div class="loginr">
						<form method="post" name="actForm" id="actForm" action="/login.htm">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<input type="text" name="userName" id="userName" />
									</td>
								</tr>
								<tr>
									<td height="12"></td>
								</tr>
								<tr>
									<td>
										<input type="password" name="password" id="password" />
									</td>
								</tr>
								<tr>
									<td height="14"></td>
								</tr>
								<tr>
									<td>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<table width="100%" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td style="font-size:13px;color:silver;">
																<%--<input type="radio" name="radio" id="dkey" value="dkey" />--%>
																<input type="checkbox" id="rmbUser"/> <label for="rmbUser">记住密码</label>
															</td>
															<td>&nbsp;</td>
															<%--<td>
																<input type="radio" name="radio" id="radio" value="radio" /> 自动登录
															</td>
														--%></tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
													<table width="100%" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td height="12"></td>
															<td height="12"></td>
														</tr>
														<tr>
															<td colspan=2>
																<input type="button" onclick="saveUserInfo();" name="button" id="button" value="登录" /> 
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
