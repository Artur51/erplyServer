package com.middleware.erply.configurations;

import com.middleware.erply.properties.AuthProperties;
import com.middleware.erply.services.ErplySessionAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class AuthRequestInterceptor implements RequestInterceptor {
    @Autowired
    private ErplySessionAuthService authService;
    @Autowired
    private AuthProperties authProperties;

    @Override
    public void apply(
            RequestTemplate template) {
        template.header("clientCode", "" + authProperties.getClientCode());
        template.header("sessionKey", authService.getSessionKey());
    }

}
