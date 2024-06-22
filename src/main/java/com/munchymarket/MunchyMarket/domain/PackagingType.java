package com.munchymarket.MunchyMarket.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString(of = {"id", "packagingTypeName", "packagingTypeDescription"})
@Entity
public class PackagingType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packaging_type_id", updatable = false)
    private Long id;

    @Column(name = "packaging_type_name", length = 50, nullable = false)
    private String packagingTypeName;

    @Column(name = "packaging_type_des", length = 100, nullable = false)
    private String packagingTypeDescription;
}
