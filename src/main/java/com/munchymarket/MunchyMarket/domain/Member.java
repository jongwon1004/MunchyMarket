package com.munchymarket.MunchyMarket.domain;

import com.munchymarket.MunchyMarket.domain.base.TimeBaseEntity;
import com.munchymarket.MunchyMarket.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@ToString(of = {"id", "loginId", "name", "ruby", "email", "phoneNumber", "sex", "birth", "role"})
@Table(name = "members")
@Entity
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;


    @Column(nullable = false, length = 30, unique = true)
    private String loginId;

    @Column(nullable = false) //joinRequst = 8~20桁、 サーバーでハッシュ化してDB Insert (255桁)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 50)
    private String ruby;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 5) // '男'、'女'、'選択なし'
    private String sex;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Role role;


    public Member(Long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = Role.fromKey(role);
    }


    @Builder
    public Member(String loginId, String password, String name, String ruby, String email, String phoneNumber, String sex, LocalDate birth, String role) {
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

