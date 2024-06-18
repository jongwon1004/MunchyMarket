package com.munchymarket.MunchyMarket.dto;

import com.munchymarket.MunchyMarket.domain.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@Getter
@ToString
public class MemberAddressDto {

    private Long id;
    private String loginId;
    private String name;
    private String ruby;
    private String email;
    private String phoneNumber;
    private String sex;
    private String birth;
    private Role role;
    private AddressDto address;

    public MemberAddressDto(Long id, String loginId, String name, String ruby, String email, String phoneNumber, String sex, String birth, Role role, AddressDto address) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.ruby = ruby;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.birth = birth;
        this.role = role;
        this.address = address;
    }
}
