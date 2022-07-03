package com.middleware.erply.controllers.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Errors {

    public static final RuntimeException userAlreadyRegistered = new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists.");
    public static final RuntimeException providedPasswordsMismatch = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong credentials; please make sure provided passwords match.");
}