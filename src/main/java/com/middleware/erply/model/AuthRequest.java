package com.middleware.erply.model;

import lombok.ToString;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Validated
public class AuthRequest {
    @Size(min = 4, max = 50, message = "User name must be not less then {min} and not longer then {max} symbols length.")
    String username;
    @Size(min = 6, max = 50, message = "Password must be not less then {min} and not longer then {max} symbols length.")
    String password;
}
