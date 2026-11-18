package com.eterna.yofi.demoyofi.service.impl;

import com.eterna.yofi.demoyofi.constant.UserStatus;
import com.eterna.yofi.demoyofi.dto.request.UserInfoCreateRequestDto;
import com.eterna.yofi.demoyofi.dto.request.UserInfoUpdateRequestDto;
import com.eterna.yofi.demoyofi.entity.UserInfo;
import com.eterna.yofi.demoyofi.exception.AppsException;
import com.eterna.yofi.demoyofi.repository.UserInfoRepository;
import com.eterna.yofi.demoyofi.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private static final String modulename = "user";

    private final UserInfoRepository userInfoRepository;

    @Override
    public List<UserInfo> findAll() {
        return userInfoRepository.findAll();
    }

    @Override
    public UserInfo findByLogin(String login) {
        Optional<UserInfo> userInfoOpt = userInfoRepository.findByUsername(login);
        if (userInfoOpt.isPresent()) {
            return userInfoOpt.get();
        }
        return null;
    }

    @Override
    public UserInfo createUserInfo(UserInfoCreateRequestDto userInfoDto) throws AppsException {
        try {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userInfoDto, userInfo);
            userInfo.setId(UUID.randomUUID());
            userInfo.setStatus(UserStatus.ACTIVE.getCode());
            userInfo.setPlainpswd(1);
            userInfo.setCreateddate(LocalDateTime.now());

            return userInfoRepository.save(userInfo);
        } catch (Exception e) {
            log.error("Error create new User {} {}", userInfoDto.toString(), e.getMessage(), e);
            throw new AppsException("Error create new User " + userInfoDto.getUsername(), userInfoDto);
        }
    }

    @Override
    public UserInfo updateUserInfo(UserInfoUpdateRequestDto userInfoUpdateRequestDto) throws AppsException {
        try {
            UserInfo userInfo = findByLogin(userInfoUpdateRequestDto.getUsername());

            if (userInfo == null) {
                throw new AppsException("User " + userInfoUpdateRequestDto.getRole() + " is not found", userInfoUpdateRequestDto);
            }

//            if (StringUtils.isNotEmpty(userInfoUpdateRequestDto.getUsername())) {
//                userInfo.setUsername(userInfoUpdateRequestDto.getUsername());
//            }
//            if (userInfoUpdateRequestDto.getRole() != null) {
//                userInfo.setRole(userInfoUpdateRequestDto.getRole());
//            }
//            if (StringUtils.isNotEmpty(userInfoUpdateRequestDto.getPassword())) {
//                userInfo.setPassword(userInfoUpdateRequestDto.getPassword());
//                userInfo.setPlainpswd(1);
//            }
//
//            userInfo.setStatus(userInfoUpdateRequestDto.getStatus());

            BeanUtils.copyProperties(userInfoUpdateRequestDto, userInfo);
            userInfo.setUpdateddate(LocalDateTime.now());

            return userInfoRepository.save(userInfo);
//            return userInfoRepository.save(userInfo);

        } catch (AppsException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error update user info {} {}", userInfoUpdateRequestDto.getUsername(), e.getMessage(), e);
            throw new AppsException("Error update user info " + userInfoUpdateRequestDto.getUsername(), userInfoUpdateRequestDto);
        }
    }

    @Override
    public void deleteUserInfo(String login) throws AppsException {
        try {
            userInfoRepository.deleteByUsername(login);

//            UserInfo userInfo = findByLogin(login);
//
//            if (userInfo == null) throw new AppsException("User " + login + " is not found", login);
//
//            userInfo.setStatus(UserStatus.DELETED.getCode());
//            userInfo.setUpdateddate(LocalDateTime.now());
//
//            return userInfoRepository.save(userInfo);
        } catch (AppsException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error delete user info {} {}", login, e.getMessage(), e);
            throw new AppsException("Error delete user  " + login, login);
        }
    }

}
