package com.middleware.erply.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.middleware.erply.model.RegistrationRequest;
import com.middleware.erply.services.AuthService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuthControllerRegistrationTest {
    @MockBean
    AuthService authService;

    @Autowired
    MockMvc mockMvc;
    final String REGISTRATION_URL = "/auth/registration";

    public static final String TEST_USER_PASSWORD = "password1";
    public static final String TEST_USERNAME = "userName1";

    private MockHttpServletRequestBuilder createRegistrationPostRequest(
            String confirmPassword) {
        String userName = TEST_USERNAME;
        String userPassword = TEST_USER_PASSWORD;
        return createRegistrationPostRequest(userName, userPassword, confirmPassword);
    }

    private MockHttpServletRequestBuilder createRegistrationPostRequest() {
        return createRegistrationPostRequest(TEST_USERNAME, TEST_USER_PASSWORD, TEST_USER_PASSWORD);
    }

    @SneakyThrows
    private MockHttpServletRequestBuilder createRegistrationPostRequest(String username, String password, String confirmPassword) {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setConfirmPassword(confirmPassword);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(request);

        return post(REGISTRATION_URL)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .content(requestJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    }

    @Test
    public void testProvidedPasswordsWhenNotEqualsShouldReturnError() throws Exception {
        mockMvc.perform(createRegistrationPostRequest("wrongPassword"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(
                        "Wrong credentials; please make sure provided passwords match.")));
        Mockito.verify(authService, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void testUserAlreadyRegisteredShouldReturnError() throws Exception {
        Mockito.when(authService.isUserExists(Mockito.any())).thenReturn(true);

        mockMvc.perform(createRegistrationPostRequest())
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(
                        "User already exists.")));
        Mockito.verify(authService, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(authService, Mockito.times(1)).isUserExists(Mockito.any());
    }

    @Test
    public void testWhenShortPasswordShouldReturnError() throws Exception {
        String data = "1";
        mockMvc.perform(createRegistrationPostRequest(TEST_USERNAME, data, data))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("Password must be not less then 6 and not longer then 50 symbols length.")));
    }

    @Test
    public void testInvalidLongPasswordLengthShouldReturnError() throws Exception {
        String data = "te";
        mockMvc.perform(createRegistrationPostRequest(TEST_USERNAME, data, data))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("Password must be not less then 6 and not longer then 50 symbols length.")));
    }

    @Test
    public void testWhenUsernameCorrectLengthShouldPass() throws Exception {
        String data = "someCorrectUserName";
        mockMvc.perform(createRegistrationPostRequest(data, TEST_USER_PASSWORD, TEST_USER_PASSWORD))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testWhenUsernameLengthTooShortShouldReturnError() throws Exception {
        String data = "mm";
        mockMvc.perform(createRegistrationPostRequest(data, TEST_USER_PASSWORD, TEST_USER_PASSWORD))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("User name must be not less then 4 and not longer then 50 symbols length.")));
    }

    @Test
    public void testUserNameLengthTooLongShouldReturnError() throws Exception {
        String data = "testString".repeat(2000);
        mockMvc.perform(createRegistrationPostRequest(data, TEST_USER_PASSWORD, TEST_USER_PASSWORD))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("User name must be not less then 4 and not longer then 50 symbols length.")));
    }
}
