package com.munchymarket.MunchyMarket.dto.member;

import com.munchymarket.MunchyMarket.domain.Address;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class AddressDto {
    private Long id;
    private String postalCode;
    private String regionAddress;
    private String detailAddress;
    private Boolean isBaseAddress;


    public static AddressDto FromEntity(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .postalCode(address.getPostalCode())
                .regionAddress(address.getRegionAddress())
                .detailAddress(address.getDetailAddress())
                .isBaseAddress(address.getIsBaseAddress())
                .build();
    }

}
