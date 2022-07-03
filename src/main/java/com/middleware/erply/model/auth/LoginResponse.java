package com.middleware.erply.model.auth;

import lombok.Data;
import lombok.ToString;

import java.util.List;
@Data
@ToString
public class LoginResponse {
    public Status status;
    public List<Record> records;
}
