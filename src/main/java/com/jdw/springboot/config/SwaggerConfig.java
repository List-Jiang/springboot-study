package com.jdw.springboot.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi actuatorApi() {
        return GroupedOpenApi.builder()
                .group("actuator")
                .pathsToMatch("/actuator", "/actuator/**")
                .build();
    }

    @Bean
    public GroupedOpenApi hisApi() {
        return GroupedOpenApi.builder()
                .group("用户管理")
                .pathsToMatch("/sys/user", "/sys/user/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(" Spring Boot 学习项目")
                        .description("Spring Boot 学习项目注释")
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("团队介绍")
                        .url("https://list-jiang.github.io/"));
    }
}
