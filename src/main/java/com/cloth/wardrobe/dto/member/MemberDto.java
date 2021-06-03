package com.cloth.wardrobe.dto.member;

import com.cloth.wardrobe.domain.member.MemberAuthority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private Long id;
    private String name;
    private String email;
    private MemberAuthority memberAuthority;
    private String picture;
}
