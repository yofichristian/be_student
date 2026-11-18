package com.eterna.yofi.demoyofi.service;

import com.eterna.yofi.demoyofi.dto.request.UserInfoCreateRequestDto;
import com.eterna.yofi.demoyofi.dto.request.UserInfoUpdateRequestDto;
import com.eterna.yofi.demoyofi.entity.UserInfo;
import com.eterna.yofi.demoyofi.exception.AppsException;

import java.util.List;

public interface UserInfoService {

    List<UserInfo> findAll();
    UserInfo findByLogin(String login);
    UserInfo createUserInfo(UserInfoCreateRequestDto userInfoUpdateRequestDto) throws AppsException;
    UserInfo updateUserInfo(UserInfoUpdateRequestDto userInfoUpdateRequestDto) throws AppsException;
    void deleteUserInfo(String login) throws AppsException;

}
