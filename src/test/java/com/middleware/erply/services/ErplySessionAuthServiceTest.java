package com.middleware.erply.services;

import com.middleware.erply.clients.AuthClient;
import com.middleware.erply.model.auth.LoginResponse;
import com.middleware.erply.model.auth.Record;
import com.middleware.erply.model.auth.SessionData;
import com.middleware.erply.model.auth.Status;
import com.middleware.erply.properties.AuthProperties;
import com.middleware.erply.repositories.SessionDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ErplySessionAuthServiceTest {
    @InjectMocks
    ErplySessionAuthService erplySessionAuthService;
    @Mock
    SessionDataRepository sessionDataRepository;
    @Mock
    AuthClient authClient;
    @Mock
    AuthProperties authProperties;

    @BeforeEach
    public void beforeEach() {
        LoginResponse loginResponse = new LoginResponse();
        Status status = new Status();
        status.setErrorCode(0);
        loginResponse.setStatus(status);

        ArrayList<Record> list = new ArrayList<>();
        Record record = new Record();
        list.add(record);
        record.setSessionKey("123");
        record.setSessionLength(60000);
        loginResponse.setRecords(list);
        Mockito.when(authProperties.getUsername()).thenReturn("username");
        Mockito.when(authProperties.getPassword()).thenReturn("password");

        Mockito.when(authClient.login(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito
                .anyInt())).thenReturn(loginResponse);

        Mockito.when(sessionDataRepository.saveAndFlush(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
    }

    @Test
    @DisplayName("initSessionData method retrieve new session data if current is outdated")
    public void testInitSessionDataMethodRetrieveNewSessionDataIfCurrentIsOutdated() {
        SessionData value = new SessionData();

        value.setValidTill(Instant.now().minusMillis(999999));
        Mockito.when(sessionDataRepository.findById(Mockito.any())).thenReturn(Optional.of(value));

        SessionData result = erplySessionAuthService.initSessionData();
        assertNotEquals(result, value);

        Mockito.verify(authClient, Mockito.times(1)).login(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito
                .anyInt());
        Mockito.verify(sessionDataRepository, Mockito.times(1)).saveAndFlush(Mockito.any());
    }

    @Test
    @DisplayName("initSessionData method do not perform save into database and do not login if session valid")
    public void testInitSessionDataMethodDoNotPerformSaveIntoDatabaseAndDoNotLoginIfSessionValid() {
        SessionData value = new SessionData();
        value.setValidTill(Instant.now().plusMillis(999999));
        Mockito.when(sessionDataRepository.findById(Mockito.any())).thenReturn(Optional.of(value));

        SessionData result = erplySessionAuthService.initSessionData();
        assertEquals(result, value);

        Mockito.verify(authClient, Mockito.times(0)).login(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito
                .anyInt());
        Mockito.verify(sessionDataRepository, Mockito.times(0)).saveAndFlush(Mockito.any());

    }

}
