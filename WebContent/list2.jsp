<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <base href="/myweb/" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>快递管理系统</title>
        <jsp:include page="resource.jsp" />
        <script>
            //非异步处理
            /*function confirmDelete(id){
                let result = window.confirm("你确定要删除["+id+"]的快递记录吗");
                console.log(result);
                if(result == true){
                    window.location.href = "delivery?method=remove&id="+id;
                }
            }*/

            //异步处理删除
            function confirmDelete(id){
                let result = window.confirm("你确定要删除["+id+"]的快递记录吗");
                console.log(result);
                if(result == true){
                    let xmlhttp = new XMLHttpRequest();//创建了一个XMLHttpRequest类型的对象
                    xmlhttp.onreadystatechange = function (){
                        if(xmlhttp.readyState==4 && xmlhttp.status==200){
                            let result = xmlhttp.responseText;//获取服务器端给客户端响应的文本内容
                            if(result == "true"){
                                let trElement = document.getElementById(id);
                                trElement.remove();
                            }
                        }
                    }
                    //设置请求路径和请求方式
                    xmlhttp.open("GET","api/deliveryRemove?id="+id ,true);
                    //发生请求
                    xmlhttp.send();
                }
            }
        </script>
    </head>
    <body>
        <div id="dcWrap">
            <jsp:include page="head.jsp" />
            <!-- dcHead 结束 -->
            <jsp:include page="menu.jsp" />
            <div id="dcMain">
                <!-- 当前位置 -->
                <div id="urHere">快递管理系统<b>></b><strong>快递列表</strong> </div>
                <div class="mainBox"
                    style="height:auto!important;height:550px;min-height:550px;">
<%--                    <h3><a href="delivery?method=prepareAdd" class="actionBtn add">添加快递</a>快递列表</h3>--%>
                    <h3><a href="api/deliveryPrepareAdd" class="actionBtn add">添加快递</a>快递列表</h3>
                    <div id="list">

                        <table width="100%" border="0" cellpadding="8"
                            cellspacing="0" class="tableBasic">
                            <tr>
                                <th width="40" align="center">编号</th>
                                <th width="80" align="center">物流公司</th>
                                <th width="80" align="center">收件人</th>
                                <th width="80" align="center">手机号</th>
                                <th width="80" align="center">寄件日期</th>
                                <th align="center">地址</th>
                                <th width="80" align="center">发件状态</th>
                                <th width="80" align="center">操作</th>
                            </tr>
                            <%--动态遍历快递列表信息--%>
                            <c:forEach items="${allDelivery}" var="delivery">
                                <tr id="${delivery.id}">
                                    <td>${delivery.id}</td>
                                    <td>${delivery.company.companyName}</td>
                                    <td>${delivery.deliveryName}</td>
                                    <td>${delivery.phone}</td>
                                    <td>${delivery.sendTime}</td>
                                    <td>${delivery.address}</td>
                                    <td>${delivery.state==0?"未发件":"已发件"}</td>
                                    <td><a href="api/deliveryEdit?id=${delivery.id}">编辑</a> |
                                        <a href="javascript:void(0)" onclick="confirmDelete(${delivery.id})">删除</a> </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div class="clear"></div>
            <div id="dcFooter">
                <div id="footer">
                    <div class="line"></div>
                    <ul>
                        版权所有 © 2024-2025 尚硅谷教育，并保留所有权利。
                    </ul>
                </div>
            </div><!-- dcFooter 结束 -->
            <div class="clear"></div> </div>
    </body>
</html>