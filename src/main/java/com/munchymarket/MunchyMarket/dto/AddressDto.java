package com.munchymarket.MunchyMarket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class AddressDto {
    private Long id;
    private String postalCode;
    private String regionAddress;
    private String detailAddress;
    private Boolean isBaseAddress;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public AddressDto(Long id, String postalCode, String regionAddress, String detailAddress, Boolean isBaseAddress, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.postalCode = postalCode;
        this.regionAddress = regionAddress;
        this.detailAddress = detailAddress;
        this.isBaseAddress = isBaseAddress;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
