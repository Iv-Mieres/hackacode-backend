package com.hackacode.themepark.config;

import com.hackacode.themepark.config.filters.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class MySecurityFilterChain {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/token/**").permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/api/usuarios/por_nombre/**").permitAll())



                .authorizeHttpRequests(auth->
                                auth.requestMatchers("/api/compradores", "/api/empleados","/api/roles", "/api/usuarios").hasAnyRole("ADMINISTRADOR","GERENTE")
                        )
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/api/ventas", "/api/tickets","/api/ticket-details", "/api/juegos", "/api/horarios").hasAnyRole("VENTAS","GERENTE")
                )

                .authorizeHttpRequests(
                        auth -> auth.anyRequest().authenticated()
                )

                .sessionManagement(session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
