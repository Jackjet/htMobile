<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/inc/taglibs.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <TITLE>年度统计</TITLE>
    <META content="text/html; charset=utf-8" http-equiv=Content-Type>
    <SCRIPT type=text/javascript src="../js/jquery-1.9.1.js"></SCRIPT>
    <link href="/css/all.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" src="../js/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="../js/echarts/macarons.js"></script>

    <style>
        html {
            overflow-x: hidden;
        }
    </style>
    <script type="text/javascript">
        jQuery(function () {
            PxClick('');
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
<div style="border:0px solid red;width:100%;height:auto;text-align:center;padding-top:10px;vertical-align:top;">
    <div style="border:0px solid blue;text-align:center;margin-top: 30px">
        <a class="preA" href="javascript:;" onclick="PxClick(0);"><<前年
        </a>
        &nbsp;
        <span style="font-size:18px;font-weight:bold;"><span
                class="nowPxYear"></span>各科目培训占比</span>
        &nbsp;
        <a class="preA" href="javascript:;" onclick="PxClick(1);">后年 >></a>
    </div>
</div>
<div id="report-8" style="width:auto;height:600px; vertical-align: center;text-align: center;margin-left: 200px"></div>
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
                align: 'center'
            },
            x: '27%',
            y: '48%'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            right: '340',
            center: 'center',
            top: '180',
            itemHeight: 30,
            itemWidth: 30,
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
                            fontSize: '20',
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

<script>
    var curPxDataDate = "";

    //点击事件
    function PxClick(tag) {
        chart8.showLoading();
        var pxCountArray_category = new Array();
        var categoryNameArray = new Array();
        var allPxCount = 0;
        jQuery.ajaxSetup({async: false});
        //获取数据
        jQuery.getJSON("/train.htm?method=getYearTrain&tag=" + tag + "&dataDate=" + curPxDataDate, function (data) {
            allPxCount = data._AllPxCount;
            curPxDataDate = data._NowDate;
            //类别的
            pxCountArray_category = data._PxCountList_category;//_RightCount_dpt;
            categoryNameArray = data._CategoryNames;
            jQuery(".nowPxYear").html(data._NowYear);
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

</div>

</body>
</html>