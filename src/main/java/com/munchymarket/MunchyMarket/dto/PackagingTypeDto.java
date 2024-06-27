package com.munchymarket.MunchyMarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class PackagingTypeDto {

    private Long id;
    private String packagingTypeName;
    private String packagingTypeDescription;
}
