package com.middleware.erply.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtTokenServiceTest {

    @Test
    @DisplayName("getVerifyedUsername method should return provided username")
    public void testMethodShouldReturnProvidedUsername() {
        JwtTokenService jwtTokenService = new JwtTokenService();
        jwtTokenService.secret = "testsecret";
        jwtTokenService.expirationHours = 2;

        String token = jwtTokenService.generate("testUserName");
        String username = jwtTokenService.getVerifyedUsername(token);
        assertEquals("testUserName", username);
    }
}
