<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <base href="/myweb/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>快递管理系统</title>
    <jsp:include page="resource.jsp" />
  </head>
  <body>
  <div id="dcWrap">
    <jsp:include page="head.jsp" />
    <!-- dcHead 结束 -->
    <jsp:include page="menu.jsp" />
      <div id="dcMain">
        <!-- 当前位置 -->
        <div id="urHere">快递管理系统<b>></b><strong>编辑快递</strong> </div>
        <div id="manager" class="mainBox"
          style="height:auto!important;height:550px;min-height:550px;">
          <h3><a href="index.jsp" class="actionBtn">返回首页</a>编辑快递</h3>

          <form action="delivery" method="post" onsubmit="return checkForm()">
            <!-- 修改的参照id 隐藏-->
            <input type="hidden" name="id" value="${sysDelivery.id}" />
            <input type="hidden" name="method" value="update" />
            <table width="100%" border="0" cellpadding="8" cellspacing="0"
              class="tableBasic">
              <tr>
                <td width="100" align="right">收件人</td>
                <td>
                  <input type="text" name="deliveryName" value="${sysDelivery.deliveryName}" size="40"
                    class="inpMain" />
                </td>
              </tr>
              <tr>
                <td width="100" align="right">快递公司</td>
                <td>
                  <select name="companyId">
                    <option value ="1">请选择</option>
                      <%--遍历快递公司信息，动态生成<option>选项--%>
                      <c:forEach items="${allCompany}" var="company">
                        <%--company.id是集合中的快递公司的元素id--%>
                        <%--sysDelivery.companyId是添加时用户选择的快递公司id--%>
                        <option value="${company.id}" ${company.id == sysDelivery.companyId ?"selected":""}>${company.companyName}</option>
                      </c:forEach>
                  </select>
                </td>
              </tr>
              <tr>
                <td width="100" align="right">手机号</td>
                <td>
                  <input type="text" size="40" name="phone" value="${sysDelivery.phone}" class="inpMain" />
                </td>
              </tr>
              <tr>
                <td align="right">收货地址</td>
                <td>
                  <input type="text" name="address" size="40" class="inpMain" value="${sysDelivery.address}" />
                </td>
              </tr>
              <tr>
                <td align="right">寄件日期</td>
                <td>
                  <input type="date" name="sendTime" size="40" class="inpMain" value ="${sysDelivery.sendTime}"/>
                </td>
              </tr>
              <tr>
                <td align="right">发件状态</td>
                <td>
                  <input type="radio" name="state" value="0" size="40" ${sysDelivery.state==0?"checked":""} />
                  未发件
                  <input type="radio" name="state" value="1" size="40" ${sysDelivery.state==1?"checked":""} />
                  已发件
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                  <input type="submit" name="submit" class="btn" value="提交" />
                </td>
              </tr>
            </table>
          </form>
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
      <div class="clear"></div>
  </div>
  </body>
</html>