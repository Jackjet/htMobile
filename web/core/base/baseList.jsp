<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>基本维护</title>
<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script> <!--jquery包-->
<!--<script src="<c:url value="/"/>js/jquery-ui-custom.min.js" type="text/javascript"></script> jquery ui-->
<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
<script src="<c:url value="/"/>js/jquery.layout-latest.js" type="text/javascript"></script> <!--jquery 布局-->
<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/jquery.jqGrid.min.js" type="text/javascript"></script> <!--jqgrid 包-->
<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/i18n/grid.locale-cn.js" type="text/javascript"></script> <!--jqgrid 中文包-->
<script src="<c:url value="/"/>js/commonFunction.js"></script>
<script src="<c:url value="/"/>js/changeclass.js"></script> <!-- 用于改变页面样式-->
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/cupertino/jquery-ui-1.9.2.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/tabstyle.css" />

<script src="<c:url value="/"/>js/jquery.contextmenu.r2.js"></script><!-- 右键 -->

<script type="text/javascript">
	
	$().ready(function(){
		$('body').layout({
			resizerClass: 'ui-state-default',
	        west__onresize: function (pane, $Pane) {
	            jQuery("#west-grid").jqGrid('setGridWidth',$Pane.innerWidth()-2);
			}
		});
		
		//加载首页
		$.ajax({
			url: "listUser.jsp?isvalid=true",
			type: "GET",
			dataType: "html",
			beforeSend: function (xhr) {
				
			},
			complete : function (req, err) {
				//格式化tab
				loadTabCss();
				
				$("#tabs-1", "#tabs").html(req.responseText);
			}
		});
		
		$.jgrid.defaults = $.extend($.jgrid.defaults,{loadui:"enable"});

		//格式化Tab菜单
		var maintab = jQuery('#tabs','#RightPane').tabs({
	        add: function(e, ui) {
	        	//添加关闭按钮
	            $(ui.tab).parents('li:first')
	                .append('<span class="ui-tabs-close ui-icon ui-icon-close" title="关闭">X</span>')
	                .find('span.ui-tabs-close')
	                .click(function() {
	                	maintab.tabs('remove', $('li', maintab).index($(this).parents('li:first')[0]));
	                });
	        	
	            $(ui.tab).dblclick(function(){   //双击关闭事件绑定
	    		 	var li = $(ui.tab).parent();
	    		 	var index = $('#tabs li').index(li.get(0));
	    		 	$("#tabs").tabs("remove",index);
	    		 });
	            //选中刚添加的tab
	            maintab.tabs('select', '#' + ui.panel.id);
	            //可拖动tab页
				maintab.find(".ui-tabs-nav").sortable({axis:'x'});
				//格式化tab
				loadTabCss();
	        }
	    });
		
	    
	    //加载Tab内容
		$('#west-grid tr').click(function(){
			var st = "#tabs-"+$(this).find('#tabId').text();
			if($(st).html() != null ) {
				//若该tab的内容已存在则不再重新加载,将tab状态改为选中即可
				maintab.tabs('select',st);
			} else {
				//若tab的内容不存在,则加载
				maintab.tabs('add',st,$(this).find('#tabMenu').text());
				$.ajax({
					url: $(this).find('#tabUrl').text(),
					cache: false,
					type: "GET",
					dataType: "html",
					beforeSend: function (xhr) {
					},
					complete : function (req, err) {
						$(st,"#tabs").empty().html(req.responseText);
					}
				});
			}
		});
		
	});
	
	
	//保存信息后重新加载Tab
	function loadTab(url, tabId){
		$.ajax({
			url: url,
			cache: false,
			type: "GET",
			dataType: "html",
			beforeSend: function (xhr) {
			},
			complete : function (req, err) {
				$("#tabs-"+tabId).empty().html(req.responseText);
			}
		});		
	}
	
</script>
</head>

<body>
<!-- #LeftPane -->
<div class="ui-layout-west ui-widget ui-widget-content" id="LeftPane">
	<div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all">
		<div class="ui-jqgrid-view">
			<div class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">
				<span class="ui-jqgrid-title">基本维护</span>
			</div>
			<div class="ui-state-default ui-jqgrid-hdiv">
				<table cellspacing="0" cellpadding="0" border="0" style="width: 198px;" class="ui-jqgrid-htable">
					<thead>
						<tr>
							<th>模块列表</th>
						</tr>
					</thead>
				</table>
			</div>
			<div class="ui-jqgrid-bdiv">
				<table cellspacing="0" cellpadding="0" border="0" id="west-grid">
					<tbody>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">1</td>
							<td title="用户信息" style="width: 193px;" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">用户信息</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listUser.jsp?isvalid=true</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">2</td>
							<td title="人员信息" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">人员信息</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listPerson.jsp?isvalid=true</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">3</td>
							<td title="角色信息" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">角色信息</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listRole.jsp</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">4</td>
							<td title="部门信息" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">部门信息</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">/core/organizeInfor.htm?method=list</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">5</td>
							<td title="岗位信息" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">岗位信息</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">/core/structureInfor.htm?method=list</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">6</td>
							<td title="系统功能及权限" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">系统功能及权限</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">/core/virtualResource.htm?method=list&field=all</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">7</td>
							<td title="已注销用户" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">已注销用户</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listInvaliUser.jsp?isvalid=false</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">8</td>
							<td title="已注销人员" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">已注销人员</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listPerson.jsp?isvalid=false</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">9</td>
							<td title="供应商信息" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">供应商信息</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listSupplier.jsp</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">10</td>
							<td title="备件大类" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">备件大类</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listItemInfor.jsp?layer=1</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">101</td>
							<td title="备件小类" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">备件小类</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listItemInfor.jsp?layer=2</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">102</td>
							<td title="备件编码" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">备件编码</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listItemInfor.jsp?layer=3</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">11</td>
							<td title="仓库信息" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">仓库信息</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listWarehouse.jsp</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">12</td>
							<td title="出库分类" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">出库分类</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listOutOrderType.jsp</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">13</td>
							<td title="材料成本" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">材料成本</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listOutOrderCost.jsp</td>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr" onmouseover="over(this,'ui-state-hover jqgrow ui-row-ltr')" onmouseout="out(this)" onclick="clicked(this)">
							<td style="display: none; width: 1px;" id="tabId">14</td>
							<td title="设备" id="tabMenu"><div style="width: 36px;" class="tree-wrap tree-wrap-ltr"><div class="ui-icon ui-icon-document-b tree-leaf" style="left: 18px;"></div></div><span style="cursor: pointer;">设备</span></td>
							<td style="display: none; width: 1px;" id="tabUrl">listOutOrderDevice.jsp</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- #RightPane -->
<div class="ui-layout-center ui-helper-reset ui-widget-content" id="RightPane" style="overflow-y:hidden;">
	<!-- Tabs pane -->
	<div id="tabs" class="jqgtabs">
		<ul>
			<li><a href="#tabs-1">用户信息</a></li>
		</ul>
		<div id="tabs-1"></div>
	</div>
</div>
</body>
</html>

