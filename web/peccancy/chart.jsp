<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
		<TITLE>首页</TITLE>
		<META content="text/html; charset=utf-8" http-equiv=Content-Type>
		<SCRIPT type=text/javascript src="../js/jquery-1.9.1.js"></SCRIPT>
		<link href="/css/all.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../js/echarts/echarts.min.js"></script>
		<script type="text/javascript" src="../js/echarts/macarons.js"></script>
		
		<style>
			html { overflow-x:hidden; }
		</style>
		<script type="text/javascript">

			//jQuery.noConflict(); 
			jQuery(function(){
				reportTitleClick('ct0','0');
				monthCountClick("");
			});
			
			var chartThemeName = 'macarons';
	//alert($(_peccancyCategory));
		</script>
		<style>
			.clickTitle{
				font-size:14px;
			}
			a{
				text-decoration: none;
			}
			.clickTitle a,.clickTitle a:visited{
				color:black;
				font-size:12px;
				outline:none;
			}
			.clickTitle a:hover,.clickTitle a:active{
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
		<!-------------------- 违章统计 --------------------->
				<tr>
					<td rowspan="2" style="width:60%;background:white;" valign="top">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								
								<a class="preA" href="javascript:;" onclick="yearCostClick(0);"><<前一年</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<span style="font-size:18px;font-weight:bold;"><span class="nowYear_cost"></span>违章统计</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="preA" href="javascript:;" onclick="yearCostClick(1);">后一年>></a>
							</div>
							  <div id="reportTitleDiv" class="clickTitle">
								  <a href="javascript:;" id="ct0" onclick="reportTitleClick('ct0','0');">全部</a>/
								<c:forEach items="${_allCategory}" var="category" varStatus="index">
									<a href="javascript:;" id="ct${index.index+1}" onclick="reportTitleClick('ct${index.index+1}','${category.categoryId}');">${category.categoryName }</a> /
								</c:forEach>
							</div>
						</div><br/>
						<div id="report-1" style="width:600px;height:550px;">
						
						</div>
						<script type="text/javascript">
							 // 基于准备好的dom，初始化echarts实例
					        var chart1 = echarts.init(document.getElementById('report-1'));

					        option1 = {
				        	    title : {
				        	        subtext: '单位：次',
				        	        left:20
				        	    },
				        	      tooltip : {
				        	        trigger: 'axis'
				        	    },
				        	    xAxis : [{
				        	            type : 'category',
				        	            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
				        	        }],
				        	    yAxis : [{
				        	            type : 'value'
				        	        }],
				        	    series : [{
				        	    				name:'违章次数',
						        	            type:'bar',
						        	            data:[],
						        	     }]
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
					        	reportTitleClick("ct1","1");
					        	costPie();
					        }

					        //点击事件
					        function reportTitleClick(id,peccancyType){
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
								var countArray = new Array();
								jQuery.ajaxSetup({async:false});
								//获取数据
								jQuery.getJSON("/peccancy.htm?method=statistics&currentYear="+curYear_cost+"&peccancyType="+peccancyType,function(data) {
									countArray = data._CountList;
									curYear_cost = data._Year;
									jQuery(".nowYear_cost").html(data._Year);

								});
								var newdata = [
								        	{
								         		data: countArray,
								         		itemStyle:{
				                                    normal:{
				                                        color:'#336ca0'
				                                    }
				                                }
								         	},
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
							
								<a class="preA" href="javascript:;" onclick="monthCountClick(0);"><<前月</a>
								&nbsp;&nbsp;
								<span style="font-size:18px;font-weight:bold;"><span class="nowYearMonth"></span>各违章类型占比</span>
								&nbsp;&nbsp;
								<a class="preA" href="javascript:;" onclick="monthCountClick(1);">后月>></a>
							</div>
						</div>
						<div id="report-5" style="width:auto;height:300px;">
						
						</div>
						
						<script type="text/javascript">  
					        
							 // 基于准备好的dom，初始化echarts实例
					        var chart5 = echarts.init(document.getElementById('report-5'),chartThemeName);
	
					        option5 = {
				        		title: {
				        	        subtext: '0',
				        	        subtextStyle:{
				        	        	fontSize: 15,
				        	        	fontWeight: 'bold',
				        	        	color: 'blue',
				        	        	align: 'right'
				        	        },
				        	        x: '23%',
				        	        y: '45%'
				        	   },
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
				        	    },
				        	    series : [
				        	        {
				        	            //name: '访问来源',
				        	            type: 'pie',
				        	            radius: ['30%', '55%'],
				        	            center: ['30%', '55%'],
				        	            label: {
				        	            	normal: {
				        	            		position: 'outside',
				        	            		formatter: "{d}%"
				        	            	},
				        	                emphasis: {
				        	                    show: true,
				        	                    textStyle: {
				        	                        fontSize: '15',
				        	                        fontWeight: 'bold'
				        	                    }
				        	                }
				        	            },
				        	            labelLine: {
				        	            	normal: {
				        	            		show: true
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
							</div>
						</div>
						<div id="report-6" style="width:auto;height:250px;">
						
						</div>
						
						<script>
							// 基于准备好的dom，初始化echarts实例
					        var chart6 = echarts.init(document.getElementById('report-6'),chartThemeName);
	
					        option6 = {
					        		title: {
					        	        subtext: '0',
					        	        subtextStyle:{
					        	        	fontSize: 15,
					        	        	fontWeight: 'bold',
					        	        	color: 'red',
					        	        	align: 'right'
					        	        },
					        	        x: '23%',
					        	        y: '45%',
					        	   },
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
				        	    },
				        	    series : [
				        	        {
				        	            type: 'pie',
				        	            radius: ['30%', '55%'],
				        	            center: ['30%', '55%'],
				        	            label: {
				        	            	normal: {
				        	            		position: 'outside',
				        	            		formatter: "{d}%"
				        	            	},
				        	                emphasis: {
				        	                    show: true,
				        	                    textStyle: {
				        	                        fontSize: '15',
				        	                        fontWeight: 'bold'
				        	                    }
				        	                }
				        	            },
				        	            labelLine: {
				        	            	normal: {
				        	            		show: true
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
					        	//chart4.showLoading();
								chart5.showLoading();
								chart6.showLoading();
								
								//改变char
								var pTypeNameArray = new Array();
								var pTypeCountArray = new Array();
								var CountArray_dpt = new Array();
								var DptNameArray = new Array();
								var allPeccancyCount = 0;
								
								jQuery.ajaxSetup({async:false});
								//获取数据
								jQuery.getJSON("/peccancy.htm?method=getPie&tag="+tag+"&dataDate="+curDataDate,function(data) {
									//alert(data);
									allPeccancyCount = data._AllCount;
									curDataDate = data._NowDate;
									//类型的
									pTypeNameArray=data._typeNames;
									pTypeCountArray=data._Count_type;
									//部门的
									CountArray_dpt = data._Count_dpt;//_RightCount_dpt;
									DptNameArray = data._DptNames;
									
									jQuery(".nowYearMonth").html(data._NowYearMonth);
								});

								var series5Array = new Array();
								for(var i=0;i<pTypeNameArray.length;i++){
									var tmpStr = {name:pTypeNameArray[i],value:pTypeCountArray[i]};
									series5Array.push(tmpStr);
								}
								
								chart5.setOption({
										title:{subtext:'总数：'+allPeccancyCount},
										legend: {
						        	        data: pTypeNameArray
						        	    },	
						        	    series : [
													{
													    data : series5Array
													}
						        	              ]
								});
								chart5.hideLoading();
								//chart6
								var series6Array = new Array();
								for(var i=0;i<DptNameArray.length;i++){
									var tmpStr = {name:DptNameArray[i],value:CountArray_dpt[i]};
									series6Array.push(tmpStr);
								}
								chart6.setOption({
										title:{subtext:'总数：'+allPeccancyCount},
										legend: {
						        	        data: DptNameArray
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
				
	</table>
</body>
</html>