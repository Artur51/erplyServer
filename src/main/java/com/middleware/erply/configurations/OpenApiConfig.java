package com.middleware.erply.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")

@OpenAPIDefinition(
        info = @Info(
                title = "Erply middleware",
                version = "1.0.0",
                contact = @Contact(
                        name = "contact name",
                        email = "mockEmail@server.com",
                        url = "https://www.example.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"),
                termsOfService = "${tos.uri}",
                description = "Test application to execute product records crud operations.")
)
public class OpenApiConfig {
}
