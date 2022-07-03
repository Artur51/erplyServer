package com.middleware.erply.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.middleware.erply.model.auth.LoginResponse;

@FeignClient(url = "${authUrl}", name = "auth")
public interface AuthClient {

    @PostMapping
    LoginResponse login(
            @RequestParam(name = "clientCode") int clientCode,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "request") String request,
            @RequestParam(name = "sendContentType") int sendContentType
    );
}
