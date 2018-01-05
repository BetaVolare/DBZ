package com.didispace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("DBZ")
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.didispace.controller"))
                .paths(PathSelectors.any()).build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("保修管理系统低保真")
                .description("=========================================================================================")
                .termsOfServiceUrl("http://=================.com/")
                .contact("保修管理系统低保真")
                .version("1.0")
                .build();
    }
}
