<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/inc/taglibs.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <TITLE>首页</TITLE>
    <META content="text/html; charset=utf-8" http-equiv=Content-Type>
    <SCRIPT type=text/javascript src="js/jquery-1.9.1.js"></SCRIPT>
    <link href="/css/all.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" src="/js/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="/js/echarts/macarons.js"></script>

    <style>
        html {
            overflow-x: hidden;
        }

    </style>
    <script type="text/javascript">

        jQuery(function () {
            departmentClick('dt1', '0');
            pxTypeClick("pt0", "0")
            monthCountClick("");
            monthPxClick("");
            yearPxClick("");
        });

        var chartThemeName = 'macarons';

    </script>
    <style>
        .clickTitle {
            font-size: 14px;
        }

        a {
            text-decoration: none;
        }

        .clickTitle a, .clickTitle a:visited {
            color: black;
            font-size: 12px;
            outline: none;
        }

        .clickTitle a:hover, .clickTitle a:active {
        / / font-size: 16 px;
            color: #1470ED;
            outline: none;
        }

        .preA {
            color: blue;
            outline: none;
            text-decoration: none;
        }
    </style>
</HEAD>
<BODY style="background-color:#f5f5f5;">
<div id="mainContainer" style="width:100%;height:100%;overflow-x:hidden;border:0px solid red;">
    <table border=0 width="100%" style="height:100%;background:#f5f5f5;" cellspacing="12" cellpadding="10">
        <!------------------- 隐患排除汇总统计 ---------------------->
        <tr>
            <td style="width:60%;background:white;" rowspan="2">
                <div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
                    <div style="border:0px solid blue;text-align:center;">
                        <a class="preA" href="javascript:;" onclick="yearCheckClick(0);"><
                            <前一年
                        </a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <span style="font-size:18px;font-weight:bold;"><span class="nowCheckYear"
                                                                             id="nowCheckYear"></span> 年隐患排查汇总统计</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a class="preA" href="javascript:;" onclick="yearCheckClick(1);">后一年>></a>
                    </div>
                    <br/>
                    <div id="departmentDiv" class="clickTitle">
                        <a href="javascript:;" id="dt1" onclick="departmentClick('dt1','0');">全部</a>/
                        <c:forEach items="${_Departments}" var="department" varStatus="index">
                            <a href="javascript:;" id="dt${index.index+2}"
                               onclick="departmentClick('dt${index.index+2}','${department.departmentId}');">${department.departmentName }</a> /
                        </c:forEach>
                    </div>
                </div>
                <br/>
                <div id="report-4" style="width:650px;height:400px;">

                </div>

                <script>
                    // 基于准备好的dom，初始化echarts实例
                    var chart4 = echarts.init(document.getElementById('report-4'));
                    option4 = {
                        title: {
                            subtext: '',
                            left: 20
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['隐患项', '整改项'],
                            right: 70
                        },
                        toolbox: {
                            show: false
                        },
                        calculable: true,
                        xAxis: [
                            {
                                type: 'category',
                                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [

                            {
                                name: '隐患项',
                                type: 'bar',
                                data: [],
                                itemStyle: {
                                    normal: {
                                        color: '#336ca0'
                                    }
                                },
                            },
                            {
                                name: '整改项',
                                type: 'bar',
                                data: [],
                                itemStyle: {
                                    normal: {
                                        color: '#22B14C'
                                    }
                                },
                            }
                        ]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    chart4.setOption(option4);
                    var curYear_check = ${_ThisYear};

                    function yearCheckClick(tag) {
                        if (tag == "0") {
                            curYear_check = curYear_check - 1;
                        } else if (tag == "1") {
                            curYear_check = curYear_check + 1;
                        }
                        departmentClick('dt1', '0');
                    }

                    //点击事件
                    function departmentClick(id, departmentId) {
                        chart4.showLoading();
                        jQuery.each(jQuery("#departmentDiv a"), function (i, n) {
                            //alert(jQuery(this).attr("id"));
                            if (jQuery(this).attr("id") == id) {
                                jQuery(this).css("color", "#1470ED");

                            } else {
                                jQuery(this).css("color", "black");
                            }
                        });
                        jQuery("#monthDept").html(jQuery("#" + id).text()).css("color", "#1470ED");
                        //改变char
                        var reformArray = new Array();
                        var errorArray = new Array();
                        jQuery.ajaxSetup({async: false});
                        //获取数据
                        jQuery.getJSON("/reform.htm?method=getYearReforms&currentYear=" + curYear_check + "&departmentId=" + departmentId, function (data) {
                            reformArray = data._ReformCountList;
                            errorArray = data._ErrorCountList;
                            curYear_cost = data._Year;
                            jQuery(".nowCheckYear").html(curYear_cost);

                        });
                        var newdata = [
                            {
                                data: errorArray
                            },
                            {
                                data: reformArray
                            }
                        ];
                        chart4.setOption({//载入新数据
                            series: newdata
                        });
                        monthCountClick("");
                        chart4.hideLoading();
                    }

                </script>
            </td>

            <td style="background:white;">
                <div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
                    <div style="border:0px solid blue;text-align:center;">

                        <a class="preA" href="javascript:;" onclick="monthCountClick(0);"><
                            <前月
                        </a>
                        &nbsp;&nbsp;
                        <span style="font-size:18px;font-weight:bold;"><span class="nowYearMonth"></span>各部门异常项占比</span>
                        &nbsp;&nbsp;
                        <a class="preA" href="javascript:;" onclick="monthCountClick(1);">后月>></a>
                    </div>
                </div>
                <div id="report-5" style="width:auto;height:220px;"></div>
                <script type="text/javascript">
                    // 基于准备好的dom，初始化echarts实例
                    var chart5 = echarts.init(document.getElementById('report-5'), chartThemeName);
                    option5 = {
                        title: {
                            text: '',
                            textStyle: {
                                fontSize: 15,
                                fontWeight: 'bold',
                                color: 'blue',
                                align: 'center'
                            },
                            subtext: '0',
                            subtextStyle: {
                                fontSize: 15,
                                fontWeight: 'bold',
                                color: 'blue',
                                align: 'center'
                            },
                            x: '24%',
                            y: '41%'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient: 'vertical',
                            right: '0',
                            center: 'center',
                            top: '20',
                            itemHeight: 10,
                            itemWidth: 12,
                        },
                        series: [
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
                    chart5.setOption(option5);
                </script>
            </td>
        </tr>
        <tr style="height:260px;">
            <td style="background:white;">
                <div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
                    <div style="border:0px solid blue;text-align:center;">
                        <a class="preA" href="javascript:;" onclick="monthCountClick(0);"><
                            <前月
                        </a>
                        &nbsp;&nbsp;
                        <span style="font-size:18px;font-weight:bold;"><span class="nowYearMonth"></span><span
                                id="monthDept"></span>隐患整改率</span>
                        &nbsp;&nbsp;
                        <a class="preA" href="javascript:;" onclick="monthCountClick(1);">后月>></a>
                    </div>
                </div>
                <div id="report-6" style="width:auto;height:260px;">
                </div>

                <script>
                    // 基于准备好的dom，初始化echarts实例
                    var chart6 = echarts.init(document.getElementById('report-6'), chartThemeName);

                    option6 = {
                        tooltip: {
                            formatter: "{a}<br/>{b}:{c}%"
                        },
                        toolbox: {
                            feature: {
                                saveAsImage: {}
                            }
                        },
                        series: [
                            {
                                name: '',
                                type: 'gauge',
                                detail: {formatter: '{value}%'},
                                data: []
                            }
                        ]
                    };


                    // 使用刚指定的配置项和数据显示图表。
                    chart6.setOption(option6);
                    var curDataDate = "";

                    //点击事件
                    function monthCountClick(tag) {
                        //chart4.showLoading();
                        chart5.showLoading();
                        chart6.showLoading();

                        //改变char
                        var dptCountArray = new Array();
                        var dptNamesArray = new Array();
                        var rateArray = new Array();
                        var allCheckCount = 0;
                        var allRate = 0;

                        jQuery.ajaxSetup({async: false});
                        //获取数据
                        jQuery.getJSON("/reform.htm?method=getMonthCount&tag=" + tag + "&dataDate=" + curDataDate, function (data) {
                            allCheckCount = data._AllCount;
                            curDataDate = data._NowDate;
                            //部门的
                            dptCountArray = data._DptCount;//_RightCount_dpt;
                            dptNamesArray = data._DptNames;
                            rateArray = data._ReformRates;
                            allRate = data._AllRate;
                            jQuery("#nowYearMonth").html(data._NowYearMonth);
                            jQuery(".nowYearMonth").html(data._NowYearMonth);
                        });

                        var series5Array = new Array();
                        for (var i = 0; i < dptNamesArray.length; i++) {
                            var tmpStr = {name: dptNamesArray[i], value: dptCountArray[i]};
                            series5Array.push(tmpStr);
                        }
                        chart5.setOption({
                            title: {subtext: '隐患：' + allCheckCount + ''},
                            legend: {
                                data: dptNamesArray
                            },
                            series: [
                                {
                                    data: series5Array
                                }
                            ]
                        });
                        chart5.hideLoading();

                        //chart6
                        var series6 = 0;
                        var dptName = $("#monthDept").text();

                        for (var i = 0; i < dptNamesArray.length; i++) {
                            if (dptName == dptNamesArray[i]) {
                                series6 = rateArray[i];
                            } else if (dptName == "全部") {
                                series6 = allRate;
                            }
                        }
                        chart6.setOption({
                            series: [
                                {
                                    data: [{value: series6, name: '整改率'}]
                                }
                            ]
                        });
                        chart6.hideLoading();
                    }
                </script>
            </td>
        </tr>


        <!------------------- 培训统计 ---------------------->
        <tr>
            <td style="width:60%;background:white;" rowspan="2">
                <div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
                    <div style="border:0px solid blue;text-align:center;">
                        <a class="preA" href="javascript:;" onclick="yearPxClick(0);"><
                            <前一年
                        </a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <span style="font-size:18px;font-weight:bold;"><span id="nowYear_px"></span> 年培训统计情况</span>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a class="preA" href="javascript:;" onclick="yearPxClick(1);">后一年>></a>
                    </div>

                    <div id="pxTypeDiv" class="clickTitle">
                        <a href="javascript:;" id="pt0" onclick="pxTypeClick('pt0','0');">全部</a> /
                        <c:forEach items="${_TrainCategories}" var="tCategory" varStatus="index">
                            <a href="javascript:;" id="pt${index.index+1}"
                               onclick="pxTypeClick('pt${index.index+1}','${tCategory.categoryId}');">${tCategory.categoryName }</a> /
                        </c:forEach></div>
                </div>
                <br/>
                <div id="report-7" style="width:650px;height:400px;">

                </div>

                <script>
                    // 基于准备好的dom，初始化echarts实例
                    var chart7 = echarts.init(document.getElementById('report-7'));

                    // 指定图表的配置项和数据
                    option7 = {
                        title: {
                            text: '',
                            subtext: ''
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['培训次数']
                        },
                        toolbox: {
                            show: true,
                        },
                        calculable: true,
                        xAxis: [
                            {
                                type: 'category',
                                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: '培训次数',
                                type: 'bar',
                                data: [],
                            }
                        ]
                    };


                    // 使用刚指定的配置项和数据显示图表。
                    chart7.setOption(option7);

                    var curDataDate_px = ${_ThisYear};

                    function yearPxClick(tag) {
                        if (tag == "0") {
                            curDataDate_px = curDataDate_px - 1;
                        } else if (tag == "1") {
                            curDataDate_px = curDataDate_px + 1;
                        }

                        pxTypeClick("pt0", "0")
                    }

                    //点击事件
                    function pxTypeClick(id, categoryId) {
                        chart7.showLoading();
                        jQuery.each(jQuery("#pxTypeDiv a"), function (i, n) {
                            if (jQuery(this).attr("id") == id) {
                                jQuery(this).css("color", "#1470ED");

                            } else {
                                jQuery(this).css("color", "black");
                            }
                        });

                        //改变char
                        var pxCountArray = new Array();
                        jQuery.ajaxSetup({async: false})
                        jQuery.getJSON("/train.htm?method=getYearTrainData&currentYear=" + curDataDate_px + "&categoryId=" + categoryId, function (data) {
                            pxCountArray = data._PxCountList;
                            curDataDate_px = data._Year;
                            jQuery("#nowYear_px").html(curDataDate_px);

                        });
                        var newdata = [
                            {
                                data: pxCountArray
                            }
                        ];
                        chart7.setOption({//载入新数据
                            series: newdata
                        });
                        chart7.hideLoading();
                    }

                </script>
            </td>

            <td style="background:white;">
                <div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
                    <div style="border:0px solid blue;text-align:center;">
                        <a class="preA" href="javascript:;" onclick="monthPxClick(0);"><
                            <前月
                        </a>
                        &nbsp;
                        <span style="font-size:18px;font-weight:bold;"><span
                                class="nowPxYearMonth"></span>各科目培训占比</span>
                        &nbsp;
                        <a class="preA" href="javascript:;" onclick="monthPxClick(1);">后月>></a>
                    </div>
                </div>
                <div id="report-8" style="width:auto;height:330px;">

                </div>

                <script type="text/javascript">

                    // 基于准备好的dom，初始化echarts实例
                    var chart8 = echarts.init(document.getElementById('report-8'), chartThemeName);

                    option8 = {
                        title: {
                            text: '',
                            textStyle: {
                                fontSize: 15,
                                fontWeight: 'bold',
                                color: 'blue',
                                //lingHeight: 25,
                                align: 'center'
                            },
                            subtext: '0',
                            subtextStyle: {
                                fontSize: 15,
                                fontWeight: 'bold',
                                color: 'blue',
                                //lingHeight: 75,
                                align: 'center'
                            },
                            x: '23%',
                            y: '48%'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient: 'vertical',
                            right: '40',
                            center: 'center',
                            top: '40',
                            itemHeight: 10,
                            itemWidth: 12,
                        },
                        series: [
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
                    chart8.setOption(option8);
                </script>
            </td>
        </tr>


        <script>
            var curPxDataDate = "";
            //点击事件
            function monthPxClick(tag) {
                chart8.showLoading();
                var pxCountArray_category = new Array();
                var categoryNameArray = new Array();
                var allPxCount = 0;
                jQuery.ajaxSetup({async: false});
                //获取数据
                jQuery.getJSON("/train.htm?method=getMonthTrainData&tag=" + tag + "&dataDate=" + curPxDataDate, function (data) {
                    allPxCount = data._AllPxCount;
                    curPxDataDate = data._NowDate;
                    //类别的
                    pxCountArray_category = data._PxCountList_category;//_RightCount_dpt;
                    categoryNameArray = data._CategoryNames;
                    jQuery(".nowPxYearMonth").html(data._NowYearMonth);
                });


                var series8Array = new Array();
                for (var i = 0; i < categoryNameArray.length; i++) {
                    var tmpStr = {name: categoryNameArray[i], value: pxCountArray_category[i]};
                    series8Array.push(tmpStr);
                }

                chart8.setOption({
                    title: {subtext: '总次数：' + allPxCount + ''},
                    legend: {
                        data: categoryNameArray
                    },
                    series: [
                        {
                            data: series8Array
                        }
                    ]
                });
                chart8.hideLoading();
            }
        </script>
    </table>
</div>
</BODY>
</HTML>
