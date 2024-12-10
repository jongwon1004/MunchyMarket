package com.munchymarket.MunchyMarket.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springdoc.core.annotations.ParameterObject;

@Schema(description = "회원 로그인 요청 데이터")
@ParameterObject // @ModelAttribute 같이 form 데이터를 받을때 Swagger 에서 RequestBody 가 보이지 않도록 설정
public record MemberLoginRequest(
//        @Schema(description = "로그인 아이디", example = "hanako0315")
        String loginId,

//        @Schema(description = "비밀번호", example = "helloha0315!")
        String password
) {
}
