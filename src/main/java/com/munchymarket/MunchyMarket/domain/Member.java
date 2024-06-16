package com.munchymarket.MunchyMarket.domain;

import com.munchymarket.MunchyMarket.domain.base.TimeBaseEntity;
import com.munchymarket.MunchyMarket.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString(of = {"id", "loginId", "name", "ruby", "email", "phoneNumber", "sex", "birth", "role"})
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;


    @Column(nullable = false, length = 30, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ruby;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private String birth;

    @Enumerated(EnumType.STRING)
    private Role role;


    public Member(Long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = Role.fromKey(role);
    }


    @Builder
    public Member(String loginId, String password, String name, String ruby, String email, String phoneNumber, String sex, String birth, String role) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.ruby = ruby;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.birth = birth;
        this.role = Role.fromKey(role);
    }
}

