package com.munchymarket.MunchyMarket.repository;

import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.request.LoginValidateCheckRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findMemberByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);

}
