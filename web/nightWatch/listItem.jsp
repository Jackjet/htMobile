<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>巡更项配置</title>
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
		//var height = window.screen.height;
	
		//新增小项
		function addNwItem(){
			window.open("/nightWatch/item.htm?method=edit", "_blank");
		}
		//修改小项
		function editNwItem(xId){
			window.open("/nightWatch/item.htm?method=edit&xId="+xId, "_blank");
		}
		//删除小项
		function delNwItem(xId){
			if(xId != null){
				var url = "/nightWatch/item.htm?method=delete&xId="+xId;
				var yes = window.confirm("确定要删除吗？");
				if (yes) {
					$.ajax({
						url: url,	//删除数据的url
						cache: false,
						type: "POST",
						dataType: "html",
						beforeSend: function (xhr) {						
							
						},
							
						complete : function (req, err) {
							alert("数据已经删除！");
							$("#listNwItem").trigger("reloadGrid"); 
						}
					});	
				}
			}
		}
		
	</script>
	<div>
		<table id="listNwItem"></table> <!-- 信息列表 -->
		<div id="pagerNwItem"></div> <!-- 分页 -->
	</div>
	
	<!-- 查询框 -->
	<div id="multiSearchDialogNwItem" style="display: none;">  
	    <table>  
	        <tbody>  
	            <tr>  
		            <td>  
	                    <input type="hidden" class="searchField" value="itemName"/>小项名称：
	                    <input type="hidden" class="searchOper" value="cn"/>
	                </td>  
	                <td>  
	                    <input type="text" class="searchString" size="25"/>  
	                </td>  
		        </tr>
		        

	        </tbody>  
	    </table>  
	</div>
	
	<script type="text/javascript"> 		
		
		//自定义操作栏的显示内容
	  
		//小项显示标题
		function formatNwItemTitle(cellValue, options, rowObject) {				
			var html = '';
			html = "<a href='javascript:;'  onclick='editNwItem("+options.rowId+")'>";
			html += cellValue;
			html += "</a>";				
			return html;
		}
		
		
		//小项操作
		function formatNwItemOperate(cellValue, options, rowObject){
			//return "<a href=# onclick='editNwItem("+rowObject.driverId+")'><font color=blue>[修改]</font></a>";
			var html = "";
			//html += "<a href=# onclick='editNwItem("+rowObject.xId+")' alt='调整小项'>【<font color=blue>调整小项</font>】</a>&nbsp;&nbsp;";
			html += "<a href=# onclick='delNwItem("+rowObject.xId+");' alt='删除'>【<font color=red>删除</font>】</a>";
			return html;
		}
		
	    
		//加载表格数据
		var $mygrid = jQuery("#listNwItem").jqGrid({
            url:'/nightWatch/item.htm?method=list',
            rownumbers: true,	//是否显示序号
            datatype: "json",   //从后台获取的数据类型              
           	autowidth: true,	//是否自适应宽度
			//height: "auto",
			height:document.documentElement.clientHeight-51,
            colNames:['Id','小项名称', '小项代码', '排序号', '相关操作'],//表的第一行标题栏
            //以下为每列显示的具体数据
            colModel:[
                {name:'xId',index:'xId', width:0, search:false, hidden:true, key:true},  
                {name:'itemName',index:'itemName', width:30,align:'center', formatter:formatNwItemTitle},
                {name:'itemCode',index:'itemCode', width:10,align:'center'},
                {name:'orderNo',index:'orderNo', width:10,align:'center'},
                {name:'operate',width:20,align:'center',formatter:formatNwItemOperate}
            ],
            //caption: "油耗管理",
            sortname: 'orderNo', //默认排序的字段
            sortorder: 'asc',	//默认排序形式:升序,降序
            multiselect: false,	//是否支持多选,可用于批量删除
            viewrecords: true,	//是否显示数据的总条数(显示在右下角)
            rowNum: 20,			//每页显示的默认数据条数
            rowList: [15,20,30],//可选的每页显示的数据条数(显示在中间,下拉框形式)
            scroll: false, 		//是否采用滚动分页的形式
            scrollrows: false,	//当选中的行数据隐藏时,grid是否自动滚               
            jsonReader:{
               repeatitems: false	//告诉JqGrid,返回的数据的标签是否是可重复的
            },         
            pager: "#pagerNwItem"	//分页工具栏
            
       	}).navGrid('#pagerNwItem',{edit:false,add:false,del:false,search:false});       
	
		//自定义按钮
		jQuery("#listNwItem").jqGrid('navButtonAdd','#pagerNwItem', {
			caption:"增加小项", title:"点击增加小项", buttonicon:'ui-icon-plusthick', onClickButton: addNwItem
		});
		
		jQuery("#listNwItem").jqGrid('navButtonAdd','#pagerNwItem', {
			caption:"查询", title:"点击查询", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialog
		});
		
		//打开查询窗口并进行窗口初始化
		var multiSearchParams = new Array();
		function openMultipleSearchDialog() {
		    multiSearchParams[0] = "#listNwItem";				//列表Id
			multiSearchParams[1] = "#multiSearchDialogNwItem";//查询模态窗口Id
			
			initSearchDialog();
			
		    $(multiSearchParams[1]).dialog("open");
		    window.scrollTo(0, 0);
		}
		
		
	</script>

