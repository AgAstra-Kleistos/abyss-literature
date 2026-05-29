package com.abyss.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public OpenAPI openAPI() {
        log.info("初始化openAPI");
        return new OpenAPI()
                .info(new Info()
                        .title("北冥文学项目接口文档")  // 标题
                        .version("1.0")  // 版本
                        .description("后台管理系统API")  // 描述
                        .contact(new Contact().name("mirror"))  // 联系方式
                );
    }

    @Bean
    public GroupedOpenApi adminApi() {
        log.info("初始化adminApi");
        return GroupedOpenApi.builder() // 分组名称
                .group("admin 管理端") // 路径匹配
                .pathsToMatch("/admin/**")  // 忽略路径
                .build();
    }
}