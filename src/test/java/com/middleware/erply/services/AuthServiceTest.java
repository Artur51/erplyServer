package com.middleware.erply.services;

import com.middleware.erply.controllers.TestAuthentication;
import com.middleware.erply.model.AuthRequest;
import com.middleware.erply.model.User;
import com.middleware.erply.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AuthServiceTest {
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    UserRepository userRepository;
    @Autowired
    AuthService authService;
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    JwtTokenService jwtTokenService;

    @BeforeEach
    private void beforeEach() {
        final User databaseUser = new User();
        Mockito.when(userRepository.saveAndFlush(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new TestAuthentication(databaseUser));
        Mockito.when(userRepository.findByUsername(databaseUser.getUsername())).thenReturn(Optional.of(databaseUser));
    }

    @Test
    @DisplayName("loadUserByUsername method retrieve user by user name from repository")
    public void testLoadUserByUsernameMethodRetrieveUserByUserNameFromRepository() {
        User user = new User();
        Mockito.when(userRepository.findOneByUsername(Mockito.any())).thenReturn(user);
        UserDetails result = authService.loadUserByUsername("test");
        assertEquals(user, result);
    }

    @Test
    @DisplayName("save method encode password and save user record")
    public void testSaveMethodEncodePasswordAndSaveUserRecord() {
        User user = new User();
        authService.save(user);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(Mockito.any());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("isUserExists method check record exists")
    public void testIsUserExistsMethodCheckRecordExists() {
        User user = new User();
        user.setUsername("username");
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));

        boolean result = authService.isUserExists(user);
        assertTrue(result);
    }

    @Test
    @DisplayName("login method login returns token")
    public void testLoginMethodLoginReturnsToken() {
        Mockito.when(jwtTokenService.generate(Mockito.any())).thenReturn("token");
        String result = authService.login(new AuthRequest("username", "password"));

        Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(Mockito.any());

        assertEquals("token", result);
    }

}
