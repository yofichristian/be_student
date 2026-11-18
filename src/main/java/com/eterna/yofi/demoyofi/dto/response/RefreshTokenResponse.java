package com.eterna.yofi.demoyofi.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
//@Builder
public class RefreshTokenResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessToken;
    private String expiredDate;

    public RefreshTokenResponse(String accessToken, String expiredDate) {
        this.accessToken = accessToken;
        this.expiredDate = expiredDate;
    }

}
