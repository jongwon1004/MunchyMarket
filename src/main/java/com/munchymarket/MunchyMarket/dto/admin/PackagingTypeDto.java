package com.munchymarket.MunchyMarket.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class PackagingTypeDto {

    private Long id;
    private String packagingTypeName;
    private String packagingTypeDescription;
}
