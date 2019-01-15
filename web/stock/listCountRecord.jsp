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
	
	<script src="<c:url value="/"/>js/jquery.extend.js" type="text/javascript"></script><!--jquery扩展包-->
	<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/jquery.extend.css" />
	
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/cupertino/jquery-ui-1.9.2.custom.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/tabstyle.css" />

<script type="text/javascript">
	//初始化列表和查询窗口Id
	var multiSearchParams = new Array();
	multiSearchParams[0] = "#listCountRecord";			//列表Id
	multiSearchParams[1] = "#multiSearchDialog";//查询模态窗口Id


	
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

<title>历史库存</title>
	<div id="edit_dlg"></div>
	<div>
		<table id="listCountRecord"></table> <!-- 信息列表 -->
		<div id="pagerCountRecord"></div> <!-- 分页 -->
	</div>
	
	<!-- 查询框 -->
	<div id="multiSearchDialog" style="display: none;">  
	    <table>  
	        <tbody>  
	            <tr>  
		           <td>  
		              备件小类：
		             
		           </td>  
		           <td>  
		              <select id="smallItemId"  onchange="getItems('smallItemId');">  
		              </select>
		           </td>  
		        </tr>
		        
		        
	            <tr>  
		           <td>  
		              <input type="hidden" class="searchField" value="item.itemId"/>备件：
		              <input type="hidden" class="searchOper" value="eq"/>
		           </td>  
		           <td>  
		              <select class="searchString" id="searchItemId">  
		              </select>
		           </td>  
		        </tr>
		        
		        
		        <tr>  
	                <td>  
	                    <input type="hidden" class="searchField" value="recordDate"/>记录日期：
	                    <input type="hidden" class="searchOper" value="eq"/>
	                </td>  
	                <td>  
	                    <input class="searchString" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" size="12"/>
	                </td>  
	            </tr><!--
	            
	            <tr>  
	                <td>  
	                    <input type="hidden" class="searchField" value="createDate"/>创建时间终：
	                    <input type="hidden" class="searchOper" value="le"/>
	                </td>  
	                <td>  
	                    <input class="searchString" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="true" size="12"/>
	                </td>  
	            </tr>
	        --></tbody>  
	    </table>  
	</div>
	
	<script src="<c:url value="/"/>js/multisearch.js"></script> <!--加载模态多条件查询相关js-->		
	<script type="text/javascript"><!-- 		
	    
		//加载表格数据
		var $mygrid = jQuery("#listCountRecord").jqGrid({
            url:'/stock/countRecord.htm?method=list',
            //rownumbers: true,	//是否显示序号
            datatype: "json",   //从后台获取的数据类型              
           	autowidth: true,	//是否自适应宽度
			//height: "auto",
			height:document.documentElement.clientHeight-75,
            colNames:['Id','备件', '记录日期', '库存量', '创建时间'],//表的第一行标题栏
            //以下为每列显示的具体数据
            colModel:[
                {name:'inforId',index:'inforId', width:0, search:false, hidden:true, key:true},            
                {name:'itemName',index:'itemName', width:80, sortable:true,align:'left',sorttype:"string"},
                {name:'recordDateStr',index:'recordDateStr', width:30,align:'center'},
                {name:'itemCount',index:'itemCount', width:40,align:'center'},
                {name:'recordDateStr',index:'recordDateStr', width:40,align:'center'}
            ],
            caption: "历史库存",
            sortname: 'inforId', //默认排序的字段
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
            pager: "#pagerCountRecord"	//分页工具栏
            //caption: "用户信息"	//表头
       	}).navGrid('#pagerCountRecord',{edit:false,add:false,del:false,search:false});       
	
		//自定义按钮
		/*jQuery("#listCountRecord").jqGrid('navButtonAdd','#pagerCountRecord', {
			caption:"新增", title:"点击新增信息", buttonicon:'ui-icon-plusthick', onClickButton: addCountRecord
		});*/
		
		/*jQuery("#listCountRecord").jqGrid('navButtonAdd','#pagerCountRecord', {
			caption:"<span style='color: red;'>作废</span>", title:"订单作废申请", buttonicon:'ui-icon-closethick', onClickButton: deleteCountRecord
		});*/
		
		jQuery("#listCountRecord").jqGrid('navButtonAdd','#pagerCountRecord', {
			caption:"查询", title:"点击查询", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialog
		});
		
		jQuery().ready(function (){
		    //获取备件信息(查询条件)
		    $.getJSON("/stock/itemInfor.htm?method=getSmallItems",function(data) {
		           var options = "<option value=''>--选择备件小类--</option>";
		           $.each(data._Items, function(i, n) {
		               options += "<option value='"+n.itemId+"'>"+n.itemName+"</option>";   
		           });   
		           $('#smallItemId').html(options);   
		        }   
		    );
		    
		    //自动完成
			$("#smallItemId").combobox();
			$("#searchItemId").combobox();    
		    
		});
		
		//根据小类得到备件
	    function getItems(id){
	   		var smallItemId=$("#"+id).find("option:selected").attr("value");
	   		//alert(smallItemId);
	    	$.getJSON("/stock/itemInfor.htm?method=getItemsBySmall&smallItemId="+smallItemId,function(data) {
		           var options = "";
		           $.each(data._Items, function(i, n) {
		               //options += "<option value='"+n.supplier.supplierId+"'  class='"+n.restCount+"'>"+n.supplier.supplierName+"</option>";
		               options += "<option value='"+n.itemId+"' class='"+n.itemUnit+"|"+n.itemPrice+"|"+n.priceUnit+"|"+n.count+"'>【"+n.itemCode+"】"+n.itemName+"</option>";
		               //+"|"+n.inOrderItemId+"   
		           });   
		           $('#searchItemId').html(options);   
		        }   
		    );
	    }
		
	</script>