package com.sparta.kakaotalkbackend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://43.201.71.248:8080", "http://localhost:8080", "http://localhost:3000")
                .allowedMethods("*")
                .exposedHeaders("Access_Token", "Refresh_Token")
                .allowCredentials(true)
                .maxAge(3000);
    }
}
