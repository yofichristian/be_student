package com.eterna.yofi.demoyofi.dto.request;

import com.eterna.yofi.demoyofi.dto.BaseObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoCreateRequestDto extends BaseObject {

    private String username;
    private String fullname;
    private String password;
    private String role;

}
