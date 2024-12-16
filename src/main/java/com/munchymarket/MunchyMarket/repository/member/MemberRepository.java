package com.munchymarket.MunchyMarket.repository.member;

import com.munchymarket.MunchyMarket.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findMemberByLoginId(String loginId);

    Boolean existsByLoginId(String loginId);
    Boolean existsByEmail(String email);
}
