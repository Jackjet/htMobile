<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>

<title>系统功能及权限</title>

<script type="text/javascript">
	var heigth = window.screen.height;
	//修改
	function editResource(resourceId,operationId){
		/*var returnResTag = window.showModalDialog("/core/virtualResource.htm?method=edit&resourceId="+resourceId+"&operationId="+operationId,'',"dialogWidth:900px;dialogHeight:"+heigth+"px;center:Yes;dialogTop: 10px; dialogLeft: 300px;");
		if(returnResTag == "Y") {
			loadTab('/core/virtualResource.htm?method=list', '6');
		}*/
		window.open("/core/virtualResource.htm?method=edit&field=all&tabId=6&resourceId="+resourceId+"&operationId="+operationId,"_blank");
	}
	 $(document).ready(function(){
    	$("#gbox_grid_35_resource").css("height",document.documentElement.clientHeight-50);
    	
    	//菜单树中的右键事件
		var firstMenu = {   //某个层绑定
		    bindings: {
	          'item_1': function(t) {
	          	editResource(t.id,'3');
	          },
	
	          'item_2': function(t) {
	        	editResource(t.id,'1');
	          },
	
	          'item_3': function(t) {
	        	editResource(t.id,'2');
	          },
	
	          /*'item_4': function(t) {
	            //alert("id为 "+t.id+" 下面的--删除");
	            doDeleteCategory(t.id);
	          }*/
	        },
	        menuStyle: {
	          border: '1px solid #85b5d9',
	          width: '120px'
	        },
	        itemStyle : {
	          fontFamily: 'verdana',
	          backgroundColor: '#e1effb',
	          color: '#2e6e9e',
	          border: 'none',
	          padding: '1px'
	        },
	        
	        itemHoverStyle: {
	          color: 'white',
	          backgroundColor: '#87b6d9',
	          border: 'none'
	        }
	      };
		$('.resourceTR').contextMenu('myMenu',firstMenu);
    });
</script>
<style>
	a{
		text-decoration: none;
	}
