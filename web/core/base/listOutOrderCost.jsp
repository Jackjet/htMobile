<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<script type="text/javascript">
	var multiSearchParamsCost = new Array();
	multiSearchParamsCost[0] = "#listCost";			//列表Id
	multiSearchParamsCost[1] = "#multiSearchDialogCost";//查询模态窗口Id
	var height = window.screen.height;
	//新增
	function addCost(){
		/*var returnRolTag = window.showModalDialog("/stock/outOrderCost.htm?method=edit",null,"dialogWidth:800px;dialogHeight:"+height+"px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnRolTag == "refresh") {
			//保存信息后重新加载tab
			loadTab("listOutOrderCost.jsp", "13");
		}*/
		window.open("/stock/outOrderCost.htm?method=edit","_blank");
	}
	//修改
	function editCost(rowId){
		/*ar returnRolTag = window.showModalDialog("/stock/outOrderCost.htm?method=edit&rowId="+rowId,'',"dialogWidth:800px;dialogHeight:"+height+"px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnRolTag == "refresh") {
			//保存信息后重新加载tab
			loadTab("listOutOrderCost.jsp", "13");
		}*/
		window.open("/stock/outOrderCost.htm?method=edit&rowId="+rowId,"_blank");
	}
</script>

<title>材料成本信息</title>

  		<div>
			<table id="listCost"></table>
			<div id="pageCost"></div>
		</div>
		
		<div id="multiSearchDialogCost" style="display: none;">  
		    <table>  
		        <tbody>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="costName"/>名称：
		                    <input type="hidden" class="searchOper" value="cn"/>
		                </td>  
		                <td>  
		                    <input type="text" class="searchString"/>  
		                </td>  
		            </tr>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="costCode"/>编码：
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
			function openMultipleSearchDialogCost(){
				openMultipleSearchDialogs(multiSearchParamsCost);
			}
			
			//自定义操作栏的显示内容
		    function formatOperation(cellvalue, options, rowdata) {
	           var returnStr = "<a href='javascript:;' onclick='editCost("+options.rowId+")'>[修改]</a>";
	           return returnStr;
		    }
		    
		    
		    //显示姓名
		   	function formatCostName(cellValue, options, rowObject) {			
				var html = '';
				html = "<a href='javascript:;' onclick='editCost("+options.rowId+")'>" + cellValue + "</a>";				
				return html;
			}
		    
			//加载表格数据
			var $mygrid = jQuery("#listCost").jqGrid({ 
                url:'/stock/outOrderCost.htm?method=list',
                //rownumbers: true,
                datatype: "json",                
               	autowidth: true,
				//height: "auto",
				height:document.documentElement.clientHeight-97,
                colNames:['Id', '名称', '编码','显示次序','备注'],  
                colModel:[
                    {name:'costId',index:'costId', width:0, sorttype:"int", search:false, key:true, hidden:true},
                    {name:'costName',index:'costName', width:30,editable:true,formatter:formatCostName, sortable:false, sorttype:"string"},
                    {name:'costCode',index:'costCode', width:30, align:'center'},
                    {name:'displayOrder',index:'displayOrder', width:15, align:'center'},
                    {name:'memo',index:'memo', width:30, align:'center'}
                ],
                sortname: 'costId',
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
                pager: "#pageCost"
                //caption: "角色信息"
        }).navGrid('#pageCost',{edit:false,add:false,del:false,search:false});       
        //}).navGrid('#pageCost',{edit:false,add:false,del:false}).searchGrid({multipleSearch:true,autoOpen:false});
		
		//自定义按钮
		jQuery("#listCost").jqGrid('navButtonAdd','#pageCost', {
			caption:"新增", title:"点击新增信息", buttonicon:'ui-icon-plusthick', onClickButton: addCost
		});
		jQuery("#listCost").jqGrid('navButtonAdd','#pageCost', {
			caption:"删除", title:"点击删除信息", buttonicon:'ui-icon-closethick', onClickButton: deleteCost
		});
		jQuery("#listCost").jqGrid('navButtonAdd','#pageCost', {
			caption:"查询", title:"点击查询信息", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialogCost
		});
		
		//批量删除
		function deleteCost(){
			doDelete("/stock/outOrderCost.htm?method=delete","listCost");
		}
		
	</script>