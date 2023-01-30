package com.example.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


//配置类注解
@Configuration

@EnableOpenApi
public class SwaggerConfig {

    @Value("${swagger.enabled}")
    Boolean swaggerEnabled;
    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.OAS_30)

                .apiInfo(apiInfo())

                .enable(swaggerEnabled)

                .select()

                .apis(RequestHandlerSelectors.basePackage("com.example.blog.web"))

                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("blog接口文档")
                .description("本接口适用与个人博客项目")
                .contact(new Contact("微什么", "https://www.my.blog.cn", "admin@lzzy.net"))
                .version("V1.0")
                .build();
    }
}
