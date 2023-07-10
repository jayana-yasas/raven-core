package com.example.demo.entity;


import com.example.demo.enums.OptRequestType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "otp_request")
public class OtpRequest extends BaseEntity {

    @Column(name = "request_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public OptRequestType requestType;

    @Column(name = "otp")
    public String otp;

    @Column(name = "otp_for")
    public String otpFor;

    @Column(name = "trace_id")
    public String traceId;

    @Column(name = "reference_id")
    public String referenceId;

    @Column(name = "expiry_time")
    public LocalDateTime expiryTime;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public OtpUser userId;

    @PrePersist
    private void prePersist() {
        if (getCreatedAt() == null) setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    private void preUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

}
