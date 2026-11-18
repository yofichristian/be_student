package com.eterna.yofi.demoyofi.repository;

import com.eterna.yofi.demoyofi.entity.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

//    @Query("SELECT a FROM MstUser a WHERE a.login=:login")
//    Optional<UserInfo> findByLogin(String login);
    Optional<UserInfo> findByUsername(String login);

    @Modifying
    @Transactional
//    @Query("update UserInfo a set a.status=0 where a.username=:username")
//    @Query("update UserInfo a set a.deleted=1 where a.username=:username")
    @Query("delete UserInfo where username=:username")
    void deleteByUsername(String username);

//    @Query("SELECT a FROM AuditTrail a WHERE a.projectId=:projectId order by a.createdDate desc")
//    List<AuditTrail> findAllByProjectId(String projectId);
//
//    @Query("SELECT a FROM AuditTrail a WHERE a.createdDate>=:createdDateAfter and a.createdDate<=:createdDateBefore" +
//            " order by a.createdDate desc")
//    List<AuditTrail> findByCreatedDateBetween(LocalDateTime createdDateAfter, LocalDateTime createdDateBefore);

}
