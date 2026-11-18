package com.eterna.yofi.demoyofi.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    private final static BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder(12);

    public static String encode(String data) {
        try {
            return encoder.encode(data);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "passwordCANfdafasffu8u4q535";
        }
    }

}
