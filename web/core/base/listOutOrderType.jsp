<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<script type="text/javascript">
	var multiSearchParamsType = new Array();
	multiSearchParamsType[0] = "#listType";			//列表Id
	multiSearchParamsType[1] = "#multiSearchDialogType";//查询模态窗口Id
	var height = window.screen.height;
	//新增
	function addType(){
		/*var returnRolTag = window.showModalDialog("/stock/outOrderType.htm?method=edit",null,"dialogWidth:800px;dialogHeight:"+height+"px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnRolTag == "refresh") {
			//保存信息后重新加载tab
			loadTab("listOutOrderType.jsp", "12");
		}*/
		window.open("/stock/outOrderType.htm?method=edit","_blank");
	}
	//修改
	function editType(rowId){
		/*var returnRolTag = window.showModalDialog("/stock/outOrderType.htm?method=edit&rowId="+rowId,'',"dialogWidth:800px;dialogHeight:"+height+"px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnRolTag == "refresh") {
			//保存信息后重新加载tab
			loadTab("listOutOrderType.jsp", "12");
		}*/
		window.open("/stock/outOrderType.htm?method=edit&rowId="+rowId,"_blank");
	}
</script>

<title>出库分类信息</title>

  		<div>
			<table id="listType"></table>
			<div id="pageType"></div>
		</div>
		
		<div id="multiSearchDialogType" style="display: none;">  
		    <table>  
		        <tbody>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="typeName"/>名称：
		                    <input type="hidden" class="searchOper" value="cn"/>
		                </td>  
		                <td>  
		                    <input type="text" class="searchString"/>  
		                </td>  
		            </tr>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="typeCode"/>编码：
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
			function openMultipleSearchDialogType(){
				openMultipleSearchDialogs(multiSearchParamsType);
			}
			
			//自定义操作栏的显示内容
		    function formatOperation(cellvalue, options, rowdata) {
	           var returnStr = "<a href='javascript:;' onclick='editType("+options.rowId+")'>[修改]</a>";
	           return returnStr;
		    }
		    
		    
		    //显示姓名
		   	function formatTypeName(cellValue, options, rowObject) {			
				var html = '';
				html = "<a href='javascript:;' onclick='editType("+options.rowId+")'>" + cellValue + "</a>";				
				return html;
			}
		    
			//加载表格数据
			var $mygrid = jQuery("#listType").jqGrid({ 
                url:'/stock/outOrderType.htm?method=list',
                //rownumbers: true,
                datatype: "json",                
               	autowidth: true,
				//height: "auto",
				height:document.documentElement.clientHeight-97,
                colNames:['Id', '名称', '编码','显示次序','备注'],  
                colModel:[
                    {name:'typeId',index:'typeId', width:0, sorttype:"int", search:false, key:true, hidden:true},
                    {name:'typeName',index:'typeName', width:30,editable:true,formatter:formatTypeName, sortable:false, sorttype:"string"},
                    {name:'typeCode',index:'typeCode', width:30, align:'center'},
                    {name:'displayOrder',index:'displayOrder', width:15, align:'center'},
                    {name:'memo',index:'memo', width:30, align:'center'}
                ],
                sortname: 'typeId',
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
                pager: "#pageType"
                //caption: "角色信息"
        }).navGrid('#pageType',{edit:false,add:false,del:false,search:false});       
        //}).navGrid('#pageType',{edit:false,add:false,del:false}).searchGrid({multipleSearch:true,autoOpen:false});
		
		//自定义按钮
		jQuery("#listType").jqGrid('navButtonAdd','#pageType', {
			caption:"新增", title:"点击新增信息", buttonicon:'ui-icon-plusthick', onClickButton: addType
		});
		jQuery("#listType").jqGrid('navButtonAdd','#pageType', {
			caption:"删除", title:"点击删除信息", buttonicon:'ui-icon-closethick', onClickButton: deleteType
		});
		jQuery("#listType").jqGrid('navButtonAdd','#pageType', {
			caption:"查询", title:"点击查询信息", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialogType
		});
		
		//批量删除
		function deleteType(){
			doDelete("/stock/outOrderType.htm?method=delete","listType");
		}
		
	</script>