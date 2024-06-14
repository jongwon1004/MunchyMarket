package com.munchymarket.MunchyMarket.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimeBaseEntity {

    @CreatedDate
    @Column(name = "created_date")
    private String createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private String lastModifiedDate;
}