</style>
</head>
<body>
<div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all" id="gbox_grid_35_resource" dir="ltr" style="width: 100%;overflow-y:auto;">
<div class="ui-jqgrid-view" id="gview_grid_35" style="width: 100%">
  <%--<div class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">
    <span class="ui-jqgrid-title">系统功能及权限</span>
  </div>--%>
  <div style="width: 100%" class="ui-state-default ui-jqgrid-hdiv">
    <div class="ui-jqgrid-hbox" style="width: 100%">
      <table cellspacing="0" cellpadding="0" border="0" aria-labelledby="gbox_grid_35_resource" role="grid" style="width: 100%" class="ui-jqgrid-htable">
          <thead>
              <tr role="rowheader" class="ui-jqgrid-labels">
                <th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 2%;">
                  <div id="jqgh_rn"></div>
				</th>
				
				<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 18%;">
				    <span class="ui-jqgrid-resize ui-jqgrid-resize-ltr" style="cursor: col-resize;">&nbsp;</span>
				    <div id="jqgh_title" class="ui-jqgrid-sortable">
						模块名称
				    </div>
				</th>
		
				<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 20%;">
				    <div id="jqgh_url" class="ui-jqgrid-sortable">
						浏览角色
				    </div>
				</th>
		
				<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 20%;">
				    <div id="jqgh_url" class="ui-jqgrid-sortable">
						修改角色
				    </div>
				</th>
		
				<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 20%;">
				    <div id="jqgh_url" class="ui-jqgrid-sortable">
						删除角色
				    </div>
				</th>
		
				<%--<th class="ui-state-default ui-th-column ui-th-ltr" onmouseover="over(this,'ui-state-hover ui-th-column ui-th-ltr')" onmouseout="out(this)" style="width: 20%;">
				    <div id="jqgh_url" class="ui-jqgrid-sortable">
						相关操作
				    </div>
				</th>
				
	    	  --%></tr>
		   </thead>
       </table>
     </div>
   </div>

	<div class="ui-jqgrid-bdiv" style="height: auto; width: 100%">
		<div style="position: relative;">
			<div>
				<table cellspacing="0" cellpadding="0" border="0" id="grid_35" class="ui-jqgrid-btable" role="grid" aria-multiselectable="false" aria-labelledby="gbox_grid_35_resource" style="width: 100%">
					<tbody>
						<c:forEach items="${_Resources}" var="resource" varStatus="index">
							<tr id="${resource.resourceId}" class="ui-widget-content jqgrow ui-row-ltr resourceTR" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
								<td style="text-align: center; width: 2%;" class="ui-state-default jqgrid-rownum" role="gridcell">${index.index+1}</td>
								<c:choose>
									<c:when test="${resource.layer == 1}">
										<td style="width: 18%;" role="gridcell"><img src="<c:url value='/'/>images/tree_folder4.gif" border="0"/>${resource.resourceName}</td>
										<td style="text-align: left; width: 20%;" role="gridcell"><a href="javascript:;" onclick="editResource('${resource.resourceId}','3');"><c:forEach items="${_ViewRoles[index.index]}" var="view" varStatus="viewIndex"><span style="white-space:pre-wrap;">${view}</span><c:if test="${view != null && ((viewIndex.index+1) < fn:length(_ViewRoles[index.index]))}">、</c:if></c:forEach></a></td>
										<td style="text-align: left; width: 20%;" role="gridcell">&nbsp;</td>
										<td style="text-align: left; width: 20%;" role="gridcell">&nbsp;</td>
										<%--<td style="text-align: left; width: 20%;" role="gridcell">&nbsp;<a href="javascript:;" onclick="editResource('${resource.resourceId}','3');">浏览</a></td>
									--%></c:when>
									<c:otherwise>
										<td style="width: 18%;" role="gridcell"><c:forEach begin="0" end="${resource.layer}">&nbsp;&nbsp;</c:forEach><img src="<c:url value='/'/>images/tree_folder3.gif" border="0"/>${resource.resourceName}</td>
										<td style="text-align: left; width: 20%;" role="gridcell"><a href="javascript:;" onclick="editResource('${resource.resourceId}','3');" title="点击编辑"><c:forEach items="${_ViewRoles[index.index]}" var="view" varStatus="viewIndex"><span style="white-space:pre-wrap;">${view}</span><c:if test="${view != null && ((viewIndex.index+1) < fn:length(_ViewRoles[index.index]))}">、</c:if></c:forEach></a></td>
										<td style="text-align: left; width: 20%;" role="gridcell"><a href="javascript:;" onclick="editResource('${resource.resourceId}','1');" title="点击编辑"><c:forEach items="${_EditRoles[index.index]}" var="edit" varStatus="editIndex"><span style="white-space:pre-wrap;">${edit}</span><c:if test="${edit != null && ((editIndex.index+1) < fn:length(_EditRoles[index.index]))}">、</c:if></c:forEach></a></td>
										<td style="text-align: left; width: 20%;" role="gridcell"><a href="javascript:;" onclick="editResource('${resource.resourceId}','2');" title="点击编辑"><c:forEach items="${_DeleteRoles[index.index]}" var="delete" varStatus="deleteIndex"><span style="white-space:pre-wrap;">${delete}</span><c:if test="${delete != null && ((deleteIndex.index+1) < fn:length(_DeleteRoles[index.index]))}">、</c:if></c:forEach></a></td><%--
										<td style="text-align: left; width: 20%;" role="gridcell"><c:forEach items="${_OperationTREE}" var="operation"><c:choose><c:when test="${!empty operation.childs}"><img src="<c:url value='/images'/>/tree_folder4.gif" border="0"/></c:when><c:otherwise>&nbsp;</c:otherwise></c:choose><a href="javascript:;" onclick="editResource('${resource.resourceId}','${operation.operationId}');">${operation.operationName}</a></c:forEach></td>
									--%></c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div class="contextMenu" id="myMenu" style="display:none;">
	  <ul>
	    <li id="item_1"><img src="<c:url value="/"/>images/notify_new.gif" />  编辑 “浏览”权限</li>
	    <li id="item_2"><img src="<c:url value="/"/>images/chm.gif" />  编辑 “修改”权限</li>
	    <li id="item_3"><img src="<c:url value="/"/>images/delete.gif" />  &nbsp;编辑 “删除”权限</li>
	    <%--<li id="item_4"><img src="<c:url value="/"/>images/delete.gif" /> &nbsp;删除</li>--%>
	  </ul>
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
												<span class="ui-icon ui-icon-refresh" onclick="loadTab('/core/virtualResource.htm?method=list&field=all', '6');"></span>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
						<td align="right" id="pager_35_right">
							<div class="ui-paging-info" style="text-align: right;" dir="ltr">共 ${fn:length(_Resources)} 条</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>