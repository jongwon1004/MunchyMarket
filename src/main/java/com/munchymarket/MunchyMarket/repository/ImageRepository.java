package com.munchymarket.MunchyMarket.repository;

import com.munchymarket.MunchyMarket.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
