package com.munchymarket.MunchyMarket.request;

import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.utils.PhoneNumberUtil;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * TODO: 주소데이터도 추가해야될것. 나중에 컨틀로러에서 주소엔티티 SET
 * TODO: 비밀번호는 대소문자 상관없이, 특수문자는 무조건 1개이상으로 수정할것
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest{

    @NotBlank(message = "ログインIDを入力してください")
    @Pattern(regexp = "^(?=.*\\d)[a-zA-Z\\d]{8,20}$", message = "ログインIDは英字と数字を含む8文字以上20文字以下で入力してください")
    private String loginId; // "admin123"

    @NotBlank(message = "パスワードを入力してください")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[a-zA-Z\\d@$!%*?&]{8,20}$",
            message = "パスワードは英字、数字、特殊文字をそれぞれ1文字以上含む8文字以上20文字以下で入力してください"
    )
    private String password; // "password!"

    @NotBlank(message = "確認用パスワードを入力してください")
    @Size(message = "確認用パスワードは8文字以上20文字以下で入力してください", min = 8, max = 20)
    private String confirmPassword;// "password!"

    @NotBlank(message = "名前を入力してください")
    private String name; // "太郎"

    @NotBlank(message = "ふりがなを入力してください")
    private String ruby; // "タロウ"

    @Email(message = "メールアドレスの形式で入力してください")
    @NotBlank(message = "メールアドレスを入力してください")
    private String email; // "admin@gmail.com"

    @NotBlank(message = "電話番号を入力してください")
    private String phoneNumber; // "XXX-XXXX-XXXX"

    @NotBlank(message = "性別を選択してください")
    private String sex; // "男", "女", "選択なし"

    @NotBlank(message = "生年月日を入力してください")
    private String birth; // "yyyy-MM-dd"

    @NotBlank(message = "郵便番号を入力してください")
    private String postalCode; // "XXX-XXXX"

    @NotBlank(message = "地域の都道府県から始まる住所を入力してください")
    private String regionAddress; // "東京都渋谷区渋谷

    @NotBlank(message = "詳細住所を入力してください")
    private String detailAddress; // "1-2-3"

    private String code;
    private Boolean smsVerified;

    public void changePassword(String password) {
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .ruby(ruby)
                .email(email)
                .phoneNumber(PhoneNumberUtil.phoneNumberFormat(phoneNumber))
                .sex(sex)
                .birth(birth)
                .role("ROLE_USER")
                .build();
    }
}