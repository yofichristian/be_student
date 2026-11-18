package com.eterna.yofi.demoyofi.dto.request;

import com.eterna.yofi.demoyofi.dto.BaseObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysParamDto extends BaseObject {

    private String id;
    private String header;
    private String code;
    private String value;
    private Double valueint;
    private String description;
    private Integer status;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
