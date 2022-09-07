package com.bookstore.rubens.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.bookstore.rubens";
    private static final String API_TITLE = "Bookstore Manager";
    private static final String API_DESCRIPTION = "Bookstore Manager API";
    private static final String CONTACT_NAME = "Rubens Ferreira de Sousa";
    private static final String CONTACT_GITHUB = "https://github.com/RubensFsousa";
    private static final String CONTACT_EMAIL = "ruben.dev@gmail.com";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .version(API_DESCRIPTION)
                .description(API_DESCRIPTION)
                .contact(new Contact(CONTACT_NAME, CONTACT_GITHUB, CONTACT_EMAIL))
                .build();
    }
}