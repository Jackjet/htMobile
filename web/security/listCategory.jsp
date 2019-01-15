<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>

<title>文档分类</title>
	<script src="<c:url value="/"/>js/multisearch.js"></script> <!--加载模态多条件查询相关js-->
	<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script> <!--jquery包-->
	<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
	<script src="<c:url value="/"/>js/jquery.layout.js" type="text/javascript"></script> <!--jquery 布局-->
	<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/jquery.jqGrid.min.js" type="text/javascript"></script> <!--jqgrid 包-->
	<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/i18n/grid.locale-cn.js" type="text/javascript"></script> <!--jqgrid 中文包-->
	<script src="<c:url value="/"/>js/inc_javascript.js"></script>
	<script src="<c:url value="/"/>js/commonFunction.js"></script>
	<script src="<c:url value="/"/>js/changeclass.js"></script> <!--用于改变页面样式-->
	
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/gkSecure/jquery-ui-1.9.2.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/tabstyle.css" />
	

<script type="text/javascript">
	$(document).ready(function(){
		$("body").css("height",document.documentElement.clientHeight);
		
		$(document).tooltip();
	});
	var height = window.screen.height;
	//新增
	function doAdd(){
		/*var refresh = window.showModalDialog("/security/documentCategory.htm?method=edit",null,"dialogWidth:900px;dialogHeight:"+height+"px;center:Yes;dialogTop: 5px; dialogLeft: 300px;");
		if(refresh == "Y") {
			self.location.reload();
		}*/
		window.open("/security/documentCategory.htm?method=edit","_blank");
	}
	//修改
	function doModify(categoryId,parentId){
		/*var refresh = window.showModalDialog("/security/documentCategory.htm?method=edit&categoryId="+categoryId,'',"dialogWidth:900px;dialogHeight:"+height+"px;center:Yes;dialogTop: 5px; dialogLeft: 300px;");
		if(refresh == "Y") {
			self.location.reload();
		}*/
		window.open("/security/documentCategory.htm?method=edit&categoryId="+categoryId,"_blank");
	}
	//删除
	function doDelete(categoryId){
		var yes = window.confirm("确定要删除吗？");
		if (yes) {
			var form = document.getElementById('listForm');
			form.action = "<c:url value='/security/documentCategory.htm'/>?method=delete&categoryId="+categoryId;
			form.submit();
			window.location.reload();
		}
	}
