package com.smartship.backend.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
public class GlobalConfig implements WebMvcConfigurer {

    public final List<String> SECURED_PATHS = List.of("/api/v1/users");
    @Value("HvA")
    public String issuer;
    @Value("${smart.app.jwtExpirationMs}")
    public int tokenDurationOfValidity;
    @Value("${smart.app.jwtSecret}")
    private String passphrase;

    public String getPassphrase() {
        return passphrase;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:*", "https://smartship-fe-app-production.up.railway.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
                .exposedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
                .allowCredentials(true);
    }
}
