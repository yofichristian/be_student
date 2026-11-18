package com.eterna.yofi.demoyofi.repository;

import com.eterna.yofi.demoyofi.entity.SysParam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SysParamRepository extends JpaRepository<SysParam, String> {

    List<SysParam> findAllByHeader(String header);
    Optional<SysParam> findByHeaderAndCode(String header, String code);

    @Modifying
    @Transactional
    @Query("update SysParam a set a.status=0 where a.id=:id")
    void deleteById(UUID id);


}
