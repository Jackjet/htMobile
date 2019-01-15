<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>

<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title>文档大全</title>
		<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
		<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/jquery-1.9.0.min.js" type="text/javascript"></script>
		<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js" type="text/javascript"></script> <!--jquery ui-->
		<script src="<c:url value="/"/>js/commonFunction.js"></script>
		<script src="<c:url value="/"/>js/changeclass.js"></script> <!-- 用于改变页面样式-->
		<script src="<c:url value="/"/>js/inc_javascript.js"></script>
		<script src="<c:url value="/"/>js/multisearch.js"></script> <!--加载模态多条件查询相关js-->
		<script type="text/javascript" src="../js/ztree/js/jquery.ztree.all.js"></script>
		<script type="text/javascript" src="../js/ztree/js/jquery.ztree.exhide.js"></script>
		<link rel="stylesheet" href="../js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<!--<link rel="stylesheet" href="../js/ztree/css/metroStyle/metroStyle.css" type="text/css">--> 
		<link href="/css/all.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/gkSecure/jquery-ui-1.9.2.custom.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/ui.jqgrid.css" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/tabstyle.css" />
		<link rel="stylesheet" type="text/css" href="../js/leftnav/css/font-awesome.min.css">
		<link rel="stylesheet" href="../js/mloading/jquery.mloading.css"> 
		<script src="../js/mloading/jquery.mloading.js"></script>
		<script type="text/javascript" src="../js/ztree/js/ztree.js"></script>
		<script type='text/javascript' src=../js/expertToExcel.js></script>
		<script>
            var arr=new Array();
            arr=${_Categories};
            var zNodes=arr;
            $(function(){
                jQuery.ajaxSetup({async:false});
                $("#mainContent").css("height",document.documentElement.clientHeight);
                $("#leftMenu").css("height",document.documentElement.clientHeight-15);
                $("#rightContent").css("height",document.documentElement.clientHeight);
                $("#rightMainContainer").css("height",document.documentElement.clientHeight-75);
                //$(".widget").button();

                $(element).mLoading({
                    text:"",//加载文字，默认值：加载中...
                    icon:"",//加载图标，默认值：一个小型的base64的gif图片
                    html:false,//设置加载内容是否是html格式，默认值是false
                    content:"",//忽略icon和text的值，直接在加载框中显示此值
                    mask:true//是否显示遮罩效果，默认显示
                });

                $(element).mLoading("show");//显示loading组件
                $(element).mLoading("hide");//隐藏loading组件


                //标题的图标是集成bootstrap 的图标  更改 请参考bootstrap的字体图标替换自己想要的图标
                /**
                 var arr = new Array();
                 arr = ${_Categories};
                 $("#").ProTree({
					arr: arr,
					simIcon: "fa fa-file-text-o",//单个标题字体图标 不传默认glyphicon-file
					mouIconOpen: "fa fa-caret-down",//含多个标题的打开字体图标  不传默认glyphicon-folder-open
					mouIconClose:"fa fa-caret-right",//含多个标题的关闭的字体图标  不传默认glyphicon-folder-close
					callback: function(id,name) {
						//alert("你选择的id是" + id + "，名字是" + name);
						//$("#rightMainContainer").mLoading();
						loadFiles(id);
						$("#docTitleContainer").html(name);
						//alert(name);
						if(name.indexOf('隐患排查与治理') > -1){
							//alert(1);
							$("#rightMainTop").css("display","");
							loadReport();
						}else{
							//alert(2);
							$("#rightMainTop").css("display","none");
						}
					}

				})


                 if('${_CategoryName}'.indexOf('隐患排查与治理') > -1){
					//alert(1);
					$("#rightMainTop").css("display","");
					loadReport();
				}else{
					//alert(2);
					$("#rightMainTop").css("display","none");
				}
                 */

            });
            function hideLoad(id){
                $("#"+id).mLoading("hide");//隐藏loading组件
            }
            //加载文件列表
            function loadFiles(id) {
                showLoad("rightMainContainer");
                $("#rightMainTop").css("display", "none");
                $.getJSON("/security/document.htm?method=getDocuments&categoryId=" + id, function (data) {
                    var content = "";
                    $.each(data._Documents, function (i, n) {
                        content += "<tr><td align='left' class='fileTd'><span title='点击下载查看' style=\"cursor:pointer;\" onclick=\"downFile('" + n.documentId + "');\">" + n.documentTitle + "</span></td>";
                        content += "<td align='center' class='fileTd'>";
                        if(${_GLOBAL_USER.docDelete}){
                        content += "<a href='javascript:;' onclick=\"delDocument('" + n.documentId + "');\">删除</a>";
                        }
                        content += "</td></tr>";
                    });

                    $('#fileBody').html(content);
                    $("#categoryId").val(id);
                });

                hideLoad("rightMainContainer");
            }
            //新增
            function addFile(){
                window.open("/security/document.htm?method=addDocument&categoryId="+$("#categoryId").val(),"_blank");
            }

            //下载查看
            function downFile(id){
                window.open("/security/document.htm?method=downDocument&documentId="+id,"_blank");
            }

            //删除
            function delDocument(id){
                //alert(id);
                if(confirm("确认删除？")){
                    $.ajax({
                        url: '/security/document.htm?method=deleteDocument&documentId='+id,
                        type:'POST',
                        async: false,
                        cache: false,
                        dataType: "json",
                        success: function(data){
                            if(data.success){
                                alert("删除成功");

                                loadFiles($("#categoryId").val());
                            }else{
                                alert("删除失败");
                            }
                        }
                    });
                }
            }

            function showLoad(id){
                $("#"+id).mLoading({
                    text:"正在努力加载，请稍候~",//加载文字，默认值：加载中...
                    icon:"/images/waiting.gif",//加载图标，默认值：一个小型的base64的gif图片
                    html:false,//设置加载内容是否是html格式，默认值是false
                    content:"",//忽略icon和text的值，直接在加载框中显示此值
                    mask:true//是否显示遮罩效果，默认显示
                });
            }
            //加载报表
            function loadReport(){
                showLoad("rightContent");

                $.getJSON("/reform.htm?method=reformYearReport",function(data) {
                    var content = "";
                    if(data.success){
                        $("#dataYear").html(data.thisYear);
                        $.each(data.result, function(i, n) {
                            //alert(i);
                            var mapData_1 = n;
                            //alert(mapData_1);
                            for(var key in mapData_1){
                                //alert(key);
                                var array_1 = new Array();
                                array_1 = mapData_1[key];
                                //alert(array_1);
                                for(var m=0;m<array_1.length;m++){

                                    var mapData_2 = array_1[m];
                                    //alert(mapData_2);
                                    for(var key1 in mapData_2){
                                        $("#"+key+"_"+key1).html(mapData_2[key1]);
                                    }
                                }
                            }

                        });

                    }

                });

                hideLoad("rightContent");
            }

            //查看月报表
            function viewMonthReport(month){
                var thisYear = $("#dataYear").html();
                window.open("/reform.htm?method=reformMonthReport&year="+thisYear+"&month="+month,"_blank");
            }
		</script>
	<style type="text/css">
		.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
		 .ztree li span.button.ico_docu {  
            background-position: -145px 0px;  
            margin-right: 2px;  
            vertical-align: top;  
        }
	</style>
		
		<style>
			body{
				margin: 0 auto;
				font-family:"microsoft yahei",黑体;
			}
			#mainContent{
				margin: 0 auto;
				width:100%;
				height:100%;
				overflow-x: scroll;
				border:0px solid red;	
			}
			#mainTab{
				height:100%;
				width:100%;
				
			}
			#leftMenu{
				width:100%;
				height:100%;
				border-top:1px solid gray;
				border-right:1px solid gray;
				padding-left:20px;
				padding-top:10px;
				overflow-x:hidden;
				overflow-y:scroll;
			}
			#rightContent{
				width:100%;
				height:100%;
				padding-left:20px;
				margin-left:8px;
				overflow-x: scroll;
			}
			#rightTop{
				width:100%;
				height:40px;
				padding:0;
				border:0px solid red;
			}
			#rightTopLeft{
				height:40px;
				border-top:1px solid gray;
				border-right:1px solid gray;
				text-align:center;
				font-size:16px;
				font-weight:bold;
				width:auto;
				padding-left:5px;
				padding-right:5px;
			}
			#docTitleContainer{
				line-height:45px;
				border:0px solid red;
				display:in-line;
			}
			#rightTopRight{
				height:40px;
				border-bottom:1px solid gray;
				text-align:center;
			}
			
			#rightMainTop td,#rightMainTop th{
				background:white;
			}
			
			#rightMainTop a{
				color:blue;
				outline:none;
			}
			
			#rightTab{
				width:100%;
				height:100%;
				margin: 0 auto;
			}
			
			#rightMainContainer{
				width:100%;
				border:0px solid black;
				padding-top:0px;
				overflow-x:scroll;
				overflow-y:auto;
				margin-left:5px;
			}
			
			.fileTd{
				 height:40px;
				 border-bottom:1px solid lightGray;
				 padding-left:50px;
			}
			
			.fileTd a{
				color:black;
				
				padding-right:50px;
				text-decoration:none;
			}
			
			#rightBottom{
				height:30px;
				position:fixed;
				bottom:0;
				width:100%;
				border-top:1px solid gray;
				border-left:1px solid gray;
				padding-left:15px;
				background:white;
				margin-left:0px
			}
			
			.blueFont{
				color:#144d9d;
			}
			
			.countFont{
				color:#144d9d;
				font-weight:normal;
				font-size:15px;
			}
			.widget{
				height: 21px;
            	line-height: 21px;
           	 	padding: 0 11px;
            	background: black;
            	border-radius: 3px;
            	color: #fff;
            	display: inline-block;
            	text-decoration: none;
            	font-size: 12px;
            	font-weight:600;
            	outline: none;
			}
		</style>
	</head>
	
	<body>
		<input type="hidden" name="categoryId" id="categoryId" value="${_CategoryId}" />
		<input type="hidden" name="docAdd" id="docAdd" value="${_GLOBAL_USER.docAdd}">
		<input type="hidden" name="docDelete" id="docDelete" value="${_GLOBAL_USER.docDelete}">
		<div id="mainContent">
			<table id="mainTab" border=0 cellspacing=0 cellpadding=0>
				<tr>
					<td width="260" valign="top">
							<div id="leftMenu" class="ztree"></div>
					</td>
					<td valign="top">
						<div id="rightContent">
							<table width="100%"  id="rightTab" cellspacing=0 cellpadding=0>
								<tr>
									<td width="250" nowrap height="40" valign="top">
										<div id="rightTopLeft">
											<span id="docTitleContainer">
												
											</span>
										</div>
									</td>
									<td>
										<div id="rightTopRight"></div>			
									</td>
								</tr>
								<tr>
									<td colspan=2 valign=top>
										<div id="rightMainTop" style="border:0px solid red;widht:97%;height:auto;text-align:center;display: none">
											<div>
												<table id="dataTab" border=0 width="100%" style="margin:0 auto;" cellspacing=1 cellpadding=2 bgcolor="gray">
													<tr>
														<th colspan=14>
															<span style="font-size:16px;"><span id="dataYear">${_ThisYear}</span>年隐患异常情况汇总</span>
															&nbsp;&nbsp;
															<a href="javascript:;" onclick="javascript:toExcel('dataTab','隐患异常情况汇总');">导出Excel</a>
														</th>
													</tr>
													<tr>
														<th></th>
														<th><a href="javascript:;" onclick="viewMonthReport(1);">1月</a></th>
														<th><a href="javascript:;" onclick="viewMonthReport(2);">2月</a></th>
														<th><a href="javascript:;" onclick="viewMonthReport(3);">3月</a></th>
														<th><a href="javascript:;" onclick="viewMonthReport(4);">4月</a></th>
														<th><a href="javascript:;" onclick="viewMonthReport(5);">5月</a></th>
														<th><a href="javascript:;" onclick="viewMonthReport(6);">6月</a></th>
														<th><a href="javascript:;" onclick="viewMonthReport(7);">7月</a></th>
														<th><a href="javascript:;" onclick="viewMonthReport(8);">8月</a></th>
														<th><a href="javascript:;" onclick="viewMonthReport(9);">9月</a></th>
														<th><a href="javascript:;" onclick="viewMonthReport(10);">10月</a></th>
														<th><a href="javascript:;" onclick="viewMonthReport(11);">11月</a></th>
														<th><a href="javascript:;" onclick="viewMonthReport(12);">12月</a></th>
														<th>合计</th>
													</tr>
													<tr>
														<td>市场营销部</td>
														<td class="countFont" id="11_1"></td>
														<td class="countFont" id="11_2"></td>
														<td class="countFont" id="11_3"></td>
														<td class="countFont" id="11_4"></td>
														<td class="countFont" id="11_5"></td>
														<td class="countFont" id="11_6"></td>
														<td class="countFont" id="11_7"></td>
														<td class="countFont" id="11_8"></td>
														<td class="countFont" id="11_9"></td>
														<td class="countFont" id="11_10"></td>
														<td class="countFont" id="11_11"></td>
														<td class="countFont" id="11_12"></td>
														<td class="countFont" id="11_13"></td>
													</tr>
													<tr>
														<td>人力资源部</td>
														<td class="countFont" id="12_1"></td>
														<td class="countFont" id="12_2"></td>
														<td class="countFont" id="12_3"></td>
														<td class="countFont" id="12_4"></td>
														<td class="countFont" id="12_5"></td>
														<td class="countFont" id="12_6"></td>
														<td class="countFont" id="12_7"></td>
														<td class="countFont" id="12_8"></td>
														<td class="countFont" id="12_9"></td>
														<td class="countFont" id="12_10"></td>
														<td class="countFont" id="12_11"></td>
														<td class="countFont" id="12_12"></td>
														<td class="countFont" id="12_13"></td>
													</tr>
													<tr>
														<td>整车物流部</td>
														<td class="countFont" id="13_1"></td>
														<td class="countFont" id="13_2"></td>
														<td class="countFont" id="13_3"></td>
														<td class="countFont" id="13_4"></td>
														<td class="countFont" id="13_5"></td>
														<td class="countFont" id="13_6"></td>
														<td class="countFont" id="13_7"></td>
														<td class="countFont" id="13_8"></td>
														<td class="countFont" id="13_9"></td>
														<td class="countFont" id="13_10"></td>
														<td class="countFont" id="13_11"></td>
														<td class="countFont" id="13_12"></td>
														<td class="countFont" id="13_13"></td>
													</tr>
													<tr>
														<td>质量安全部</td>
														<td class="countFont" id="14_1"></td>
														<td class="countFont" id="14_2"></td>
														<td class="countFont" id="14_3"></td>
														<td class="countFont" id="14_4"></td>
														<td class="countFont" id="14_5"></td>
														<td class="countFont" id="14_6"></td>
														<td class="countFont" id="14_7"></td>
														<td class="countFont" id="14_8"></td>
														<td class="countFont" id="14_9"></td>
														<td class="countFont" id="14_10"></td>
														<td class="countFont" id="14_11"></td>
														<td class="countFont" id="14_12"></td>
														<td class="countFont" id="14_13"></td>
													</tr>
													<tr>
														<td>码头运营部</td>
														<td class="countFont" id="15_1"></td>
														<td class="countFont" id="15_2"></td>
														<td class="countFont" id="15_3"></td>
														<td class="countFont" id="15_4"></td>
														<td class="countFont" id="15_5"></td>
														<td class="countFont" id="15_6"></td>
														<td class="countFont" id="15_7"></td>
														<td class="countFont" id="15_8"></td>
														<td class="countFont" id="15_9"></td>
														<td class="countFont" id="15_10"></td>
														<td class="countFont" id="15_11"></td>
														<td class="countFont" id="15_12"></td>
														<td class="countFont" id="15_13"></td>
													</tr>
													<tr>
														<td>党群工作部</td>
														<td class="countFont" id="16_1"></td>
														<td class="countFont" id="16_2"></td>
														<td class="countFont" id="16_3"></td>
														<td class="countFont" id="16_4"></td>
														<td class="countFont" id="16_5"></td>
														<td class="countFont" id="16_6"></td>
														<td class="countFont" id="16_7"></td>
														<td class="countFont" id="16_8"></td>
														<td class="countFont" id="16_9"></td>
														<td class="countFont" id="16_10"></td>
														<td class="countFont" id="16_11"></td>
														<td class="countFont" id="16_12"></td>
														<td class="countFont" id="16_13"></td>
													</tr>
													<tr>
														<td>总经理办公室</td>
														<td class="countFont" id="17_1"></td>
														<td class="countFont" id="17_2"></td>
														<td class="countFont" id="17_3"></td>
														<td class="countFont" id="17_4"></td>
														<td class="countFont" id="17_5"></td>
														<td class="countFont" id="17_6"></td>
														<td class="countFont" id="17_7"></td>
														<td class="countFont" id="17_8"></td>
														<td class="countFont" id="17_9"></td>
														<td class="countFont" id="17_10"></td>
														<td class="countFont" id="17_11"></td>
														<td class="countFont" id="17_12"></td>
														<td class="countFont" id="17_13"></td>
													</tr>
													<tr>
														<td>计划财务部</td>
														<td class="countFont" id="18_1"></td>
														<td class="countFont" id="18_2"></td>
														<td class="countFont" id="18_3"></td>
														<td class="countFont" id="18_4"></td>
														<td class="countFont" id="18_5"></td>
														<td class="countFont" id="18_6"></td>
														<td class="countFont" id="18_7"></td>
														<td class="countFont" id="18_8"></td>
														<td class="countFont" id="18_9"></td>
														<td class="countFont" id="18_10"></td>
														<td class="countFont" id="18_11"></td>
														<td class="countFont" id="18_12"></td>
														<td class="countFont" id="18_13"></td>
													</tr>
													<tr>
														<td>采购部</td>
														<td class="countFont" id="19_1"></td>
														<td class="countFont" id="19_2"></td>
														<td class="countFont" id="19_3"></td>
														<td class="countFont" id="19_4"></td>
														<td class="countFont" id="19_5"></td>
														<td class="countFont" id="19_6"></td>
														<td class="countFont" id="19_7"></td>
														<td class="countFont" id="19_8"></td>
														<td class="countFont" id="19_9"></td>
														<td class="countFont" id="19_10"></td>
														<td class="countFont" id="19_11"></td>
														<td class="countFont" id="19_12"></td>
														<td class="countFont" id="19_13"></td>
													</tr>
													<tr>
														<td>技术规划部</td>
														<td class="countFont" id="20_1"></td>
														<td class="countFont" id="20_2"></td>
														<td class="countFont" id="20_3"></td>
														<td class="countFont" id="20_4"></td>
														<td class="countFont" id="20_5"></td>
														<td class="countFont" id="20_6"></td>
														<td class="countFont" id="20_7"></td>
														<td class="countFont" id="20_8"></td>
														<td class="countFont" id="20_9"></td>
														<td class="countFont" id="20_10"></td>
														<td class="countFont" id="20_11"></td>
														<td class="countFont" id="20_12"></td>
														<td class="countFont" id="20_13"></td>
													</tr>
													<tr>
														<td>信息技术部</td>
														<td class="countFont" id="21_1"></td>
														<td class="countFont" id="21_2"></td>
														<td class="countFont" id="21_3"></td>
														<td class="countFont" id="21_4"></td>
														<td class="countFont" id="21_5"></td>
														<td class="countFont" id="21_6"></td>
														<td class="countFont" id="21_7"></td>
														<td class="countFont" id="21_8"></td>
														<td class="countFont" id="21_9"></td>
														<td class="countFont" id="21_10"></td>
														<td class="countFont" id="21_11"></td>
														<td class="countFont" id="21_12"></td>
														<td class="countFont" id="21_13"></td>
													</tr>
													<tr>
														<td>数据业务部</td>
														<td class="countFont" id="22_1"></td>
														<td class="countFont" id="22_2"></td>
														<td class="countFont" id="22_3"></td>
														<td class="countFont" id="22_4"></td>
														<td class="countFont" id="22_5"></td>
														<td class="countFont" id="22_6"></td>
														<td class="countFont" id="22_7"></td>
														<td class="countFont" id="22_8"></td>
														<td class="countFont" id="22_9"></td>
														<td class="countFont" id="22_10"></td>
														<td class="countFont" id="22_11"></td>
														<td class="countFont" id="22_12"></td>
														<td class="countFont" id="22_13"></td>
													</tr>
													<tr>
														<td>零部件物流部</td>
														<td class="countFont" id="23_1"></td>
														<td class="countFont" id="23_2"></td>
														<td class="countFont" id="23_3"></td>
														<td class="countFont" id="23_4"></td>
														<td class="countFont" id="23_5"></td>
														<td class="countFont" id="23_6"></td>
														<td class="countFont" id="23_7"></td>
														<td class="countFont" id="23_8"></td>
														<td class="countFont" id="23_9"></td>
														<td class="countFont" id="23_10"></td>
														<td class="countFont" id="23_11"></td>
														<td class="countFont" id="23_12"></td>
														<td class="countFont" id="23_13"></td>
													</tr>
													<tr>
														<td class="blueFont">合计</td>
														<td class="countFont" id="99_1"></td>
														<td class="countFont" id="99_2"></td>
														<td class="countFont" id="99_3"></td>
														<td class="countFont" id="99_4"></td>
														<td class="countFont" id="99_5"></td>
														<td class="countFont" id="99_6"></td>
														<td class="countFont" id="99_7"></td>
														<td class="countFont" id="99_8"></td>
														<td class="countFont" id="99_9"></td>
														<td class="countFont" id="99_10"></td>
														<td class="countFont" id="99_11"></td>
														<td class="countFont" id="99_12"></td>
														<td class="countFont" id="99_13"></td>
													</tr>
												</table>
											</div>
										</div>
										<div id="rightMainContainer">
											<table border=0 width="100%"  cellspacing=0>
												<tr>
													<th style="height:40px;border-bottom:1px solid gray;">文件名</th>
													<th style="height:40px;width:25%;border-bottom:1px solid gray;">操作</th>
												</tr>
												<tbody id="fileBody">
												
												</tbody>
												
												</table>
										</div>
										<div id="rightBottom">
											<c:if test="${_GLOBAL_USER.docAdd == 'true'}">
												<a class="widget" href="javascript:;" onclick="addFile()">新增</a>
											</c:if>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>

