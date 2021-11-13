package com.cloth.wardrobe.config.auth.dto;

import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.member.Role;
import lombok.Builder;
import lombok.Getter;
import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;
    private String picture;

    @Builder
    public SessionMember(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }

    public Member toEntity(SessionMember sessionMember) {
        return new Member(sessionMember.getName(), sessionMember.getEmail(), sessionMember.getPicture(), Role.USER);
    }
}
