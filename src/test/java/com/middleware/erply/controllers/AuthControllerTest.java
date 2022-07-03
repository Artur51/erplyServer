package com.middleware.erply.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.middleware.erply.model.AuthRequest;
import com.middleware.erply.model.User;
import com.middleware.erply.repositories.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AuthControllerTest {
    static final String LOGIN_URL = "/auth/login";
    public static final String TEST_USER_PASSWORD = "password1";
    public static final String TEST_USERNAME = "userName1";

    @Autowired
    AuthController authController;

    @MockBean
    AuthenticationManager authenticationManager;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected UserRepository userRepository;

    @Autowired
    protected BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    private void beforeEach() {
        final User databaseUser = getRegisteredUser(passwordEncoder, true);
        Mockito.when(userRepository.saveAndFlush(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(new TestAuthentication(databaseUser));
        Mockito.when(userRepository.findByUsername(databaseUser.getUsername())).thenReturn(Optional.of(databaseUser));
    }

    @SneakyThrows
    public static MockHttpServletRequestBuilder createLoginPostRequest(
            String username,
            String password) {
        AuthRequest authRequest = AuthRequest.builder().username(username).password(password).build();
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(authRequest);

        return MockMvcRequestBuilders.post(LOGIN_URL)//
                .content(requestJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    }

    @Test
    public void testWhenUserNameLengthTooShortShouldReturnError() throws Exception {
        mockMvc.perform(createLoginPostRequest("un", TEST_USER_PASSWORD)) //
                .andDo(MockMvcResultHandlers.print()) //
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())//
                .andExpect(MockMvcResultMatchers.content().string(//
                        org.hamcrest.Matchers.containsString("User name must be not less then 4 and not longer then 50 symbols length.")));
    }

    @Test
    public void testLoginForExistedUserShouldWork() throws Exception {
        final User user = getRegisteredUser(passwordEncoder);
        mockMvc.perform(createLoginPostRequest(user.getUsername(), user.getPassword())) //
                .andDo(MockMvcResultHandlers.print()) //
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public static User getRegisteredUser(BCryptPasswordEncoder passwordEncoder) {
        return getRegisteredUser(passwordEncoder, false);
    }

    public static User getRegisteredUser(
            BCryptPasswordEncoder passwordEncoder,
            boolean encodedPassword) {
        final User user = new User();
        user.setUsername(TEST_USERNAME);
        if (encodedPassword) {
            user.setPassword(passwordEncoder.encode(TEST_USER_PASSWORD));
        } else {
            user.setPassword(TEST_USER_PASSWORD);
        }
        return user;
    }
}
