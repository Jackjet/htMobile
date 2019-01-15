<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<script src="<c:url value="/"/>js/jquery.extend.js" type="text/javascript"></script><!--jquery扩展包-->
	<link rel="stylesheet" type="text/css" href="<c:url value="/"/>css/jquery.extend.css" />

<script type="text/javascript">
	//初始化列表和查询窗口Id
	var multiSearchParamsItem = new Array();
	multiSearchParamsItem[0] = "#listItemInfor${param.layer}";			//列表Id
	multiSearchParamsItem[1] = "#multiSearchDialogItemInfor${param.layer}";//查询模态窗口Id
	//新增
	function addItemInfor(){
		/*var returnRolTag = window.showModalDialog("/stock/itemInfor.htm?method=edit",null,"dialogWidth:900px;dialogHeight:650px;center:Yes;dialogTop: 5px; dialogLeft: 300px;");
		if(returnRolTag == "Y") {
			//保存信息后重新加载tab
			//loadTab("listItemInfor${param.layer}.jsp", "3");
			$("#listItemInfor${param.layer}").trigger("reloadGrid"); 
		}*/
		window.open("/stock/itemInfor.htm?method=edit&layer=${param.layer}","_blank");
	}
	//修改
	function editItemInfor(rowId){
		/*var returnRolTag = window.showModalDialog("/stock/itemInfor.htm?method=edit&rowId="+rowId,'',"dialogWidth:900px;dialogHeight:650px;center:Yes;dialogTop: 5px; dialogLeft: 300px;");
		if(returnRolTag == "Y") {
			//保存信息后重新加载tab
			//loadTab("listItemInfor.jsp", "3");
			$("#listItemInfor").trigger("reloadGrid"); 
		}*/
		window.open("/stock/itemInfor.htm?method=edit&rowId="+rowId,"_blank");
	}
</script>
<script type="text/javascript">
			
		/** 自定义多条件查询 */
		//初始化查询窗口
		function initSearchDialog() {
		    $(multiSearchParamsItem[1]).dialog({
		        autoOpen: false,       
		        modal: true,   
		        resizable: true,       
		        width: 350,   
		        title: "多条件查询",   
		        buttons: {   
		            "查询": multipleSearch,
		            "重置": clearSearch
		        }   
		    });
		}
		
		//打开查询窗口
	    function openMultipleSearchDialog() {
		    //初始化窗口
		    initSearchDialog();
		    
		    $(multiSearchParamsItem[1]).dialog("open");
		    window.scrollTo(0, 0);
		}
		
		//多条件查询
		function multipleSearch() {
		    var rules = "";   
		    $("tbody tr", multiSearchParamsItem[1]).each(function(i){    	//(1)从multipleSearchDialog对话框中找到各个查询条件行   
		        var searchField = $(".searchField", this).val();    	//(2)获得查询字段
		        var searchOper = $(".searchOper", this).val();  		//(3)获得查询方式   
		        var searchString = $(".searchString", this).val();  	//(4)获得查询值   
		        
		        if(searchField && searchOper && searchString) { 		//(5)如果三者皆有值且长度大于0，则将查询条件加入rules字符串   
		            rules += ',{"field":"' + searchField + '","op":"' + searchOper + '","data":"' + searchString + '"}';   
		        }   
		    });   
		    if(rules) { 
		        rules = rules.substring(1);								//(6)如果rules不为空，且长度大于0，则去掉开头的逗号
		    }   
		       
		    var filtersStr = '{"groupOp":"AND","rules":[' + rules + ']}';//(7)串联好filtersStr字符串
		       
		    var postData = $(multiSearchParamsItem[0]).jqGrid("getGridParam", "postData");   
		       
		    $.extend(postData, {filters: filtersStr});   				//(8)将filters参数串加入postData选项
		       
		    $(multiSearchParamsItem[0]).jqGrid("setGridParam", {  
		        search: true    										//(9)将jqGrid的search选项设为true   
		    }).trigger("reloadGrid", [{page:1}]);   					//(10)重新载入Grid表格,且返回第一页  
		       
		    $(multiSearchParamsItem[1]).dialog("close");
		}
		
		//重置查询条件
		function clearSearch() {
		    var sdata = {
		        searchString: ""	//将查询数据置空
		    };   
		       
		    var postData = $("#gridTable").jqGrid("getGridParam", "postData");   
		       
		    $.extend(postData, sdata);   
		       
		    $(multiSearchParamsItem[0]).jqGrid("setGridParam", {   
		        search: false  
		    }).trigger("reloadGrid", [{page:1}]);   
		       
		    resetSearchDialog(); 
		}
		var resetSearchDialog = function() {
		    $("select",multiSearchParamsItem[1]).val("");   
		    $(":text",multiSearchParamsItem[1]).val("");   
		}

		/** ********** */
</script>

