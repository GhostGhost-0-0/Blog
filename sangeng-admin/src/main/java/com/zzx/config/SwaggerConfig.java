package com.zzx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @BelongProject: SGBlog
 * @BelongPackage: com.zzx.config
 * @Author: 那个小楠瓜
 * @CreateTime: 2022-08-23 16:14
 * @Description: Swagger 配置类
 * @Version: 1.0
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zzx.controller"))
                .build();
    }

    public ApiInfo apiInfo() {
        Contact contact = new Contact("楠瓜博客", "www.nan.com", "295375895@qq.com");
        return new ApiInfoBuilder()
                .title("nan 个人博客后台文档")
                .description("个人博客的相关接口文档")
                .contact(contact)
                .version("0.0.1")
                .build();
    }
}
