package com.cloth.wardrobe.dto.member;

import com.cloth.wardrobe.domain.member.MemberAuthority;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberDto {

    private Long id;

    @NotEmpty(message = "별명을 입력해주세요.")
    private String name;
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String account;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
    private MemberAuthority memberAuthority;
    private String city;
    private String street;
    private String zipcode;
}
