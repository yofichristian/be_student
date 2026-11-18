package com.eterna.yofi.demoyofi.security;


import com.eterna.yofi.demoyofi.dto.request.LoginRequest;

public interface AuthService {

    Object login(LoginRequest request);
    Object refreshToken();
//    Object decodePassword(String password);
//    Object validateToken(String token);

}
