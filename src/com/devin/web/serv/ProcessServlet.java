package com.devin.web.serv;

import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.devin.web.bean.Company;
import com.devin.web.bean.Delivery;
import com.devin.web.bean.User;
import com.devin.web.service.*;
import com.devin.web.util.MD5Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/api/*")
public class ProcessServlet extends HttpServlet {
    private final ServerImple serverProcess = new ServerImple();
    private final DeliveryService deliveryService = new DeliveryServiceImpl();
    private final CompanyService companyService = new CompanyServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
//        System.out.println("------------------");

        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);
//        if(pathInfo.contains("&")){
//
//        }
        //>>> TODO 使用switch分发目录
        switch (pathInfo) {
            case "/login" ->{
                try {
//                    System.out.println("req,resp,parameterMap");
                    login(req,resp,parameterMap);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "/logout"->{
                try {
//                    System.out.println("req,resp,parameterMap");
                    logout(req,resp);
                } catch (Exception e) {
                    System.out.println("logout出错了");
                    throw new RuntimeException(e);
                }
            }
            case "/register" ->{
                try {
                    register(req,resp,parameterMap);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "/updatePwd" -> {
                try {
                    updatePwd(req,resp,parameterMap);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "/checkUsername" ->checkUsername(req,resp,parameterMap);
            case "/checkPassword" ->checkPassword(req,resp,parameterMap);
            case "/checkOldPassword"->checkPassword(req,resp,parameterMap);
            case "/deliveryList" ->{deliveryList(req,resp);
            }

            default -> resp.sendError(404);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp, Map<String, String[]> pm) throws Exception {

    if (pm.containsKey("username") && pm.containsKey("password")&&pm.containsKey("nickname")&&pm.containsKey("rePassword")){

        serverProcess.addUser(new User(pm.get("username")[0],pm.get("password")[0],pm.get("nickname")[0]));
         }
    }
    //登录处理方法
    private void login(HttpServletRequest req, HttpServletResponse resp, Map<String, String[]> pm) throws Exception {
        // 定义用户名正则表示字符串的规则，用户名规则是6-10位英文字母或数字组成，不能以数字开头
//        String usernameReg = "^[a-zA-Z][a-zA-Z0-9]{5,9}$"; // 修改：将JavaScript正则转换为Java String
//        // 定义正则表示字符串的规则，密码规则是6-12位英文字母或数字组成
//        String userPwdReg = "^[a-zA-Z0-9]{6,12}$";   // 修改：将JavaScript正则转换为Java String
//        //定义临时变量存储用户名密码进行校验
//        String username = pm.get("username")[0];
//        String password = pm.get("password")[0];
//        if(username == null || !username.matches(usernameReg) || username.isEmpty()||
//                         password == null || !password.matches(userPwdReg) || password.isEmpty()){
//            req.setAttribute("msg","username or password incorrect");
//            req.getRequestDispatcher("login.jsp").forward(req, resp);
//            return; //验证失败直接退出
//        }
        //开始验证密码
        User user = serverProcess.loginUser(new User(pm.get("username")[0], pm.get("password")[0]));
        if (user!=null){
            //先获取Session对象在设置
            req.getSession().setAttribute("user",user);
            // 登录成功 跳转到Index.jsp
            resp.sendRedirect(getServletContext().getContextPath()+"/index.jsp"); //重定向
        }else{
            //登录失败，返回提示信息
                req.setAttribute("msg","Login Failed"); //
                req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

    }
    //登出处理
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getSession().invalidate();
//        req.getSession().removeAttribute("user");     也可以这样删除
        resp.sendRedirect(getServletContext().getContextPath()+"/login.jsp");
    }
    private void updatePwd(HttpServletRequest req, HttpServletResponse resp, Map<String, String[]> pm) throws Exception {
        System.out.println("update开始执行");
//        User user = new User(pm.get("username")[0], pm.get("password")[0]);
        String oldPassword = req.getParameter("oldPassword");
        try {
            //从会话域中获取已登录的用户对象
            User sysUser = (User) req.getSession().getAttribute("user");
            if (sysUser == null) {
                //如果sysUser为null，说明还未登录，返回null
                resp.getWriter().write("请登录");
                return;
            } else if (sysUser.getPassword().equals(MD5Util.encrypt(oldPassword))) {
                //如果用户本次输入的旧密码与已登录用户的密码一致，返回true
                System.out.println((sysUser.getUsername()+"---"+ pm.get("newPassword")[0]+"--"+ pm.get("oldPassword")[0]));
                //    public boolean updateUser(String userName, String oldPassword, String newPassword) throws Exception {
                //        int b = userDAO.updatePassword(userName, oldPassword, newPassword);
                boolean b = serverProcess.updateUser(sysUser.getUsername(), pm.get("oldPassword")[0], pm.get("newPassword")[0]);
                System.out.println("用户修改密码->新密码"+pm.get("newPassword")[0]+"-------"+pm.get("oldPassword")[0]);
                if (b) {
                        //改密成功后直接退出
                        logout(req,resp);
//                    resp.getWriter().write("true");
                } else {
                    //如果用户本次输入的旧密码与已登录用户的密码不一致，返回false
                    resp.getWriter().write("false");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获得session


    }
    private void checkUsername(HttpServletRequest req, HttpServletResponse resp, Map<String, String[]> pm)throws ServletException, IOException {

    }
    private void checkPassword(HttpServletRequest req, HttpServletResponse resp, Map<String, String[]> pm)throws ServletException, IOException {
        //页面中需要给我们传什么数据
        //获取页面中输入的旧密码
        String oldPassword = req.getParameter("oldPassword");
        System.out.println(oldPassword);
        try {
            //从已登录的用户中获取用户名
            User sysUser = (User) req.getSession().getAttribute("User");
            if(sysUser == null){
                //如果用户没有登录，跳转到登录页面
                resp.sendRedirect("login.html");
            }else{
                resp.setContentType("text/html; charset=UTF-8");
                //先检查旧密码是否正确
                if(MD5Util.encrypt(oldPassword).equals(sysUser.getPassword())){
                    resp.getWriter().write("true");
                }else{
                    resp.getWriter().write("false");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void  deliveryList(HttpServletRequest request, HttpServletResponse response){
        try {
            //查询当前登录用户的userId对应的快递记录
            User sysUser = (User) request.getSession().getAttribute("user");
            List<Delivery> allDelivery = deliveryService.getAllDeliveryByUserId(sysUser.getId());

            //查询现有的所有快递公司
            List<Company> list = companyService.getAllCompany();
            //把所有快递公司的list集合，转为Map，key是company的id，value是company的name
            /*Map<Integer, String> map = list.stream().collect(Collectors.toMap(c -> c.getId(), c -> c.getCompanyName()));
            for (SysDelivery sysDelivery : allDelivery) {
                Integer companyId = sysDelivery.getCompanyId();
                Company company = new Company(companyId, map.get(companyId));
                sysDelivery.setCompany(company);
            }*/
            //key是快递公司的id，value是快递公司对象
            Map<Integer, Company> map = list.stream().collect(Collectors.toMap(Company::getId, c -> c));
            for (Delivery sysDelivery : allDelivery) {
                sysDelivery.setCompany(map.get(sysDelivery.getCompanyId()));
                System.out.println("======="+map.get(sysDelivery.getCompanyId()));
            }
            //测试打印

            request.setAttribute("allDelivery", allDelivery);
            request.getRequestDispatcher("/list.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
