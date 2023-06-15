package com.hackacode.themepark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
//    @Bean
//    public Docket api() {
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.hackacode.themepark.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(getApiInfo());
//    }
//
//    private ApiInfo getApiInfo() {
//        return new ApiInfo(
//                "King Park",
//                "Api de administracion y venta de tickets de juegos tematicos",
//                "1.0",
//                "https://kingpark.com/",
//                new Contact("kingpark", "https://kingpark.com", "kingpark@gmail.com"),
//                "King Park SRL License",
//                "https://kingpark.com/license",
//                Collections.emptyList()
//        );
//    }
}
