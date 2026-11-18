package com.eterna.yofi.demoyofi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "mst_user")
public class UserInfo {

    @Id
    @ColumnDefault("uuid_generate_v4()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "login", nullable = false, length = 15)
    private String username;

    @Column(name = "fullname", nullable = false, length = 30)
    private String fullname;

    @ColumnDefault("'USER'")
    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @ColumnDefault("'password'")
    @Column(name = "password", nullable = false, length = 80)
    private String password;

    @ColumnDefault("1")
    @Column(name = "plainpswd", nullable = false)
    private Integer plainpswd;

    @ColumnDefault("1")
    @Column(name = "status")
    private Integer status;

    @ColumnDefault("0")
    @Column(name = "deleted")
    private Integer deleted;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "createddate")
    private LocalDateTime createddate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updateddate")
    private LocalDateTime updateddate;


}