package com.cloth.wardrobe.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Spring Security 권한 코드에 항상는 ROLE_이 앞에 붙어야만 한다.
 */
@Getter
@RequiredArgsConstructor
public enum MemberAuthority {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
