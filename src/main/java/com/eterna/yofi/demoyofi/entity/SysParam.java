package com.eterna.yofi.demoyofi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "mst_sysparam")
public class SysParam {

    @Id
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "header", nullable = false, length = 30)
    private String header;

    @Column(name = "code", nullable = false, length = 15)
    private String code;

    @Column(name = "value", length = 80)
    private String value;

    @Column(name = "valueint")
    private Double valueint;

    @Column(name = "description", length = 100)
    private String description;

    @ColumnDefault("1")
    @Column(name = "status")
    private Integer status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "createddate")
    private LocalDateTime createddate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updateddate")
    private LocalDateTime updateddate;


}