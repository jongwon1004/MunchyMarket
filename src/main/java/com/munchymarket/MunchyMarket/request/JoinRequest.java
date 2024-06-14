package com.munchymarket.MunchyMarket.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * TODO: 주소데이터도 추가해야될것. 나중에 컨틀로러에서 주소엔티티 SET
 */
public record JoinRequest(@NotBlank(message = "ログインIDを入力してください") String loginId,
                          @NotBlank(message = "パスワードを入力してください")
                          @Pattern(
                                  regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                                  message = "パスワードは大文字、小文字、数字、特殊文字を含む8文字以上で入力してください"
                          ) String password,
                          @NotBlank(message = "確認用パスワードを入力してください") String confirmPassword,
                          @NotBlank(message = "名前を入力してください") String name,
                          @NotBlank(message = "ふりがなを入力してください") String ruby,
                          @Email(message = "メールアドレスの形式で入力してください")
                          @NotBlank(message = "メールアドレスを入力してください") String email,
                          @NotBlank(message = "性別を選択してください") String sex
) {
}