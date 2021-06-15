package com.cloth.wardrobe.dto.member;

import com.cloth.wardrobe.domain.member.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private Long id;
    private String name;
    private String email;
    private Role role;
    private String picture;
}
