package com.hackacode.themepark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CorsConfig {
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        // configuration.setAllowedOrigins(List.of("http://localhost:4200", "https://angular-springboot-*.vercel.app"));
//        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200"));
//        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
//        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Origin", "X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:4200");
        }
    };
}
}
