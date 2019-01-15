<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/inc/taglibs.jsp" %>

<!doctype html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>隐患任务状态</title>
<script src="<c:url value="/"/>js/datePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<c:url value="/"/>js/jquery-ui-1.9.2.custom/js/jquery-ui-1.9.2.custom.min.js"
        type="text/javascript"></script>
<!--jquery ui-->
<script src="<c:url value="/"/>js/jquery.layout-latest.js" type="text/javascript"></script>
<!--jquery 布局-->
<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/jquery.jqGrid.src.js" type="text/javascript"></script>
<!--jqgrid 包-->
<script src="<c:url value="/"/>js/jquery.jqGrid-4.4.5/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<!--jqgrid 中文包-->
<script src="<c:url value="/"/>js/commonFunction.js"></script>
<script src="<c:url value="/"/>js/changeclass.js"></script>
<!-- 用于改变页面样式-->
<script src="<c:url value="/"/>js/inc_javascript.js"></script>
<script src="<c:url value="/"/>js/multisearch.js"></script>
<!--加载模态多条件查询相关js-->
<link rel="stylesheet" type="text/css" media="screen"
      href="<c:url value="/"/>js/jquery-ui-1.9.2.custom/css/gkSecure/jquery-ui-1.9.2.custom.css"/>
<link rel="stylesheet" type="text/css" media="screen"
      href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/ui.jqgrid.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/"/>js/jquery.jqGrid-4.4.5/css/tabstyle.css"/>
<body>
<div>
    <table id="listReformWork" style="table-layout: fixed;"></table> <!-- 信息列表 -->
    <div id="pageReformWork"></div> <!-- 分页 -->
</div>
<!-- 查询框 -->
<div id="multiSearchDialogReform" style="display: none;">
    <table>
        <tbody>
        <tr>
            <td>
                <input type="hidden" class="searchField" value="findTime"/>起始时间：
                <input type="hidden" class="searchOper" value="ge"/>
            </td>
            <td>
                <input class="searchString" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true" size="25"/>
            </td>
        </tr>

        <tr>
            <td>
                <input type="hidden" class="searchField" value="findTime"/>截止时间：
                <input type="hidden" class="searchOper" value="le"/>
            </td>
            <td>
                <input class="searchString" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true" size="25"/>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
