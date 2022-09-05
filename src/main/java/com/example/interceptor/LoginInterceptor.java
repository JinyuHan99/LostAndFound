package com.example.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getServletPath();
        //getServletPath()方法返回请求行中的资源名部分,比getRequestURI和getRequestURL解析更安全
        System.out.println("interceptor running");
        System.out.println(uri);
        //判断当前请求地址是否登录地址
        if (uri.contains("login") || uri.contains("register") ) {
            //登录注册请求，直接放行
//            System.out.println(uri);
            return true;
        } else {
            //判断用户是否登录
//            System.out.println("user hasn't logged in");
//            System.out.println(request.getSession().getAttribute("username"));
            if (request.getSession().getAttribute("username") != null) {
                //说明已经登录，放行
                return true;
            } else {
                //没有登录，重定向到登录界面
                response.sendRedirect(request.getContextPath() + "/pages/login_client.html");
            }
        }

        //默认拦截
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("posthandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("aftercompletion");
    }
}
