package com.middleware.erply.services;

import com.middleware.erply.clients.AuthClient;
import com.middleware.erply.model.auth.LoginResponse;
import com.middleware.erply.model.auth.Record;
import com.middleware.erply.model.auth.SessionData;
import com.middleware.erply.properties.AuthProperties;
import com.middleware.erply.repositories.SessionDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ErplySessionAuthService {
    private final AuthClient authClient;
    private final SessionDataRepository sessionDataRepository;
    private final AuthProperties authProperties;

    private SessionData sessionData;

    final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init() {
        SessionData sessionData = initSessionData();
        long delay = sessionData.getValidTill().minusMillis(Instant.now().toEpochMilli()).toEpochMilli();
        if(delay > 0){
            executor.schedule(this::init, delay, TimeUnit.MILLISECONDS);
        }
    }

    public SessionData initSessionData() {
        sessionData = sessionDataRepository.findById(authProperties.getUsername()).orElse(null);

        if (sessionData == null || sessionData.getValidTill().isBefore(Instant.now())) {
            LoginResponse result = authClient.login(authProperties.getClientCode(), //
                    authProperties.getUsername(), //
                    authProperties.getPassword(), //
                    "verifyUser", //
                    authProperties.getSendContentType());

            if (result.getStatus().getErrorCode() == 0
                    && result.getRecords() != null && !result.getRecords().isEmpty()) {
                Record record = result.getRecords().get(0);
                String sessionKey = record.getSessionKey();
                int sessionLength = record.getSessionLength();

                sessionData = SessionData.builder()
                        .username(authProperties.getUsername())
                        .sessionKey(sessionKey)
                        .validTill(Instant.now().plusSeconds(sessionLength))
                        .build();
                sessionData = sessionDataRepository.saveAndFlush(sessionData);
            } else {
                throw new RuntimeException("Session data is not obtained.");
            }
        }
        return sessionData;
    }

    public String getSessionKey() {
        return sessionData == null ? null : sessionData.getSessionKey();
    }
}
