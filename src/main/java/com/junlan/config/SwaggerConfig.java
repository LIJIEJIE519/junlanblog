package com.junlan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author LJ
 * @Date 2020/11/26
 * msg Swagger 配置, UI
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket jlApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.junlan.controller"))
                .paths(PathSelectors.any())
                .build()
                // 配置统一参数，具体操作涉及见securityContexts
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("JunLan Swagger API")
                .description("JunLan Swagger UI")
                .termsOfServiceUrl("http://localhost:8080")
                .version("1.0")
                .build();
    }

    // 全站统一参数配置，一般是token。
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList= new ArrayList();
        apiKeyList.add(new ApiKey("token", "token", "header"));
        return apiKeyList;
    }

    //  配置应用于securitySchemes操作（通过正则表达式）和HTTP方法。
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth())
                //过滤要验证的路径
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build());
        return securityContexts;
    }

    // 增加全局认证
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        // 验证增加, 名称同securitySchemes()中header写入token
        securityReferences.add(new SecurityReference("token", authorizationScopes));
        return securityReferences;
    }


    // 全局参数配置
    private List<Parameter> globalParameters() {
        List<Parameter> parameters = new ArrayList<>();
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        // header query cookie
        parameterBuilder.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false);
        parameters.add(parameterBuilder.build());
        return parameters;
    }
}
