<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<script type="text/javascript">
var height = window.screen.height;
	//新增
	function addInfor_base(){
		/*var returnArray = window.showModalDialog("/security/document.htm?method=edit",'',"dialogWidth:800px;dialogHeight:600px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnArray != null && returnArray[0] == "refresh") {
			self.location.reload();
		}*/
		window.open("/security/document.htm?method=edit","_blank");
	}
	
	//修改
	function editInfor_base(rowId){
		/*var returnArray = window.showModalDialog("/security/document.htm?method=edit&documentId="+rowId,'',"dialogWidth:800px;dialogHeight:600px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		if(returnArray != null && returnArray[0] == "refresh") {
			self.location.reload();
		}*/
		window.open("/security/document.htm?method=edit&documentId="+rowId,"_blank");
	}
	
	//授权
	function doAuthorize_base(rowId){
		var returnArray = window.showModalDialog("/security/document.htm?method=editInforRight&rowId="+rowId,'',"dialogWidth:900px;dialogHeight:"+height+"px;center:Yes;dialogTop: 100px; dialogLeft: 300px;");
		if(returnArray != null && returnArray[0] == "refresh") {
			self.location.reload();
		}
	}
	
	//查看
	function viewInfor_base(rowId){
		//window.showModalDialog("/security/document.htm?method=view&rowId="+rowId,'',"dialogWidth:600px;dialogHeight:600px;center:Yes;dialogTop: 200px; dialogLeft: 300px;");
		window.open("/security/document.htm?method=view&rowId="+rowId,"_blank");
	}
</script>

