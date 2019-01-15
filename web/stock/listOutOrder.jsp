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

	
	<link href="<c:url value="/"/>js/loadmask/jquery.loadmask.css" rel="stylesheet" type="text/css" />
	<script type='text/javascript' src='<c:url value="/"/>js/loadmask/jquery.loadmask.min.js'></script>
<script type="text/javascript">
	//初始化列表和查询窗口Id
	var multiSearchParams = new Array();
	multiSearchParams[0] = "#listOutOrderInfor";			//列表Id
	multiSearchParams[1] = "#multiSearchDialog";//查询模态窗口Id


	//新增
	function addOutOrderInfor(){
		var height = window.screen.height;
		//var refresh = window.showModalDialog("/stock/outOrderInfor.htm?method=edit",null,"dialogWidth:900px;dialogHeight:"+height+"px;center:Yes;dialogTop: 5px; dialogLeft: 200px;");
		//if(refresh == "Y") {
		//	self.location.reload();
		//} 
		window.open("/stock/outOrderInfor.htm?method=edit", "_blank");
		
		//window.open("/stock/outOrderInfor.htm?method=edit",null,"");
		
		
		/*$.ajax({
			url: "/stock/outOrderInfor.htm?method=edit",
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
		/*var refresh = window.showModalDialog("/stock/outOrderInfor.htm?method=viewOutOrderInfor&rowId="+rowId,'',"dialogWidth:900px;dialogHeight:650px;center:Yes;dialogTop: 5px; dialogLeft: 200px;");
		if(refresh == "Y") {
			//self.location.reload();
			$("#listOutOrderInfor").trigger("reloadGrid"); 
		} */
		window.open("/stock/outOrderInfor.htm?method=view&rowId="+rowId, "_blank");
	}
	
	//退回
	function doBack(rowId){
		if(confirm("确定执行退回申请吗？")){
			$.ajax({
				url: '/stock/outOrderInfor.htm?method=doBack&outOrderId='+rowId,
				cache: false,
				type: "GET",
				//dataType : "json",
				async: true,
	            cache: false,
				beforeSend: function (xhr) {
				},
				complete : function(req, msg) {
				},
				success : function (msg) {
					if(msg == 'success'){
						alert("申请成功！");
						window.location.reload();
					}
					if(msg == 'fail'){
						alert("申请失败，请重试！");
					}
					$mygrid.trigger("reloadGrid");
				}
			});
		}
		//window.open("/stock/outOrderInfor.htm?method=view&rowId="+rowId, "_blank");
	}
	
	function reloadGrid(){
		$("#listOutOrderInfor").trigger("reloadGrid"); 
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
	
	function rtnItems(id){
     	//var nextInp = document.getElementById(next);
		var event = arguments.callee.caller.arguments[0] || window.event;
		if(event.keyCode == 13){//判断是否按了回车，enter的keycode代码是13
			searchItems(id);
		}
     }
	
	//查询备件
    function searchItems(inputId){
    	var keyWord = $("#"+inputId).val();
    	if(keyWord != "" && keyWord != null){
    		if(keyWord.length == 1){
    			alert("关键字长度应不少于2！");
    			$("#"+inputId).focus();
    		}else{
    			$("#multiSearchDialog").mask("搜索中，请稍等...");
	     		$.getJSON("/stock/itemInfor.htm?method=getItemsByTagName&range=out&itemTag="+encodeURI(keyWord),function(data) {
		           var options = "<option value=''>--选择备件--</option>";
		           $.each(data._Items, function(i, n) {
		               options += "<option value='"+n.itemId+"'>【"+n.itemCode+"】"+n.itemName+"</option>";
		           });   
		           $("#selItemId").html(options);   
		           $("#multiSearchDialog").unmask();
		           $("#selItemId").focus();
		        });
		        $("#multiSearchDialog").unmask();
    		}
    		
    	}
   	
	    $("#multiSearchDialog").unmask();
    }
</script>

<title>出库单</title>
	<div id="edit_dlg"></div>
	<div>
		<table id="listOutOrderInfor"></table> <!-- 信息列表 -->
		<div id="pagerOutOrderInfor"></div> <!-- 分页 -->
	</div>
	
	<!-- 查询框 -->
	<div id="multiSearchDialog" style="display: none;width:200px;">  
	    <table>  
	        <tbody>  
	            <tr>  
		           <td>  
		              <input type="hidden" class="searchField" value="outOrderNo"/>出库单号：
		              <input type="hidden" class="searchOper" value="cn"/>
		           </td>  
		           <td>  
		              <input type="text" class="searchString"/>  
		           </td>  
		        </tr>
		        
		        
		        <tr>
		        	<td>
		        		<input type="hidden" class="searchField" value="itemId"/>备件：
		             	<input type="hidden" class="searchOper" value="eq"/>
		        	</td>
		        	<td>
		        		<input id="searchWord" size="10" onkeydown="rtnItems('searchWord');" onblur="searchItems('searchWord');"/>
						<img src="<c:url value="/"/>images/ssky.png" onclick="searchItems('searchWord');" align="absmiddle" height="22" border=0 style="cursor:pointer;margin-left:-7px;"/>
						<br/>
						<select class="searchString" id="selItemId">
                      		<option value="">--选择备件--</option>
                      	</select>
		        	</td>
		        </tr>
		        
		        <tr>  
		           <td>  
		              <input type="hidden" class="searchField" value="department.organizeId"/>使用部门：
		              <input type="hidden" class="searchOper" value="eq"/>
		           </td>  
		           <td>  
		               <select class="searchString" id="departmentId"></select>
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
	                        <option value="0">审批未通过</option>
	                        <option value="1">高于1000，待审批</option>
	                        <option value="2">已出库</option>
	                        <option value="3">退回，待审批</option>
	                        <option value="4">已退回</option>
	                    </select>
	                </td>  
	            </tr>
		        
		        <tr>  
	                <td nowrap>  
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
			if(cellValue == "合计"){
				html = cellValue;
			}else if(cellValue.length > 24){
				html += cellValue.substr(0,24);
    			html += " ······";
    			html += "</a>";			
			}else{
				html += cellValue;
				html += "</a>";			
			}
				
			return html;
		}
		
		
		//自定义状态
		function formatStatus(cellValue, options, rowObject) {
			var html = "";
			if(cellValue == "0"){
				html = "<font color=blue>审批未通过</font>";
			}
			if(cellValue == "1"){
				html = "<font color=red>高于1000，待审批</font>";
			}	
			
			if(cellValue == "2"){
				html = "<font color=green>已出库</font>";
			}	
			
			if(cellValue == "3"){
				html = "<font color=green>退回，待审批</font>";
			}
			
			if(cellValue == "4"){
				html = "<font color=red>已退回</font>";
			}	
			
			return html;
		}
		
		//操作
		function formatOperate(cellValue, options, rowObject) {				
			var html = '';
			if(rowObject.status == '2'){
				html = "【<b><font color=red><a href='javascript:;' title='点击申请退回' onclick='doBack("+options.rowId+")'>退回</a></font></b>】";
			}
			return html;
		}
	    
		//加载表格数据
		var $mygrid = jQuery("#listOutOrderInfor").jqGrid({
            url:'/stock/outOrderInfor.htm?method=list',
            //rownumbers: true,	//是否显示序号
            datatype: "json",   //从后台获取的数据类型              
           	autowidth: true,	//是否自适应宽度
			//height: "auto",
			height:document.documentElement.clientHeight-100,
            colNames:['Id','出库单号', '需求日期', '分类', '设备','总金额','领料人','发放日期', '状态','操作'],//表的第一行标题栏
            //以下为每列显示的具体数据
            colModel:[
                {name:'outOrderId',index:'outOrderId', width:0, search:false, hidden:true, key:true},            
                {name:'outOrderNo',index:'outOrderNo', width:40, sortable:true, formatter:formatTitle,sorttype:"string"},
                {name:'createDateStr',index:'createDateStr', width:30,align:'center'},
                {name:'typeName',index:'typeName', width:40,align:'center'},
                {name:'deviceName',index:'deviceName', width:40,align:'center'},
                {name:'amount',index:'amount', width:30,align:'center'},
                {name:'applierName',index:'applierName', width:40,align:'center'},
                {name:'sendDateStr',index:'sendDateStr', width:40,align:'center'},
                {name:'status',index:'status', width:35,align:'center', formatter:formatStatus},
                {name:'operate', width:25,align:'center', formatter:formatOperate}
            ],
            caption: "出库单",
            sortname: 'outOrderId', //默认排序的字段
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
            pager: "#pagerOutOrderInfor",	//分页工具栏
            //caption: "用户信息"	//表头
            footerrow : true,   //显示底部一行
            //userDataOnFooter : true,
            //altRows : true,
            gridComplete:function(){
	            var rowNum=parseInt($(this).getGridParam("records"),10);
	            if(rowNum>0){
	                $(".ui-jqgrid-sdiv").show();
	                var amount=$(this).getCol("amount",false,"sum").toFixed(2);
	                 //将合计值显示出来
	                $(this).footerData("set",{"outOrderNo":"合计","amount":amount}); 
	            }else{
	                $(".ui-jqgrid-sdiv").hide();
	            }
	        }
       	}).navGrid('#pagerOutOrderInfor',{edit:false,add:false,del:false,search:false});       
	
		//自定义按钮
		jQuery("#listOutOrderInfor").jqGrid('navButtonAdd','#pagerOutOrderInfor', {
			caption:"新增", title:"点击新增信息", buttonicon:'ui-icon-plusthick', onClickButton: addOutOrderInfor
		});
		
		/*jQuery("#listOutOrderInfor").jqGrid('navButtonAdd','#pagerOutOrderInfor', {
			caption:"<span style='color: red;'>作废</span>", title:"订单作废申请", buttonicon:'ui-icon-closethick', onClickButton: deleteOutOrderInfor
		});*/
		
		jQuery("#listOutOrderInfor").jqGrid('navButtonAdd','#pagerOutOrderInfor', {
			caption:"查询", title:"点击查询", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialog
		});
		
		//删除数据
		function deleteOutOrderInfor(){
		
			doDelete("/stock/outOrderInfor.htm?method=delete","listOutOrderInfor");
			
		}
		
		/** 查询条件中的部门,班组,用户下拉联动 */
		//部门信息初始化
		$('#departmentId').selectInit();
		
		//加载部门及联动信息		
		$.loadDepartments("departmentId", null, "authorId");
		
	</script>