var setting = {
    async: {
        enable: true,
        type: "get",
        dataType: "json"
    },
    view: {
        addHoverDom: addHoverDom,
        expandSpeed: "slow",
        showIcon: false,
        showLine: false,
        fontCss: {'font-family': 'sans-serif', 'font-weight': 'normal', 'font-size': '14px'},
        removeHoverDom: removeHoverDom,
        selectedMulti: false
    },
    edit: {
        enable: true,
        //给节点额外增加属性来控制“重命名”、“删除”图标的显示或隐藏
        removeTitle: '删除',
        renameTitle: '重命名',
        showRenameBtn: showRenameBtn,
        showRemoveBtn: showRemoveBtn
    },
    data: {
        keep: {
            leaf: true
        },
        key: {
            name: "name",
        },
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            system: "system",
            rootPId: ""
        }
    },
    callback: {
        beforeEditName: beforeEditName,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        onRemove: onRemove,
        onRename: onRename,
        onAsyncSuccess: onAsyncSuccess,
        beforeClick: zTreeBeforeClick,
        //beforeDblClick: zTreeBeforeDblClick,
        //onDblClick: zTreeOnDblClick
        onClick: zTreeOnClick
    }
};

function beforeEditName(treeId, treeNode) {
    return confirm("确认修改 节点 -- " + treeNode.name + " 吗？");
}

//用于捕获节点被删除之前的事件回调函数，并且根据返回值确定是否允许删除操作
function beforeRemove(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("leftMenu");
    zTree.selectNode(treeNode);
    if (treeNode.isParent) {
        return false;
    }
    return confirm("确认删除 该文件夹 -" + treeNode.name + "及所包含文件 吗？");
}

//用于捕获删除节点之后的事件回调函数。
function onRemove(e, treeId, treeNode) {
    $.post('/security/document.htm?method=delCategory&id=' + treeNode.id);
    alert(treeNode.name + "删除成功");
    var zTree = $.fn.zTree.getZTreeObj("leftMenu");
    var node = zTree.getSelectedNodes();
    var node1 = node[0];
    node1.isParent = true;//把属性变成true，让这个节点被认为是根节点
    zTree.reAsyncChildNodes(node1, "refresh", false);
}

//用于捕获节点编辑名称结束（Input 失去焦点 或 按下 Enter 键）之后，更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
function beforeRename(treeId, treeNode, newName) {
    if (newName.length == 0) {
        alert("节点名称不能为空.");
        var zTree = $.fn.zTree.getZTreeObj("leftMenu");
        setTimeout(function () {
            zTree.editName(treeNode)
        }, 10);
        return false;
    }
    return true;
}

//用于捕获节点编辑名称结束之后的事件回调函数。
function onRename(event, treeId, treeNode) {
    $.post('/security/document.htm?method=renameCategory&id=' + treeNode.id + '&name=' + encodeURI(treeNode.name));
    alert("修改成功");
}

//是否显示编辑按钮
function showRenameBtn(treeId, treeNode) {
    // if (treeNode.level>1){
    // 	return true
    // }

    return false;
}

//是否显示删除按钮
function showRemoveBtn(treeId, treeNode) {
    if (treeNode.level > 1&&getDeleteRight()) {
        return true;
    }
    return false;
}
function getAddRight() {
    var add = document.getElementById("docAdd").value;
    if (add=='true'){
        return true;
    }
    return false;
}
function getDeleteRight() {
    var del = document.getElementById("docDelete").value;
    if (del=='true'){
        return true;
    }
    return false;
}
//添加自定义添加按钮
var newCount = 1;

function addHoverDom(treeId, treeNode) {
    if (treeNode.level < 1) return;
    if(!getAddRight()) return;
    var sObj = $("#" + treeNode.tId + "_span"); //获取节点信息
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0) return;

    var addStr = "<span class='button add' id='addBtn_" + treeNode.id + "' title='添加' onfocus='this.blur();'></span>"; //定义添加按钮
    sObj.append(addStr); //加载添加按钮
    var btn = $("#addBtn_" + treeNode.id);
    //  if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});

    if (btn)
        btn.bind("click", function () {
            var name = prompt("请输入文件夹名称");
            if (name == null) {
                return;
            } else if (name == "") {
                alert("名称不能为空");
            } else {
                var param = "&pId=" + treeNode.id + "&name=" + name;
                var zTree = $.fn.zTree.getZTreeObj("leftMenu");
                $.get(
                    encodeURI(encodeURI("/security/document.htm?method=addCategory"
                        + param)), function (data) {
                        if ($.trim(data) != null) {
                            var treenode = $.trim(data);
                            zTree.addNodes(treeNode, {
                                pid: treeNode.id,
                                name: name,
                            }, true);
                            alert("添加成功！");
                        }
                        ;
                    });
                window.location.reload();
            }
            ;

        });
};

//鼠标离开后不显示添加按钮
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.id).unbind().remove();
};

//		function selectAll() {
//			var zTree = $.fn.zTree.getZTreeObj("leftMenu");
//			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
//		}
//用于捕获异步加载正常结束的事件回调函数
function onAsyncSuccess(event, treeId, treeNode, msg) {
    var zTree = $.fn.zTree.getZTreeObj("leftMenu");
    zTree.updateNode(treeNode); // 异步加载成功后刷新树节点
}

//每次鼠标双击后的触发事件
function zTreeOnClick(event, treeId, treeNode) {
    if (treeNode.name.indexOf('第11章隐患排查与治理') > -1) {
        $("#rightMainTop").css("display", "");
        $("#rightMainContainer").css("display", "none");
        loadReport();
    } else {
        $("#rightMainTop").css("display", "none");
        $("#rightMainContainer").css("display", "");
        loadFiles(treeNode.id);
    }
    $("#categoryId").html(treeNode.id);
};

//用于捕获 zTree 上鼠标双击之前的事件回调函数，并且根据返回值确定触发 onDblClick 事件回调函数
function zTreeBeforeClick(treeId, treeNode) {
    $("#docTitleContainer").html(treeNode.name);
    return true;
};


$(document).ready(function () {
    $.fn.zTree.init($("#leftMenu"), setting, zNodes);
    $("#selectAll").bind("click", selectAll);
});
	