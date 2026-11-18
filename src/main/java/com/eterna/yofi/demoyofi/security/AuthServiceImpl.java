package com.eterna.yofi.demoyofi.security;

import com.eterna.yofi.demoyofi.constant.SystemConstant;
import com.eterna.yofi.demoyofi.dto.request.LoginRequest;
import com.eterna.yofi.demoyofi.dto.response.LoginResponse;
import com.eterna.yofi.demoyofi.entity.UserInfo;
import com.eterna.yofi.demoyofi.service.BaseService;
import com.eterna.yofi.demoyofi.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final static String modulename = "security";

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserInfoService userInfoService;

    @Override
    public Object login(LoginRequest request) {
        try {
            UserInfo userInfo = userInfoService.findByLogin(request.getUsername());
            if  (userInfo == null) {
                return new LoginResponse(request.getUsername(), "username not found");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            if (!authentication.isAuthenticated())  //not authenticated
                return new LoginResponse(request.getUsername(), "password not authenticated");

            String token = jwtUtil.generateToken(request.getUsername());
            Date expiredDate = jwtUtil.getExpiredDate(token);

            return new LoginResponse(request.getUsername(), token, BaseService.printDate(expiredDate));

        } catch (BadCredentialsException e) {
            log.error("Error login failed {} {}", request.toString(),  e.getMessage(), e);
            return new LoginResponse(request.getUsername(), "incorrect password");
        } catch (Exception e) {
            log.error("Error login failed {} {}", request.toString(),  e.getMessage(), e);
            return new LoginResponse(request.getUsername(), "username not found");
//            return new LoginResponse(request.getUsername(),e.getMessage());
        }

    }

    @Override
    public Object refreshToken() {
        String username = MDC.get(SystemConstant.USERNAME);
        String token = jwtUtil.generateToken(username);
        Date expiredDate = jwtUtil.getExpiredDate(token);

        return new LoginResponse(username, token,
                BaseService.printDate(expiredDate));
    }

//    @Override
//    public Object decodePassword(String password) {
//        return null;
//    }
//
//    @Override
//    public Object validateToken(String token) {
//        return null;
//    }

}
