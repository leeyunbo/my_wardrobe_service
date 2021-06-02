package com.cloth.wardrobe.config.auth.dto;

import com.cloth.wardrobe.domain.member.Member;
import lombok.Getter;

@Getter
public class SessionMember {
    private String name;
    private String email;
    private String picture;

    public SessionMember(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
