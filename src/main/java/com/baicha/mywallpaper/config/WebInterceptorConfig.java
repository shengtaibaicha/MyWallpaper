package com.baicha.mywallpaper.config;

import com.baicha.mywallpaper.interceptor.ContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private ContextInterceptor contextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(contextInterceptor)
                .excludePathPatterns("/wallpaper/file/find/**", "/wallpaper/kaptcha/**", "/wallpaper/file/tag/**")// 排除的路径
                .addPathPatterns("/wallpaper/file/**"); // 拦截的路径
    }
}