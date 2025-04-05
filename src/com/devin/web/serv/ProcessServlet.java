package com.devin.web.serv;

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
import java.text.SimpleDateFormat;
import java.util.Date;
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
//            case "/deliveryList" ->{deliveryList(req,resp);            }
            case "/deliveryList" ->{list(req,resp);            }

            case "/deliveryPrepareAdd" ->{deliveryPrepareAdd(req,resp,parameterMap);            }
            case "/deliveryAdd" ->deliveryAdd(req,resp,parameterMap);
            case "/deliveryRemove"->deliveryRemove(req,resp,parameterMap);
            case "/deliveryEdit" ->{deliveryEdit(req,resp,parameterMap);
                }
            case "/deliveryBeforeUpdate"->{
                deliveryBeforeUpdate(req,resp,parameterMap);
            }
            case "/List"->{ list(req,resp);
            }
            default -> {
                resp.sendError(404);
                System.out.println("进入到default分支");
            }
        }
    }

    private void deliveryBeforeUpdate(HttpServletRequest request, HttpServletResponse response, Map<String, String[]> parameterMap) {
        try {
            String id = request.getParameter("id");
            Delivery delivery = deliveryService.getDetail(id);
            List<Company> companyList = companyService.getAllCompany();
            request.setAttribute("companyList",companyList);
            request.setAttribute("delivery",delivery);
            request.getRequestDispatcher("/edit.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deliveryEdit(HttpServletRequest request, HttpServletResponse response, Map<String, String[]> parameterMap) {
        try {
            Delivery delivery = new Delivery();
            delivery.setId(Integer.parseInt(request.getParameter("id")));
            delivery.setDeliveryName(request.getParameter("deliveryName"));
            User sysUser = (User) request.getSession().getAttribute("user");
            delivery.setUserId(sysUser.getId());
            delivery.setCompanyId(Integer.parseInt(request.getParameter("companyId")));
            delivery.setPhone(request.getParameter("phone"));
            delivery.setAddress(request.getParameter("address"));
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            delivery.setSendTime(sf.parse(request.getParameter("sendTime")));
            delivery.setState(Integer.parseInt(request.getParameter("state")));

            deliveryService.updateDelivery(delivery);
            request.getRequestDispatcher("/api/deliveryList").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String currentPageStr = request.getParameter("currentPage");
            String pageCountStr = request.getParameter("pageCount");
            int currentPage = currentPageStr == null ? 1 : Integer.parseInt(currentPageStr);
            int pageCount = pageCountStr == null  ? 5 : Integer.parseInt(pageCountStr);
            User sysUser = (User) request.getSession().getAttribute("user");
            long totalCount = deliveryService.getTotal(sysUser.getId());

            int lastPage = (int) (totalCount % pageCount==0? totalCount / pageCount: totalCount / pageCount+1);
            if(currentPage>lastPage && totalCount!=0){
                currentPage=lastPage;
            }
            List<Delivery> deliveries = deliveryService.findList(sysUser.getId(), currentPage, pageCount);

            List<Company> companyList = companyService.getAllCompany();
            Map<Integer, Company> map = companyList.stream().collect(Collectors.toMap(c -> c.getId(), c -> c));
            for (Delivery delivery : deliveries) {
                delivery.setCompany(map.get(delivery.getCompanyId()));
            }

            request.setAttribute("deliveries",deliveries);
            request.setAttribute("currentPage",currentPage);
            request.setAttribute("pageCount",pageCount);
            request.setAttribute("lastPage",lastPage);

            request.getRequestDispatcher("/list.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deliveryRemove(HttpServletRequest request, HttpServletResponse response, Map<String, String[]> parameterMap) {
        try {
            String id = request.getParameter("id");
            boolean flag = deliveryService.deleteDelivery(Integer.parseInt(id));
            if(flag){
                response.getWriter().write("true");
            }else{
                response.getWriter().write("false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deliveryAdd(HttpServletRequest request, HttpServletResponse response,Map<String, String[]> pm) throws ServletException, IOException {
            Delivery sysDelivery = null;
            try {
                String deliveryName = pm.get("deliveryName")[0];
                Integer companyId = Integer.parseInt(pm.get("companyId")[0]);
                String phone = pm.get("phone")[0];
                String address = pm.get("address")[0];

                String sendTimeStr = pm.get("sendTime")[0];
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date sendTime = sf.parse(sendTimeStr);

                Integer state = Integer.parseInt(pm.get("state")[0]);

                User sysUser = (User) request.getSession().getAttribute("user");
                System.out.println("调试信息sysUser = "+sysUser);
                //这里填写0，是因为DAO层不用id值，id是自增的
                sysDelivery = new Delivery(0,deliveryName,sysUser.getId(),companyId,phone,address,sendTime,state,null);
                System.out.println("创建的订单信息-----"+sysDelivery.toString());
                boolean flag = deliveryService.saveDelivery(sysDelivery);
                if(flag){
                    //跳转到列表页
                    //跳转页面
                    System.out.println("跳转页面到/deliveryList");
//                    request.getRequestDispatcher(getServletContext().getContextPath()+"/deliveryList").forward(request,response);
                    request.getRequestDispatcher("/api/deliveryList").forward(request, response);
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                //失败，跳转会添加页面
                List<Company> list = companyService.getAllCompany();
                request.setAttribute("allCompany", list);
                request.setAttribute("sysDelivery",sysDelivery);
                request.getRequestDispatcher("/deliveryAdd").forward(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    private void deliveryPrepareAdd(HttpServletRequest req, HttpServletResponse resp,Map<String, String[]> mp) {
        try {
            //进入添加页面之前，要查询现有的所有快递公司
            List<Company> list = companyService.getAllCompany();
            req.setAttribute("allCompany", list);
            //跳转到添加页面add.jsp
            req.getRequestDispatcher("/add.jsp").forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp, Map<String, String[]> pm) throws Exception {

    if (pm.containsKey("username") && pm.containsKey("password")&&pm.containsKey("nickname")&&pm.containsKey("rePassword")){

        boolean f = serverProcess.addUser(new User(pm.get("username")[0],pm.get("password")[0],pm.get("nickname")[0]));
            if(f){
                resp.sendRedirect(getServletContext().getContextPath()+"/login.jsp");
            }else {
                req.setAttribute("msg","Register Failed"); //
                req.getRequestDispatcher("/register.jsp" +
                        "").forward(req, resp);
            }
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
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
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
    private void deliveryList(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 查询当前登录用户的userId对应的快递记录
            User sysUser = (User) request.getSession().getAttribute("user");
            System.out.println("当前登录用户: " + (sysUser != null ? sysUser.toString() : "未登录"));

            List<Delivery> allDelivery = deliveryService.getAllDeliveryByUserId(sysUser.getId());
            System.out.println("根据用户ID " + sysUser.getId() + " 查询到的快递记录数量: " + allDelivery.size());
            // 打印所有快递记录的详细信息
            for (Delivery delivery : allDelivery) {
                System.out.println("快递记录: " + delivery.toString());
            }

            // 查询现有的所有快递公司
            List<Company> list = companyService.getAllCompany();
            System.out.println("查询到的快递公司数量: " + list.size());
            // 打印所有快递公司信息
            for (Company company : list) {
                System.out.println("快递公司: " + company.toString());
            }

            // 把所有快递公司的list集合，转为Map，key是company的id，value是company对象
            Map<Integer, Company> map = list.stream().collect(Collectors.toMap(Company::getId, c -> c));
            System.out.println("快递公司Map大小: " + map.size());
            // 打印Map中的键值对
            map.forEach((id, company) -> System.out.println("Map Entry - ID: " + id + ", Company: " + company.toString()));

            // 为每条快递记录设置对应的公司对象并调试输出
            for (Delivery sysDelivery : allDelivery) {
                Company company = map.get(sysDelivery.getCompanyId());
                sysDelivery.setCompany(company);
                System.out.println("为快递记录 [ID: " + sysDelivery.getId() + "] 设置公司: " +
                        (company != null ? company.toString() : "未找到对应公司, CompanyId: " + sysDelivery.getCompanyId()));
            }

            // 将数据存入request并转发
            request.setAttribute("allDelivery", allDelivery);
            System.out.println("将快递记录列表存入request，准备转发到 /list.jsp");
            request.getRequestDispatcher("/list.jsp").forward(request, response);
            System.out.println("转发到 /list.jsp 完成");

        } catch (Exception e) {
            System.err.println("发生异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
