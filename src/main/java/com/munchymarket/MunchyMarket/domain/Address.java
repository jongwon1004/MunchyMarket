package com.munchymarket.MunchyMarket.domain;

import com.munchymarket.MunchyMarket.domain.base.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Address extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    @Column(name = "region_address", nullable = false, length = 50)
    private String regionAddress;

    @Column(name = "detail_address", nullable = false, length = 50)
    private String detailAddress;

    @Column(name = "is_base_address", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isBaseAddress;

    @Builder
    public Address(Member member, String postalCode, String regionAddress, String detailAddress, Boolean isBaseAddress) {
        this.member = member;
        this.postalCode = postalCode;
        this.regionAddress = regionAddress;
        this.detailAddress = detailAddress;
        this.isBaseAddress = isBaseAddress;
    }
}
