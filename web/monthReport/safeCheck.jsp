<%@ include file="/inc/taglibs.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/moment.js/2.18.1/moment-with-locales.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="../js/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="../js/echarts/macarons.js"></script>
    <script>
        $(function () {
            $(".form-control").datetimepicker({format: 'YYYY-MM', locale: moment.locale('zh-cn')});
            $(".acci").click(function () {
                var msg = "您确定要删除吗？";
                if (confirm(msg) == true) {
                    $.ajax({
                        type: 'post',
                        url: '/monthReport.htm?method=deleteAccident',
                        data: {accidentId: this.value},
                        success: function (data) {
                            location.reload();
                        },
                        error: function (data) {
                            alert("后台出错");
                        }
                    })
                } else {
                    return false;
                }
            });
            $(".fo").click(function () {
                var msg = "您确定要删除吗？";
                if (confirm(msg) == true) {
                    $.ajax({
                        type: 'post',
                        url: '/monthReport.htm?method=deleteFocus',
                        data: {focusId: this.value},
                        success: function (data) {
                            location.reload();
                        },
                        error: function (data) {
                            alert("后台出错");
                        }
                    })
                } else {
                    return false;
                }
            });
        });

        function editAccident(accidentId) {
            window.open("/monthReport.htm?method=editAccident&accidentId=" + accidentId);
        }
    </script>
