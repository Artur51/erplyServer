package com.middleware.erply.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
@Data
public class AuthProperties {
    int clientCode;
    String username;
    String password;
    int sendContentType;
}
