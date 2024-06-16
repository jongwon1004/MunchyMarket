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

    private String postalCode;
    private String regionAddress;
    private String detailAddress;
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
