package com.middleware.erply.repositories;

import com.middleware.erply.model.auth.SessionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionDataRepository extends JpaRepository<SessionData, String> {
}
