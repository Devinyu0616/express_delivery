package com.devin.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = "/*") // 拦截所有请求
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取请求的 URI
        String uri = request.getRequestURI();


        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length()); // 去掉上下文路径后的部分

        HttpSession session = request.getSession(false);
        Object user = (session != null) ? session.getAttribute("user") : null;

        // 定义允许未登录访问的资源
        boolean isLoginPage = path.endsWith("/login.jsp");
        boolean isRegisterPage = path.endsWith("/register.jsp");
        boolean isApiLogin = path.endsWith("/api/login");
        boolean isApiRegister = path.endsWith("/api/register");

        // 定义静态资源路径
        boolean isStaticResource = path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/");

        // 未登录时
        if (user == null) {
            if (isLoginPage || isRegisterPage || isApiLogin || isApiRegister || isStaticResource) {
                // 放行登录、注册页面、相关 API 以及静态资源
                filterChain.doFilter(request, response);
            } else {
                // 重定向到登录页面
                response.sendRedirect(contextPath + "/login.jsp");
            }
        } else {
            // 已登录，放行所有请求
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}