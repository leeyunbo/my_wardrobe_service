package com.cloth.wardrobe.dto.member;

import com.cloth.wardrobe.domain.member.MemberAuthority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private Long id;
    private String name;
    private String account;
    private String password;
    private MemberAuthority memberAuthority;
    private String city;
    private String street;
    private String zipcode;
}
