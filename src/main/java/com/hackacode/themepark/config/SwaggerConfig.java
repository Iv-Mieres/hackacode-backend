package com.hackacode.themepark.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI themeparkOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Crazy Land Themepark API")
                        .description("Api rest application for thematic park administration")
                        .version("v1.0.0")
                        .license(new License().name("Crazy Land 1.0").url("http://crazy-land.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Themepark Documentation")
                        .url("https://crazy-land.com/docs"));
    }
}
