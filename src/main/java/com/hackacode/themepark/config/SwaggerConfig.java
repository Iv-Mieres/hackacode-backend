package com.hackacode.themepark.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI themeparkOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Fantasy Kingdom Themepark API")
                        .description("Aplicación de API Rest para la administracion de parque temático")
                        .version("v1.0.0")
                        .license(new License().name("Fantasy Kingdom 1.0").url("http://fantasy-kingdom.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentacion de la API")
                        .url("https://fantasy-kingdom.com/docs"));
    }
}
