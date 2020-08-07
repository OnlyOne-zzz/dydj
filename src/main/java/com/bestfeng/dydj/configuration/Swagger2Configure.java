package com.bestfeng.dydj.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Configuration
@EnableSwagger2
public class Swagger2Configure {


    public List<Parameter> createParameters() {
        ParameterBuilder token = new ParameterBuilder()
                .name("fw-access-token")
                .description("认证token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false);
        return Collections.singletonList(token.build());
    }


    @Bean
    public Docket createRestDydjApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("点约到家PI")
                .description("")
                .contact(new Contact("jinzhuan", "", ""))
                .version("1.0")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("dydj-api")
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("com.bestfeng.dydj.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(createParameters());
    }

    @Bean
    public Docket createAuthRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("权限API")
                .description("")
                .contact(new Contact("jinzhuan", "", ""))
                .version("1.0")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("auth-api")
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("org.aurochsframework.boot.authorization.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(createParameters());
    }

    @Bean
    public Docket createOrgRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("机构API")
                .description("")
                .contact(new Contact("jinzhuan", "", ""))
                .version("1.0")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("org-api")
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("org.aurochsframework.boot.org.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(createParameters());
    }
}
