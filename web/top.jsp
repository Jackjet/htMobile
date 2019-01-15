<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<script type="text/javascript" src="<c:url value="/"/>js/poshytip-1.2/demo/includes/jquery-1.4.2.min.js"></script>
	<SCRIPT type=text/javascript src="js/js.js"></SCRIPT>
	<script src="<c:url value='/js'/>/colortip-1.0/colortip-1.0-jquery.js"></script>
	<link href="/css/all.css" rel="stylesheet" type="text/css" />
	<SCRIPT>
		function re_login()
		{
			msg="确认要注销么？";
		  	if(window.confirm(msg))
		    	parent.parent.location="/logout.htm";
		}
		function show() {
            var openUrl = "images/download.png";//弹出窗口的url
            var iWidth=280; //弹出窗口的宽度;
            var iHeight=280; //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
            window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);

        }
		function home(){
			//window.location.href="main"
		}

	</SCRIPT>
	<style>
		body{
			margin:0 auto;
			font-family:微软雅黑;
		}
		a{
			text-decoration:none;
			color:black;
		}
		a:hover,a:visited {
			outline:none;
		}
	</style>
<META name=GENERATOR content="MSHTML 9.00.8112.16476">
<title>首页</title>
</HEAD>
<BODY>
	<div style="border:0px solid red;margin:0 auto;width:100%;height:65px;">
		<table border=0 style="height:100%;width:100%;" cellspacing="0">
			<tr>
				<td style="border:0px solid red;width:198px;background:#112F53;" align="center">
					<span style="color:#0FD7FD;font-size:18px;">
						您好，<span>${_GLOBAL_USER.name}</span>
					</span>
				</td>
				<td valign="middle"><span style="font-size:25px;">&nbsp;&nbsp;上海海通国际汽车码头安监平台</span></td>
				<td style="width:220px;" align=center>
					<!--  <span style="cursor:pointer;"><a href="http://192.168.1.112:8080/exam" target="_blank">考试系统</a></span>
					&nbsp;&nbsp;&nbsp;&nbsp;-->
					<span style="cursor:pointer;" onclick="javascript:home();"><a href="main.jsp" target="mainInfor">首页</a></span>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span style="cursor:pointer;" onclick="javascript:re_login();">退出</span>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<span style="cursor:pointer;"><img src="images/smallewm.png" onclick="show()"></span>
				</td>
			</tr>
		</table>
	</div>
</BODY>
</HTML>
