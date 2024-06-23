package com.munchymarket.MunchyMarket.service;


import com.munchymarket.MunchyMarket.repository.packagingtype.PackagingTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PackagingTypeService {

    private final PackagingTypeRepository packagingTypeRepository;

}
