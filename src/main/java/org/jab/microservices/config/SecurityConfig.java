package org.jab.microservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/api/**"
    };

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {

        // Add a filter with Authorization on header
        return http
                .addFilterAt(new AuthFilter(),SecurityWebFiltersOrder.AUTHORIZATION)
                .authorizeExchange()
                // Passing a white list endpoint, do not need to
                // authenticate
                .pathMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyExchange().authenticated()
                .and().build();
    }
}