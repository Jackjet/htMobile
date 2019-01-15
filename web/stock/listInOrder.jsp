<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
	<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
	<script src="<c:url value="/"/>js/jquery-1.9.1.js" type="text/javascript"></script> <!--jquery包-->
	<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
	<script src="<c:url value="/"/>js/jquery.layout.js" type="text/javascript"></script> <!--jquery 布局-->
	<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/jquery.jqGrid.min.js" type="text/javascript"></script> <!--jqgrid 包-->
	<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/i18n/grid.locale-cn.js" type="text/javascript"></script> <!--jqgrid 中文包-->
	<%--<script src="<c:url value="/"/>js/inc_javascript.js"></script>
	--%><script src="<c:url value="/"/>js/commonFunction.js"></script>
	<script src="<c:url value="/"/>js/multisearch.js"></script> <!--加载模态多条件查询相关js-->
	
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/cupertino/jquery-ui-1.9.2.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/tabstyle.css" />

<script type="text/javascript">
	//初始化列表和查询窗口Id
	var multiSearchParams = new Array();
	multiSearchParams[0] = "#listInOrderInfor";			//列表Id
	multiSearchParams[1] = "#multiSearchDialog";//查询模态窗口Id


	//新增
	function addInOrderInfor(){
		var height = window.screen.height;
		//var refresh = window.showModalDialog("/stock/inOrderInfor.htm?method=edit",null,"dialogWidth:900px;dialogHeight:"+height+"px;center:Yes;dialogTop: 5px; dialogLeft: 200px;");
		//if(refresh == "Y") {
		//	self.location.reload();
		//} 
		window.open("/stock/inOrderInfor.htm?method=edit", "_blank");
		
		//window.open("/stock/inOrderInfor.htm?method=edit",null,"");
		
		
		/*$.ajax({
			url: "/stock/inOrderInfor.htm?method=edit",
			type: "GET",
			dataType: "html",
			beforeSend: function (xhr) {
			},
			complete : function (req, err) {
				$("#edit_dlg").html(req.responseText);
				
			}
		});*/
		//$("#edit_dlg").dialog("open");
	}
	//查看
	function doView(rowId){
		/*var refresh = window.showModalDialog("/stock/inOrderInfor.htm?method=viewInOrderInfor&rowId="+rowId,'',"dialogWidth:900px;dialogHeight:650px;center:Yes;dialogTop: 5px; dialogLeft: 200px;");
		if(refresh == "Y") {
			//self.location.reload();
			$("#listInOrderInfor").trigger("reloadGrid"); 
		} */
		window.open("/stock/inOrderInfor.htm?method=view&rowId="+rowId, "_blank");
	}
	
	function reloadGrid(){
		$("#listInOrderInfor").trigger("reloadGrid"); 
	}
	
	$(function() {
	    $("#edit_dlg").dialog({
	    	autoOpen: false,
	    	draggable: true,
	    	height: document.documentElement.clientHeight,
	    	width: document.documentElement.clientWidth,
	      modal: true
	    });
	  });
</script>

