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
		
		<script type="text/javascript" src="/js/ichart/ichart.1.2.min.js"></script>
		
		<style>
			html { overflow-x:hidden; }
		
		</style>
		<script type="text/javascript">

			//jQuery.noConflict(); 
			jQuery(function(){
				//alert($.get('reportTitleDiv'));
				//firstChart();
				var data = [
		         	/*{
		         		name : '预算',
		         		value:[0,0,0,0,0,0,0,0,0,0,0,0,0],
		         		color:'#336CA0'
		         	},
		         	{
		         		name : '实际',
		         		value:[0,0,0,0,0,0,0,0,0,0,0,0,0],
		         		color:'#B05FFD'
		         	}*/
		         	{
		         		name : '预算',
		         		value:[45,52,54,74,90,84,45,52,54,74,90,84],
		         		color:'#336CA0'
		         	},
		         	{
		         		name : '实际',
		         		value:[60,80,105,125,108,120,60,80,105,125,108,130],
		         		color:'#B05FFD'
		         	}
		         ];
				var firstChart = new iChart.ColumnMulti2D({
					id:'chart1',
					render : 'report-1',
					data: data,
					labels:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
					title : '',
					subtitle : '',
					footnote : '单位 ：万',
					width : document.documentElement.clientWidth * 0.58,
					height : 350,
					background_color : '#ffffff',
					legend:{
						enable:true,
						background_color : null,
						showType:'follow',
						style:'textAlign:left;padding:4px 5px;cursor:pointer;backgroundColor:rgba(239,239,239,.85);fontSize:12px;color:black;',
						border : {
							enable : false
						}
					},
					animation : true,//开启过渡动画
					animation_duration:800,//800ms完成动画
					coordinate:{
						background_color : '#fff',
						scale:[{
							 position:'left',	
							 start_scale:0,
							 end_scale:140,
							 scale_space:20
						}],
						width:document.documentElement.clientWidth * 0.48,
						height:260
					}
				});
				firstChart.draw();
				
				secondChart();
				thirdChart();
				fourthChart();
				reportTitleClick('rt1','1');
			});
			

			function reportTitleClick(id,costType){
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
				jQuery.getJSON("/security/cost.htm?method=getDataByType&costType="+costType,function(data) {
					ysArray = data._YsList;
					zcArray = data._ZcList;
					
				});
				//alert(ysArray.length);
				var newdata = [
				        	{
				         		name : '预算',
				         		value: ysArray,
				         		color:'#336CA0'
				         	},
				         	{
				         		name : '实际',
				         		value: zcArray,
				         		color:'#B05FFD'
				         	}
					  	 ];
				alert(newdata);
				//var chart = $.get('firstChart');//根据ID获取图表对象
				alert(firstChart.getTitle());
				firstChart.load(newdata);//载入新数据
				//firstChart(createFirstSource(ysArray,zcArray));
			}
			
			//7种类型的数据源
			function createFirstSource(ysArray,zcArray){
				return [
		        	{
		         		name : '预算',
		         		value:ysArray,
		         		color:'#336CA0'
		         	},
		         	{
		         		name : '实际',
		         		value:zcArray,
		         		color:'#B05FFD'
		         	}
			  	 ]

			}
			
			//第一个柱形图
			function firstChart(){
				//alert(data[0]);
				var data = [
		         	/*{
		         		name : '预算',
		         		value:[0,0,0,0,0,0,0,0,0,0,0,0,0],
		         		color:'#336CA0'
		         	},
		         	{
		         		name : '实际',
		         		value:[0,0,0,0,0,0,0,0,0,0,0,0,0],
		         		color:'#B05FFD'
		         	}*/
		         	{
		         		name : '预算',
		         		value:[45,52,54,74,90,84,45,52,54,74,90,84],
		         		color:'#336CA0'
		         	},
		         	{
		         		name : '实际',
		         		value:[60,80,105,125,108,120,60,80,105,125,108,130],
		         		color:'#B05FFD'
		         	}
		         ];
				var firstChart = new iChart.ColumnMulti2D({
					id:'chart1',
					render : 'report-1',
					data: data,
					labels:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
					title : '',
					subtitle : '',
					footnote : '单位 ：万',
					width : document.documentElement.clientWidth * 0.58,
					height : 350,
					background_color : '#ffffff',
					legend:{
						enable:true,
						background_color : null,
						showType:'follow',
						style:'textAlign:left;padding:4px 5px;cursor:pointer;backgroundColor:rgba(239,239,239,.85);fontSize:12px;color:black;',
						border : {
							enable : false
						}
					},
					animation : true,//开启过渡动画
					animation_duration:800,//800ms完成动画
					coordinate:{
						background_color : '#fff',
						scale:[{
							 position:'left',	
							 start_scale:0,
							 end_scale:140,
							 scale_space:20
						}],
						width:document.documentElement.clientWidth * 0.48,
						height:260
					}
				});
				firstChart.draw();
			}
			
			//第二个，饼图
			function secondChart(){
				var data = [
				        	{name : '安全设施设备安装维护',value : 4,color:'#326a9e'},
				        	{name : '应急救援',value : 26,color:'#3b77b0'},
				        	{name : '隐患整改',value : 1,color:'#3770a7'},
				        	{name : '劳动防护',value : 20,color:'#4689c8'},
				        	{name : '安全教育和培训',value : 1,color:'#6f8bb2'},
				        	{name : '安全设施设备检测',value : 29,color:'#A5b8d5'},
				        	{name : '其他直接相关支出',value : 19,color:'#b0bbcc'}
			        	];
	        	
				var secondChart = new iChart.Pie2D({
					render : 'report-2',
					data: data,
					title : {
						text : '',
						height:40,
						fontsize : 30,
						color : '#282828'
					},
					footnote : {
						/*text : 'ichartjs.com',
						color : '#486c8f',
						fontsize : 12,
						padding : '0 38'*/
					},
					sub_option : {
						mini_label_threshold_angle : 40,//迷你label的阀值,单位:角度
						mini_label:{//迷你label配置项
							fontsize:10,
							fontweight:600,
							color : '#ffffff'
						},
						label : {
							background_color:null,
							sign:false,//设置禁用label的小图标
							padding:'0 4',
							border:{
								enable:false,
								color:'#666666'
							},
							fontsize:11,
							fontweight:600,
							color : '#4572a7'
						},
						border : {
							width : 2,
							color : '#ffffff'
						},
						listeners:{
							parseText:function(d, t){
								return d.get('value')+"%";//自定义label文本
							}
						} 
					},
					legend:{
						enable:true,
						padding:0,
						offsetx:-10,
						offsety:10,
						color:'black',
						fontsize:12,//文本大小
						sign_size:15,//小图标大小
						line_height:14,//设置行高
						sign_space:10,//小图标与文本间距
						border:false,
						align:'left',
						background_color : null//透明背景
					}, 
					animation:true,
					shadow : false,
					shadow_blur : 6,
					shadow_color : '#aaaaaa',
					shadow_offsetx : 0,
					shadow_offsety : 0,
					background_color:null,
					align:'left',//右对齐
					offsetx:20,//设置向x轴负方向偏移位置60px
					offsety:10,
					offset_angle:-90,//逆时针偏移120度
					width : document.documentElement.clientWidth * 0.35,
					height : 180,
					radius:150
				});
				//利用自定义组件构造右侧说明文本
				/*chart.plugin(new iChart.Custom({
						drawFn:function(){
							//在右侧的位置，渲染说明文字
							chart.target.textAlign('start')
							.textBaseline('top')
							.textFont('600 20px Verdana')
							.fillText('Market Fragmentation:\nTop Mobile\nOperating Systems',120,80,false,'#be5985',false,24)
							.textFont('600 12px Verdana')
							.fillText('Source:ComScore,2012',120,160,false,'#999999');
						}
				}));*/
				
				secondChart.draw();

			}
			
			//第三个，饼图
			function thirdChart(){
				var data = [
				        	{name : '安全设施设备安装维护',value : 4,color:'#326a9e'},
				        	{name : '应急救援',value : 26,color:'#3b77b0'},
				        	{name : '隐患整改',value : 1,color:'#3770a7'},
				        	{name : '劳动防护',value : 20,color:'#4689c8'},
				        	{name : '安全教育和培训',value : 1,color:'#6f8bb2'},
				        	{name : '安全设施设备检测',value : 29,color:'#A5b8d5'},
				        	{name : '其他直接相关支出',value : 19,color:'#b0bbcc'}
			        	];
	        	
				var thirdChart = new iChart.Pie2D({
					render : 'report-3',
					data: data,
					title : {
						text : '',
						height:40,
						fontsize : 30,
						color : '#282828'
					},
					footnote : {
						/*text : 'ichartjs.com',
						color : '#486c8f',
						fontsize : 12,
						padding : '0 38'*/
					},
					sub_option : {
						mini_label_threshold_angle : 40,//迷你label的阀值,单位:角度
						mini_label:{//迷你label配置项
							fontsize:10,
							fontweight:600,
							color : '#ffffff'
						},
						label : {
							background_color:null,
							sign:false,//设置禁用label的小图标
							padding:'0 4',
							border:{
								enable:false,
								color:'#666666'
							},
							fontsize:11,
							fontweight:600,
							color : '#4572a7'
						},
						border : {
							width : 2,
							color : '#ffffff'
						},
						listeners:{
							parseText:function(d, t){
								return d.get('value')+"%";//自定义label文本
							}
						} 
					},
					legend:{
						enable:true,
						padding:0,
						offsetx:-10,
						offsety:10,
						color:'black',
						fontsize:12,//文本大小
						sign_size:15,//小图标大小
						line_height:14,//设置行高
						sign_space:10,//小图标与文本间距
						border:false,
						align:'left',
						background_color : null//透明背景
					}, 
					animation:true,
					shadow : false,
					shadow_blur : 6,
					shadow_color : '#aaaaaa',
					shadow_offsetx : 0,
					shadow_offsety : 0,
					background_color:null,
					align:'left',//右对齐
					offsetx:20,//设置向x轴负方向偏移位置60px
					offsety:10,
					offset_angle:-90,//逆时针偏移120度
					width : document.documentElement.clientWidth * 0.35,
					height : 180,
					radius:150
				});
				//利用自定义组件构造右侧说明文本
				/*chart.plugin(new iChart.Custom({
						drawFn:function(){
							//在右侧的位置，渲染说明文字
							chart.target.textAlign('start')
							.textBaseline('top')
							.textFont('600 20px Verdana')
							.fillText('Market Fragmentation:\nTop Mobile\nOperating Systems',120,80,false,'#be5985',false,24)
							.textFont('600 12px Verdana')
							.fillText('Source:ComScore,2012',120,160,false,'#999999');
						}
				}));*/
				
				thirdChart.draw();

			}
			
			//第四个，柱形图
			function fourthChart(){
				var uniColor = '#336ca0';
				var data = [
				        	{name : '人机混合\n作业区域',value : 27,color:uniColor},
				        	{name : '人、车、货\n混合作业\n场所临时\n占道作业\n安全措施',value : 8,color:uniColor},
				        	{name : '外来人员上\n下船舶安\n全（靠泊船次）',value : 5,color:uniColor},
				        	{name : '港内交通\n（车辆或\n机械）安全管理',value : 13,color:uniColor},
				        	{name : '驳船具备\n开工安全\n信息确认',value : 9,color:uniColor},
				        	{name : '复岗（新）\n司机实习\n不到位',value : 3,color:uniColor},
				        	{name : '管理人员\n违章（开\n车超速、无\n证驾驶、登\n高不戴安\n全带、临\n水不穿救\n生衣等）',value : 19,color:uniColor},
				        	{name : '集卡码头\n调头管理',value : 23,color:uniColor},
				        	{name : '铲车液压\n系统专用\n支撑装置',value : 16,color:uniColor},
				        	{name : '外发包修\n理管理',value : 24,color:uniColor},
				        	{name : '窨井盖、\n排水（电\n缆）沟盖\n板缺失、损坏',value : 14,color:uniColor},
				        	{name : '客运综合\n楼商业活动\n安全措施',value : 30,color:uniColor}

			        	];
	        	
				new iChart.Column2D({
					render : 'report-4',
					data: data,
					title : '',
					showpercent:false,
					decimalsnum:0,
					width : document.documentElement.clientWidth * 0.96,
					height : 500,
					animation : true,//开启过渡动画
					animation_duration:800,//800ms完成动画
					column_width : 40,
					offsety:-20,
					label : {
						//rotate:-15,
						color : 'black'
					},
					tip:{
						enable:true,
						listeners:{
							 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
							parseText:function(tip,name,value,text,i){
								
								return name;
							}
						}
					}/*,

					coordinate:{
						background_color:'#fefefe',
						scale:[{
							 position:'left',	
							 start_scale:0,
							 end_scale:40,
							 scale_space:8,
							 listeners:{
								parseText:function(t,x,y){
									return {text:t}
								}
							}
						}]
					}*/
				}).draw();

			}

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
			
		</style>
	</HEAD>
	<BODY style="background-color:#f5f5f5;">
		<div id="mainContainer" style="width:100%;height:100%;overflow-x:hidden;border:0px solid red;">
			<table border=0 width="100%" style="height:100%;background:#f5f5f5;" cellspacing="12" cellpadding="10">
				<tr style="height:200px;">
					<td rowspan="2" style="width:60%;background:white;" valign="top">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<span style="font-size:18px;font-weight:bold;">2017年度安全费用</span>
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
						<div id="report-1">
						
						</div>
					</td>
					<td style="background:white;">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<span style="font-size:18px;font-weight:bold;">2017年度安全费用预算占比</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="/security/cost.htm?method=editYs" target="_blank">
									<img alt="" src="/images/editPen.png" width="20" height="18" align="absmiddle" border="0">
								</a>
							</div>
						</div>
						<div id="report-2">
						
						</div>
					</td>
				</tr>
				<tr style="height:200px;">
					<td style="background:white;">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<span style="font-size:18px;font-weight:bold;">2017年度安全费用实际支出占比</span>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="/security/cost.htm?method=editZc" target="_blank">
									<img alt="" src="/images/editPen.png" width="20" height="18" align="absmiddle" border="0">
								</a>
							</div>
						</div>
						<div id="report-3">
						
						</div>
					</td>
				</tr>
				<tr style="height:300px;">
					<td style="background:white;" colspan="2">
						<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
							<div style="border:0px solid blue;text-align:center;">
								<span style="font-size:18px;font-weight:bold;">12月危险节点安全检查汇总</span>
							</div>
						</div><br/>
						<div id="report-4">
						
						</div>
					</td>
				</tr>
			</table>			
		</div>
	</BODY>
</HTML>
