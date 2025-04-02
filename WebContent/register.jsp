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

      //定义变量记录用户名和密码、确认密码对应校验状态

      //提示用户名格式的函数
      function usernameTips(){
          // 获得用户名格式的提示框，用蓝色字提示：用户名是6-10位英文字母或数字组成，不能以数字开头
          let accountMsg =document.getElementById("accountMsg");
          accountMsg.innerHTML="<font color='blue'>用户名是6-10位英文字母或数字组成，不能以数字开头</font>"
      }
      //提示密码格式的函数
      function passwordTips(){
          // 获得密码格式的提示框，用蓝色字提示：密码是6-12位英文字母或数字组成
        let userPwdMsg = document.getElementById("passwordMsg")
        userPwdMsg.innerHTML="<font color='blue'>密码是6-12位英文字母或数字组成</font>"
      }

      // 检验用户名格式是否合法的函数
      function checkUsername(){
        // 获取用户输入的用户名信息
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
        /*// 格式OK,用户名校验状态为true 并在用户名格式提示的框中用绿色字样提示:用户名可以使用!
        accountState = true;
        accountMsg.innerHTML="<font color='green'>用户名可以使用!</font>"*/
        //格式正确，需要发起异步请求到服务器端检查用户名是不是存在
        checkUsernameExist(accountMsg,username);
      }

      function checkUsernameExist(accountMsg,username){
        let xmlhttp = new XMLHttpRequest();//创建了一个XMLHttpRequest类型的对象

        xmlhttp.onreadystatechange = function (){

          if(xmlhttp.readyState==4 && xmlhttp.status==200){
            let result = xmlhttp.responseText;//获取服务器端给客户端响应的文本内容
            if(result=="true"){//用户名已存在
              accountMsg.style="color:red;"
              accountMsg.innerText="用户名已存在!";
              accountState = false;
            }else {
              accountMsg.style="color:green;"
              accountMsg.innerText="用户名可以使用!";
              accountState = true;
            }
          }
        }
        //设置请求路径和请求方式
        xmlhttp.open("GET","user?method=checkUsername&username="+ username ,true);
        //发生请求
        xmlhttp.send();
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


      //定义函数，检验确认密码的格式和要求
      function checkReUserPwd(){
           //新密码合理才可以校验确认密码。如果新密码不合理，弹窗提示：先输入新密码,再输入确认密码!
          if(!passwdState){
            alert("先输入密码,再输入确认密码!")
            return;
          }

          //获取用户输入的确认密码值
          let rePwdInput = document.getElementById("rePwdInput")  
          let rePwd = rePwdInput.value;

          //获取确认密码格式的提示框
          let rePwdMsg = document.getElementById("rePwdMsg")
           // 格式有误时,确认密码校验状态为false,并在确认密码格式提示的框中用红色字样提示:确认密码格式错误!
          if(!userPwdReg.test(rePwd)){
              rePwdMsg.innerHTML="<font color='red'>确认密码格式错误!</font>"
              rePwdState = false;
              return;
          } 
           //获取用户输入的新密码值, 用以对比两次密码是否一致
          let newPwdInput = document.getElementById("userPwdInput")  
          let newPwd = newPwdInput.value;
          // 如果密码和确认密码不一致，确认密码校验状态为false,并在确认密码格式提示的框中用红色字样提示:两次密码不一致!
          if(rePwd != newPwd){
              rePwdMsg.innerHTML="<font color='red'>两次密码不一致!</font>"
              rePwdState = false;
              return;
          } 

          //如果密码和确认密码一致，确认密码校验状态为true,并在确认密码格式提示的框中用绿色字样提示:两次密码一致!
          rePwdMsg.innerHTML="<font color='green'>两次密码一致!</font>"
          rePwdState = true;
      }
      
       //确认表单是否可以提交的函数
      function checkForm(){
          // 表单在提交时, 必须确保用户名、新密码、确认密码校验状态都为true，才能提交
          return accountState && passwdState && rePwdState;
      }
    </script>  
  </head>
  <body>
    <div id="dcWrap">
      <div id="dcMain">
        <!-- 当前位置 -->
        <div id="manager" class="mainBox" style="height: auto !important; height: 550px; min-height: 550px">
          <h3>新用户注册</h3>
          <!-- 提交表单事件onsubmit触发时，必须调用函数来确保用户名、新密码、确认密码校验状态都为true，才能提交。-->
          <form action="api/user" method="post" onsubmit="return checkForm()">
            <!--如果隐藏域method的value值是register，那么UserServlet中会调用register方法来处理注册的请求-->
            <input type="hidden" name="method" value="register" />
            <table width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">
              <tr>
                <td width="100" align="right">用户名</td>
                <td>
                  <!-- 用户名文本框获取焦点时，要调用函数来提示用户名格式 -->
                  <!-- 用户名文本框失去焦点时，要调用函数来校验用户名格式是否正确 -->
                  <input id="usernameInput" type="text" name="username" size="40" class="inpMain" onfocus="usernameTips()" onblur="checkUsername()"/>
                  &nbsp; &nbsp;
                  <span id="accountMsg"></span>
                </td>
              </tr>
              <tr>
                <td width="100" align="right">昵称</td>
                <td>
                  <input type="text" name="nickname" size="40" class="inpMain" />
                </td>
              </tr>
              <tr>
                <td width="100" align="right">密码</td>
                <td>
                  <!-- 密码文本框获取焦点时，要调用函数来提示密码格式 -->
                  <!-- 密码文本框失去焦点时，要调用函数来校验密码格式是否正确 -->
                  <input id="userPwdInput" type="password" name="password" size="40" class="inpMain" onfocus="passwordTips()" onblur="checkUserPwd()" />
                  &nbsp; &nbsp;
                  <span id="passwordMsg"></span>
                </td>
              </tr>
              <tr>
                <td align="right">确认密码</td>
                <td>
                   <!-- 确认密码文本框失去焦点时，要调用函数来校验确认密码格式是否正确 -->
                  <input id="rePwdInput" type="password" name="rePassword" size="40" class="inpMain" onblur="checkReUserPwd()"/>
                  &nbsp; &nbsp; 
                  <span id="rePwdMsg"></span>
                </td>
              </tr>
              <tr>
                <td></td>
                <td>
                  <input type="submit" class="btn" value="注册" />
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
