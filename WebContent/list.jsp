<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <base href="/myweb/" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>快递管理系统</title>
        <jsp:include page="resource.jsp" />
        <script type="text/javascript">

            //用AJAX，删除
            function removeDelivery(id){
                if (confirm("确定要删除编号为["+id+"]快递记录？")) {
                    let xmlhttp = new XMLHttpRequest();
                    // 设置回调函数处理响应结果
                    xmlhttp.onreadystatechange = function () {
                        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                            let result = xmlhttp.responseText;

                            if (result == "true") {
                                let trElement = document.getElementById(id);
                                trElement.remove()
                            } else {
                                alert("删除失败！");
                            }
                        }
                    }
                    // 设置请求方式和请求的资源路径
                    xmlhttp.open("GET","api/deliveryRemove?id="+id ,true);
                    // 发送请求
                    xmlhttp.send();
                }
            }

            //切换分页
            function changePage(page){
                let pageCountElement = document.getElementById("pageCount");
                let pageCount = pageCountElement.value;
                window.location.href = "api/deliveryList?currentPage="+page+"&pageCount="+pageCount;
            }
            //切换每页记录数
            function changePageCount(pageCount){
                window.location.href = "api/deliveryList?currentPage=1&pageCount="+pageCount;
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
                            <c:forEach items="${deliveries}" var="delivery" >
                                <tr id="${delivery.id}">
                                    <td align="center">${delivery.id}</td>
                                    <td>${delivery.company.companyName}</td>
                                    <td align="center">${delivery.deliveryName}</td>
                                    <td align="center">${delivery.phone}</td>
                                    <td align="center">${delivery.sendTime}</td>
                                    <td align="center">${delivery.address}</td>
                                    <td align="center">${delivery.state == 0 ?'未发件':'已发件'}</td>
                                    <td align="center">
                                        <a href="api/deliveryBeforeUpdate?id=${delivery.id}">编辑</a>
                                        | <a href="javascript:void(0)" onclick="removeDelivery(${delivery.id})">删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td align="right" colspan="8">
                                    每页显示<select id="pageCount" onchange="changePageCount(this.value)" style="width:60px;border: #8F8F8F solid 1px;margin: 5px;">
                                    <option value="5" ${pageCount==5?"selected":""}>5</option>
                                    <option value="10" ${pageCount==10?"selected":""}>10</option>
                                    <option value="20" ${pageCount==20?"selected":""}>20</option>
                                    <option value="50" ${pageCount==50?"selected":""}>50</option>
                                </select>行&nbsp;&nbsp;&nbsp;&nbsp;
                                    共${lastPage}页&nbsp;&nbsp;
                                    <c:if test="${currentPage!=1}" >
                                        <a href="javascript:void(0)" class="actionBtn" onclick="changePage(1)">第一页</a> &nbsp;&nbsp;
                                        <a href="javascript:void(0)" class="actionBtn" onclick="changePage(${currentPage-1})">上一页</a> &nbsp;&nbsp;
                                    </c:if>
                                    <a href="javascript:void(0)" class="actionBtn">第${currentPage}页</a> &nbsp;&nbsp;
                                    <c:if test="${currentPage!=lastPage}">
                                        <a href="javascript:void(0)" class="actionBtn" onclick="changePage(${currentPage+1})">下一页</a> &nbsp;&nbsp;
                                        <a href="javascript:void(0)" class="actionBtn" onclick="changePage(${lastPage})">最后一页</a> &nbsp;&nbsp;
                                    </c:if>
                                    跳转到第<input id="pageInput" style="width:40px;border: #8F8F8F solid 1px;margin: 5px;" type="text" onblur="changePage(this.value)">页&nbsp;&nbsp;
                                </td>
                            </tr>
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