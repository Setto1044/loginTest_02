package com.sns.my.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests((auth)-> auth
                .requestMatchers("/login", "register").permitAll()
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated()
        );

        http.formLogin((auth) -> auth
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main")
                .permitAll()
        );

        http.csrf((auth) -> auth.disable());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
