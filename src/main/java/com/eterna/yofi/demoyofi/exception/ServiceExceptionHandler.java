package com.eterna.yofi.demoyofi.exception;

import com.eterna.yofi.demoyofi.constant.AppErrorCode;
import com.eterna.yofi.demoyofi.dto.ResourceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({AppsException.class})
    public ResponseEntity<Object> handleServiceExceptions(
            AppsException e,
            WebRequest request
    ) {
        return ResponseEntity.ok().body(
                new ResourceResponse(e.getErrorCode(), e.getMessage(), e.getData())
        );
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                errors
        );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllExceptions(
            Exception e,
            WebRequest request
    ) {

        AppErrorCode appErrorCode = AppErrorCode.getByCode(e.getMessage());
        if (appErrorCode != null) {
            log.error("Error code : {} - {}", e.getMessage(), appErrorCode.getDescription());
            return ResponseEntity.ok().body(
                    new ResourceResponse(appErrorCode.getCode(), appErrorCode.getDescription(), null)
            );
        }

        log.error("Error code : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResourceResponse(AppErrorCode.ERROR, e.getMessage())
        );
    }


}
