<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <script>
        $(function () {
            $(".form-control").datetimepicker({format: 'YYYY-MM-DD', locale: moment.locale('zh-cn')});
            $(".btn-danger").click(function () {
                var msg = "您确定要删除吗？";
                if (confirm(msg)==true){
                    $.ajax({
                        type:'post',
                        url:'/handOver.htm?method=deleteHand',
                        data:{handId:this.value},
                        success:function (data) {
                            location.reload();//刷新
                        },
                        error:function (data) {
                            alert("后台出错");
                        }
                    })
                }else{
                    return false;
                }
            });
        });

        function delImg(filepath, unionId) {
            window.open("/morning.htm?method=delImg&filepath=" + filepath + "&unionId=" + unionId, "_self");
        }

        function editHand(handId) {
            window.open("/handOver.htm?method=editHand&handId=" + handId);
        }
    </script>
</head>
<body>
<div class="container">
    <div class="col-md-12 column" style="margin-top: 30px">
        <form role="form" class="form-inline" action="morning.htm?method=morningList" method="post">
            <div class="form-group">
                <input class="form-control" name="beginDate" type="text" value="${beginDate}"
                       style="width: 150px;text-align: center"/>&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;
            </div>
            <div class="form-group">
                <input class="form-control" name="endDate" type="text" value="${endDate}"
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
                        <a href="#panel-293786" data-toggle="tab"><b>生产事故</b></a>
                    </li>
                    <li>
                        <a href="#panel-293787" data-toggle="tab"><b>联合检查详情</b></a>
                    </li>
                    <li>
                        <a href="#panel-134989" data-toggle="tab"><b>隐患详情</b></a>
                    </li>
                    <li>
                        <a href="#panel-334990" data-toggle="tab"><b>整改单详情 </b></a>
                    </li>
                    <li>
                        <a href="#panel-434991" data-toggle="tab"><b>重要节点检查详情</b></a>
                    </li>
                    <li>
                        <a href="#panel-534992" data-toggle="tab"><b>交班重点</b></a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="panel-293786">
                        <c:forEach items="${unionVos}" var="union">
                            <c:if test="${union.unionAttachs !=''&& union.category eq '生产事故'}">
                                <c:forEach items="${union.unionAttachs}" var="attachs" varStatus="a">
                                    <a href="${attachs}" target="_blank"><img style="margin-top: 20px" title="点击查看大图"
                                                                              width="1100"
                                                                              src="${attachs}"></a>
                                    <input type="button" value="删除" class="btn btn-warning"
                                           onclick="delImg('${attachs}','${union.unionId}');">
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="tab-pane" id="panel-293787">
                        <c:forEach items="${unionVos}" var="union">
                            <c:if test="${union.unionAttachs !=''&& union.category eq '联合检查'}">
                                <c:forEach items="${union.unionAttachs}" var="attachs" varStatus="a">
                                    <a href="${attachs}" target="_blank"><img style="margin-top: 20px" title="点击查看大图"
                                                                              width="1100"
                                                                              src="${attachs}"></a>
                                    <input type="button" value="删除" class="btn btn-warning"
                                           onclick="delImg('${attachs}','${union.unionId}')">
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="tab-pane" id="panel-134989">
                        <table class="table table-bordered" style="margin-top: 10px">
                            <tr style="background-color: #1c94c4">
                                <td style="width:10%; text-align: center">
                                    <font color="#f0f8ff">编号</font>
                                </td>
                                <td style="text-align: center">
                                    <font color="#f0f8ff">现场情况</font>
                                </td>
                                <td style="text-align: center">
                                    <font color="#f0f8ff">现场照片</font>
                                </td>
                            </tr>
                            <c:forEach items="${reformVos}" var="reform" varStatus="r">
                                <tr style="background-color: mintcream">
                                    <td style="text-align: center;vertical-align: middle">
                                            ${r.count}
                                    </td>
                                    <td style="vertical-align: middle">
                                            ${reform.content}
                                    </td>
                                    <td style="text-align: center;vertical-align: middle;width: 60%">
                                        <c:if test="${reform.errorAttachs !=''}">
                                            <c:forEach items="${reform.errorAttachs}" var="attachs" varStatus="a">
                                                <c:if test="${attachs!=''}">
                                                    <a href="${attachs}" target="_blank"><img style="padding-top: 10px"
                                                                                              title="点击查看大图" width="240"
                                                                                              height="180"
                                                                                              src="${attachs}"></a>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="tab-pane" id="panel-334990">
                        <table class="table table-bordered" style="margin-top: 10px">
                            <tr style="background-color: #1c94c4">
                                <td style="text-align: center;vertical-align: middle">
                                    <font color="#f0f8ff"> 编号</font>
                                </td>
                                <td style="text-align: center">
                                    <font color="#f0f8ff">现场情况</font>
                                </td>
                                <td style="text-align: center">
                                    <font color="#f0f8ff">现场照片</font>
                                </td>
                            </tr>
                            <c:forEach items="${peccancyVos}" var="peccancy" varStatus="p">
                                <tr style="background-color: mintcream">
                                    <td style="text-align: center;vertical-align: middle">
                                            ${p.count}
                                    </td>
                                    <td style="vertical-align: middle">
                                            ${peccancy.content}
                                    </td>
                                    <td style="text-align: center;vertical-align: middle;width: 60%">
                                        <c:if test="${peccancy.errorAttachs !=''}">
                                            <c:forEach items="${peccancy.errorAttachs}" var="attachs" varStatus="a">
                                                <a href="${attachs}" target="_blank"><img style="padding-top: 10px"
                                                                                          title="点击查看大图" width="240"
                                                                                          height="180"
                                                                                          src="${attachs}"></a>
                                            </c:forEach>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="tab-pane" id="panel-434991">
                        <table class="table table-bordered">
                            <tbody>
                            <c:forEach items="${workItemVos}" var="item" varStatus="w">
                                <tr>
                                    <td style="text-align: center;vertical-align: middle">
                                            ${item.workName}
                                    </td>
                                    <td>
                                        <table class="table table-bordered">
                                            <c:forEach items="${item.patrolVos}" var="pvos">
                                                <tr>
                                                    <td style="text-align: center;vertical-align: middle">${pvos.bigName}</td>
                                                    <td>
                                                        <table class="table table-bordered">
                                                            <tr>
                                                                <c:forEach items="${pvos.items}" var="small">
                                                                    <td style="text-align: center;vertical-align: middle">${small.smallName}</td>
                                                                </c:forEach>
                                                            </tr>
                                                            <tr>
                                                                <c:forEach items="${pvos.items}" var="small">
                                                                    <td style="text-align: center;vertical-align: middle">
                                                                        <c:if test="${small.result==1}"><img
                                                                                src="../images/chenhui/正常.png"></c:if>
                                                                        <c:if test="${small.result==-1}"><img
                                                                                src="../images/chenhui/错误.png"></c:if>
                                                                    </td>
                                                                </c:forEach>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane" id="panel-534992">
                        <table class="table table-bordered"
                               style="margin-top: 10px;word-break:break-all; word-wrap:break-all;">
                            <thead>
                            <tr style="background-color: #1c94c4">
                                <th style="text-align: center;vertical-align: middle;width: 5%">
                                    编号
                                </th>
                                <th style="text-align: center;vertical-align: middle;width: 15%">
                                    标题
                                </th>
                                <th style="text-align: center;vertical-align: middle;width: 30%">
                                    交班内容
                                </th>
                                <th style="text-align: center;vertical-align: middle;width: 20%">
                                    附件
                                </th>
                                <th style="text-align: center;vertical-align: middle;width: 15%">
                                    操作
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${handleVos}" var="handle" varStatus="a">
                                <tr <c:if test="${a.index%2==0}">class="success"</c:if>>
                                    <td style="text-align: center;vertical-align: middle">${a.count}</td>
                                    <td style="text-align: center;vertical-align: middle;">${handle.title}</td>
                                    <td style="vertical-align: middle;">${handle.content}</td>
                                    <td style="text-align: center;vertical-align: middle">
                                        <c:if test="${handle.attaches !=''}">
                                            <c:forEach items="${handle.attaches}" var="attach" varStatus="a">
                                                <a href="${attach}" target="_blank"><img style="padding-top: 10px"
                                                                                         title="点击查看大图" width="240"
                                                                                         height="180"
                                                                                         src="${attach}"></a>
                                            </c:forEach>
                                        </c:if>
                                    </td>
                                    <td style="text-align: center;vertical-align: middle"><input type="button" value="编辑" class="btn btn-success" onclick="editHand('${handle.handleId}')">
                                        <button  class="btn btn-danger" value="${handle.handleId}">删除</button>
                                    </td>
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
