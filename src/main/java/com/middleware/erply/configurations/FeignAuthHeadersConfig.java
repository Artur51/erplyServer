package com.middleware.erply.configurations;

import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

public class FeignAuthHeadersConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new AuthRequestInterceptor();
    }
}