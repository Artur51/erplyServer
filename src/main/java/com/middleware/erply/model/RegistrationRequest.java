package com.middleware.erply.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

@Data
@Validated
@EqualsAndHashCode(callSuper = true)
public class RegistrationRequest extends AuthRequest {
    @Size(min = 6, max = 50, message = "Password must be not less then {min} and not longer then {max} symbols length.")
    private String confirmPassword;
}
