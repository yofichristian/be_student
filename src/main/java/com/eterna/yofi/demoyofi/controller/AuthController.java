package com.eterna.yofi.demoyofi.controller;

import com.eterna.yofi.demoyofi.dto.request.LoginRequest;
import com.eterna.yofi.demoyofi.security.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

//    private final SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final AuthService authService;

//    @GetMapping(path = {"","/",} )
//    public ResponseEntity<Object> index() {
//        String msg = String.format("AuthController, hello, time is %s", sdf.format(new Date()));
//        return ResponseEntity.ok().body(
//                msg
//        );
//    }

//    @PostMapping(path = {"","/",} )
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok().body(
                authService.login(request)
        );
    }

//    @GetMapping("/refreshtoken")
//    public ResponseEntity<Object> refreshtoken() {
//        return ResponseEntity.ok().body(
//                authService.refreshToken()
//        );
//    }

}
