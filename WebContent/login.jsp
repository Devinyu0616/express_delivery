<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="jakarta.tags.core" %>--%>
<!DOCTYPE html>
<html>
  <head>
    <base href="/myweb/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>快递管理系统</title>
    <link href="css/public.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/global.js"></script>
   <!-- 代码实现-->
 <script>
  // 定义用户名正则表示字符串的规则，用户名规则是6-10位英文字母或数字组成，不能以数字开头
  let  usernameReg= /^[a-zA-Z][a-zA-Z0-9]{5,9}$/
  // 定义正则表示字符串的规则，密码规则是6-12位英文字母或数字组成
  let  userPwdReg= /^[a-zA-Z0-9]{6,12}$/

   //定义记录用户名和密码对应校验状态变量，初始化为false。true表示检验通过，false表示校验不通过。
  let  accountState = false;
  let  passwdState = false;

  // 检验用户名格式是否合法的函数
  function checkUsername(){
      // 获得用户在页面上输入的用户名信息
      let usernameInput =document.getElementById("usernameInput")
      let username = usernameInput.value
      // 获得用户名格式的提示框
      let accountMsg =document.getElementById("accountMsg")
      // 格式有误时,用户名校验状态为false,并在用户名格式提示的框中用红色字样提示:用户名格式错误!
      if(!usernameReg.test(username)){ 
          accountMsg.innerHTML="<font color='red'>用户名格式错误!</font>"
          accountState = false;
          return; 
      }
      // 格式OK,用户名校验状态为true 并在用户名格式提示的框中用绿色字样提示:用户名可以使用!
      accountState = true;
      accountMsg.innerHTML="<font color='green'>用户名可以使用!</font>"
  }

  // 检验密码格式是否合法的函数
  function checkUserPwd(){
      // 获得用户在页面上输入的密码信息
      let userPwdInput =document.getElementById("userPwdInput")
      let userPwd = userPwdInput.value
      // 获得密码格式的提示框
      let userPwdMsg = document.getElementById("passwordMsg")
      // 格式有误时,密码校验状态为false,并在密码格式提示的框中用红色字样提示:密码格式错误!
      if(!userPwdReg.test(userPwd)){ 
          userPwdMsg.innerHTML="<font color='red'>密码格式错误!</font>"
          passwdState = false;
          return;
      }
      //  格式OK,密码校验状态为true 并在密码格式提示的框中用绿色字样提示:密码可以使用!
      passwdState = true;
      userPwdMsg.innerHTML="<font color='green'>密码可以使用!</font>"
  }
   //确认表单是否可以提交的函数
  function checkForm(){
      // 表单在提交时, 必须确保用户名和密码校验状态都为true，才能提交
      return accountState && passwdState
  }
</script>  
  </head>
  <body>
    <div id="dcWrap">
      <div id="dcMain">
        <!-- 当前位置 -->
        <div id="manager" class="mainBox" style="height: auto !important; height: 550px; min-height: 550px">
          <h3>用户登陆</h3>
          <!-- 提交表单事件onsubmit触发时，必须调用函数来确保用户名和密码校验状态都为true，才能提交。-->
          <form action="api/login" method="get" onsubmit="return checkForm()">
            <input type="hidden" name="method" value="login">
            <table width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">
              <tr>
                <td width="100" align="right">用户名</td>
                <td>
                  <!-- 用户名文本框失去焦点时，要调用函数来校验用户名格式是否正确 -->
                  <input id="usernameInput" type="text" name="username" size="40" class="inpMain" onblur="checkUsername()"  value="Admin12345"/>
                  &nbsp; &nbsp;
                  <span id="accountMsg"></span>
                </td>
              </tr>
              <tr>
                <td width="100" align="right">密码</td>
                <td>
                  <!-- 密码文本框失去焦点时，要调用函数来校验密码格式是否正确 -->
                  <input id="userPwdInput" type="password" name="password" size="40" class="inpMain" onblur="checkUserPwd()" value="Admin12345" />
                  &nbsp; &nbsp;
                  <span id="passwordMsg"></span>
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                  <input type="submit" class="btn" value="登录" /> &nbsp; &nbsp; 
                  <span style="font-weight: bolder;text-decoration: underline solid;"><a href="register.html">注&nbsp;册</a></span>
                  &nbsp;&nbsp;&nbsp;
                  <span style="color:red;">${msg}</span>
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
      </div>
      <!-- dcFooter 结束 -->
      <div class="clear"></div>
    </div>
  </body>
</html>
