package com.munchymarket.MunchyMarket.repository.packagingtype;

import com.munchymarket.MunchyMarket.domain.PackagingType;
import com.munchymarket.MunchyMarket.dto.admin.PackagingTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackagingTypeRepository extends JpaRepository<PackagingType, Long> {

    @Query("SELECT new com.munchymarket.MunchyMarket.dto.admin.PackagingTypeDto(pt.id, pt.packagingTypeName, pt.packagingTypeDescription) FROM PackagingType pt")
    List<PackagingTypeDto> findAllPackagingTypes();
}
