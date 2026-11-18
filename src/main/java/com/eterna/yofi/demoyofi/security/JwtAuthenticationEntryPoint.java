package com.eterna.yofi.demoyofi.security;

import com.eterna.yofi.demoyofi.constant.SystemConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.eterna.yofi.demoyofi.service.BaseService.printDate;
import static com.eterna.yofi.demoyofi.service.BaseService.toJson;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L;

//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response,
//                         AuthenticationException authException) throws IOException {
//
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        Map<String, Object> data = new HashMap<>();
        data.put("path", request.getRequestURI());
        data.put("timestamp", printDate(new Date()));
        data.put("error", StringUtils.isEmpty(MDC.get(SystemConstant.ERROR_MESSAGE)) ? authException.getMessage() : MDC.get(SystemConstant.ERROR_MESSAGE));
        data.put("status", response.getStatus());

        response.getOutputStream().println(toJson(data));

    }

}
