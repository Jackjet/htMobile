<%@ include file="/inc/taglibs.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js"
            type="text/javascript"></script> <!--jquery ui-->
    <script src="<c:url value="/"/>js/jquery.layout-latest.js" type="text/javascript"></script> <!--jquery 布局-->
    <script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/jquery.jqGrid.src.js" type="text/javascript"></script>
    <!--jqgrid 包-->
    <script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <!--jqgrid 中文包-->
    <script src="<c:url value="/"/>js/commonFunction.js"></script>
    <script src="<c:url value="/"/>js/changeclass.js"></script> <!-- 用于改变页面样式-->
    <script src="<c:url value="/"/>js/inc_javascript.js"></script>
    <script src="<c:url value="/"/>js/multisearch.js"></script> <!--加载模态多条件查询相关js-->
    <link rel="stylesheet" type="text/css" media="screen"
          href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/gkSecure/jquery-ui-1.9.2.custom.css"/>
    <link rel="stylesheet" type="text/css" media="screen"
          href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/ui.jqgrid.css"/>
    <link rel="stylesheet" type="text/css" media="screen"
          href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/tabstyle.css"/>
</head>
<body>
    <div class="row clearfix">
        <div class="col-md-12 column" style="margin-left: 30px">
            <h4>
                用户管理
            </h4>
        </div>
    </div>
    <div class="row clearfix" style="margin-left: 20px;margin-right: 20px">
        <div class="col-md-12 column">
            <table id="listUserInfo" class="table"></table>
            <div id="pagerUserInfo"></div>
        </div>
    </div>
<script>
    //加载表格数据
    $("#listUserInfo").jqGrid({
        url: '/core/user.htm?method=listUsers',
        rownumbers: true,	//是否显示序号
        datatype: "json",   //从后台获取的数据类型
        autowidth: true,	//是否自适应宽度
        height: document.documentElement.clientHeight-180,
        colNames: ['Id', '用户名', '姓名', '部门', '相关操作'],//表的第一行标题栏
        //以下为每列显示的具体数据
        colModel: [
            {name: 'xId', index: 'xId', width: 0, hidden: true},
            {name: 'userName', index: 'userName', width: 30, align: 'center'},
            {name: 'name', index: 'name', width: 10, align: 'center'},
            {name: 'departmentName', index: 'departmentName', width: 10, align: 'center'},
            {name: 'operate', width: 20, align: 'center', formatter: formatOperate}
        ],
        sortname: 'xId', //默认排序的字段
        sortorder: 'asc',	//默认排序形式:升序,降序
        multiselect: false,	//是否支持多选,可用于批量删除
        viewrecords: true,	//是否显示数据的总条数(显示在右下角)
        rowNum: 20,			//每页显示的默认数据条数
        rowList: [10, 20, 30],//可选的每页显示的数据条数(显示在中间,下拉框形式)
        jsonReader: {
            repeatitems: false	//告诉JqGrid,返回的数据的标签是否是可重复的
        },
        pager: "#pagerUserInfo",	//分页工具栏
    }).navGrid('#pagerUserInfo', {edit: false, add: false, del: false, search: false});

    //自定义按钮
    jQuery("#listUserInfo").jqGrid('navButtonAdd', '#pagerUserInfo', {
        caption: "新增用户", title: "点击新增", buttonicon: 'ui-icon-plusthick', onClickButton: addUser
    });
    //大项操作
    function formatOperate(cellValue, options, rowObject){
        //return "<a href=# onclick='editBigCategory("+rowObject.driverId+")'><font color=blue>[修改]</font></a>";
        var html = "";
        html += "<a href=# onclick='editUser("+rowObject.xId+")' alt='修改'>【<font color=blue>修改</font>】</a>&nbsp;&nbsp;";
        html += "<a href=# onclick='logout("+rowObject.xId+");' alt='注销'>【<font color=red>注销</font>】</a>";
        return html;
    }
    function editUser(rowId){
        window.open("/core/user.htm?method=edit&xId="+rowId,"_blank");
    }
    function addUser(){
        window.open("/core/user.htm?method=edit","_blank");
    }
    function logout(rowId){
        if(rowId!=null || rowId!=0){
            var msg = "您确定要注销吗？";
            if (confirm(msg) == true) {
                $.ajax({
                    url: "/core/user.htm?method=logout&rowId=" + rowId,	//注销或恢复的url
                    cache: false,
                    type: "POST",
                    dataType: "html",
                    beforeSend: function (xhr) {
                    },

                    complete: function (req, err) {
                        alert("操作成功！");
                        $("#listUserInfo").trigger("reloadGrid");
                    }
                });
            }
        }
    }
</script>
</body>
</html>
