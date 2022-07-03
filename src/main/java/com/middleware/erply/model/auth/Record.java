package com.middleware.erply.model.auth;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Record {
    public String userID;
    public String userName;
    public String employeeID;
    public String employeeName;
    public String groupID;
    public String groupName;
    public String sessionKey;
    public int sessionLength;
    public String loginUrl;
}
