package com.middleware.erply.clients;

import com.middleware.erply.model.auth.SessionData;
import com.middleware.erply.repositories.SessionDataRepository;
import com.middleware.erply.services.ErplySessionAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class ErplySessionBasedTest {

    @MockBean
    SessionDataRepository sessionDataRepository;

    @Autowired
    ErplySessionAuthService erplySessionAuthService;

    @BeforeEach
    public void beforeEach() {
        Mockito.when(sessionDataRepository.saveAndFlush(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        SessionData sessionData = erplySessionAuthService.initSessionData();
        assertNotNull(sessionData);
        assertNotNull(sessionData.getSessionKey());

        System.out.println("sessionData.getSessionKey() " + sessionData.getSessionKey());
    }
}
