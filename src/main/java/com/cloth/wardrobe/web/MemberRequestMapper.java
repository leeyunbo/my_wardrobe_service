package com.cloth.wardrobe.web;

import com.cloth.wardrobe.web.auth.dto.RequestForMember;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MemberRequestMapper {
    public RequestForMember toRequestDto(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return RequestForMember.builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .picture((String) attributes.get("picture"))
                .build();
    }
}
