package com.example.keymanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "api_keys")
@EntityListeners(AuditingEntityListener.class)
public class ApiKeys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_name", nullable = false)
    private String apiName;

    @Column(name = "api_key", nullable = false)
    private String apikey;

    @Column(name = "created_at",updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at",insertable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "created_by",updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by",insertable = false)
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;
}