</head>
<body>
<div class="container">
    <div class="col-md-12 column" style="margin-top: 30px">
        <form role="form" class="form-inline" action="/monthReport.htm?method=monthList" method="post">
            <div class="form-group">
                <input class="form-control" name="dataDateStr" type="text" value="${dataDateStr}"
                       style="width: 150px;text-align: center"/>
            </div>
            <button type="submit" class="btn btn-default" style="margin-left: 30px">查询</button>
        </form>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column" style="margin-top: 30px">
            <div class="tabbable" id="tabs-402440">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="#panel-293787" data-toggle="tab"><b><span class="nowYearMonth">${nowYearMonth}</span>生产事故</b></a>
                    </li>
                    <li>
                        <a href="#panel-134989" data-toggle="tab"><b><span class="nowYearMonth">${nowYearMonth}</span>安检情况汇总</b></a>
                    </li>
                    <li>
                        <a href="#panel-334990" data-toggle="tab"><b>隐患及违章明细</b></a>
                    </li>
                    <li>
                        <a href="#panel-434991" data-toggle="tab"><b><span class="lastYearMonth">${lastYearMonth}</span>工作重点反馈</b></a>
                    </li>
                    <li>
                        <a href="#panel-534992" data-toggle="tab"><b><span class="nowYearMonth">${nowYearMonth}</span>检查重点</b></a>
                    </li>
                </ul>
                <div class="tab-content" style="margin-top: 20px">
                    <div class="tab-pane active" id="panel-293787">
                        <table class="table table-bordered"
                               style="margin-top: 10px;table-layout: fixed;word-break:break-all; word-wrap:break-all;">
                            <tr style="background-color: #1c94c4">
                                <th style="text-align: center;width: 50px">
                                    <font color="#f0f8ff">编号</font>
                                </th>
                                <th style="text-align: center;width: 100px">
                                    <font color="#f0f8ff">时间</font>
                                </th>
                                <th style="text-align: center;width: 100px">
                                    <font color="#f0f8ff">地点</font>
                                </th>
                                <th style="text-align: center;width: 100px">
                                    <font color="#f0f8ff">责任部门</font>
                                </th>
                                <th style="text-align: center">
                                    <font color="#f0f8ff">事故内容</font>
                                </th>
                                <th style="text-align: center">
                                    <font color="#f0f8ff">附件</font>
                                </th>
                                <th style="text-align: center;width: 150px">
                                    <font color="#f0f8ff">编辑</font>
                                </th>
                            </tr>
                            <c:forEach items="${accidents}" var="accident" varStatus="a">
                                <tr style="background-color: mintcream">
                                    <td style="text-align: center;vertical-align: middle">
                                            ${a.count}
                                    </td>
                                    <td style="text-align: center;vertical-align: middle">
                                            ${accident.time}
                                    </td>
                                    <td style="text-align: center;vertical-align: middle">
                                            ${accident.address}
                                    </td>
                                    <td style="text-align: center;vertical-align: middle">
                                            ${accident.departmentName}
                                    </td>
                                    <td style="vertical-align: middle">
                                            ${accident.content}
                                    </td>
                                    <td style="text-align: center;vertical-align: middle">
                                        <c:forEach items="${accident.attaches}" var="attach" varStatus="a">
                                            <a href="${attach}" target="_blank"><img style="padding-top: 10px"
                                                                                     title="点击查看大图" width="300"
                                                                                     height="225"
                                                                                     src="${attach}"></a>
                                        </c:forEach>
                                    </td>
                                    <td style="text-align: center;vertical-align: middle"><input type="button"
                                                                                                 value="编辑"
                                                                                                 class="btn btn-success"
                                                                                                 onclick="editAccident('${accident.accidentId}')">
                                        <button class="btn btn-danger acci" value="${accident.accidentId}">删除</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="tab-pane" id="panel-134989">
                        <div id="report-1" style="width:550px;height:400px;float: left">

                        </div>
                        <div id="report-2" style="width:550px;height:400px;float: left">

                        </div>
                        <script>
                            var chart1 = echarts.init(document.getElementById('report-1'));
                            option1 = {
                                title: {
                                    text: '整改统计',
                                    subtext: '次数'
                                },
                                tooltip: {
                                    trigger: 'axis'
                                },
                                legend: {
                                    data: ['上月', '本月'],
                                    left: 200
                                },
                                toolbox: {
                                    show: true,
                                    feature: {
                                        mark: {show: true},
                                        dataView: {show: true, readOnly: false},
                                        magicType: {show: true, type: ['line', 'bar']},
                                        saveAsImage: {show: true},
                                    },
                                    left: 350
                                },
                                calculable: true,
                                xAxis: [
                                    {
                                        type: 'category',
                                        data: ['整改', '码头', '整车', '零部件', '技规部', '其他'],
                                    }
                                ],
                                yAxis: [
                                    {
                                        type: 'value'
                                    }
                                ],
                                series: [
                                    {
                                        name: '上月',
                                        type: 'bar',
                                        data: [],
                                    },
                                    {
                                        name: '本月',
                                        type: 'bar',
                                        data: [],
                                    }
                                ]
                            };
                            chart1.setOption(option1);
                            var chart2 = echarts.init(document.getElementById('report-2'));
                            option2 = {
                                title: {
                                    text: '隐患统计',
                                    subtext: '次数'
                                },
                                tooltip: {
                                    trigger: 'axis'
                                },
                                legend: {
                                    data: ['上月', '本月']
                                },
                                toolbox: {
                                    show: true,
                                    feature: {
                                        mark: {show: true},
                                        dataView: {show: true, readOnly: false},
                                        magicType: {show: true, type: ['line', 'bar']},
                                        saveAsImage: {show: true}
                                    },
                                    left: 350
                                },
                                calculable: true,
                                xAxis: [
                                    {
                                        type: 'category',
                                        data: ['隐患', '码头', '整车', '零部件', '技规部', '其他']
                                    }
                                ],
                                yAxis: [
                                    {
                                        type: 'value'
                                    }
                                ],
                                series: [
                                    {
                                        name: '上月',
                                        type: 'bar',
                                        data: [],
                                    },
                                    {
                                        name: '本月',
                                        type: 'bar',
                                        data: [],
                                    }
                                ]
                            };
                            chart2.setOption(option2);
                            var plArray = ${pLast};
                            var pnArray = ${pNow};
                            var rlArray = ${rLast};
                            var rnArray = ${rNow};
                            var newdata1 = [
                                {
                                    data: plArray
                                },
                                {
                                    data: pnArray
                                }
                            ];
                            var newdata2 = [
                                {
                                    data: rlArray
                                },
                                {
                                    data: rnArray
                                }
                            ];
                            chart1.setOption({//载入新数据
                                series: newdata1
                            });
                            chart2.setOption({//载入新数据
                                series: newdata2
                            });
                        </script>
                        <table class="table table-bordered" style="margin-top: 10px">
                            <tr style="background-color: #1c94c4">
                                <td style="text-align: center">
                                    <font color="#f0f8ff">项目</font>
                                </td>
                                <td style="text-align: center">
                                    <font color="#f0f8ff"><span class="nowYearMonth">${nowYearMonth}</span></font>
                                </td>
                                <td style="text-align: center">
                                    <font color="#f0f8ff"><span class="lastYearMonth">${lastYearMonth}</span></font>
                                </td>
                                <td style="text-align: center">
                                    <font color="#f0f8ff">环比</font>
                                </td>
                            </tr>
                            <c:forEach items="${safeChecks}" var="safe" varStatus="s">

                                <tr style="background-color: mintcream;<c:if test='${s.count==1||s.count==7}'>font: bold 15px red;</c:if>" <c:if test="${s.count==1||s.count==7}">class="danger" </c:if>>
                                    <td style="text-align: center;vertical-align: middle">
                                            ${safe.projectName}
                                    </td>
                                    <td style="text-align: center;vertical-align: middle">
                                            ${safe.nowCount}
                                    </td>
                                    <td style="text-align: center;vertical-align: middle">
                                            ${safe.lastCount}
                                    </td>
                                    <td style="text-align: center;vertical-align: middle">
                                        <c:if test="${safe.ringCount>0}"><font color="red">${safe.ringCount}</font></c:if>
                                        <c:if test="${safe.ringCount==0}"><font color="orange">${safe.ringCount}</font></c:if>
                                        <c:if test="${safe.ringCount<0}"><font color="green">${safe.ringCount}</font></c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="tab-pane" id="panel-334990">
                        <div id="report-3" style="width:660px;height:500px;float: left">

                        </div>
                        <script>
                            var chart3 = echarts.init(document.getElementById('report-3'));
                            option3 = {
                                title: {
                                    text: '类型统计',
                                    subtext: '次数'
                                },
                                tooltip: {
                                    trigger: 'axis'
                                },
                                legend: {
                                    data: ['上月', '本月'],
                                    left: 200
                                },
                                toolbox: {
                                    show: true,
                                    feature: {
                                        mark: {show: true},
                                        dataView: {show: true, readOnly: false},
                                        magicType: {show: true, type: ['line', 'bar']},
                                        saveAsImage: {show: true},
                                    },
                                    left: 350
                                },
                                calculable: true,
                                xAxis: [
                                    {
                                        type: 'category',
                                        data: ['操作规范', '消防', '安保', '外来', '设备设施', '施工', '季节性工作'],
                                    }
                                ],
                                yAxis: [
                                    {
                                        type: 'value'
                                    }
                                ],
                                series: [
                                    {
                                        name: '上月',
                                        type: 'bar',
                                        data: [],
                                    },
                                    {
                                        name: '本月',
                                        type: 'bar',
                                        data: [],
                                    }
                                ]
                            };
                            chart3.setOption(option3);
                            var clArray = ${cLast};
                            var cnArray = ${cNow};
                            var newdata3 = [
                                {
                                    data: clArray
                                },
                                {
                                    data: cnArray
                                }
                            ];
                            chart3.setOption({//载入新数据
                                series: newdata3
                            });
                        </script>

                        <table class="table table-bordered" style="margin-top: 10px">
                            <tr style="background-color: #1c94c4">
                                <td style="width:15%; text-align: center;vertical-align: middle">
                                    <font color="#f0f8ff"> 类</font>
                                </td>
                                <td style="text-align: center;vertical-align: middle">
                                    <table style="width:100%;border:none">
                                        <tr style="background-color: #1c94c4">
                                            <td style="width: 20%; text-align: center;vertical-align: middle"><font
                                                    color="#f0f8ff">项</font>
                                            </td>
                                            <td style="width: 10%;text-align: center;vertical-align: middle"><font
                                                    color="#f0f8ff"><span
                                                    class="nowYearMonth">${nowYearMonth} </span></font></td>
                                            <td style="width: 10%;text-align: center;vertical-align: middle"><font
                                                    color="#f0f8ff"><span
                                                    class="lastYearMonth">${lastYearMonth}</span></font></td>
                                            <td style="width: 10%;text-align: center;vertical-align: middle"><font
                                                    color="#f0f8ff">环比</font>
                                            </td>
                                            <td style="width: 50%;text-align: center;vertical-align: middle"><font
                                                    color="#f0f8ff">突出问题</font></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <c:forEach items="${details}" var="detail">
                                <tr style="background-color: mintcream">
                                    <td style="text-align: center;vertical-align: middle">
                                            ${detail.bigName}
                                    </td>
                                    <td style="text-align: center;vertical-align: middle">
                                        <table class="table table-bordered">
                                            <c:forEach items="${detail.countVOS}" var="count">
                                                <tr style="background-color: mintcream">
                                                    <td style="width: 20%;text-align: center;vertical-align: middle">${count.categoryName}</td>
                                                    <td style="width: 10%;text-align: center;vertical-align: middle">${count.nowCount}</td>
                                                    <td style="width: 10%;text-align: center;vertical-align: middle">${count.lastCount}</td>
                                                    <td style="width: 10%;text-align: center;vertical-align: middle">
                                                        <c:if test="${count.ringCount>0}"><font color="red">${count.ringCount}</font></c:if>
                                                        <c:if test="${count.ringCount==0}"><font color="orange">${count.ringCount}</font></c:if>
                                                        <c:if test="${count.ringCount<0}"><font color="green">${count.ringCount}</font></c:if>

                                                    </td>
                                                    <td style="width: 50%;text-align: center;vertical-align: middle">${count.issueDetail}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="tab-pane" id="panel-434991">
                        <table class="table table-bordered"
                               style="word-break:break-all;word-wrap:break-all;table-layout:fixed;margin-top: 10px">
                            <thead>
                            <tr style="background-color: #1c94c4">
                                <th style="text-align: center;vertical-align: middle;width: 100px">
                                    工作内容
                                </th>
                                <th style="text-align: center;vertical-align: middle;width: 400px">
                                    发现问题
                                </th>
                                <th style="text-align: center;vertical-align: middle;width: 400px">
                                    附件
                                </th>
                                <th style="text-align: center;vertical-align: middle;width: 100px">
                                    编辑
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${feedbackVo.records}" var="record" varStatus="f">
                                <tr>
                                    <td style="text-align: center;vertical-align: middle">${record.key}</td>
                                    <td style="vertical-align: middle">${record.value}</td>
                                    <c:if test="${f.count==1}">
                                        <td style="text-align: center;vertical-align: middle"
                                            rowspan="${fn:length(feedbackVo.records)}">
                                            <c:forEach items="${feedbackVo.attaches}" var="attach" varStatus="a">
                                                <a href="${attach}" target="_blank"><img style="padding-top: 10px"
                                                                                         title="点击查看大图" width="360"
                                                                                         height="240"
                                                                                         src="${attach}"></a>
                                            </c:forEach>
                                        </td>
                                    </c:if>
                                    <c:if test="${f.count==1}">
                                        <td style="text-align: center;vertical-align: middle"
                                            rowspan="${fn:length(feedbackVo.records)}">
                                            <button class="btn btn-danger fo" value="${feedbackVo.focusId}">删除</button>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane" id="panel-534992">
                        <table class="table table-bordered" style="word-break:break-all;word-wrap:break-all;table-layout:fixed;margin-top: 10px">
                            <thead>
                            <tr style="background-color: #1c94c4">
                                <th style="text-align: center;vertical-align: middle;width: 10%">
                                    项目
                                </th>
                                <th style="text-align: center;vertical-align: middle;width: 40%">
                                    检查内容及要求
                                </th>
                                <th style="text-align: center;vertical-align: middle;width: 40%">
                                    附件
                                </th>
                                <th style="text-align: center;vertical-align: middle;width: 10%">
                                    编辑
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${checkFocusVo.records}" var="record" varStatus="r">
                                <tr>
                                    <td style="text-align: center;vertical-align: middle">${record.key}</td>
                                    <td style="vertical-align: middle">${record.value}</td>
                                    <c:if test="${r.count==1}">
                                        <td style="text-align: center;vertical-align: middle"
                                            rowspan="${fn:length(checkFocusVo.records)}">
                                            <c:forEach items="${checkFocusVo.attaches}" var="attach" varStatus="a">
                                                <a href="${attach}" target="_blank"><img style="padding-top: 10px"
                                                                                         title="点击查看大图" width="360"
                                                                                         height="240"
                                                                                         src="${attach}"></a>
                                            </c:forEach>
                                        </td>
                                    </c:if>
                                    <c:if test="${r.count==1}">
                                        <td style="text-align: center;vertical-align: middle"
                                            rowspan="${fn:length(checkFocusVo.records)}">
                                            <button class="btn btn-danger fo" value="${checkFocusVo.focusId}">删除</button>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
