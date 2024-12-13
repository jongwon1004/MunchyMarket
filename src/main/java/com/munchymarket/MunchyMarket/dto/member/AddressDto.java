package com.munchymarket.MunchyMarket.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private String postalCode;
    private String regionAddress;
    private String detailAddress;
    private Boolean isBaseAddress;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
