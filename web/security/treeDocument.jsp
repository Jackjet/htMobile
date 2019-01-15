<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title>文档大全</title>
		<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
		<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/jquery-1.9.0.min.js" type="text/javascript"></script>
		<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
		<script src="<c:url value="/"/>js/jquery.layout-latest.js" type="text/javascript"></script> <!--jquery 布局-->
		<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/jquery.jqGrid.src.js" type="text/javascript"></script> <!--jqgrid 包-->
		<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/i18n/grid.locale-cn.js" type="text/javascript"></script> <!--jqgrid 中文包-->
		<script src="<c:url value="/"/>js/commonFunction.js"></script>
		<script src="<c:url value="/"/>js/changeclass.js"></script> <!-- 用于改变页面样式-->
		<script src="<c:url value="/"/>js/inc_javascript.js"></script>
		<script src="<c:url value="/"/>js/multisearch.js"></script> <!--加载模态多条件查询相关js-->
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/gkSecure/jquery-ui-1.9.2.custom.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/ui.jqgrid.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/tabstyle.css" />
		
		<script type="text/javascript">
			$(document).ready(function(){
			
				$('body').layout({
					resizerClass: 'ui-state-default',
			        west__onresize: function (pane, $Pane) {
			            jQuery("#west-grid").jqGrid('setGridWidth',$Pane.innerWidth()-2);
					}
				});
				
				$.jgrid.defaults = $.extend($.jgrid.defaults,{loadui:"enable"});
				
				//格式化Tab菜单
				var maintab = jQuery('#tabs','#RightPane').tabs({
			        add: function(e, ui) {
			            //添加关闭按钮
			            $(ui.tab).parents('li:first')
			                .append('<span class="ui-tabs-close ui-icon ui-icon-close" title="关闭"></span>')
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
				
				//加载菜单树
		    	jQuery("#west-grid").jqGrid({
			        url:"/security/document.htm?method=list&categoryId=${_Category.categoryId}", 			        
			        datatype: "json",
			        //async: true,
			        treedatatype: "json",
			        height: "auto",
			        pager: false,
			        loadui: "disable",
			        colNames: ["Id","文档分类","fromReport","layer"],
			        colModel: [
			            {name: "categoryId",index:"categoryId", sorttype:"int", width:1, hidden:true, key:true},
			            {name: "categoryName",index:"categoryName", resizable: false, sortable:false},
			            {name: "fromReport",index:"fromReport",width:1,hidden:true},
			            {name: "layer",index:"layer",width:1,hidden:true}
			        ],
			        treeReader : {
			        	level_field: "layer",
					    left_field: 'leftIndex',
						right_field: 'rightIndex',
					    leaf_field: "leaf",
					    expanded_field: true
					},
					treeGridModel: 'nested',					
			        sortname: 'categoryId',//默认排序的字段
			        treeGrid: true,		//树形grid
					//caption: "文档分类",
			        ExpandColumn: "categoryName",
			        autowidth: true,
			        rowNum: 500,
			        ExpandColClick: true, 
			        treeIcons: {leaf:'ui-icon-document-b'},
			        jsonReader:{
		               repeatitems: false	//告诉JqGrid,返回的数据的标签是否是可重复的
		            },
	                
                	loadComplete:function(xhr){
                		//alert('${_Category.categoryId}');
                		var record = jQuery("#west-grid").getRowData('${_Category.categoryId}');
					    //alert(record.categoryName);
					    jQuery("#west-grid").expandRow(record);
                	},
		            
		            //单击事件
			        onSelectRow: function(rowid) {
			            var treedata = $("#west-grid").jqGrid('getRowData',rowid);
			            
			           //alert(treedata.categoryName);
			           
			           resetNode(rowid);
			            if(treedata.leaf=="true") {
			                var st = "#tabs-1"+treedata.categoryId;
							if($(st).html() != null ) {
								maintab.tabs('select',st);
							}else if(treedata.fromReport==1){
								maintab.tabs('add',st, treedata.categoryName);
								//$(st,"#tabs").load(treedata.url);
								$.ajax({
									url: "docRightWorkList.jsp?categoryId="+treedata.categoryId,
									cache: false,
									type: "GET",
									dataType: "html",
									beforeSend: function (xhr) {
										//$(st,"#tabs").height(100).addClass("tabpreloading");
										//$(st,"#tabs").css("text-align","center").html("<img src='images/loading.gif' border=0 />");
									},
									complete : function (req, err) {
										$(st,"#tabs").empty().html(req.responseText);
										//alert(req.responseText);
									}
								});
							}else{
								maintab.tabs('add',st, treedata.categoryName);
								//$(st,"#tabs").load(treedata.url);
								$.ajax({
									url: "docRightList.jsp?categoryId="+treedata.categoryId,
									cache: false,
									type: "GET",
									dataType: "html",
									beforeSend: function (xhr) {
										//$(st,"#tabs").height(100).addClass("tabpreloading");
										//$(st,"#tabs").css("text-align","center").html("<img src='images/loading.gif' border=0 />");
									},
									complete : function (req, err) {
										$(st,"#tabs").empty().html(req.responseText);
										//alert(req.responseText);
									}
								});
							}
			            }
			        }
			    });
			    
			    //加载首页
				$.ajax({
					url: "listDocument.jsp?isRoot=true&categoryId="+${_Category.categoryId},
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
				
				
			    
			    //加载Tab内容
				$('#west-grid tr').click(function(){
					var st = "#t"+$(this).find('#tabId').text();
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
				
		    });//ready结束
			
			//保存信息后重新加载tab
			function loadTab(categoryId){
				$.ajax({
					url: "docRightList.jsp?categoryId="+categoryId,
					cache: false,
					type: "GET",
					dataType: "html",
					beforeSend: function (xhr) {
					},
					complete : function (req, err) {
						$("#tabs-1"+categoryId).empty().html(req.responseText);
					}
				});		
			}
			
		</script>
	</head>
	
	<body>
	<!-- #LeftPane -->
		
		<div class="ui-layout-west ui-widget ui-widget-content" id="LeftPane">
			<!-- <a href="#" onclick="expandNode();">TEST</a> -->
			<table id="west-grid"></table>
		</div>
		
		<!-- #RightPane -->
		<div class="ui-layout-center ui-helper-reset ui-widget-content" id="RightPane" style="overflow-y:hidden;">
			<!-- Tabs pane -->
			<div id="tabs" class="jqgtabs">
				<ul>
					<li><a href="#tabs-1">全部文档</a></li>
				</ul>
				<div id="tabs-1"></div>
			</div>
		</div>
	</body>
</html>

