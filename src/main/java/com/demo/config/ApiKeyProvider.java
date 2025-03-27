package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyProvider {
    @Value("${api.key}") // Reads from application.properties
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}