<title>备件信息</title>

  		<div>
			<table id="listItemInfor${param.layer}"></table>
			<div id="pagerItemInfor${param.layer}"></div>
		</div>
		
		<!-- 查询框 -->
		<div id="multiSearchDialogItemInfor${param.layer}" style="display: none;">  
		    <table>  
		        <tbody>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="itemName"/>名称：
		                    <input type="hidden" class="searchOper" value="cn"/>
		                </td>  
		                <td>  
		                    <input type="text" class="searchString"/>
		                </td>  
		            </tr>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="itemCode"/>编码：
		                    <input type="hidden" class="searchOper" value="cn"/>
		                </td>  
		                <td>  
		                    <input type="text" class="searchString"/>
		                </td>  
		            </tr>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="itemBrand"/>品牌：
		                    <input type="hidden" class="searchOper" value="cn"/>
		                </td>  
		                <td>  
		                    <input type="text" class="searchString"/>
		                </td>  
		            </tr>  
		            <c:if test="${param.layer == '2'}">
		            	<tr>  
			                <td>  
			                    <input type="hidden" class="searchField" value="parent.itemId"/>大类：
			                    <input type="hidden" class="searchOper" value="eq"/>
			                </td>  
			                <td>  
			                    <select class="searchString" id="b_itemId">  
			                    </select>
			                </td>  
			            </tr>
		            </c:if>
		            <c:if test="${param.layer == '3'}">
		            	<tr>  
			                <td>  
			                    <input type="hidden" class="searchField" value="parent.itemId"/>小类：
			                    <input type="hidden" class="searchOper" value="eq"/>
			                </td>  
			                <td>  
			                    <select class="searchString" id="s_itemId">  
			                    </select>
			                </td>  
			            </tr>
		            </c:if>
		            <!--
		            
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="supplierInfor.supplierId"/>供应商：
		                    <input type="hidden" class="searchOper" value="eq"/>
		                </td>  
		                <td>  
		                    <select class="searchString" id="s_supplierId">  
		                    </select>
		                </td>  
		            </tr>
		        --></tbody>  
		    </table>  
		</div>
		<!-- ----- -->
		
		<script type="text/javascript"> 		
			$(function() {	
				$(document).tooltip();
			});
			//自定义操作栏的显示内容
		    function formatOperation(cellvalue, options, rowdata) {
	           var returnStr = "<a href='javascript:;' onclick='editItemInfor("+options.rowId+")'>[修改]</a>";
	           if (cellvalue) {
	              returnStr += " <a href='javascript:;'><font color='gray'>[删除]</font></a>";
	           }else {
	              returnStr += " <a href='javascript:;' onclick='deleteItemInfor("+options.rowId+")'>[删除]</a>";
	           }
	           return returnStr;
		    }
			
			function formatPrice(cellValue, options, rowdata){
				var html = cellValue + "("+rowdata.priceUnit+")";
				
				return html;
			}
		    
		    var colNames = "";
		    var colModel = "";
		    if('${param.layer}' == 1){
		    	colNames = ['Id', '名称', '编码','单位','次序','操作'];'供应商',
		    	colModel = [
                    {name:'itemId',index:'itemId', width:0, sorttype:"int", search:false, key:true, hidden:true},
                    {name:'itemName',index:'itemName',width:35, sortable:true, sorttype:"string"},
                    {name:'itemCode',index:'itemCode',width:20, sortable:true, sorttype:"string",align:'center'},
                    //{name:'supplierName',index:'supplierName',width:35, sortable:true, sorttype:"string"},
                    {name:'itemUnit',index:'itemUnit',width:15, sortable:true, align:'center', sorttype:"string"},
                    {name:'displayOrder',index:'displayOrder',width:10, sortable:true, align:'center', sorttype:"string"},
                    {name:'fixed', width:15, align:'center', search:false, sortable:false, formatter:formatOperation}
                ];
		    }
		    
		    if('${param.layer}' == 2){
		    	colNames = ['Id', '名称', '编码','次序','大类','操作'];//'供应商','单位','单价','库存量',
		    	colModel = [
                    {name:'itemId',index:'itemId', width:0, sorttype:"int", search:false, key:true, hidden:true},
                    {name:'itemName',index:'itemName',width:40, sortable:true, sorttype:"string"},
                    {name:'itemCode',index:'itemCode',width:25, sortable:true, sorttype:"string",align:'center'},
                    //{name:'supplierName',index:'supplierName',width:25, sortable:true, sorttype:"string"},
                    //{name:'itemUnit',index:'itemUnit',width:25, sortable:true, sorttype:"string",align:'center'},
                    //{name:'itemPrice',index:'itemPrice',width:25, sortable:true, formatter:formatPrice,align:'center'},
                    {name:'displayOrder',index:'displayOrder',width:15,align:'center', sortable:true, sorttype:"string"},
                    //{name:'count',index:'count',width:25, sortable:true, sorttype:"string",align:'center'},
                    {name:'parentName',index:'parentName',width:25, sortable:true, sorttype:"string",align:'left'},
                    {name:'fixed', width:25, align:'center', search:false, sortable:false, formatter:formatOperation}
                ];
		    }
		    
		    if('${param.layer}' == 3){
		    	colNames = ['Id', '名称', '编码','单位','单价','次序','库存量','小类','操作'];//'供应商',
		    	colModel = [
                    {name:'itemId',index:'itemId', width:0, sorttype:"int", search:false, key:true, hidden:true},
                    {name:'itemName',index:'itemName',width:40, sortable:true, sorttype:"string"},
                    {name:'itemCode',index:'itemCode',width:25, sortable:true, sorttype:"string",align:'center'},
                    //{name:'supplierName',index:'supplierName',width:25, sortable:true, sorttype:"string"},
                    {name:'itemUnit',index:'itemUnit',width:25, sortable:true, sorttype:"string",align:'center'},
                    {name:'itemPrice',index:'itemPrice',width:25, sortable:true, formatter:formatPrice,align:'center'},
                    {name:'displayOrder',index:'displayOrder',width:15,align:'center', sortable:true, sorttype:"string"},
                    {name:'count',index:'count',width:25, sortable:true, sorttype:"string",align:'center'},
                    {name:'parentName',index:'parentName',width:25, sortable:true, sorttype:"string",align:'left'},
                    {name:'fixed', width:25, align:'center', search:false, sortable:false, formatter:formatOperation}
                ];
		    }
		    
			//加载表格数据
			var $mygrid = jQuery("#listItemInfor${param.layer}").jqGrid({ 
                url:'/stock/itemInfor.htm?method=list&layer=${param.layer}',
                rownumbers: true,
                datatype: "json",                
               	autowidth: true,
				//height: "auto",
				height:document.documentElement.clientHeight-97,
                colNames:colNames,
                colModel:colModel,
                sortname: 'itemCode',
                sortorder: 'asc',
                viewrecords: true,
                rowNum: 20,
                rowList: [10,20,30],
                scroll: false, 
                scrollrows: false,                          
                jsonReader:{
                   repeatitems: false
                },         
                pager: "#pagerItemInfor${param.layer}"
                //caption: "角色信息"
        }).navGrid('#pagerItemInfor${param.layer}',{edit:false,add:false,del:false,search:false});       
        //}).navGrid('#pagerItemInfor${param.layer}',{edit:false,add:false,del:false}).searchGrid({multipleSearch:true,autoOpen:false});
		
		//自定义按钮
		jQuery("#listItemInfor${param.layer}").jqGrid('navButtonAdd','#pagerItemInfor${param.layer}', {
			caption:"新增", title:"点击新增信息", buttonicon:'ui-icon-plusthick', onClickButton: addItemInfor
		});
		
		jQuery("#listItemInfor${param.layer}").jqGrid('navButtonAdd','#pagerItemInfor${param.layer}', {
			caption:"查询", title:"点击查询", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialog
		});
		
		//删除数据
		function deleteItemInfor(rowId){
			if(rowId!=null || rowId!=0){			
				var yes = window.confirm("确定要删除吗？");
				if (yes) {
					$.ajax({
						url: "/stock/itemInfor.htm?method=delete&rowIds="+rowId,	//删除数据的url
						cache: false,
						//data:{personId: rowId},
						type: "POST",
						dataType: "html",
						beforeSend: function (xhr) {							
						},
						
						complete : function (req, err) {
							alert("数据已经删除！");
							$("#listItemInfor${param.layer}").trigger("reloadGrid"); 
						}
					});
				}
			}			
		}
		
		jQuery().ready(function (){
		    /*//获取供应商信息(查询条件)
		    $.getJSON("/stock/supplierInfor.htm?method=getSuppliers",function(data) {
		           var options = "<option value=''>--选择供应商--</option>";
		           $.each(data._Suppliers, function(i, n) {
		               options += "<option value='"+n.supplierId+"'>"+n.supplierName+"</option>";   
		           });   
		           $('#s_supplierId').html(options);   
		        }   
		    );*/
		    
		    //获取大类信息(查询条件)
		    $.getJSON("/stock/itemInfor.htm?method=getBigCategorys",function(data) {
		           var options = "<option value=''>--选择大类--</option>";
		           $.each(data._Categorys, function(i, n) {
		               options += "<option value='"+n.itemId+"'>【"+n.itemCode+"】"+n.itemName+"</option>";   
		           });   
		           $('#b_itemId').html(options);   
		           
		           $('#b_itemId').combobox();  
		    });
		    
		    //获取小类信息(查询条件)
		    $.getJSON("/stock/itemInfor.htm?method=getSmallItems",function(data) {
		           var options = "<option value=''>--选择小类--</option>";
		           $.each(data._Items, function(i, n) {
		               options += "<option value='"+n.itemId+"'>【"+n.itemCode+"】"+n.itemName+"</option>";   
		           });   
		           $('#s_itemId').html(options);  
		           
		           $("#s_itemId").combobox(); 
		    });
		});
		
	</script>