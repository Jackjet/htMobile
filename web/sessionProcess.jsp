<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title></title>
	<script>
		function init(){
			alert('${_Action_ErrorMessage}');
			var hasParent = false;
			if(window.parent!=null){
				hasParent = true;
			}
			if(hasParent){
				top.location.href = "login.jsp";
			}else{
				window.location.href = "login.jsp";
			}
		}
	</script>
  </head>
  
  <body onload="init();">
    
  </body>
</html>
