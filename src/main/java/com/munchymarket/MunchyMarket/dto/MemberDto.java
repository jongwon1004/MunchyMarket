package com.munchymarket.MunchyMarket.dto;

import com.munchymarket.MunchyMarket.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String birth;
    private Role role;

}
