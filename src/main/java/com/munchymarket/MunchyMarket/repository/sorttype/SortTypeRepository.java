package com.munchymarket.MunchyMarket.repository.sorttype;

import com.munchymarket.MunchyMarket.domain.SortType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SortTypeRepository extends JpaRepository<SortType, Long>, SortTypeRepositoryCustom {

}