<title></title>

  		<div>
			<table id="listAllDocument"></table>
			<div id="pager"></div>
		</div>
		
		<!-- 查询框 -->
		<div id="multiSearchDialog" style="display: none;">  
		    <table>
		        <tbody> 
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="documentTitle"/>标题：
		                    <input type="hidden" class="searchOper" value="cn"/>
		                </td>  
		                <td>  
		                    <input type="text" class="searchString" size="25"/>  
		                </td>  
		            </tr>
		            <tr>  
		                <td>  
		                    <input type="hidden" class="searchField" value="keyword"/>关键字：
		                    <input type="hidden" class="searchOper" value="cn"/>
		                </td>  
		                <td>  
		                    <input type="text" class="searchString"/>
		                </td>  
		            </tr>
		            
		        </tbody>  
		    </table>  
		</div>
		
		<script type="text/javascript"><!-- 		
			
			//自定义操作栏的显示内容
		    function formatOperation(cellvalue, options, rowdata) {
	           var returnStr = "<a href='javascript:;' onclick='editInfor_base("+options.rowId+")'>[修改]</a>";
	           //returnStr += " <a href='javascript:;' onclick='doAuthorize_base("+options.rowId+")'>[授权]</a>";
	           //  <a href='javascript:;' onclick='viewInfor("+options.rowId+")'>[查看]</a>
	           return returnStr;
		    }
		    
		    //自定义显示boolean型内容
		    function formatBol(cellvalue) {
	           var returnStr;
	           if (cellvalue) {
	              returnStr = "推荐";
	           }else {
	              returnStr = "不推荐";
	           }
	           return returnStr;
		    }
		    
		    //自定义附件显示
		    function formatAttachment(cellValue, options, rowObject) {				
				var html = '';
				//alert(cellValue);
				html = showAttachment(cellValue,'');				
				return html;
			}
			
			//标题查看链接
			function formatTitle(cellValue, options, rowObject) {				
				var html = '';
				html = "<a href='javascript:;' title='"+cellValue+"' onclick='viewInfor_base("+options.rowId+")'>";
				
				if(cellValue.length > 30){
    				html += cellValue.substr(0,30);
    				html += " ······";
    			}else{
    				html += cellValue;
    			}
				
				html += "</a>";
				return html;				
			}
		   
			//加载表格数据
			var $mygrid = jQuery("#listAllDocument").jqGrid({
			
                url:"/security/document.htm?method=getDocumentInfor&categoryId="+${param.categoryId},
                rownumbers: true,
                datatype: "json",                
               	autowidth: true,
				height:document.documentElement.clientHeight-97,
                colNames:['Id','标题','所属分类','操作'],//表的第一行标题栏'更新时间','精华文档',,'操作'
	            colModel:[
	                {name:'documentId',index:'documentId', width:0, sorttype:"int", search:false, key:true, hidden:true},
	                {name:'documentTitle',index:'documentTitle',align:'left', sortable:true,sorttype:"string",formatter:formatTitle},
	                //{name:'documentCode',index:'documentCode',align:'center', width:20},
	                //{name:'keyword',index:'keyword', width:25,align:'left'},
	                //{name:'description',index:'description', width:40,align:'left'},
	                {name:'category.categoryName',index:'category.categoryName', width:60,align:'center'},
	                //{name:'author.person.personName',index:'author.person.personName', width:40, align:'center'},
	                //{name:'createTime',index:'createTime', width:40, align:'center'},
	                //{name:'editor.person.personName',index:'editor.person.personName', width:40, align:'center'},
	               // {name:'updateTime',index:'updateTime', width:55, align:'center'},
	                //{name:'commended',index:'commended', width:30, align:'center',formatter:formatBol},
	                //{name:'op',index:'op', width:50, align:'formatOperationleft'}
	                //{name:'attachment',index:'attachment', width:25, align:'left',formatter:formatAttachment},
	                {name:'op',index:'op',align:'center', width:50, search:false, sortable:false, formatter:formatOperation}
	            ],
                sortname: 'documentId',
                sortorder: 'desc',
                multiselect: true,	//是否支持多选,可用于批量删除
                viewrecords: true,
                rowNum: 20,
                rowList: [15,20,30],
                scroll: false, 
                scrollrows: false,                          
                jsonReader:{
                   repeatitems: false
                },         
                pager: "#pager"
	        }).navGrid('#pager',{edit:false,add:false,del:false,search:false});       
			
			//自定义按钮
			if (${param.isRoot != 'true'}) {
				//为根分类时不显示
				jQuery("#listAllDocument").jqGrid('navButtonAdd','#pager', {
					caption:"新增", title:"点击新增文档", buttonicon:'ui-icon-plusthick', onClickButton: addInfor_base
				});
			}
			jQuery("#listAllDocument").jqGrid('navButtonAdd','#pager', {
				caption:"<span style='color: red;'>删除</span>", title:"点击批量删除", buttonicon:'ui-icon-closethick', onClickButton: deleteInfor
			});
			jQuery("#listAllDocument").jqGrid('navButtonAdd','#pager', {
				caption:"查询", title:"点击查询", buttonicon:'ui-icon-search', onClickButton: openDialog
			});
			
			//打开查询窗口并进行窗口初始化
			var multiSearchParams = new Array();
			function openDialog() {
			    multiSearchParams[0] = "#listAllDocument";				//列表Id
				multiSearchParams[1] = "#multiSearchDialog";//查询模态窗口Id
				
				initSearchDialog();
				
			    $(multiSearchParams[1]).dialog("open");
			    window.scrollTo(0, 0);
			}
			
			//批量删除
			function deleteInfor(){
				doDelete("/security/document.htm?method=delete","listAllDocument");
			}
			//部门信息初始化
			$('#departmentId').selectInit();
			
			//加载部门及联动信息		
			$.loadDepartments("departmentId", null, "personId");
			
			jQuery().ready(function (){
		    //获取部门信息(查询条件)
		   /* $.getJSON("/core/organizeInfor.htm?method=getDepartments",function(data) {
		           var options = "<option value=''>--选择部门--</option>";
		           $.each(data._Departments, function(i, n) {
		               options += "<option value='"+n.organizeId+"'>"+n.organizeName+"</option>";   
		           });   
		           $('#departmentId').html(options);
		        }   
		    );*/
		    
		    //获取分类信息(查询条件)
		    $.getJSON("/security/document.htm?method=getCategoryName",function(data) {
		           var options = "";
		           $.each(data._CategoryNames, function(i, n) {
		               options += "<option value='"+n.categoryId+"'>";
		                
		               for(var j=0;j<=n.layer;j++){
		               	options += "&nbsp;";
		               };
		               
		               if(n.layer==1){
		               	options += "<b>+</b>";
		               }
		               if(n.layer==2){
		               	options += "<b>-</b>"; 
		               }
		               
		               options += n.categoryName+"</option>";
		           });   
		           $('#searchCategoryId').html(options);
		        }   
		    );
		});
			
		</script>