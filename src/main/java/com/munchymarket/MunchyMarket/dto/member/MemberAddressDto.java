package com.munchymarket.MunchyMarket.dto.member;

import com.munchymarket.MunchyMarket.domain.Address;
import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.domain.enums.Role;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
@Builder
public class MemberAddressDto {

    private Long memberId;
    private String loginId;
    private String name;
    private String ruby;
    private String email;
    private String phoneNumber;
    private String sex;
    private LocalDate birth;
    private Role role;
    private AddressDto address;


    public static MemberAddressDto FromEntity(Member member, Address address) {
        return MemberAddressDto.builder()
                .memberId(member.getId())
                .loginId(member.getLoginId())
                .name(member.getName())
                .ruby(member.getRuby())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .sex(member.getSex())
                .birth(member.getBirth())
                .role(member.getRole())
                .address(address != null ? AddressDto.FromEntity(address) : null)
                .build();
    }
}
