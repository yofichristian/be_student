package com.eterna.yofi.demoyofi.controller;

import com.eterna.yofi.demoyofi.dto.ResourceResponse;
import com.eterna.yofi.demoyofi.dto.request.UserInfoCreateRequestDto;
import com.eterna.yofi.demoyofi.dto.request.UserInfoUpdateRequestDto;
import com.eterna.yofi.demoyofi.exception.AppsException;
import com.eterna.yofi.demoyofi.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserInfoService userInfoService;

    //    @GetMapping(path = {"","/",} )
    @GetMapping("/list")
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok().body(
                new ResourceResponse(userInfoService.findAll())
//                userInfoService.findAll()
        );
    }

    @GetMapping("/get/{login}")
    public ResponseEntity<Object> getData(@PathVariable("login") String login) {
        return ResponseEntity.ok().body(
                new ResourceResponse(userInfoService.findByLogin(login))
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUserInfo(@RequestBody UserInfoCreateRequestDto userInfoDto) throws AppsException {
        return ResponseEntity.ok().body(
                new ResourceResponse(userInfoService.createUserInfo(userInfoDto))
        );
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUserInfo(
            @RequestBody UserInfoUpdateRequestDto userInfoDto) throws AppsException {
        return ResponseEntity.ok().body(
                new ResourceResponse(
                        userInfoService.updateUserInfo(userInfoDto)
                )
        );
    }

    @GetMapping("/delete/{login}")
    public ResponseEntity<Object> deleteUserInfo(@PathVariable("login") String login) throws AppsException {
        userInfoService.deleteUserInfo(login);
        return ResponseEntity.ok().body(
            new ResourceResponse(
                    "success"
                    //userInfoService.deleteUserInfo(login)
                )
        );
    }
}
