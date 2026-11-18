package com.eterna.yofi.demoyofi.security;


import com.eterna.yofi.demoyofi.entity.UserInfo;
import com.eterna.yofi.demoyofi.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j

public class JwtUserDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public JwtUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserInfo> optUserInfo = userInfoRepository.findByUsername(login);
        if (optUserInfo.isEmpty()) throw new UsernameNotFoundException("User not found");
        UserInfo userInfo = optUserInfo.get();
        if (userInfo.getStatus()!=1) throw new UsernameNotFoundException("User status is not active");

        String password = userInfo.getPlainpswd() == 1 ? PasswordEncoder.encode(userInfo.getPassword()) : userInfo.getPassword();

        return new JwtUserDetails(
                login,
                password,
                userInfo.getRole()
        );

    }

}
