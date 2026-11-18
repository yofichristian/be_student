package com.eterna.yofi.demoyofi.dto.request;

import com.eterna.yofi.demoyofi.dto.BaseObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoUpdateRequestDto extends BaseObject {

    private String username;
    private String password;
    private String fullname;
    private String role;
    private Integer status;

}
