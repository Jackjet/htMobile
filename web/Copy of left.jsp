<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
	<title>jQuery实现TaoBaoUED左侧导航 - 站长素材</title>
	<link href="/js/nav/basic.css" rel="stylesheet" type="text/css">
	<link href="/js/nav/style.css" rel="stylesheet" type="text/css">
	<SCRIPT type=text/javascript src="js/jquery-1.9.1.js"></SCRIPT>
	<link href="/css/all.css" rel="stylesheet" type="text/css" />
	<script src="/js/nav/index.js"></script>
	<script>
		//setTimeout(setNav,5000); 
		function setNav(){
			$('#lanPos').css('top', $('.hover').offset().top);
			$('ul li').hover(function() {
				$('#lanPos').css('top', $(this).offset().top);
			}, function() {
				$('#lanPos').css('top', $('.hover').offset().top);
			})

			$('ul li').click(function() {
				alert(1);
				for ( var i = 0; i < $('ul li').size(); i++) {
					if (this == $('ul li').get(i)) {
						$('ul li').eq(i).children('a').addClass('hover');
					} else {
						$('ul li').eq(i).children('a').removeClass('hover');
					}
				}
				// return false;
			})
		}
	</script>
	<style>
		/* {
			SCROLLBAR-ARROW-COLOR: #26488e;
			BORDER-BOTTOM: 0px;
			BORDER-LEFT: 0px;
			SCROLLBAR-FACE-COLOR: #c3d7ff;
			PADDING-BOTTOM: 0px;
			MARGIN: 0px;
			PADDING-LEFT: 0px;
			PADDING-RIGHT: 0px;
			SCROLLBAR-DARKSHADOW-COLOR: #26488e;
			SCROLLBAR-HIGHLIGHT-COLOR: #c3d7ff;
			BORDER-TOP: 0px;
			SCROLLBAR-TRACK-COLOR: #eff4ff;
			BORDER-RIGHT: 0px;
			SCROLLBAR-3DLIGHT-COLOR: #eff4ff;
			PADDING-TOP: 0px
		}*/
	</style>
</head>
	<body>
		<ul>
			<c:forEach items="${_Menus}" var="menu" varStatus="index">
				<c:choose>
					<c:when test="${index.index == 0 }">
						<li><A class="hover" href="/security/document.htm?method=listBase&categoryId=${menu.categoryId}" target="mainInfor">${menu.categoryName}</A></li>
					</c:when>
					<c:otherwise>
						<li><A href="/security/document.htm?method=listBase&categoryId=${menu.categoryId}" target="mainInfor">${menu.categoryName}</A></li>
					</c:otherwise>
				</c:choose>
				
			</c:forEach>
			<li><A href="/security/documentCategory.htm?method=list" target="mainInfor">文档分类维护</A></li>
		
			<%--<li><A class="hover" href="/security/treeDocument.jsp" target="mainInfor">0前端交流</A></li>
			<li><A href="#" >1交互设计</A></li>
			<li><A href="#">3视觉设计</A></li>
			<li><A href="#">3用户研究</A></li>
			<li><A href="#">4设计茶吧</A></li>
			<li><A href="#">5前端交流</A></li>
			<li><A href="#">6团队生活</A></li>
			<li><A href="#">7交互设计</A></li>
			<li><A href="#">8视觉设计</A></li>
			<li><A href="#">9用户研究</A></li>
			<li><A href="#">10设计茶吧</A></li>
			<li><A href="#">11前端交流</A></li>
			<li><A href="#">12团队生活</A></li>
			<li><A href="#">13交互设计</A></li>
			<li><A href="#">14视觉设计</A></li>
			<li><A href="#">15用户研究</A></li>
			<li><A href="#">16设计茶吧</A></li>
			--%>
			<div id="lanPos"></div>
		</ul>
</body>
</html>


