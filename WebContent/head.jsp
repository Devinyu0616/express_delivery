<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="c" uri="jakarta.tags.core" %>--%>
<base href="/myweb/" />
<div id="dcHead">
    <div id="head">
        <div class="logo"><a href="index.jsp">
            <img width="100px" height="25px" src="images/dclogo.gif" alt="logo"></a></div>
        <div class="nav">
            <ul class="navRight">
                <li class="M noLeft">
                    <%--${sysUser.nickname} 从request,session等域中取出登录用户的昵称--%>
                        <a href="JavaScript:void(0);">您好，${user.nickname}</a>
                    <div class="drop mUser">
                        <a href="password.jsp">修改密码</a>
                    </div>
                </li>
                <li class="noRight"><a href="api/logout">退出</a></li>
            </ul>
        </div>
    </div>
</div>