<title>入库单</title>
	<div id="edit_dlg"></div>
	<div>
		<table id="listInOrderInfor"></table> <!-- 信息列表 -->
		<div id="pagerInOrderInfor"></div> <!-- 分页 -->
	</div>
	
	<!-- 查询框 -->
	<div id="multiSearchDialog" style="display: none;">  
	    <table>  
	        <tbody>  
	        	 <tr>  
		           <td>  
		              <input type="hidden" class="searchField" value="inOrderId"/>订单流水号：
		              <input type="hidden" class="searchOper" value="eq"/>
		           </td>  
		           <td>  
		              <input type="text" class="searchString"/>  
		           </td>  
		        </tr>
	            <tr>  
		           <td>  
		              <input type="hidden" class="searchField" value="inOrderNo"/>订单描述：
		              <input type="hidden" class="searchOper" value="cn"/>
		           </td>  
		           <td>  
		              <input type="text" class="searchString"/>  
		           </td>  
		        </tr>
		        
		        <tr>  
	                <td>  
	                    <input type="hidden" class="searchField" value="status"/>状态：
	                    <input type="hidden" class="searchOper" value="eq"/>
	                </td>  
	                <td>  
	                    <select class="searchString">  
	                        <option value="">所有状态</option>
	                        <option value="3">未入库</option>
	                        <option value="0">已入库</option>
	                        <option value="1">作废待审批</option>
	                        <option value="2">已作废</option>
	                    </select>
	                </td>  
	            </tr>
		        
		        <tr>  
	                <td>  
	                    <input type="hidden" class="searchField" value="createDate"/>创建时间始：
	                    <input type="hidden" class="searchOper" value="ge"/>
	                </td>  
	                <td>  
	                    <input class="searchString" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" size="12"/>
	                </td>  
	            </tr>
	            
	            <tr>  
	                <td>  
	                    <input type="hidden" class="searchField" value="createDate"/>创建时间终：
	                    <input type="hidden" class="searchOper" value="le"/>
	                </td>  
	                <td>  
	                    <input class="searchString" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" size="12"/>
	                </td>  
	            </tr>
	        </tbody>  
	    </table>  
	</div>
	
	<script src="<c:url value="/"/>js/multisearch.js"></script> <!--加载模态多条件查询相关js-->		
	<script type="text/javascript"><!-- 		
		
		//自定义操作栏的显示内容
	  
		//显示标题
		function formatTitle(cellValue, options, rowObject) {				
			var html = '';
			html = "<a href='javascript:;' title='"+cellValue+"' onclick='doView("+options.rowId+")'>";
			if(cellValue.length > 24){
				html += cellValue.substr(0,24);
    			html += " ······";
			}else{
				html += cellValue;
			}
			html += "</a>";				
			return html;
		}
		
		
		//自定义状态
		function formatStatus(cellValue, options, rowObject) {
			var html = "";
			if(cellValue == "0"){
				html = "已入库";
			}	
			
			if(cellValue == "1"){
				html = "<font color=blue>作废，待审批</font>";
			}	
			
			if(cellValue == "2"){
				html = "<font color=red>已作废</font>";
			}		
			
			if(cellValue == "3"){
				html = "<font color=green><b>未入库</b></font>";
			}		
			
			return html;
		}
	    
		//加载表格数据
		var $mygrid = jQuery("#listInOrderInfor").jqGrid({
            url:'/stock/inOrderInfor.htm?method=list',
            //rownumbers: true,	//是否显示序号
            datatype: "json",   //从后台获取的数据类型              
           	autowidth: true,	//是否自适应宽度
			//height: "auto",
			height:document.documentElement.clientHeight-100,
            colNames:['订单流水号','订单描述', '创建日期', '仓库', '供应商','总金额（去税）', '货币', '状态'],//表的第一行标题栏
            //以下为每列显示的具体数据
            colModel:[
                {name:'inOrderId',index:'inOrderId', width:20, search:false,  key:true,align:'center'},  //hidden:true,          
                {name:'inOrderNo',index:'inOrderNo', width:40, sortable:true, formatter:formatTitle,sorttype:"string"},
                {name:'createDate',index:'createDate', width:30,align:'center'},
                {name:'warehouse.houseName',index:'warehouse.houseName', width:20,align:'center'},
                {name:'supplier.supplierName',index:'supplier.supplierName', width:50,align:'center'},
                {name:'pureAmount',index:'pureAmount', width:30,align:'center'},
                {name:'priceUnit',index:'priceUnit', width:20,align:'center'},
                {name:'status',index:'status', width:30,align:'center', formatter:formatStatus}
            ],
            caption: "入库单",
            sortname: 'inOrderId', //默认排序的字段
            sortorder: 'desc',	//默认排序形式:升序,降序
            multiselect: false,	//是否支持多选,可用于批量删除
            viewrecords: true,	//是否显示数据的总条数(显示在右下角)
            rowNum: 20,			//每页显示的默认数据条数
            rowList: [15,20,30],//可选的每页显示的数据条数(显示在中间,下拉框形式)
            scroll: false, 		//是否采用滚动分页的形式
            scrollrows: false,	//当选中的行数据隐藏时,grid是否自动滚               
            jsonReader:{
               repeatitems: false	//告诉JqGrid,返回的数据的标签是否是可重复的
            },         
            pager: "#pagerInOrderInfor",	//分页工具栏
            //caption: "用户信息"	//表头
            
            footerrow : true,   //显示底部一行
            //userDataOnFooter : true,
            //altRows : true,
            gridComplete:function(){
	            var rowNum=parseInt($(this).getGridParam("records"),10);
	            if(rowNum>0){
	                $(".ui-jqgrid-sdiv").show();
	                var pureAmount=$(this).getCol("pureAmount",false,"sum").toFixed(2);
	                 //将合计值显示出来
	                $(this).footerData("set",{"inOrderId":"合计","pureAmount":pureAmount}); 
	            }else{
	                $(".ui-jqgrid-sdiv").hide();
	            }
	        }
       	}).navGrid('#pagerInOrderInfor',{edit:false,add:false,del:false,search:false});       
	
		//自定义按钮
		jQuery("#listInOrderInfor").jqGrid('navButtonAdd','#pagerInOrderInfor', {
			caption:"新增", title:"点击新增信息", buttonicon:'ui-icon-plusthick', onClickButton: addInOrderInfor
		});
		
		/*jQuery("#listInOrderInfor").jqGrid('navButtonAdd','#pagerInOrderInfor', {
			caption:"<span style='color: red;'>作废</span>", title:"订单作废申请", buttonicon:'ui-icon-closethick', onClickButton: deleteInOrderInfor
		});*/
		
		jQuery("#listInOrderInfor").jqGrid('navButtonAdd','#pagerInOrderInfor', {
			caption:"查询", title:"点击查询", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialog
		});
		
		//删除数据
		function deleteInOrderInfor(){
		
			doDelete("/stock/inOrderInfor.htm?method=delete","listInOrderInfor");
			
		}
		
	</script>