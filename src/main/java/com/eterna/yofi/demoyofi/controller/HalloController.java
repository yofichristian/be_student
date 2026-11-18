package com.eterna.yofi.demoyofi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("hallo")
@RequiredArgsConstructor
@Slf4j
public class HalloController {

    private final SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping(path = {"","/",} )
    public ResponseEntity<Object> index() {
        String msg = String.format("HalloController, hello, time is %s", sdf.format(new Date()));
        log.info(msg);
        return ResponseEntity.ok().body(
                msg
        );
    }

}
