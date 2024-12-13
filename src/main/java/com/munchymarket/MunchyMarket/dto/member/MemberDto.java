package com.munchymarket.MunchyMarket.dto.member;

import com.munchymarket.MunchyMarket.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String loginId;
    private String name;
    private String ruby;
    private String email;
    private String phoneNumber;
    private String sex;
    private LocalDate birth;
    private Role role;
}
