package com.myuan.post.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * @author liuwei
 * @date 2018/1/20 11:15
 * swagger配置类
 * 注意：swagger依赖中自带google-guava，pom文件不要再引入guava依赖，否则报错
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.myuan.post.controller")) //扫描指定controller的包生成API
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("码猿社区 RESTful API")
            .description("更多信息请浏览码猿社区")
            .termsOfServiceUrl("http://localhost:8080/")
            .contact("liuwei")
            .version("1.0")
            .build();
    }

}
