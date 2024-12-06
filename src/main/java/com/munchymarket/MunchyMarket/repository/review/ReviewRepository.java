package com.munchymarket.MunchyMarket.repository.review;

import com.munchymarket.MunchyMarket.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

}
