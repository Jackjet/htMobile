<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>巡检任务状态</title>
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
	
		//新增大项
		function addPatrolWork(){
			window.open("/patrol/bigCategory.htm?method=edit", "_blank");
		}
		//修改大项
		function editPatrolWork(xId){
			window.open("/patrol/bigCategory.htm?method=edit&xId="+xId, "_blank");
		}
		//增加小项
		function addSmallCategory(bigId){
			window.open("/patrol/smallCategory.htm?method=edit&bigId="+bigId, "_blank");
		}
		//修改小项
		function editSmallCategory(xId){
			window.open("/patrol/smallCategory.htm?method=edit&xId="+xId, "_blank");
		}
		//提交审核
		function submitToCheck(xId){
			
			if(PatrolWorkId != null){
				var url = "/vehicle/dealPatrolWork.htm?method=processAudit&xId="+xId;
				var yes = window.confirm("确定要提交到审核吗？");
				if (yes) {
					$.ajax({
						url: url,	//url
						cache: false,
						type: "POST",
						dataType: "html",
						beforeSend: function (xhr) {						
							
						},
							
						complete : function (req, err) {
							alert("已提交审核！");
							$("#listPatrolWork").trigger("reloadGrid"); 
						}
					});	
				}
			}
		}
		//删除大项
		function delPatrolWork(xId){
			if(xId != null){
				var url = "/patrol/bigCategory.htm?method=delete&xId="+xId;
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
							$("#listPatrolWork").trigger("reloadGrid"); 
						}
					});	
				}
			}
		}
		//删除大项
		function delSmallCategory(xId){
			if(xId != null){
				var url = "/patrol/smallCategory.htm?method=delete&xId="+xId;
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
							$("#listPatrolWork").trigger("reloadGrid"); 
						}
					});	
				}
			}
		}
		//查看
		function doPatrolWorkView(rowId){
			window.open("/vehicle/dealPatrolWork.htm?method=process&xId="+rowId, "_blank");
		}
	</script>
	<div>
		<table id="listPatrolWork"></table> <!-- 信息列表 -->
		<div id="pagerPatrolWork"></div> <!-- 分页 -->
	</div>
	
	<!-- 查询框 -->
	<div id="multiSearchDialogPatrolWork" style="display: none;">  
	    <table>  
	        <tbody>
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
		#patrolDetail th,#patrolDetail td{
			background:white;
			text-align:center;
		}
	</style>
	<div id="patrolDetail" style="display:none;">
		<table border=0 width="100%" style="margin:0 auto;" cellspacing=1 cellpadding=5 bgcolor="gray">
			<tr>
				<th>巡检大项</th>
				<th>序号</th>
				<th>巡检点</th>
				<th>是否正常</th>
				<th>操作时间</th>
				<th>异常内容</th>
				<th>图片</th>
			</tr>
			
			<tbody id="patrolDetailBody">
				
			</tbody>
		</table>
	</div>
	
	<script type="text/javascript"> 		
		
		//自定义操作栏的显示内容
		$( function() {    
			$( "#patrolDetail" ).dialog({      
				autoOpen: false,       
		        modal: true,   
		        resizable: true,       
		        width: document.documentElement.clientWidth - 100,   
		        height: document.documentElement.clientHeight,
		        title: "巡检详情"
			}); 
			
		});
		
		//查看巡检详情
		function viewDetail(itemId){
			$("#patrolDetailBody").html("");
			$.ajax({
				url: '/patrol.htm?method=getBigs&itemId='+itemId,
				type:'POST', 
		        async: false,
		        cache: false,
		        dataType: "json",
		        success: function(data){
		        	var bigArray = new Array();
		        	bigArray = data.result;
		        	
					var rtnHtml = "";
					var cNo = 1;
					for(var i = 0;i<bigArray.length;i++){
						
						if(bigArray[i] != null){
							//alert(bigArray[i]);
							var detailArray = bigArray[i].details;
							rtnHtml += "<tr><td rowspan="+detailArray.length+">" + bigArray[i].categoryName + "</td>";
							
							
							for(var j=0;j<detailArray.length;j++){
								var detail = detailArray[j];
								
								if(j >0) {
									rtnHtml += "<tr>";
								}
								
								rtnHtml += "<td>"+cNo+"</td>";
								rtnHtml += "<td align='left'>"+detail.smallName+"</td>";
								var tmpResult = "";
								if(detail.opResult == 0){
									tmpResult = "<font color='black'>未检</font>";
								}else if(detail.opResult == 1){
									tmpResult = "<font color='blue'>是</font>";
								}else if(detail.opResult == -1){
									tmpResult = "<font color='red'>否</font>";
								}
								rtnHtml += "<td>"+tmpResult+"</td>";
								rtnHtml += "<td>"+detail.endTimeStr+"</td>";
								rtnHtml += "<td align='left'>&nbsp;&nbsp;"+detail.errorLog+"</td>";
								
								rtnHtml += "<td>";
								var tmpAttachArray = detail.attachs.split(";");
				        		for(var m=0;m<tmpAttachArray.length;m++){
				        			if(tmpAttachArray[m] != null && tmpAttachArray[m] !=""){
				        				rtnHtml += "<a href=\"/" + tmpAttachArray[m].split("|")[0] + "\" target=\"_blank\">";
					        			rtnHtml += "<img border=0 title=\"点击查看大图\" src=\"/" + tmpAttachArray[m].split("|")[0] + "\" width=\"35\" height=\"35\" />";
					        			rtnHtml += "</a>&nbsp;&nbsp;";
				        			}
				        		}
				        		rtnHtml += "</td>";
								
								rtnHtml += "</tr>";
								cNo += 1;
							}
						}
						
						
					}
					
					//return rtnHtml;
					$("#patrolDetailBody").html(rtnHtml);
		        	
		        }
			});
			
			
			
			
			$("#patrolDetail").dialog("open");
		}
		
		
		//小项操作
		function formatOperate(cellValue, options, rowObject){
			var html = "";
			//alert(rowObject);
			//var bigList = new Array();
			//bigList = rowObject.bigList;
			//alert(bigList.valueOf());
			//for(var i=0;i<bigList.length;i++){
			//	alert(bigList[i].categoryName);
			//}
			//alert(bigList);
			//bigList = [1,2,3];
			html += "<a href='#' onclick='viewDetail("+rowObject.itemId+");' alt='巡检项'>【<font color=red>巡检详情</font>】</a>";
			//alert(html);
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
		var $mygrid = jQuery("#listPatrolWork").jqGrid({
            url:'/patrol.htm?method=patrolList',
            rownumbers: true,	//是否显示序号
            datatype: "json",   //从后台获取的数据类型              
           	autowidth: true,	//是否自适应宽度
			//height: "auto",
			height:document.documentElement.clientHeight-51,
            colNames:['Id','巡检标题', '开始时间', '完成时间', '执行人', '发布人', '状态', '相关操作'],//表的第一行标题栏
            //以下为每列显示的具体数据
            colModel:[
	            {name:'itemId',index:'itemId', width:0, search:false, hidden:true, key:true},  
	            {name:'workName',index:'workName', width:30,align:'center'},
	            {name:'beginTime',index:'beginTime', width:15,align:'center'},
	            //{name:'endTimeStr',index:'endTimeStr', width:15,align:'center'},
	            {name:'finishTime',index:'finishTime', width:15,align:'center'},
	            {name:'executerName',index:'executerName', width:10,align:'center'},
	            {name:'createrName',index:'createrName', width:10,align:'center'},
	            {name:'sta',index:'sta', width:10,align:'center',formatter:formatSta},
	            {name:'operate',width:20,align:'center',formatter:formatOperate,sortable:false}
            ],
            //caption: "油耗管理",
            sortname: 'itemId', //默认排序的字段
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
            pager: "#pagerPatrolWork"//分页工具栏
            
       	}).navGrid('#pagerPatrolWork',{edit:false,add:false,del:false,search:false});       
	
		//自定义按钮
		//jQuery("#listPatrolWork").jqGrid('navButtonAdd','#pagerPatrolWork', {
		//	caption:"增加大项", title:"点击增加大项", buttonicon:'ui-icon-plusthick', onClickButton: addPatrolWork
		//});
		
		jQuery("#listPatrolWork").jqGrid('navButtonAdd','#pagerPatrolWork', {
			caption:"查询", title:"点击查询", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialog
		});
		
		//打开查询窗口并进行窗口初始化
		var multiSearchParams = new Array();
		function openMultipleSearchDialog() {
		    multiSearchParams[0] = "#listPatrolWork";				//列表Id
			multiSearchParams[1] = "#multiSearchDialogPatrolWork";//查询模态窗口Id
			
			initSearchDialog();
			
		    $(multiSearchParams[1]).dialog("open");
		    window.scrollTo(0, 0);
		}
		
		
	</script>

