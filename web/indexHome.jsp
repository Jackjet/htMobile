<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
		<title>上海海通国际汽车码头安监平台</title>
		<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
		<script src="js/js.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/pngfix.js"></script>
		<link rel="stylesheet" type="text/css" href="css/style.css" />
		<link href="/css/all.css" rel="stylesheet" type="text/css" />
		<style>
			#frame_main{
				BACKGROUND-COLOR: white;
				BORDER-LEFT: #85b0cb 1px solid;
				PADDING-LEFT: 4px;
			}
		</style>
	</head>
	
		<frameset id="frame1" framespacing="0" border="0" frameborder="NO" cols="*" rows="65,*,20">
		    <frame scrolling="no" frameborder="0" src="top.jsp" noresize="" name="banner"></frame>
			<frameset id="frame2" framespacing="0" border="0" frameborder="NO" cols="200,*" rows="*">
			    <frame scrolling="auto" frameborder="0" src="left.jsp" noresize="" name="leftmenu"></frame>
			    <frame scrolling-x="no" allowTransparency="true" scrolling-y="auto" frameborder="0" src="main.jsp" name="mainInfor" id="mainInfor"></frame>
		    </frameset>
	    	<frame scrolling="no" frameborder="0" src="statusBar.jsp" noresize="" name="status_bar"></frame>
	    	<div id="livemargins_control" style="position: absolute; display: none; z-index: 9999;"></div>
		</frameset>
		
		<noframes></noframes>
	
</html>