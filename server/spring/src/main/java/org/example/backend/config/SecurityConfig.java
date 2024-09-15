package org.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/oauth2/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2Login(withDefaults())
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessHandler((exchange, authentication) -> {
                                    exchange.getExchange().getResponse().setStatusCode(HttpStatus.FOUND);
                                    exchange.getExchange().getResponse().getHeaders().setLocation(URI.create("https://accounts.google.com/Logout"));
                                    return Mono.empty();
                                })
                );
        return http.build();
    }
}
