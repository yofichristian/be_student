package com.eterna.yofi.demoyofi.dto;

import com.eterna.yofi.demoyofi.constant.AppErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
public class ResourceResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private Boolean success = true;
    private String responseCode = "00";
    private String responseMessage = "Success";
    private String responseTime = sdf.format(new Date());
    private Object data = null;

    public ResourceResponse(Object data) {
        this.data = data;
    }

    public ResourceResponse(String responseCode, String responseMessage, Object data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
        this.success = "00".equals(this.responseCode);
    }

    public ResourceResponse(AppErrorCode appErrorCode, Object data) {
        this.responseCode = appErrorCode.getCode();
        this.responseMessage = appErrorCode.getDescription();
        this.data = data;
        this.success = "00".equals(this.responseCode);
    }

    public static ResourceResponse sendError(Object data) {
        return new ResourceResponse(AppErrorCode.ERROR, data);
    }

}
