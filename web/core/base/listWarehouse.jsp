<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<script type="text/javascript">
	var multiSearchParamsWarehouse = new Array();
	multiSearchParamsWarehouse[0] = "#listWarehouse";			//列表Id
	multiSearchParamsWarehouse[1] = "#multiSearchDialogWarehouse";//查询模态窗口Id
	var height = window.screen.height;
	//新增
	function addWarehouse(){
		/*var returnRolTag = window.showModalDialog("/stock/warehouse.htm?method=edit",null,"dialogWidth:800px;dialogHeight:"+height+"px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnRolTag == "refresh") {
			//保存信息后重新加载tab
			loadTab("listWarehouse.jsp", "11");
		}*/
		
		window.open("/stock/warehouse.htm?method=edit","_blank");
	}
	//修改
	function editWarehouse(rowId){
		/*var returnRolTag = window.showModalDialog("/stock/warehouse.htm?method=edit&rowId="+rowId,'',"dialogWidth:800px;dialogHeight:"+height+"px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnRolTag == "refresh") {
			//保存信息后重新加载tab
			loadTab("listWarehouse.jsp", "11");
		}*/
		
		window.open("/stock/warehouse.htm?method=edit&rowId="+rowId,"_blank");
	}
</script>

<title>仓库信息</title>

  		<div>
			<table id="listWarehouse"></table>
			<div id="pageWarehouse"></div>
		</div>
		
		<div id="multiSearchDialogWarehouse" style="display: none;">  
		    <table>  
		        <tbody>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="houseName"/>名称：
		                    <input type="hidden" class="searchOper" value="cn"/>
		                </td>  
		                <td>  
		                    <input type="text" class="searchString"/>  
		                </td>  
		            </tr>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="houseCode"/>编码：
		                    <input type="hidden" class="searchOper" value="cn"/>
		                </td>  
		                <td>  
		                    <input type="text" class="searchString"/>
		                </td>  
		            </tr>  
		        </tbody>  
		    </table>  
		</div>
		<script src="<c:url value="/"/>js/multisearch2.js"></script> 		
		<script type="text/javascript"> 		
			//打开查询窗口	
			function openMultipleSearchDialogWarehouse(){
				openMultipleSearchDialogs(multiSearchParamsWarehouse);
			}
			
			//自定义操作栏的显示内容
		    function formatOperation(cellvalue, options, rowdata) {
	           var returnStr = "<a href='javascript:;' onclick='editWarehouse("+options.rowId+")'>[修改]</a>";
	           return returnStr;
		    }
		    
		    
		    //显示姓名
		   	function formatWarehouseName(cellValue, options, rowObject) {			
				var html = '';
				html = "<a href='javascript:;' onclick='editWarehouse("+options.rowId+")'>" + cellValue + "</a>";				
				return html;
			}
		    
			//加载表格数据
			var $mygrid = jQuery("#listWarehouse").jqGrid({ 
                url:'/stock/warehouse.htm?method=list',
                //rownumbers: true,
                datatype: "json",                
               	autowidth: true,
				//height: "auto",
				height:document.documentElement.clientHeight-97,
                colNames:['Id', '名称', '编码','地址','描述'],  
                colModel:[
                    {name:'houseId',index:'houseId', width:0, sorttype:"int", search:false, key:true, hidden:true},
                    {name:'houseName',index:'houseName', width:30,editable:true,formatter:formatWarehouseName, sortable:false,align:'left', sorttype:"string"},
                    {name:'houseCode',index:'houseCode', width:20, align:'center'},
                    {name:'address',index:'address', width:30, align:'left'},
                    {name:'description',index:'description', width:30, align:'left'}
                ],
                sortname: 'houseId',
                sortorder: 'asc',
                multiselect: true,
                viewrecords: true,
                rowNum: 20,
                rowList: [10,20,30],
                scroll: false, 
                scrollrows: false,                          
                jsonReader:{
                   repeatitems: false
                },         
                pager: "#pageWarehouse"
                //caption: "角色信息"
        }).navGrid('#pageWarehouse',{edit:false,add:false,del:false,search:false});       
        //}).navGrid('#pageWarehouse',{edit:false,add:false,del:false}).searchGrid({multipleSearch:true,autoOpen:false});
		
		//自定义按钮
		jQuery("#listWarehouse").jqGrid('navButtonAdd','#pageWarehouse', {
			caption:"新增", title:"点击新增信息", buttonicon:'ui-icon-plusthick', onClickButton: addWarehouse
		});
		jQuery("#listWarehouse").jqGrid('navButtonAdd','#pageWarehouse', {
			caption:"删除", title:"点击删除信息", buttonicon:'ui-icon-closethick', onClickButton: deleteWarehouse
		});
		jQuery("#listWarehouse").jqGrid('navButtonAdd','#pageWarehouse', {
			caption:"查询", title:"点击查询信息", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialogWarehouse
		});
		
		//批量删除
		function deleteWarehouse(){
			doDelete("/stock/warehouse.htm?method=delete","listWarehouse");
		}
		
	</script>