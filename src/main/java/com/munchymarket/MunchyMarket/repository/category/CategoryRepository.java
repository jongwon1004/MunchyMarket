package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {


//    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.id = :id")
//    Optional<Category> findByIdWithChildren(@Param("id") Long id);

}
