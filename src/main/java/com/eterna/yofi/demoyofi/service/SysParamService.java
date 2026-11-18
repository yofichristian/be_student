package com.eterna.yofi.demoyofi.service;

import com.eterna.yofi.demoyofi.dto.request.SysParamDto;
import com.eterna.yofi.demoyofi.entity.SysParam;
import com.eterna.yofi.demoyofi.exception.AppsException;

import java.util.List;

public interface SysParamService {

    List<SysParam> findAll();
    List<SysParam> findAllByHeader(String header);
    SysParam findByHeaderAndCode(String header, String code);
    SysParam createSysParam(SysParamDto sysParamDto) throws AppsException;
    SysParam updateSysParam(SysParamDto sysParamDto) throws AppsException;
    Object deleteSysParam(String sysParamId) throws AppsException;

}