</script>
</head>
<body style="overflow-y:auto;">
<form id="listForm" action="/security/documentCategory.htm?method=list" method="post">
<div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all" id="gbox_grid_35" dir="ltr" style="width: 100%">
<div class="ui-jqgrid-view" id="gview_grid_35" style="width: 100%">
  <div class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">
    <span class="ui-jqgrid-title">文档分类</span>
  </div>
  <div style="width: 100%" class="ui-state-default ui-jqgrid-hdiv">
    <div class="ui-jqgrid-hbox" style="width: 100%">
      <table cellspacing="0" cellpadding="0" border="0" aria-labelledby="gbox_grid_35" role="grid" style="width: 100%" class="ui-jqgrid-htable">
          <thead>
              <tr role="rowheader" class="ui-jqgrid-labels">
                <th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 4%;">
                  <div id="jqgh_rn"></div>
				</th>
				
				<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 20%;">
				    <span class="ui-jqgrid-resize ui-jqgrid-resize-ltr" style="cursor: col-resize;">&nbsp;</span>
				    <div id="jqgh_title" class="ui-jqgrid-sortable">
						分类名称
				    </div>
				</th>
				
				<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 6%;">
				    <span class="ui-jqgrid-resize ui-jqgrid-resize-ltr" style="cursor: col-resize;">&nbsp;</span>
				    <div id="jqgh_title" class="ui-jqgrid-sortable">
						分类编号
				    </div>
				</th>
				
				<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 6%;">
				    <span class="ui-jqgrid-resize ui-jqgrid-resize-ltr" style="cursor: col-resize;">&nbsp;</span>
				    <div id="jqgh_title" class="ui-jqgrid-sortable">
						显示次序
				    </div>
				</th>
				
				<%--<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 17%;">
				    <span class="ui-jqgrid-resize ui-jqgrid-resize-ltr" style="cursor: col-resize;">&nbsp;</span>
				    <div id="jqgh_title" class="ui-jqgrid-sortable">
						浏览权限
				    </div>
				</th>
				
				<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 17%;">
				    <span class="ui-jqgrid-resize ui-jqgrid-resize-ltr" style="cursor: col-resize;">&nbsp;</span>
				    <div id="jqgh_title" class="ui-jqgrid-sortable">
						修改权限
				    </div>
				</th>
				
				<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 17%;">
				    <span class="ui-jqgrid-resize ui-jqgrid-resize-ltr" style="cursor: col-resize;">&nbsp;</span>
				    <div id="jqgh_title" class="ui-jqgrid-sortable">
						删除权限
				    </div>
				</th>--%>
		
				<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 10%;">
				    <div id="jqgh_url" class="ui-jqgrid-sortable">
						相关操作
				    </div>
				</th>
				
	    	  </tr>
		   </thead>
       </table>
     </div>
   </div>

	<div class="ui-jqgrid-bdiv" style="height: auto; width: 100%">
		<div style="position: relative;">
			<div>
				<table cellspacing="0" cellpadding="0" border="0" id="grid_35" class="ui-jqgrid-btable" role="grid" aria-multiselectable="false" aria-labelledby="gbox_grid_35" style="width: 100%">
					<tbody>
						<c:forEach items="${_TREE}" var="category" begin="1" varStatus="index">
							<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
								<td style="text-align: center; width: 4%;" class="ui-state-default jqgrid-rownum">${index.index}</td>
								<td style="width: 20%;"><c:choose><c:when test="${category.layer == 1}"><img src="<c:url value='/'/>images/tree_folder4.gif" border="0"/></c:when><c:otherwise><c:forEach begin="0" end="${category.layer}">&nbsp;</c:forEach><img src="<c:url value='/'/>images/tree_folder3.gif" border="0"/></c:otherwise></c:choose>${category.categoryName}</td>
								<td style="text-align: center; width: 6%;">${category.categoryCode}</td>
								<td style="text-align: center; width: 6%;">${category.displayNo}</td>
								<%--<td style="text-align: left; width: 17%;"><c:set var="viewStr" value=""/><c:forEach items="${_ViewRights[index.index]}" var="view" varStatus="viewIndex"><c:if test="${!empty view}"><c:set var="viewStr" value="${viewStr}${view}、"/></c:if></c:forEach><span style="white-space:pre-wrap;" title="包含人员：${viewStr}">${fn:substring(viewStr,0,13)}<c:if test="${fn:length(viewStr)>13}">...</c:if></span></td>
								<td style="text-align: left; width: 17%;"><c:set var="editStr" value=""/><c:forEach items="${_EditRights[index.index]}" var="edit" varStatus="editIndex"><c:if test="${!empty edit}"><c:set var="editStr" value="${editStr}${edit}、"/></c:if></c:forEach><span style="white-space:pre-wrap;" title="包含人员：${editStr}">${fn:substring(editStr,0,13)}<c:if test="${fn:length(editStr)>13}">...</c:if></span></td>
								<td style="text-align: left; width: 17%;"><c:set var="deleteStr" value=""/><c:forEach items="${_DeleteRights[index.index]}" var="delete" varStatus="deleteIndex"><c:if test="${!empty delete}"><c:set var="deleteStr" value="${deleteStr}${delete}、"/></c:if></c:forEach><span style="white-space:pre-wrap;" title="包含人员：${deleteStr}">${fn:substring(deleteStr,0,13)}<c:if test="${fn:length(deleteStr)>13}">...</c:if></span></td>
								--%>
								<td style="text-align: center; width: 10%;"><a href="javascript:;" onclick="doModify('${category.categoryId}','${category.parent.categoryId}');">[修改]</a> <a href="javascript:;" onclick="doDelete('${category.categoryId}');">[删除]</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div id="pager_35" style="width: 100%" class="ui-state-default ui-jqgrid-pager ui-corner-bottom" dir="ltr">
		<div role="group" class="ui-pager-control" id="pg_pager_35">
			<table cellspacing="0" cellpadding="0" border="0" role="row" style="width: 100%; table-layout: fixed;" class="ui-pg-table">
				<tbody>
					<tr>
						<td align="left" id="pager_35_left">
							<table cellspacing="0" cellpadding="0" border="0" style="float: left; table-layout: auto;" class="ui-pg-table navtable">
								<tbody>
									<tr>
										<td class="ui-pg-button ui-corner-all" title="刷新表格" id="refresh_grid_35">
											<div class="ui-pg-div">
												<span class="ui-icon ui-icon-refresh" onclick="javascript:location.reload();"></span>
											</div>
										</td>
										<td class="ui-pg-button ui-corner-all" title="点击新增信息" style="cursor: pointer;">
											<div class="ui-pg-div">
												<span class="ui-icon ui-icon-plusthick"></span>
												<span style="color: red;" onclick="doAdd();">新增</span>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
						<td align="right" id="pager_35_right">
							<div class="ui-paging-info" style="text-align: right;" dir="ltr">共 ${fn:length(_TREE)-1} 条</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
</div>
</form>					  
</body>
</html>