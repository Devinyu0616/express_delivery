<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="c" uri="jakarta.tags.core" %>--%>

<!DOCTYPE html>
<html>
<head>
    <base href="/myweb/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>快递管理系统</title>
    <meta name="Copyright" content="Douco Design." />
    <%--在当前的JSP页面中包含其他JSP页面内容--%>
    <jsp:include page="resource.jsp" />
</head>
<body>
<div id="dcWrap">
    <jsp:include page="head.jsp" />
    <!-- dcHead 结束 -->
    <jsp:include page="menu.jsp" />
    <div id="dcMain"> <!-- 当前位置 -->
        <div id="urHere">快递管理系统</div> <div id="index" class="mainBox"
                                                 style="padding-top:18px;height:auto!important;height:550px;min-height:550px;">
            <div class="warning">欢迎光临：${user.nickname}</div>
<%--            TODO >>>这里有问题--%>
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