package com.munchymarket.MunchyMarket.domain;

import com.munchymarket.MunchyMarket.domain.base.TimeBaseEntity;
import com.munchymarket.MunchyMarket.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String loginId;

    private String password;
    private String name;
    private String ruby;
    private String email;
    private String phoneNumber;
    private String birth;

    @Enumerated(EnumType.STRING)
    private Role role;


    public Member(Long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = Role.fromKey(role);
    }


    @Builder
    public Member(String loginId, String password, String name, String ruby , String email, String phoneNumber, String birth, String role) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.ruby = ruby;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.role = Role.fromKey(role);
    }
}

