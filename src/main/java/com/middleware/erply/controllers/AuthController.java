package com.middleware.erply.controllers;

import com.middleware.erply.controllers.errors.Errors;
import com.middleware.erply.model.AuthRequest;
import com.middleware.erply.model.RegistrationRequest;
import com.middleware.erply.model.User;
import com.middleware.erply.services.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201", // HttpStatus.CREATED,
                            description = "User login with username and password. As the result jwt token is returned.") })
    @PostMapping(value = "/registration")
    public ResponseEntity<String> registration(
            @RequestBody @Valid RegistrationRequest request,
            BindingResult errors) {
        User user = new User();
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());

        if (authService.isUserExists(user)) {
            throw Errors.userAlreadyRegistered;
        }
        if (!user.getPassword().equals(request.getConfirmPassword())) {
            throw Errors.providedPasswordsMismatch;
        }

        authService.save(user);
        return new ResponseEntity<>("User created.", HttpStatus.CREATED);
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User login with username and password. As the result jwt token is returned."),
                    @ApiResponse(
                            responseCode = "401", // HttpStatus.UNAUTHORIZED,
                            description = "Wrong user credentials.")

            })
    @PostMapping(value = "/login", produces = "text/plain")
    public ResponseEntity<String> login(
            @RequestBody @Valid AuthRequest request,
            BindingResult errors) {
        String token = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(token);
    }

}
