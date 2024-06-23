package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
