package com.baicha.mywallpaper.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MyWallpaper API文档")  // 文档标题
                        .version("1.0.0")  // 版本
                        .description("这是一个使用SpringDoc生成的API文档示例")  // 描述
                );
    }
}
