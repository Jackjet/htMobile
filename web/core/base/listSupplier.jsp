<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<script type="text/javascript">
	var multiSearchParamsSupplier = new Array();
	multiSearchParamsSupplier[0] = "#listSupplier";			//列表Id
	multiSearchParamsSupplier[1] = "#multiSearchDialogSupplier";//查询模态窗口Id
	
	var height = window.screen.height;
	
	//新增
	function addSupplier(){
		/*var returnRolTag = window.showModalDialog("/stock/supplierInfor.htm?method=edit",null,"dialogWidth:800px;dialogHeight:"+height+"px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnRolTag == "refresh") {
			//保存信息后重新加载tab
			loadTab("listSupplier.jsp", "9");
		}*/
		
		window.open("/stock/supplierInfor.htm?method=edit","_blank");
	}
	//修改
	function editSupplier(rowId){
		/*var returnRolTag = window.showModalDialog("/stock/supplierInfor.htm?method=edit&rowId="+rowId,'',"dialogWidth:800px;dialogHeight:350px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnRolTag == "refresh") {
			//保存信息后重新加载tab
			loadTab("listSupplier.jsp", "9");
		}*/
		
		window.open("/stock/supplierInfor.htm?method=edit&rowId="+rowId,"_blank");
	}
</script>

<title>供应商信息</title>

  		<div>
			<table id="listSupplier"></table>
			<div id="pageSupplier"></div>
		</div>
		
		<div id="multiSearchDialogSupplier" style="display: none;">  
		    <table>  
		        <tbody>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="supplierName"/>名称：
		                    <input type="hidden" class="searchOper" value="cn"/>
		                </td>  
		                <td>  
		                    <input type="text" class="searchString"/>  
		                </td>  
		            </tr>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="supplierCode"/>编码：
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
			function openMultipleSearchDialogSupplier(){
				openMultipleSearchDialogs(multiSearchParamsSupplier);
			}
			
			//自定义操作栏的显示内容
		    function formatOperation(cellvalue, options, rowdata) {
	           var returnStr = "<a href='javascript:;' onclick='editSupplier("+options.rowId+")'>[修改]</a>";
	           return returnStr;
		    }
		    
		    
		    //显示姓名
		   	function formatSupplierName(cellValue, options, rowObject) {			
				var html = '';
				html = "<a href='javascript:;' onclick='editSupplier("+options.rowId+")'>" + cellValue + "</a>";				
				return html;
			}
		    
			//加载表格数据
			var $mygrid = jQuery("#listSupplier").jqGrid({ 
                url:'/stock/supplierInfor.htm?method=list',
                //rownumbers: true,
                datatype: "json",                
               	autowidth: true,
				//height: "auto",
				height:document.documentElement.clientHeight-97,
                colNames:['Id', '名称', '编码','联系人','我方联系人','采购人'],  
                colModel:[
                    {name:'supplierId',index:'supplierId', width:0, sorttype:"int", search:false, key:true, hidden:true},
                    {name:'supplierName',index:'supplierName', width:40,editable:true,formatter:formatSupplierName, sortable:false, sorttype:"string"},
                    {name:'supplierCode',index:'supplierCode', width:20, align:'center'},
                    {name:'contactPerson',index:'contactPerson', width:20, align:'center'},
                    {name:'selfPerson',index:'selfPerson', width:20, align:'center'},
                    {name:'buyer',index:'buyer', width:20, align:'center'}
                ],
                sortname: 'supplierId',
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
                pager: "#pageSupplier"
                //caption: "角色信息"
        }).navGrid('#pageSupplier',{edit:false,add:false,del:false,search:false});       
        //}).navGrid('#pageSupplier',{edit:false,add:false,del:false}).searchGrid({multipleSearch:true,autoOpen:false});
		
		//自定义按钮
		jQuery("#listSupplier").jqGrid('navButtonAdd','#pageSupplier', {
			caption:"新增", title:"点击新增信息", buttonicon:'ui-icon-plusthick', onClickButton: addSupplier
		});
		jQuery("#listSupplier").jqGrid('navButtonAdd','#pageSupplier', {
			caption:"删除", title:"点击删除信息", buttonicon:'ui-icon-closethick', onClickButton: deleteSupplier
		});
		jQuery("#listSupplier").jqGrid('navButtonAdd','#pageSupplier', {
			caption:"查询", title:"点击查询信息", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialogSupplier
		});
		
		//批量删除
		function deleteSupplier(){
			doDelete("/stock/supplierInfor.htm?method=delete","listSupplier");
		}
		
	</script>