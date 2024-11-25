package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.dto.SortTypeDto;
import com.munchymarket.MunchyMarket.repository.sorttype.SortTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class SortTypeService {

    private final SortTypeRepository sortTypeRepository;


    public List<SortTypeDto> getSortTypes() {
        return sortTypeRepository.getSortTypes();
    }
}
