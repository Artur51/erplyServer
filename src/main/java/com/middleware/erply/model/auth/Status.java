package com.middleware.erply.model.auth;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Status{
    public String request;
    public int requestUnixTime;
    public String responseStatus;
    public int errorCode;
    public double generationTime;
    public int recordsTotal;
    public int recordsInResponse;
}