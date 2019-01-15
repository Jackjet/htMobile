<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>查看文档信息</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/view.css" />
<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script> <!--jquery包-->
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/gkSecure/jquery-ui-1.9.2.custom.css" />
	<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
	<script>
		$(function(){
			$(".widget").button();
		});
		var height = window.screen.height;
		//修改
		function editInfor_base(rowId){
			window.location.href="/security/document.htm?method=edit&documentId="+rowId;
		}
		
		//授权
		function doAuthorize_base(rowId){
			window.showModalDialog("/security/document.htm?method=editInforRight&rowId="+rowId,'',"dialogWidth:900px;dialogHeight:"+height+"px;center:Yes;dialogTop: 100px; dialogLeft: 300px;");
		}
		
		//保存信息后重新加载tab
		function loadTab(){
			alert(12);
			//window.opener.loadTab(${_DocumentInfor.category.categoryId});
		}
	</script>
</head>
<base target="_self"/>
<body>
<div id="wrap" >

<!--网站主题部分-->
<div id="right">
  <div class="emil"></div>
  <div class="module">
    <div class="content">
  <div class="xinxi"><strong>查看文档信息</strong></div><p>&nbsp;</p>
  <div class="news">
  <form id="documentInforForm" name="documentInforForm" action="<c:url value="/security/document.htm"/>?method=save" method="post" enctype="multipart/form-data">
	<input type="hidden" name="documentId" id="documentId" value="${_DocumentInfor.documentId}"/>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="black" class="doc">
            <tr>
              <td width="15%" nowrap="nowrap" align="right" bgcolor="lightGray"><span class="blues">标题：</span>&nbsp;&nbsp;</td>
              <td width="85%" bgcolor="#FFFFFF"> &nbsp;&nbsp;${_DocumentInfor.documentTitle}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="lightGray"><span class="blues">关键字：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_DocumentInfor.keyWord}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="lightGray"><span class="blues">分类：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_DocumentInfor.category.categoryName}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="lightGray"><span class="blues">作者：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_Author}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" bgcolor="lightGray"><span class="blues">创建时间：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;${_DocumentInfor.createTime}</td>
            </tr>
            <tr>
              <td align="right" nowrap="nowrap" valign="top" bgcolor="lightGray"><span class="blues">附件：</span>&nbsp;&nbsp;</td>
              <td bgcolor="#FFFFFF">&nbsp;&nbsp;
              	<c:forEach var="file" items="${_Attachment_Names}" varStatus="status">
              		<a href="<c:url value="${'/common/'}"/>download.jsp?filepath=${_Attachments[status.index]}" title="点击下载">
              			<font color="red">${file}(${_Attachment_Sizes[status.index]})</font>
              		</a><br/>&nbsp;&nbsp;
              	</c:forEach>
              </td>
            </tr>
	        
         </table>
         <div id="buttons"><table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td>
                    	<a href="javascript:void(0);" class="widget" onclick="editInfor_base(${_DocumentInfor.documentId});"><span>修 改</span></a>
                    </td>
                    <td>
                    	<a href="javascript:void(0);" class="widget" onclick="window.close();"><span>关 闭</span></a>
                    </td>
                  </tr>
          </table></div>
        </form>
  </div>
    </div>
  </div>
</div>
</div><div class="clearit"></div>

<!--网站底部部分-->
<div id="footer">上海慧智计算机技术有限公司 技术支持</div>

</body>
</html>

