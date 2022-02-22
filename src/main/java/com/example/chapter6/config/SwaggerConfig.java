package com.example.chapter6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.chapter6.api"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .useDefaultResponseMessages(false)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()));
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("API 문서")
                .description("API 문서의 내용")
                .termsOfServiceUrl("http://localhost:8080/term")
                .contact(new Contact("이름","http://localhost:8080","email@email.com"))
                .license("아무나 사용가능")
                .licenseUrl("http://localhost:8080/license")
                .version("0.0.1")
                .build();


    }
    private ApiKey apiKey(){
        return new ApiKey("Bearer","Authorization","header");
    }

    private SecurityContext securityContext(){
        return springfox.documentation.spi.service.contexts.SecurityContext.builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEveryThing");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Bearer",authorizationScopes));
    }
}