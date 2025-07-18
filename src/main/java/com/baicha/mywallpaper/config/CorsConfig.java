package com.baicha.mywallpaper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 1. 创建CORS配置对象
        CorsConfiguration config = new CorsConfiguration();
        // 允许前端域名（替换为你的前端实际地址，如http://localhost:5173）
//        config.addAllowedOrigin("http://localhost:80");
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:8089");
        config.addAllowedOrigin("http://localhost:80");
//        config.addAllowedOrigin("*");
        // 允许携带Cookie（如果需要）
        config.setAllowCredentials(true);
        // 允许所有请求方法（GET、POST、PUT等）
        config.addAllowedMethod("*");
        // 允许所有请求头
        config.addAllowedHeader("*");
        // 允许暴露的响应头（如自定义头）
        config.addExposedHeader("*");

        // 2. 配置路径映射（所有接口都允许跨域）
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // /** 表示所有接口

        // 3. 返回CORS过滤器
        return new CorsFilter(source);
    }
}