<script type="text/javascript">
    //新增大项
    function addReform() {
        window.open("/reform/addReform.jsp", "_blank");
    }
    //打开查询窗口并进行窗口初始化
    var multiSearchParams = new Array();
    function openMultipleSearchDialog() {
        multiSearchParams[0] = "#listReformWork";				//列表Id
        multiSearchParams[1] = "#multiSearchDialogReform";//查询模态窗口Id
        initSearchDialog();
        $(multiSearchParams[1]).dialog("open");
        window.scrollTo(0, 0);
    }

    /********导出excel*********/
    function exportExcel() {
        var rules = "";
        var param1 = multiSearchParams[1];
        $("tbody tr", param1).each(function (i) {    	//(1)从multipleSearchDialog对话框中找到各个查询条件行
            var searchField = $(".searchField", this).val();    	//(2)获得查询字段
            var searchOper = $(".searchOper", this).val();  		//(3)获得查询方式
            var searchString = $(".searchString", this).val();  	//(4)获得查询值
            if (searchField && searchOper && searchString) { 		//(5)如果三者皆有值且长度大于0，则将查询条件加入rules字符串
                rules += ',{"field":"' + searchField + '","op":"' + searchOper + '","data":"' + searchString + '"}';
            }
        });
        if (rules) {
            rules = rules.substring(1);								//(6)如果rules不为空，且长度大于0，则去掉开头的逗号
        }
        var filtersStr = '{"groupOp":"AND","rules":[' + rules + ']}';//(7)串联好filtersStr字符串
        alert(filtersStr);
        var url = encodeURI("/reform.htm?method=exportExcel&_search=true&page=1&rows=1000000&sidx=findTime&sord=desc&filters=" + filtersStr);
        alert(url);
        window.location.href = url;
        // window.open(url, "_blank");
    }

    //查看隐患详情
    function viewDetail(reformId) {
        var path = "/DailyReport.htm?method=detail&reformId=" + reformId;
        window.open(path, "_blank");
    }

    //小项操作
    function formatOperate(cellValue, options, rowObject) {
        var html = "";
        html += "<a href='#' onclick='viewDetail(" + rowObject.reformId + ");' alt='隐患项'>【<font color=red>隐患详情</font>】</a>";
        return html;
    }

    //状态
    function formatSta(cellValue, options, rowObject) {
        var html = "";
        if (rowObject.reformStatus == "1") {
            html += "<font color=red>待整改</font>";
        } else if (rowObject.reformStatus == "2") {
            html += "<font color=yellow>整改中</font>";
        } else if (rowObject.reformStatus == "3") {
            html += "<font color=blue>待复查</font>";
        } else if (rowObject.reformStatus == "4") {
            html += "<font color=green>已完成</font>";
        }
        return html;
    }

    //加载表格数据
    var $mygrid = jQuery("#listReformWork").jqGrid({
        url: '/reform.htm?method=reformList',
        rownumbers: true,	//是否显示序号
        datatype: "json",   //从后台获取的数据类型
        autowidth: true,	//是否自适应宽度
        height: document.documentElement.clientHeight - 51,
        colNames: ['Id', '隐患标题', '发现时间', '整改期限', '发现人', '完成时间', '状态', '相关操作'],//表的第一行标题栏
        //以下为每列显示的具体数据
        colModel: [
            {name: 'reformId', index: 'reformId', width: 0, search: false, hidden: true, key: true},
            {
                name: 'errorInfo',
                index: 'errorInfo',
                width: 30,
                align: 'center',
                overflow: 'hidden',
                whitespace: 'nowrap'
            },
            {name: 'findTime', index: 'findTime', width: 15, align: 'center'},
            {name: 'reformTime', index: 'reformTime', width: 15, align: 'center'},
            {name: 'findUserName', index: 'findUserName', width: 10, align: 'center'},
            {name: 'finishTime', index: 'finishTime', width: 15, align: 'center'},
            {name: 'reformStatus', index: 'reformStatus', width: 10, align: 'center', formatter: formatSta},
            {name: 'operate', width: 20, align: 'center', formatter: formatOperate, sortable: false}
        ],
        sortname: 'reformId', //默认排序的字段
        sortorder: 'desc',	//默认排序形式:升序,降序
        multiselect: false,	//是否支持多选,可用于批量删除
        viewrecords: true,	//是否显示数据的总条数(显示在右下角)
        rowNum: 20,			//每页显示的默认数据条数
        rowList: [10, 20, 30],//可选的每页显示的数据条数(显示在中间,下拉框形式)
        scroll: false, 		//是否采用滚动分页的形式
        scrollrows: false,	//当选中的行数据隐藏时,grid是否自动滚
        jsonReader: {
            repeatitems: false	//告诉JqGrid,返回的数据的标签是否是可重复的
        },
        pager: "#pageReformWork"//分页工具栏

    }).navGrid('#pageReformWork', {edit: false, add: false, del: false, search: false});

    //自定义按钮
    //jQuery("#listReformWork").jqGrid('navButtonAdd','#pageReformWork', {
    //	caption:"增加大项", title:"点击增加大项", buttonicon:'ui-icon-plusthick', onClickButton: addPatrolWork
    //});

    jQuery("#listReformWork").jqGrid('navButtonAdd', '#pageReformWork', {
        caption: "新增", title: "点击新增", buttonicon: 'ui-icon-add', onClickButton: addReform
    });
    jQuery("#listReformWork").jqGrid('navButtonAdd','#pageReformWork', {
        caption:"查询", title:"点击查询", buttonicon:'ui-icon-search', onClickButton: openMultipleSearchDialog
    });
    jQuery("#listReformWork").jqGrid('navButtonAdd', '#pageReformWork', {
        caption: "导出Excel", title: "点击导出", buttonicon: 'ui-icon-add', onClickButton: exportExcel
    });

</script>

