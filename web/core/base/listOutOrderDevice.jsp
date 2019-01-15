<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<script type="text/javascript">
	var multiSearchParamsDevice = new Array();
	multiSearchParamsDevice[0] = "#listDevice";			//列表Id
	multiSearchParamsDevice[1] = "#multiSearchDialogDevice";//查询模态窗口Id
	var height = window.screen.height;
	//新增
	function addDevice(){
		/*var returnRolTag = window.showModalDialog("/stock/outOrderDevice.htm?method=edit",null,"dialogWidth:800px;dialogHeight:"+height+"px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnRolTag == "refresh") {
			//保存信息后重新加载tab
			loadTab("listOutOrderDevice.jsp", "14");
		}*/
		window.open("/stock/outOrderDevice.htm?method=edit","_blank");
	}
	//修改
	function editDevice(rowId){
		/*var returnRolTag = window.showModalDialog("/stock/outOrderDevice.htm?method=edit&rowId="+rowId,'',"dialogWidth:800px;dialogHeight:"+height+"px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnRolTag == "refresh") {
			//保存信息后重新加载tab
			loadTab("listOutOrderDevice.jsp", "14");
		}*/
		window.open("/stock/outOrderDevice.htm?method=edit&rowId="+rowId,"_blank");
	}
</script>

<title>设备信息</title>

  		<div>
			<table id="listDevice"></table>
			<div id="pageDevice"></div>
		</div>
		
		<div id="multiSearchDialogDevice" style="display: none;">  
		    <table>  
		        <tbody>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="deviceName"/>名称：
		                    <input type="hidden" class="searchOper" value="cn"/>
		                </td>  
		                <td>  
		                    <input type="text" class="searchString"/>  
		                </td>  
		            </tr>  
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="deviceCode"/>编码：
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
			function openMultipleSearchDialogDevice(){
				openMultipleSearchDialogs(multiSearchParamsDevice);
			}
			
			//自定义操作栏的显示内容
		    function formatOperation(cellvalue, options, rowdata) {
	           var returnStr = "<a href='javascript:;' onclick='editDevice("+options.rowId+")'>[修改]</a>";
	           return returnStr;
		    }
		    
		    
		    //显示姓名
		   	function formatDeviceName(cellValue, options, rowObject) {			
				var html = '';
				html = "<a href='javascript:;' onclick='editDevice("+options.rowId+")'>" + cellValue + "</a>";				
				return html;
			}
		    
			//加载表格数据
			var $mygrid = jQuery("#listDevice").jqGrid({ 
                url:'/stock/outOrderDevice.htm?method=list',
                //rownumbers: true,
                datatype: "json",                
               	autowidth: true,
				//height: "auto",
				height:document.documentElement.clientHeight-97,
                colNames:['Id', '名称', '编码','统一编号','显示次序','备注'],  
                colModel:[
                    {name:'deviceId',index:'deviceId', width:0, sorttype:"int", search:false, key:true, hidden:true},
                    {name:'deviceName',index:'deviceName', width:30,editable:true,formatter:formatDeviceName, sortable:false, sorttype:"string"},
                    {name:'deviceCode',index:'deviceCode', width:30, align:'center'},
                    {name:'unionCode',index:'unionCode', width:30, align:'center'},
                    {name:'displayOrder',index:'displayOrder', width:15, align:'center'},
                    {name:'memo',index:'memo', width:30, align:'center'}
                ],
                sortname: 'deviceId',
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
                pager: "#pageDevice"
                //caption: "角色信息"
        }).navGrid('#pageDevice',{edit:false,add:false,del:false,search:false});       
        //}).navGrid('#pageDevice',{edit:false,add:false,del:false}).searchGrid({multipleSearch:true,autoOpen:false});
		
		//自定义按钮
		jQuery("#listDevice").jqGrid('navButtonAdd','#pageDevice', {
			caption:"新增", title:"点击新增信息", buttonicon:'ui-icon-plusthick', onClickButton: addDevice
		});
		jQuery("#listDevice").jqGrid('navButtonAdd','#pageDevice', {
			caption:"删除", title:"点击删除信息", buttonicon:'ui-icon-closethick', onClickButton: deleteDevice
		});
		jQuery("#listDevice").jqGrid('navButtonAdd','#pageDevice', {
			caption:"查询", title:"点击查询信息", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialogDevice
		});
		
		//批量删除
		function deleteDevice(){
			doDelete("/stock/outOrderDevice.htm?method=delete","listDevice");
		}
		
	</script>