package com.baicha.mywallpaper.interceptor;

import com.baicha.mywallpaper.model.Respons;
import com.baicha.mywallpaper.tools.JwtTool;
import com.baicha.mywallpaper.tools.RequestContextHolder;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;


// 自定义拦截器，将请求头中的token解析出来放到treadlocal中
@Component
public class ContextInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private Gson gson;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        // 从请求头获取用户ID
        String token = request.getHeader("Authorization");
        Object userId = jwtTool.validateToken(token);
        if (userId.equals(false)) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.setContentType("application/json;charset=utf-8");
            Respons error = Respons.error("登录信息已过期！");
            response.getWriter().write(gson.toJson(error));
            return false;
        }
        // 如果里面的token为null就拦截请求
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json;charset=utf-8");
            Respons error = Respons.error("请先登录！");
            response.getWriter().write(gson.toJson(error));
            return false;
        }
        // 将数据存入 ThreadLocal
        RequestContextHolder.set("userId", userId);
        // 继续请求处理
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        // 请求完成后清除 ThreadLocal 数据 防止内存泄漏
        RequestContextHolder.clear();
    }
}