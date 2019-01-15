<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>普通任务状态</title>
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
		function addCommonWork(){
			window.open("/nightWatch/item.htm?method=edit", "_blank");
		}
		//修改小项
		function editCommonWork(xId){
			window.open("/nightWatch/item.htm?method=edit&xId="+xId, "_blank");
		}
		//删除小项
		function delCommonWork(xId){
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
							$("#listCommonWork").trigger("reloadGrid"); 
						}
					});	
				}
			}
		}
		
	</script>
	<div>
		<table id="listCommonWork"></table> <!-- 信息列表 -->
		<div id="pagerCommonWork"></div> <!-- 分页 -->
	</div>
	
	<!-- 查询框 -->
	<div id="multiSearchDialogCommonWork" style="display: none;">  
	    <table>  
	        <tbody>  
	            <tr>  
		            <td>  
	                    <input type="hidden" class="searchField" value="workTitle"/>任务名称：
	                    <input type="hidden" class="searchOper" value="cn"/>
	                </td>  
	                <td>  
	                    <input type="text" class="searchString" size="25"/>  
	                </td>  
		        </tr>
		         <tr>  
	                <td>  
	                    <input type="hidden" class="searchField" value="beginTime"/>开始时间起：
	                    <input type="hidden" class="searchOper" value="ge"/>
	                </td>  
	                <td>  
	                    <input class="searchString" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true" size="25"/>
	                </td>  
	            </tr>
	            
	            <tr>  
	                <td>  
	                    <input type="hidden" class="searchField" value="beginTime"/>开始时间止：
	                    <input type="hidden" class="searchOper" value="le"/>
	                </td>  
	                <td>  
	                    <input class="searchString" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true" size="25"/>
	                </td>  
	            </tr>

	        </tbody>  
	    </table>  
	</div>
	
	<style>
		#changeLog th,#changeLog td,#workReply th,#workReply td{
			background:white;
			text-align:center;
		}
	</style>
	<div id="changeLog" style="display:none;">
		<table border=0 width="100%" style="margin:0 auto;" cellspacing=1 cellpadding=5 bgcolor="gray">
			<tr>
				<th>序号</th>
				<th>时间</th>
				<th>操作人</th>
				<th>操作内容</th>
			</tr>
			
			<tbody id="changeLogBody">
				
			</tbody>
		</table>
	</div>
	
	<div id="workReply" style="display:none;">
		<table border=0 width="100%" style="margin:0 auto;" cellspacing=1 cellpadding=5 bgcolor="gray">
			<tr>
				<th>序号</th>
				<th>时间</th>
				<th>操作人</th>
				<th>内容</th>
				<th>图片</th>
			</tr>
			
			<tbody id="workReplyBody">
				
			</tbody>
		</table>
	</div>
	
	<script type="text/javascript"> 		
		
		//自定义操作栏的显示内容
		$( function() {    
			$( "#changeLog" ).dialog({      
				autoOpen: false,       
		        modal: true,   
		        resizable: true,       
		        width: 600,   
		        height: document.documentElement.clientHeight,
		        title: "变更记录"
			}); 
			
			$( "#workReply" ).dialog({      
				autoOpen: false,       
		        modal: true,   
		        resizable: true,       
		        width: document.documentElement.clientWidth - 100,   
		        height: document.documentElement.clientHeight,
		        title: "查看回复"
			}); 
		});
	  
		//小项显示标题
		function formatCommonWorkTitle(cellValue, options, rowObject) {				
			var html = '';
			html = "<a href='javascript:;'  onclick='editCommonWork("+options.rowId+")'>";
			html += cellValue;
			html += "</a>";				
			return html;
		}
		
		//变更记录
		function changeLog(workId){
			//$("#changeLog").dialog();
			$("#changeLogBody").html("");
			$.ajax({
				url: '/commonWork.htm?method=getTraces&workId='+workId,
				type:'POST', 
		        async: false,
		        cache: false,
		        dataType: "json",
		        success: function(data){
		        	//alert(data);
		        	var result = new Array();
		        	result = data.result;
		        	//alert(result);
		        	var rtnHtml = "";
		        	for(var i=0;i<result.length;i++){
		        		rtnHtml += "<tr><td>"+(i+1)+"</td>";
		        		rtnHtml += "<td>"+result[i].opTimeStr+"</td>";
		        		rtnHtml += "<td>"+result[i].operatorName+"</td>";
		        		rtnHtml += "<td>"+result[i].content+"</td></tr>";
		        	}
		        	
		        	$("#changeLogBody").html(rtnHtml);
		        	
		        }
			});
			
			$("#changeLog").dialog("open");
		}
		
		//查看回复
		function workReply(workId){
			//$("#changeLog").dialog();
			$("#workReplyBody").html("");
			$.ajax({
				url: '/workReply.htm?method=listInfor&workId='+workId,
				type:'POST', 
		        async: false,
		        cache: false,
		        dataType: "json",
		        success: function(data){
		        	//alert(data);
		        	var result = new Array();
		        	result = data.result;
		        	//alert(result);
		        	var rtnHtml = "";
		        	for(var i=0;i<result.length;i++){
		        		//alert(i);
		        		rtnHtml += "<tr><td>"+(i+1)+"</td>";
		        		rtnHtml += "<td>"+result[i].operateTime+"</td>";
		        		rtnHtml += "<td>"+result[i].operatorName+"</td>";
		        		rtnHtml += "<td>"+result[i].content+"</td>";
		        		rtnHtml += "<td>";
		        		
		        		var tmpAttachArray = result[i].attachs.split(";");
		        		//alert(tmpAttachArray);
		        		for(var j=0;j<tmpAttachArray.length;j++){
		        			//alert(j);
		        			if(tmpAttachArray[j] != null && tmpAttachArray[j] !=""){
		        				rtnHtml += "<a href=\"/" + tmpAttachArray[j].split("|")[0] + "\" target=\"_blank\">";
			        			rtnHtml += "<img border=0 title=\"点击查看大图\" src=\"/" + tmpAttachArray[j].split("|")[0] + "\" width=\"35\" height=\"35\" />";
			        			rtnHtml += "</a>&nbsp;&nbsp;";
		        			}
		        			
		        		}
		        		
		        		rtnHtml += "</td></tr>";
		        	}
		        	//alert(rtnHtml);
		        	$("#workReplyBody").html(rtnHtml);
		        	
		        }
			});
			
			$("#workReply").dialog("open");
		}
		
		
		//小项操作
		function formatCommonWorkOperate(cellValue, options, rowObject){
			//return "<a href=# onclick='editCommonWork("+rowObject.driverId+")'><font color=blue>[修改]</font></a>";
			var html = "";
			html += "<a href=# onclick='workReply("+rowObject.workId+")' alt='查看回复'>【<font color=blue>查看回复</font>】</a>&nbsp;&nbsp;";
			html += "<a href=# onclick='changeLog("+rowObject.workId+");' alt='变更记录'>【<font color=blue>变更记录</font>】</a>";
			return html;
		}
		
		//状态
		function formatSta(cellValue, options, rowObject){
			var html = "";
			if(!rowObject.valid){
				html += "<font color=red>已删除</font>";
			}else{
				if(rowObject.workState == "0"){
					html += "<font color=blue>进行中</font>";
				}else if(rowObject.workState == "1"){
						html += "<font color=black>已完成</font>";
					}
			}
			return html;
		}
		
	    
		//加载表格数据
		var $mygrid = jQuery("#listCommonWork").jqGrid({
            url:'/commonWork.htm?method=workList',
            rownumbers: true,	//是否显示序号
            datatype: "json",   //从后台获取的数据类型              
           	autowidth: true,	//是否自适应宽度
			//height: "auto",
			height:document.documentElement.clientHeight-51,//'结束时间', 
            colNames:['Id','任务名称', '开始时间', '完成时间', '执行人', '发布人', '状态', '相关操作'],//表的第一行标题栏
            //以下为每列显示的具体数据
            colModel:[
                {name:'xId',index:'xId', width:0, search:false, hidden:true, key:true},  
                {name:'workTitle',index:'workTitle', width:30,align:'center'},
                {name:'beginTimeStr',index:'beginTimeStr', width:15,align:'center'},
                //{name:'endTimeStr',index:'endTimeStr', width:15,align:'center'},
                {name:'finishTimeStr',index:'finishTimeStr', width:15,align:'center'},
                {name:'executerName',index:'executerName', width:10,align:'center'},
                {name:'createrName',index:'createrName', width:10,align:'center'},
                {name:'sta',index:'sta', width:10,align:'center',formatter:formatSta},
                {name:'operate',width:20,align:'center',formatter:formatCommonWorkOperate,sortable:false}
            ],
            //caption: "油耗管理",
            sortname: 'xId', //默认排序的字段
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
            pager: "#pagerCommonWork"	//分页工具栏
            
       	}).navGrid('#pagerCommonWork',{edit:false,add:false,del:false,search:false});       
	
		//自定义按钮
		//jQuery("#listCommonWork").jqGrid('navButtonAdd','#pagerCommonWork', {
		//	caption:"增加小项", title:"点击增加小项", buttonicon:'ui-icon-plusthick', onClickButton: addCommonWork
		//});
		
		jQuery("#listCommonWork").jqGrid('navButtonAdd','#pagerCommonWork', {
			caption:"查询", title:"点击查询", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialog
		});
		
		//打开查询窗口并进行窗口初始化
		var multiSearchParams = new Array();
		function openMultipleSearchDialog() {
		    multiSearchParams[0] = "#listCommonWork";				//列表Id
			multiSearchParams[1] = "#multiSearchDialogCommonWork";//查询模态窗口Id
			
			initSearchDialog();
			
		    $(multiSearchParams[1]).dialog("open");
		    window.scrollTo(0, 0);
		}
		
		
	</script>

