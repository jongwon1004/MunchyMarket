package com.munchymarket.MunchyMarket.repository;

import com.munchymarket.MunchyMarket.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
