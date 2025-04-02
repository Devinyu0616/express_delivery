<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="jakarta.tags.core" %>--%>
<!DOCTYPE html>
<html>
  <head>
    <base href="/myweb/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>快递管理系统</title>
    <link href="css/public.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/global.js"></script>
    <script>
      // 定义正则表示字符串的规则，密码规则是6-12位英文字母或数字组成
      let  userPwdReg= /^[a-zA-Z0-9]{6,12}$/

      //定义变量记录账号和密码对应校验状态，true代表校验通过，false代表校验不通过
      let  oldPwdState = false;
      let  newPwdState = false;
      let  rePwdState  = false;
     
      //定义函数，校验旧密码格式
      function checkOldPwd(){
          //获取用户输入的旧密码
          let oldPwdInput = document.getElementById("oldPwdInput")  
          let oldPwd = oldPwdInput.value  

          //获取旧密码格式的提示框
          let oldPwdMsg = document.getElementById("oldPwdMsg")

          // 格式有误时,旧密码校验状态为false,并在旧密码格式提示的框中用红色字样提示:原密码格式错误!
          if(!userPwdReg.test(oldPwd)){
              oldPwdMsg.innerHTML="<font color='red'>原密码格式错误!</font>"
              oldPwdState = false;
              return;
          } 

          // 格式OK时,旧密码校验状态为true,并在旧密码格式提示的框中用绿色字样提示:原密码格式正确!
         /* oldPwdMsg.innerHTML="<font color='green'>原密码格式正确!</font>"
          oldPwdState = true;*/
        //格式正确之后，调用AJAX去服务器端检查旧密码是否正确
        checkOldPasswordCorrect(oldPwdMsg,oldPwd);
      }

      function checkOldPasswordCorrect(oldPwdMsg,oldPwd){
        let xmlhttp = new XMLHttpRequest();//创建了一个XMLHttpRequest类型的对象

        xmlhttp.onreadystatechange = function (){

          if(xmlhttp.readyState==4 && xmlhttp.status==200){
            let result = xmlhttp.responseText;//获取服务器端给客户端响应的文本内容

            if(result=="true"){
              oldPwdMsg.style="color:green;"
              oldPwdMsg.innerText = "原密码正确！";
              oldPwdState = true;
            }else {
              oldPwdMsg.style="color:red;"
              oldPwdMsg.innerText = "原密码不正确！";
              oldPwdState = false;
            }
          }
        }
        //设置请求路径和请求方式
        // xmlhttp.open("GET","checkOldPassword&oldPassword=" + oldPwd ,true);
        xmlhttp.open("GET","api/checkOldPassword?oldPassword=" + oldPwd ,true);



        //发生请求
        xmlhttp.send();

      }

      //定义函数，检验新密码格式
      function checkNewPwd(){
          //获取用户输入的新密码值
          let newPwdInput = document.getElementById("newPwdInput")  
          let newPwd = newPwdInput.value  

          //获取新密码格式的提示框
          let newPwdMsg = document.getElementById("newPwdMsg")

          // 格式有误时,新密码校验状态为false,并在新密码格式提示的框中用红色字样提示:新密码格式错误!
          if(!userPwdReg.test(newPwd)){
            newPwdMsg.innerHTML="<font color='red'>新密码格式错误!</font>"
            newPwdState = false;
            return
          } 

          // 格式OK时,新密码校验状态为true,并在新密码格式提示的框中用绿色字样提示:新密码格式正确!
          newPwdMsg.innerHTML="<font color='green'>新密码格式正确!</font>"
          newPwdState = true 
      }

      //定义函数，检验确认密码的格式和要求
      function checkReUserPwd(){
          //新密码合理才可以校验确认密码。如果新密码不合理，弹窗提示：先输入新密码,再输入确认密码!
          if(!newPwdState){
            alert("先输入新密码,再输入确认密码!")
            return;
          }

          //获取用户输入的确认密码值
          let rePwdInput = document.getElementById("rePwdInput")  
          let rePwd = rePwdInput.value
          
          //获取确认密码格式的提示框
          let rePwdMsg = document.getElementById("rePwdMsg")

          // 格式有误时,确认密码校验状态为false,并在确认密码格式提示的框中用红色字样提示:确认密码格式错误!
          if(!userPwdReg.test(rePwd)){
              rePwdMsg.innerHTML="<font color='red'>确认密码格式错误!</font>"
              rePwdState = false;
              return
          } 
          //获取用户输入的新密码值, 用以对比两次密码是否一致
          let newPwdInput = document.getElementById("newPwdInput")  
          let newPwd = newPwdInput.value  

          // 如果密码和确认密码不一致，确认密码校验状态为false,并在确认密码格式提示的框中用红色字样提示:两次密码不一致!
          if(rePwd != newPwd){
              rePwdMsg.innerHTML="<font color='red'>两次密码不一致!</font>"
              rePwdState = false;
              return
          } 

          //如果密码和确认密码一致，确认密码校验状态为true,并在确认密码格式提示的框中用绿色字样提示:两次密码一致!
          rePwdMsg.innerHTML="<font color='green'>两次密码一致!</font>"
          rePwdState = true;
      }

      //确认表单是否可以提交的函数
      function checkForm(){
          // 表单在提交时, 必须确保旧密码、新密码、确认密码校验状态都为true，才能提交
          return oldPwdState && newPwdState && rePwdState
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
        <div id="urHere">快递管理系统<b>></b><strong>修改密码</strong> </div>
        <div id="manager" class="mainBox" style="height:auto!important;height:550px;min-height:550px;">
          <h3><a href="index.jsp" class="actionBtn">返回首页</a>修改密码</h3>
          <!-- 提交表单事件onsubmit触发时，必须调用函数来确保旧密码、新密码、确认密码校验状态都为true，才能提交。-->
          <form action="api/user" method="post" onsubmit="return checkForm()">
            <input type="hidden" name="method" value="updatePassword" />
            <table width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">
              <tr>
                <td width="100" align="right">原始密码</td>
                <td>
                  <!-- 旧密码文本框失去焦点时，要调用函数来校验旧密码格式是否正确 -->
                  <input id="oldPwdInput" type="password" name="oldPassword" size="40" class="inpMain" onblur="checkOldPwd()"/>
                  &nbsp; &nbsp; 
                  <span id="oldPwdMsg"></span>
                </td>
              </tr>
              <tr>
                <td width="100" align="right">新密码</td>
                <td>
                  <!-- 新密码文本框失去焦点时，要调用函数来校验新密码格式是否正确 -->
                  <input id="newPwdInput" type="password" name="newPassword" size="40" class="inpMain" onblur="checkNewPwd()"/>
                  &nbsp; &nbsp; 
                  <span id="newPwdMsg"></span>
                </td>
              </tr>
              <tr>
                <td align="right">确认新密码</td>
                <td>
                  <!-- 确认密码文本框失去焦点时，要调用函数来校验确认密码格式是否正确 -->
                  <input id="rePwdInput" type="password" size="40" class="inpMain" onblur="checkReUserPwd()"/>
                  &nbsp; &nbsp; 
                  <span id="rePwdMsg"></span>
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
      <div class="clear"></div> </div>
  </body>
</html>