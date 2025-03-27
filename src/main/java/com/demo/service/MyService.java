package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.config.ApiKeyProvider;

@Service
public class MyService {
    @Autowired
    private ApiKeyProvider apiKeyProvider;

    public void printApiKey() {
        System.out.println("API Key: " + apiKeyProvider.getApiKey());
    }
}
