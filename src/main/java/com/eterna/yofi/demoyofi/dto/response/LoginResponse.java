package com.eterna.yofi.demoyofi.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
//@Builder
public class LoginResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String responseCode = "00";
    private String responseMessage = "Success";
    private String username;
    private String accessToken;
    private String expiredDate;
    private String xrequestid = UUID.randomUUID().toString();
//    private String message;

    public LoginResponse(String username, String message) {
        this.responseCode = "99";
        this.username = username;
        this.responseMessage = message;
    }

    public LoginResponse(String username, String accessToken, String expiredDate) {
        this.username = username;
        this.accessToken = accessToken;
        this.expiredDate = expiredDate;
    }

}
