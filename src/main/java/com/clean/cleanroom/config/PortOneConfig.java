package com.clean.cleanroom.config;


import com.siot.IamportRestClient.IamportClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PortOneConfig {

    @Value("${portone.api.url}")
    private String apiUrl;

    @Value("${portone.api.key}")
    private String apiKey;

    @Value("${portone.api.secret}")
    private String apiSecret;

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, apiSecret, apiUrl);

    }
}