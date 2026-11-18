package com.eterna.yofi.demoyofi.service.impl;

import com.eterna.yofi.demoyofi.dto.request.SysParamDto;
import com.eterna.yofi.demoyofi.entity.SysParam;
import com.eterna.yofi.demoyofi.exception.AppsException;
import com.eterna.yofi.demoyofi.repository.SysParamRepository;
import com.eterna.yofi.demoyofi.service.SysParamService;
import com.eterna.yofi.demoyofi.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SysParamServiceImpl implements SysParamService {

    private final static String modulename = "syspar";

    private final SysParamRepository sysParamRepository;

    @Override
    public List<SysParam> findAll() {
        return sysParamRepository.findAll();
    }

    @Override
    public List<SysParam> findAllByHeader(String header) {
        return sysParamRepository.findAllByHeader(header);
    }

    @Override
    public SysParam findByHeaderAndCode(String header, String code) {
        Optional<SysParam> optionalSysParam = sysParamRepository.findByHeaderAndCode(header.toUpperCase(),
                code.toUpperCase());
        if (optionalSysParam.isPresent()) {
            return optionalSysParam.get();
        }
//        return null;
        throw new AppsException("System Parameter is not found header : " + header + " code : " + code);
    }

    @Override
    public SysParam createSysParam(SysParamDto sysParamDto) throws AppsException {
        try {
            SysParam newSysParam = new SysParam();
            BeanUtils.copyProperties(sysParamDto, newSysParam);
            newSysParam.setId(UUID.randomUUID());
            newSysParam.setStatus(1);   //active
            newSysParam.setCreateddate(LocalDateTime.now());

            return sysParamRepository.save(newSysParam);
        } catch (Exception e) {
            log.error("Error create new System Parameter {} {}", sysParamDto.toString(), e.getMessage(), e);
            throw new AppsException("Error create new System Parameter " + e.getMessage(), sysParamDto);
        }
    }

    @Override
    public SysParam updateSysParam(SysParamDto sysParamDto) throws AppsException {
        try {
//            Optional<SysParam> sysParamOptional = sysParamRepository.findById(UUIDUtil.toUuid(sysParamDto.getId()));
            Optional<SysParam> optSysParam = sysParamRepository.findById(sysParamDto.getId());

            if (!optSysParam.isPresent())
                throw new AppsException("System Parameter is not found", sysParamDto);

            SysParam sysParam = optSysParam.get();

            sysParam.setValue(sysParamDto.getValue());
            sysParam.setValueint(sysParamDto.getValueint());
            sysParam.setStatus(sysParamDto.getStatus());
            sysParam.setDescription(sysParamDto.getDescription());
            sysParam.setUpdateddate(LocalDateTime.now());

            return sysParamRepository.save(sysParam);
        } catch (AppsException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error update System Parameter {} {}", sysParamDto.toString(), e.getMessage(), e);
            throw new AppsException("Error update System Parameter " + e.getMessage(), sysParamDto);
        }
    }

    @Override
    public Object deleteSysParam(String sysParamId) throws AppsException {
        try {
            sysParamRepository.deleteById(UUIDUtil.toUuid(sysParamId));
            return "Success";
        } catch (Exception e) {
            log.error("Error delete System Parameter {} {}", sysParamId, e.getMessage(), e);
        }
        return "Failed";
    }

}
