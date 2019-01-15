<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
		<TITLE>首页</TITLE>
		<META content="text/html; charset=utf-8" http-equiv=Content-Type>
		<SCRIPT type=text/javascript src="js/jquery-1.9.1.js"></SCRIPT>
		<link href="/css/all.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="/js/echarts/echarts.min.js"></script>
		<script type="text/javascript" src="/js/echarts/macarons.js"></script>
		
		<style>
			html { overflow-x:hidden; }
		
		</style>
		<script type="text/javascript">
			//jQuery.noConflict(); 
			jQuery(function(){
				reportTitleClick('rt1','1');
				costPie();
				monthCountClick("");
				monthPxClick("");
			});
			
			var chartThemeName = 'macarons';

		</script>
		<style>
			#reportTitleDiv{
				font-size:14px;
			}
			a{
				text-decoration: none;
			}
			#reportTitleDiv a,#reportTitleDiv a:visited{
				color:black;
				font-size:12px;
				outline:none;
			}
			#reportTitleDiv a:hover,#reportTitleDiv a:active{
				//font-size:16px;
				color:#1470ED;
				outline:none;
			}
			
			.preA{
				color:blue;
				outline:none;
				text-decoration:none;
			}
		</style>
	</HEAD>
	<BODY style="background-color:#f5f5f5;">
		<div id="mainContainer" style="width:100%;height:100%;overflow-x:hidden;border:0px solid red;">
			<table border=0 width="100%" style="height:100%;background:#f5f5f5;" cellspacing="12" cellpadding="10">
				
				<!------------------- 检查统计 ---------------------->
				<tr >
					<td style="width:60%;background:white;" rowspan="2">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<a class="preA" href="javascript:;" onclick="monthCountClick(0);"><<前一月</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<span style="font-size:18px;font-weight:bold;"><span id="nowYearMonth"></span> 危险节点安全检查汇总</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="preA" href="javascript:;" onclick="monthCountClick(1);">后一月>></a>
							</div>
						</div><br/>
						<div id="report-4" style="width:650px;height:350px;">
						
						</div>
						
						<script>
							 // 基于准备好的dom，初始化echarts实例
					        var chart4 = echarts.init(document.getElementById('report-4'));
	
					        // 指定图表的配置项和数据
					        /*var option4 = {
					            title: {
					                //text: 'ECharts 入门示例'
					            },
					            tooltip: {},
					            legend: {
					               // data:['销量']
					            },
					            xAxis: {
					                data: ['人机混合作业区域','人、车、货混合作业场所临时占道作业安全措施','外来人员上下船舶安全（靠泊船次）','港内交通（车辆或机械）安全管理','驳船具备开工安全信息确认','复岗（新）司机实习不到位',
						        	'管理人员违章（开车超速、无证驾驶、登高不戴安全带、临水不穿救生衣等）',
						        	'集卡码头调头管理',
						        	'铲车液压系统专用支撑装置',
						        	'外发包修理管理',
						        	'窨井盖、排水（电缆）沟盖板缺失、损坏',
						        	'客运综合楼商业活动安全措施']
					            },
					            yAxis: {},
					            series: [{
					                //name: '销量',
					                type: 'bar',
					                data: [27, 8, 5, 13, 9, 3,19,23,16,24,14,30],
	                                barWidth: 30,
					         		itemStyle:{
	                                    normal:{
	                                        color:'#336ca0'
	                                    }
	                                }
					            }]
					        };*/
					        option4 = {
				        	    title : {
				        	        subtext: '',
				        	        left:20
				        	    },
				        	    tooltip : {
				        	        trigger: 'axis'
				        	    },
				        	    legend: {
				        	        data:['正常项','异常项'],
				        	        right:70
				        	    },
				        	    toolbox: {
				        	        show : false
				        	    },
				        	    calculable : true,
				        	    xAxis : [
				        	        {
				        	            type : 'category',
				        	            data : ['1','2','3','4','5','6','7','8','9','10','11','12','13']
				        	        }
				        	    ],
				        	    yAxis : [
				        	        {
				        	            type : 'value'
				        	        }
				        	    ],
				        	    series : [
				        	        {
				        	            name:'正常项',
				        	            type:'bar',
				        	            data:[1,2,3,4,5,6,7,8,9,10,11,12,13],
						         		itemStyle:{
		                                    normal:{
		                                        color:'#336ca0'
		                                    }
		                                },
				        	            markPoint : {
				        	                data : [
				        	                    //{type : 'max', name: '最大值'},
				        	                    //{type : 'min', name: '最小值'}
				        	                ]
				        	            }
				        	        },
				        	        {
				        	            name:'异常项',
				        	            type:'bar',
				        	            data:[1,2,3,4,5,6,7,8,9,10,11,12,13],
						         		itemStyle:{
		                                    normal:{
		                                        color:'#b05ffd'
		                                    }
		                                },
				        	            markPoint : {
				        	                data : [
				        	                    //{name : '年最高', value : 182.2, xAxis: 7, yAxis: 183},
				        	                   // {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
				        	                ]
				        	            }
				        	        }
				        	    ]
				        	};

		
	
					        // 使用刚指定的配置项和数据显示图表。
					        chart4.setOption(option4);

							
					      	
						</script>
					</td>
				
					<td style="background:white;">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<span style="font-size:18px;font-weight:bold;"><span class="nowYearMonth"></span>各部门检查正常项占比</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<%--<a href="/security/cost.htm?method=editYs" target="_blank">
									<img alt="" src="/images/editPen.png" width="20" height="18" align="absmiddle" border="0">
								</a>
							--%></div>
						</div>
						<div id="report-5" style="width:auto;height:180px;">
						
						</div>
						
						<script type="text/javascript">  
					        
							 // 基于准备好的dom，初始化echarts实例
					        var chart5 = echarts.init(document.getElementById('report-5'),chartThemeName);
	
					        option5 = {
				        	    tooltip : {
				        	        trigger: 'item',
				        	        formatter: "{b} : {c} ({d}%)"
				        	    },
				        	    legend: {
				        	        orient: 'vertical',
				        	        right: '0',
				        	        center:'center',
				        	        top:'20',
				        	        itemHeight:10,
				        	        itemWidth:12,
				        	       // data: ['安全设施设备安装维护','应急救援','隐患整改','劳动防护','安全教育和培训','安全设施设备检测','其他直接相关支出']
				        	    },
				        	    series : [
				        	        {
				        	            //name: '访问来源',
				        	            type: 'pie',
				        	            radius : '75%',
				        	            center: ['30%', '50%'],
				        	            //data : ysNewData,
				        	            label: {
				        	            	normal: {
				        	            		position: 'inner',
				        	            		formatter: "{d}%"
				        	            	}
				        	            },
				        	            labelLine: {
				        	            	normal: {
				        	            		show: false
				        	            	}
				        	            },
				        	      
				        	            itemStyle: {
				        	                emphasis: {
				        	                    shadowBlur: 10,
				        	                    shadowOffsetX: 0,
				        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				        	                }
				        	            }
				        	        }
				        	    ]
				        	};

					        // 使用刚指定的配置项和数据显示图表。
					        chart5.setOption(option5);
					        
					        
					        
					        
					    </script> 
					</td>
				</tr>
				<tr style="height:200px;">
					<td style="background:white;">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<span style="font-size:18px;font-weight:bold;"><span class="nowYearMonth"></span>各部门检查异常项占比</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<%--<a href="/security/cost.htm?method=editZc" target="_blank">
									<img alt="" src="/images/editPen.png" width="20" height="18" align="absmiddle" border="0">
								</a>
							--%></div>
						</div>
						<div id="report-6" style="width:auto;height:180px;">
						
						</div>
						
						<script>
							// 基于准备好的dom，初始化echarts实例
					        var chart6 = echarts.init(document.getElementById('report-6'),chartThemeName);
	
					        option6 = {
				        	    tooltip : {
				        	        trigger: 'item',
				        	        formatter: "{b} : {c} ({d}%)"
				        	    },
				        	    legend: {
				        	        orient: 'vertical',
				        	        right: '0',
				        	        center:'center',
				        	        top:'20',
				        	        itemHeight:10,
				        	        itemWidth:12,
				        	        //data: ['安全设施设备安装维护','应急救援','隐患整改','劳动防护','安全教育和培训','安全设施设备检测','其他直接相关支出']
				        	    },
				        	    series : [
				        	        {
				        	            //name: '访问来源',
				        	            type: 'pie',
				        	            radius : '75%',
				        	            center: ['30%', '50%'],
				        	           // data : zcNewData,
				        	            //data:[
				        	               //,label:{normal:{show:false},emphasis:{show:false}},labelLine:{normal:{show:false},emphasis:{show:false}}
				        	                //{name : '安全设施设备安装维护',value : 4},
								        	//{name : '应急救援',value : 26},
								        	//{name : '隐患整改',value : 1},
								        	//{name : '劳动防护',value : 20},
								        	//{name : '安全教育和培训',value : 1},
								        	//{name : '安全设施设备检测',value : 29},
								        	//{name : '其他直接相关支出',value : 19}
				        	            //],
				        	            label: {
				        	            	normal: {
				        	            		position: 'inner',
				        	            		formatter: "{d}%"
				        	            	}
				        	            },
				        	            labelLine: {
				        	            	normal: {
				        	            		show: false
				        	            	}
				        	            },
				        	      
				        	            itemStyle: {
				        	                emphasis: {
				        	                    shadowBlur: 10,
				        	                    shadowOffsetX: 0,
				        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				        	                }
				        	            }
				        	        }
				        	    ]
				        	};
	
					        // 使用刚指定的配置项和数据显示图表。
					        chart6.setOption(option6);
					        
					        
					        
					        
					        

							var curDataDate = "";
					      //点击事件
					        function monthCountClick(tag){
					        	chart4.showLoading();
								
								//改变char
								var rightCountArray = new Array();
								var errorCountArray = new Array();
								var bigArray = new Array();
								
								var rCountArray_dpt = new Array();
								var eCountArray_dpt = new Array();
								var rDptNameArray = new Array();
								var eDptNameArray = new Array();
								
								jQuery.ajaxSetup({async:false});
								//获取数据
								jQuery.getJSON("/patrol.htm?method=getMonthCount&tag="+tag+"&dataDate="+curDataDate,function(data) {
									//alert(data);
									rightCountArray = data._RightCount;
									errorCountArray = data._ErrorCount;
									curDataDate = data._NowDate;
									bigArray = data._BigCategoryNames;
									
									//部门的
									rCountArray_dpt = data._RightCount_dpt;
									eCountArray_dpt = data._ErrorCount_dpt;
									rDptNameArray = data._RightDptNames;
									eDptNameArray = data._ErrorDptNames;
									
									jQuery("#nowYearMonth").html(data._NowYearMonth);
									jQuery(".nowYearMonth").html(data._NowYearMonth);
								});
							//alert(bigArray);
								var newdata = [
								        	{
								         		name : '正常项',
								         		data: rightCountArray,
								         		itemStyle:{
				                                    normal:{
				                                        color:'#336ca0'
				                                    }
				                                }
								         	},
								         	{
								         		name : '异常项',
								         		data: errorCountArray,
								         		itemStyle:{
				                                    normal:{
				                                        color:'#b05ffd'
				                                    }
				                                }
								         	}
									  	 ];
								var newxAxis = [
							        	        {
							        	            type : 'category',
							        	            data : bigArray
							        	        }
							        	    ];

								//alert();
								chart4.setOption({//载入新数据
							        series: newdata,
							        xAxis: newxAxis
							    });
								chart4.hideLoading();
								
								
								
								//chart5
								//var newlegenddata5 = dptNameArray;
								//var newseriesdata5 = rCountArray_dpt;
								chart5.showLoading();
								var series5Array = new Array();
								for(var i=0;i<rDptNameArray.length;i++){
									var tmpStr = {name:rDptNameArray[i],value:rCountArray_dpt[i]};
									//if(i<dptNameArray.length-1){
									//	tmpStr += ",";
									//}
									//alert(eval(tmpStr));
									//series5Array[i] = eval("("+tmpStr+")");
									series5Array.push(tmpStr);
								}
								
								chart5.setOption({
										legend: {
						        	        data: rDptNameArray
						        	    },	
						        	    series : [
													{
													    data : series5Array
													}
						        	              ]
								});
								chart5.hideLoading();
								
								
								//chart6
								chart6.showLoading();
								var series6Array = new Array();
								for(var i=0;i<eDptNameArray.length;i++){
									var tmpStr = {name:eDptNameArray[i],value:eCountArray_dpt[i]};
									series6Array.push(tmpStr);
								}
								
								chart6.setOption({
										legend: {
						        	        data: eDptNameArray
						        	    },	
						        	    series : [
													{
													    data : series6Array
													}
						        	              ]
								});
								chart6.hideLoading();
							}
						</script>
					</td>
				</tr>
				
				
				
				
				
				
				
				<!------------------- 培训统计 ---------------------->
				<tr >
					<td style="width:60%;background:white;" rowspan="2">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<a class="preA" href="javascript:;" onclick="monthPxClick(0);"><<前一月</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<span style="font-size:18px;font-weight:bold;"><span id="nowYearMonth_px"></span> 培训演练统计情况</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="preA" href="javascript:;" onclick="monthPxClick(1);">后一月>></a>
							</div>
						</div><br/>
						<div id="report-7" style="width:650px;height:350px;">
						
						</div>
						
						<script>
							 // 基于准备好的dom，初始化echarts实例
					        var chart7 = echarts.init(document.getElementById('report-7'));
	
					        // 指定图表的配置项和数据
					        option7 = {
				        	    title : {
				        	        subtext: '',
				        	        left:20
				        	    },
				        	    tooltip : {
				        	        trigger: 'axis'
				        	    },
				        	    legend: {
				        	        data:['培训次数','演练次数'],
				        	        right:70
				        	    },
				        	    toolbox: {
				        	        show : false
				        	    },
				        	    calculable : true,
				        	    xAxis : [
				        	        {
				        	            type : 'category',
				        	            data : ['1','2','3','4','5','6','7','8','9','10','11','12','13']
				        	        }
				        	    ],
				        	    yAxis : [
				        	        {
				        	            type : 'value'
				        	        }
				        	    ],
				        	    series : [
				        	        {
				        	            name:'培训次数',
				        	            type:'bar',
				        	            data:[1,2,3,4,5,6,7,8,9,10,11,12,13],
						         		itemStyle:{
		                                    normal:{
		                                        color:'#336ca0'
		                                    }
		                                },
				        	            markPoint : {
				        	                data : [
				        	                    //{type : 'max', name: '最大值'},
				        	                    //{type : 'min', name: '最小值'}
				        	                ]
				        	            }
				        	        },
				        	        {
				        	            name:'演练次数',
				        	            type:'bar',
				        	            data:[1,2,3,4,5,6,7,8,9,10,11,12,13],
						         		itemStyle:{
		                                    normal:{
		                                        color:'#b05ffd'
		                                    }
		                                },
				        	            markPoint : {
				        	                data : [
				        	                    //{name : '年最高', value : 182.2, xAxis: 7, yAxis: 183},
				        	                   // {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
				        	                ]
				        	            }
				        	        }
				        	    ]
				        	};

		
	
					        // 使用刚指定的配置项和数据显示图表。
					        chart7.setOption(option7);

							
					      	
						</script>
					</td>
				
					<td style="background:white;">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<span style="font-size:18px;font-weight:bold;"><span class="nowYearMonth_px"></span>各部门培训次数占比</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="/train/count.htm?method=editPx" target="_blank">
									<img alt="" src="/images/editPen.png" width="20" height="18" align="absmiddle" border="0">
								</a>
							</div>
						</div>
						<div id="report-8" style="width:auto;height:180px;">
						
						</div>
						
						<script type="text/javascript">  
					        
							 // 基于准备好的dom，初始化echarts实例
					        var chart8 = echarts.init(document.getElementById('report-8'),chartThemeName);
	
					        option8 = {
				        	    tooltip : {
				        	        trigger: 'item',
				        	        formatter: "{b} : {c} ({d}%)"
				        	    },
				        	    legend: {
				        	        orient: 'vertical',
				        	        right: '0',
				        	        center:'center',
				        	        top:'20',
				        	        itemHeight:10,
				        	        itemWidth:12,
				        	       // data: ['安全设施设备安装维护','应急救援','隐患整改','劳动防护','安全教育和培训','安全设施设备检测','其他直接相关支出']
				        	    },
				        	    series : [
				        	        {
				        	            //name: '访问来源',
				        	            type: 'pie',
				        	            radius : '75%',
				        	            center: ['30%', '50%'],
				        	            //data : ysNewData,
				        	            label: {
				        	            	normal: {
				        	            		position: 'inner',
				        	            		formatter: "{d}%"
				        	            	}
				        	            },
				        	            labelLine: {
				        	            	normal: {
				        	            		show: false
				        	            	}
				        	            },
				        	      
				        	            itemStyle: {
				        	                emphasis: {
				        	                    shadowBlur: 10,
				        	                    shadowOffsetX: 0,
				        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				        	                }
				        	            }
				        	        }
				        	    ]
				        	};

					        // 使用刚指定的配置项和数据显示图表。
					        chart8.setOption(option8);
					        
					        
					        
					        
					    </script> 
					</td>
				</tr>
				<tr style="height:200px;">
					<td style="background:white;">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<span style="font-size:18px;font-weight:bold;"><span class="nowYearMonth_px"></span>各部门演练次数占比</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="/train/count.htm?method=editYl" target="_blank">
									<img alt="" src="/images/editPen.png" width="20" height="18" align="absmiddle" border="0">
								</a>
							</div>
						</div>
						<div id="report-9" style="width:auto;height:180px;">
						
						</div>
						
						<script>
							// 基于准备好的dom，初始化echarts实例
					        var chart9 = echarts.init(document.getElementById('report-9'),chartThemeName);
	
					        option9 = {
				        	    tooltip : {
				        	        trigger: 'item',
				        	        formatter: "{b} : {c} ({d}%)"
				        	    },
				        	    legend: {
				        	        orient: 'vertical',
				        	        right: '0',
				        	        center:'center',
				        	        top:'20',
				        	        itemHeight:10,
				        	        itemWidth:12,
				        	        //data: ['安全设施设备安装维护','应急救援','隐患整改','劳动防护','安全教育和培训','安全设施设备检测','其他直接相关支出']
				        	    },
				        	    series : [
				        	        {
				        	            //name: '访问来源',
				        	            type: 'pie',
				        	            radius : '75%',
				        	            center: ['30%', '50%'],
				        	           // data : zcNewData,
				        	            //data:[
				        	               //,label:{normal:{show:false},emphasis:{show:false}},labelLine:{normal:{show:false},emphasis:{show:false}}
				        	                //{name : '安全设施设备安装维护',value : 4},
								        	//{name : '应急救援',value : 26},
								        	//{name : '隐患整改',value : 1},
								        	//{name : '劳动防护',value : 20},
								        	//{name : '安全教育和培训',value : 1},
								        	//{name : '安全设施设备检测',value : 29},
								        	//{name : '其他直接相关支出',value : 19}
				        	            //],
				        	            label: {
				        	            	normal: {
				        	            		position: 'inner',
				        	            		formatter: "{d}%"
				        	            	}
				        	            },
				        	            labelLine: {
				        	            	normal: {
				        	            		show: false
				        	            	}
				        	            },
				        	      
				        	            itemStyle: {
				        	                emphasis: {
				        	                    shadowBlur: 10,
				        	                    shadowOffsetX: 0,
				        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				        	                }
				        	            }
				        	        }
				        	    ]
				        	};
	
					        // 使用刚指定的配置项和数据显示图表。
					        chart9.setOption(option9);
					        
					        
					        
					        
					        

							var curDataDate_px = "";
					      //点击事件
					        function monthPxClick(tag){
					        	chart7.showLoading();
								
								//改变char
								var pxCountArray = new Array();
								var ylCountArray = new Array();
								var dptNameArray = new Array();
								
								var pxCountArray_dpt = new Array();
								var ylCountArray_dpt = new Array();
								var pxDptNameArray = new Array();
								var ylDptNameArray = new Array();
								
								jQuery.ajaxSetup({async:false});
								//获取数据
								jQuery.getJSON("/train/count.htm?method=getTrainData&tag="+tag+"&dataDate="+curDataDate_px,function(data) {
									//alert(data);
									pxCountArray = data._PxCount;
									ylCountArray = data._YlCount;
									curDataDate_px = data._NowDate;
									dptNameArray = data._DptNames;
									
									//部门的
									pxCountArray_dpt = data._PxCount_dpt;
									ylCountArray_dpt = data._YlCount_dpt;
									pxDptNameArray = data._PxDptNames;
									ylDptNameArray = data._YlDptNames;
									
									jQuery("#nowYearMonth_px").html(data._NowYearMonth);
									jQuery(".nowYearMonth_px").html(data._NowYearMonth);
								});
							//alert(bigArray);
								var newdata = [
								        	{
								         		name : '培训次数',
								         		data: pxCountArray,
								         		itemStyle:{
				                                    normal:{
				                                        color:'#336ca0'
				                                    }
				                                }
								         	},
								         	{
								         		name : '演练次数',
								         		data: ylCountArray,
								         		itemStyle:{
				                                    normal:{
				                                        color:'#b05ffd'
				                                    }
				                                }
								         	}
									  	 ];
								var newxAxis = [
							        	        {
							        	            type : 'category',
							        	            data : dptNameArray
							        	        }
							        	    ];

								//alert();
								chart7.setOption({//载入新数据
							        series: newdata,
							        xAxis: newxAxis
							    });
								chart7.hideLoading();
								
								
								
								//chart8
								chart8.showLoading();
								var series8Array = new Array();
								for(var i=0;i<pxDptNameArray.length;i++){
									var tmpStr = {name:pxDptNameArray[i],value:pxCountArray_dpt[i]};
									series8Array.push(tmpStr);
								}
								
								chart8.setOption({
										legend: {
						        	        data: pxDptNameArray
						        	    },	
						        	    series : [
													{
													    data : series8Array
													}
						        	              ]
								});
								chart8.hideLoading();
								
								
								//chart9
								chart9.showLoading();
								var series9Array = new Array();
								for(var i=0;i<ylDptNameArray.length;i++){
									var tmpStr = {name:ylDptNameArray[i],value:ylCountArray_dpt[i]};
									series9Array.push(tmpStr);
								}
								
								chart9.setOption({
										legend: {
						        	        data: ylDptNameArray
						        	    },	
						        	    series : [
													{
													    data : series9Array
													}
						        	              ]
								});
								chart9.hideLoading();
							}
						</script>
					</td>
				</tr>
				
				
				
				
				
				
				
				
				
				
				
				<!-------------------- 费用统计 --------------------->
				<tr style="height:200px;">
					<td rowspan="2" style="width:60%;background:white;" valign="top">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								
								<a class="preA" href="javascript:;" onclick="yearCostClick(0);"><<前一年</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<span style="font-size:18px;font-weight:bold;"><span class="nowYear_cost"></span>年度安全费用</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="preA" href="javascript:;" onclick="yearCostClick(1);">后一年>></a>
							</div>
							<div id="reportTitleDiv">
								<a href="javascript:;" id="rt1" onclick="reportTitleClick('rt1','1');">安全设施设备安装维护</a> / 
								<a href="javascript:;" id="rt2" onclick="reportTitleClick('rt2','2');">应急救援</a> /
								<a href="javascript:;" id="rt3" onclick="reportTitleClick('rt3','3');">隐患整改</a> /
								<a href="javascript:;" id="rt4" onclick="reportTitleClick('rt4','4');">劳动防护</a> /
								<a href="javascript:;" id="rt5" onclick="reportTitleClick('rt5','5');">安全教育和培训</a> /
								<a href="javascript:;" id="rt6" onclick="reportTitleClick('rt6','6');">安全设施设备检测</a> /
								<a href="javascript:;" id="rt7" onclick="reportTitleClick('rt7','7');">其他直接相关支出</a>
							</div>
						</div><br/>
						<div id="report-1" style="width:650px;height:350px;">
						
						</div>
						<script type="text/javascript">  
							 // 基于准备好的dom，初始化echarts实例
					        var chart1 = echarts.init(document.getElementById('report-1'));
	
					        option1 = {
				        	    title : {
				        	        subtext: '单位：万',
				        	        left:20
				        	    },
				        	    tooltip : {
				        	        trigger: 'axis'
				        	    },
				        	    legend: {
				        	        data:['预算','实际'],
				        	        right:30
				        	    },
				        	    toolbox: {
				        	        show : false
				        	    },
				        	    calculable : true,
				        	    xAxis : [
				        	        {
				        	            type : 'category',
				        	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
				        	        }
				        	    ],
				        	    yAxis : [
				        	        {
				        	            type : 'value'
				        	        }
				        	    ],
				        	    series : [
				        	        {
				        	            name:'预算',
				        	            type:'bar',
				        	            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
				        	            markPoint : {
				        	                data : [
				        	                    //{type : 'max', name: '最大值'},
				        	                    //{type : 'min', name: '最小值'}
				        	                ]
				        	            }
				        	        },
				        	        {
				        	            name:'实际',
				        	            type:'bar',
				        	            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
				        	            markPoint : {
				        	                data : [
				        	                    //{name : '年最高', value : 182.2, xAxis: 7, yAxis: 183},
				        	                   // {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
				        	                ]
				        	            }
				        	        }
				        	    ]
				        	};

	
					        // 使用刚指定的配置项和数据显示图表。
					        chart1.setOption(option1);
					        


					        var curYear_cost = ${_ThisYear};
					        
					        function yearCostClick(tag){
					        	if(tag == "0"){
					        		curYear_cost = curYear_cost - 1;
					        	}else if(tag == "1"){
					        		curYear_cost = curYear_cost + 1;
					        	}
					        	//alert(curYear_cost);
					        	reportTitleClick("rt1","1");
					        	costPie();
					        }
					        
					        //点击事件
					        function reportTitleClick(id,costType){
					        	chart1.showLoading();
								jQuery.each(jQuery("#reportTitleDiv a"),function(i,n){
									//alert(jQuery(this).attr("id"));
									if(jQuery(this).attr("id") == id){
										jQuery(this).css("color","#1470ED");
										
									}else{
										jQuery(this).css("color","black");
									}
								});
								
								//改变char
								var ysArray = new Array();
								var zcArray = new Array();
								jQuery.ajaxSetup({async:false});
								//获取数据
								//alert("/security/cost.htm?method=getDataByType&currentYear="+curYear_cost+"&costType="+costType);
								jQuery.getJSON("/security/cost.htm?method=getDataByType&currentYear="+curYear_cost+"&costType="+costType,function(data) {
									ysArray = data._YsList;
									zcArray = data._ZcList;
									curYear_cost = data._Year;
									//alert(data._Year);
									jQuery(".nowYear_cost").html(data._Year);
									
								});
								var newdata = [
								        	{
								         		name : '预算',
								         		data: ysArray,
								         		itemStyle:{
				                                    normal:{
				                                        color:'#336ca0'
				                                    }
				                                }
								         	},
								         	{
								         		name : '实际',
								         		data: zcArray,
								         		itemStyle:{
				                                    normal:{
				                                        color:'#b05ffd'
				                                    }
				                                }
								         	}
									  	 ];
								chart1.setOption({//载入新数据
							        series: newdata
							    });
								chart1.hideLoading();
							}
					    </script> 
					</td>
					<td style="background:white;">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<span style="font-size:18px;font-weight:bold;"><span class="nowYear_cost"></span>年度安全费用预算占比</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="/security/cost.htm?method=editYs" target="_blank">
									<img alt="" src="/images/editPen.png" width="20" height="18" align="absmiddle" border="0">
								</a>
							</div>
						</div>
						<div id="report-2" style="width:auto;height:180px;">
						
						</div>
						
						<script type="text/javascript">  
							//var titleData = ['安全设施设备安装维护','应急救援','隐患整改','劳动防护','安全教育和培训','安全设施设备检测','其他直接相关支出'];
							//var titleDataVal = ['1','2','3','4','5','6','7'];
							var chart2 = echarts.init(document.getElementById('report-2'),chartThemeName);
							
							
							
					        
					        
					        
					        
					        
					    </script> 
					</td>
				</tr>
				<tr style="height:200px;">
					<td style="background:white;">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<span style="font-size:18px;font-weight:bold;"><span class="nowYear_cost"></span>年度安全费用实际支出占比</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="/security/cost.htm?method=editZc" target="_blank">
									<img alt="" src="/images/editPen.png" width="20" height="18" align="absmiddle" border="0">
								</a>
							</div>
						</div>
						<div id="report-3" style="width:auto;height:180px;">
						
						</div>
						
						<script>
							// 基于准备好的dom，初始化echarts实例
					        var chart3 = echarts.init(document.getElementById('report-3'),chartThemeName);
	
							
					        function costPie(){
								//获取数据设置data
								var ysYearArray = new Array();
								var zcYearArray = new Array();
								jQuery.ajaxSetup({async:false});
								//获取数据
								jQuery.getJSON("/security/cost.htm?method=getYearData&currentYear="+curYear_cost,function(data) {
									ysYearArray = data._YsList;
									zcYearArray = data._ZcList;
									
								});
								
								var ysNewData = [
						        	                {name : '安全设施设备安装维护',value : ysYearArray[0]},
										        	{name : '应急救援',value :  ysYearArray[1]},
										        	{name : '隐患整改',value :  ysYearArray[2]},
										        	{name : '劳动防护',value :  ysYearArray[3]},
										        	{name : '安全教育和培训',value :  ysYearArray[4]},
										        	{name : '安全设施设备检测',value :  ysYearArray[5]},
										        	{name : '其他直接相关支出',value :  ysYearArray[6]}
						        	            ];
								var zcNewData = [
						        	                {name : '安全设施设备安装维护',value : zcYearArray[0]},
										        	{name : '应急救援',value :  zcYearArray[1]},
										        	{name : '隐患整改',value :  zcYearArray[2]},
										        	{name : '劳动防护',value :  zcYearArray[3]},
										        	{name : '安全教育和培训',value :  zcYearArray[4]},
										        	{name : '安全设施设备检测',value :  zcYearArray[5]},
										        	{name : '其他直接相关支出',value :  zcYearArray[6]}
						        	            ];
						        
								 // 基于准备好的dom，初始化echarts实例
						        
		
						        option2 = {
					        	    tooltip : {
					        	        trigger: 'item',
					        	        formatter: "{b} : {c} ({d}%)"
					        	    },
					        	    legend: {
					        	        orient: 'vertical',
					        	        right: '0',
					        	        center:'center',
					        	        top:'20',
					        	        itemHeight:10,
					        	        itemWidth:12,
					        	        data: ['安全设施设备安装维护','应急救援','隐患整改','劳动防护','安全教育和培训','安全设施设备检测','其他直接相关支出']
					        	    },
					        	    series : [
					        	        {
					        	            //name: '访问来源',
					        	            type: 'pie',
					        	            radius : '75%',
					        	            center: ['30%', '50%'],
					        	            data : ysNewData,
					        	            //data:[
					        	               //,label:{normal:{show:false},emphasis:{show:false}},labelLine:{normal:{show:false},emphasis:{show:false}}
					        	                //{name : '安全设施设备安装维护',value : 4},
									        	//{name : '应急救援',value : 26},
									        	//{name : '隐患整改',value : 1},
									        	//{name : '劳动防护',value : 20},
									        	//{name : '安全教育和培训',value : 1},
									        	//{name : '安全设施设备检测',value : 29},
									        	//{name : '其他直接相关支出',value : 19}
					        	            //],
					        	            label: {
					        	            	normal: {
					        	            		position: 'inner',
					        	            		formatter: "{d}%"
					        	            	}
					        	            },
					        	            labelLine: {
					        	            	normal: {
					        	            		show: false
					        	            	}
					        	            },
					        	      
					        	            itemStyle: {
					        	                emphasis: {
					        	                    shadowBlur: 10,
					        	                    shadowOffsetX: 0,
					        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					        	                }
					        	            }
					        	        }
					        	    ]
					        	};

						        // 使用刚指定的配置项和数据显示图表。
						        chart2.setOption(option2);
							
					        
						        
						        
						        
						        option3 = {
					        	    tooltip : {
					        	        trigger: 'item',
					        	        formatter: "{b} : {c} ({d}%)"
					        	    },
					        	    legend: {
					        	        orient: 'vertical',
					        	        right: '0',
					        	        center:'center',
					        	        top:'20',
					        	        itemHeight:10,
					        	        itemWidth:12,
					        	        data: ['安全设施设备安装维护','应急救援','隐患整改','劳动防护','安全教育和培训','安全设施设备检测','其他直接相关支出']
					        	    },
					        	    series : [
					        	        {
					        	            //name: '访问来源',
					        	            type: 'pie',
					        	            radius : '75%',
					        	            center: ['30%', '50%'],
					        	            data : zcNewData,
					        	            //data:[
					        	               //,label:{normal:{show:false},emphasis:{show:false}},labelLine:{normal:{show:false},emphasis:{show:false}}
					        	                //{name : '安全设施设备安装维护',value : 4},
									        	//{name : '应急救援',value : 26},
									        	//{name : '隐患整改',value : 1},
									        	//{name : '劳动防护',value : 20},
									        	//{name : '安全教育和培训',value : 1},
									        	//{name : '安全设施设备检测',value : 29},
									        	//{name : '其他直接相关支出',value : 19}
					        	            //],
					        	            label: {
					        	            	normal: {
					        	            		position: 'inner',
					        	            		formatter: "{d}%"
					        	            	}
					        	            },
					        	            labelLine: {
					        	            	normal: {
					        	            		show: false
					        	            	}
					        	            },
					        	      
					        	            itemStyle: {
					        	                emphasis: {
					        	                    shadowBlur: 10,
					        	                    shadowOffsetX: 0,
					        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					        	                }
					        	            }
					        	        }
					        	    ]
					        	};
						        // 使用刚指定的配置项和数据显示图表。
						        chart3.setOption(option3);
					        }
						</script>
					</td>
				</tr>
				
				
				
				
				
				
				
				
				
				
				
			</table>			
		</div>
	</BODY>
</HTML>
