<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/inc/taglibs.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>菜单</title>

    <SCRIPT type=text/javascript src="js/jquery-1.9.1.js"></SCRIPT>
    <link href="/css/all.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/js/leftnav/css/font-awesome.min.css">
    <link rel="stylesheet" href="/js/leftnav/css/leftnav.css" media="screen" type="text/css">
    <link rel="stylesheet" href="/js/mloading/jquery.mloading.css">
    <script src="/js/mloading/jquery.mloading.js"></script>

    <script type="text/javascript">
        function showLoad(id) {
            $("#" + id).mLoading({
                text: "正在更新目录，请稍候~",//加载文字，默认值：加载中...
                icon: "/images/waiting.gif",//加载图标，默认值：一个小型的base64的gif图片
                html: false,//设置加载内容是否是html格式，默认值是false
                content: "",//忽略icon和text的值，直接在加载框中显示此值
                mask: true//是否显示遮罩效果，默认显示
            });
        }

        function hideLoad(id) {
            $("#" + id).mLoading("hide");//隐藏loading组件
        }

        function updateDir() {
            showLoad('accordion');
            if (confirm("更新需要较长时间，点击确定开始。")) {

                $.ajax({
                    url: '/security/document.htm?method=updateCategory',
                    type: 'POST',
                    async: false,
                    cache: false,
                    dataType: "json",
                    beforeSend: function (xhr) {

                    },
                    success: function (data) {
                        if (data.success) {
                            alert("更新成功，请退出重新登录！");
                            hideLoad('accordion');
                            //loadFiles($("#categoryId").val());
                        } else {
                            alert("删除失败，请重试或联系管理员！");
                            hideLoad('accordion');
                        }
                    },
                    complete: function () {
                        hideLoad('accordion');
                    }
                });

                //hideLoad('accordion');
            } else {
                hideLoad('accordion');
            }
        }
    </script>

</head>
<body>
<div class="account-l fl">
    <%--<a class="list-title">账户概览</a>
    --%>

    <ul id="accordion" class="accordion">
        <li>
            <div class="link"><i class="fa fa-file-text"></i>安全文档<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <!-- <li><a href="/security/document.htm?method=list&categoryId=2" target="mainInfor">文档</a></li>   -->

                <c:forEach items="${_Menus}" var="menu" varStatus="index">
                    <li><a href="/security/document.htm?method=list&categoryId=${menu.categoryId}"
                           target="mainInfor">${menu.categoryName}</a></li>
                </c:forEach>

            </ul>
        </li>
        <li>
            <div class="link"><i class="fa fa-calendar"></i>月报表<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <li><a href="/monthReport.htm?method=monthList" target="mainInfor">月度汇报</a></li>
                <li><a href="/monthReport/accident.jsp" target="mainInfor">生产事故登记</a></li>
                <li><a href="/monthReport/issue.jsp" target="mainInfor">突出问题汇总</a></li>
                <li><a href="/monthReport.htm?method=editFeedBack" target="mainInfor">上月工作重点反馈</a></li>
                <li><a href="/monthReport.htm?method=editCheckFocus" target="mainInfor">本月检查重点</a></li>
            </ul>
        </li>
        <li>
            <div class="link"><i class="fa fa-calendar-o"></i>晨会汇报<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <li><a href="/morning.htm?method=morningList" target="mainInfor">每日晨会</a></li>
                <li><a href="/chenhui/union.jsp" target="mainInfor">生产事故、联合检查录入</a></li>
                <li><a href="/handOver/hand.jsp" target="mainInfor">交班重点</a></li>
            </ul>
        </li>
        <li>
            <div class="link"><i class="fa fa-bell"></i>现场动态报表<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <li><a href="/DailyReport.htm?method=dailyReport" target="mainInfor">隐患报表</a></li>
                <li><a href="/errorWork.htm?method=dailyReport" target="mainInfor">联合检查报表</a></li>
                <li><a href="/reform.htm?method=yearReport" target="mainInfor">年度汇总</a></li>
            </ul>
        </li>
        <li>
            <div class="link"><i class="fa fa-check-circle"></i>隐患整改单<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <li><a href="/peccancy/chart.jsp" target="mainInfor">整改单统计</a></li>
                <li><a href="/peccancy.htm?method=listPeccancy" target="mainInfor">整改单报表</a></li>
            </ul>
        </li>
        <li>
            <div class="link"><i class="fa fa-dashboard"></i>任务状态<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <li><a href="/reports/listPatrolStatus.jsp" target="mainInfor">重要节点巡检任务</a></li>
                <li><a href="/reports/listPeccancyStatus.jsp" target="mainInfor">整改单任务</a></li>
                <li><a href="/reports/listReformStatus.jsp" target="mainInfor">隐患任务</a></li>
            </ul>
        </li>

        <li>
            <div class="link"><i class="fa fa-dashboard"></i>培训管理<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <li><a href="/train.htm?method=trainReport" target="mainInfor">出勤统计</a></li>
                <li><a href="/train/addTrain.jsp" target="mainInfor">培训录入</a></li>
                <li><a href="/train.htm?method=dailyReport" target="mainInfor">培训报表</a></li>
                <li><a href="/train/trainChart.jsp" target="mainInfor">年度统计</a></li>
            </ul>
        </li>
        <li>
            <div class="link"><i class="fa fa-unlock-alt"></i>系统管理<i class="fa fa-chevron-down"></i></div>
            <ul class="submenu">
                <li><a href="/patrol/listPatrolCategory.jsp" target="mainInfor">巡检配置</a></li>
                <c:if test="${_GLOBAL_USER.userType==99}">
                    <li><a href="/user/listUser.jsp" target="mainInfor">用户管理</a></li>
                </c:if>
                <%--<li><a href="javascript:;" onclick="updateDir();" target="mainInfor">文档分类更新</a></li>--%>
            </ul>
        </li>
    </ul>
    <script type="text/javascript" src='/js/leftnav/js/leftnav.js'></script>
</div>
</body>
</html>


