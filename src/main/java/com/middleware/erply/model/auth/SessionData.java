package com.middleware.erply.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@Table(name = "session_data")
@NoArgsConstructor
@AllArgsConstructor
public class SessionData {
    @Id
    String username;

    @Column(name = "session_key")
    String sessionKey;

    @Column(name = "valid_till")
    Instant validTill;
}
