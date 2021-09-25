package com.cloth.wardrobe.web.auth.dto;

import com.cloth.wardrobe.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import java.io.Serializable;

/**
 * 세션 저장을 위해 직렬화 사용.
 * Member에 그대로 하기에는 Member가 엔티티로 활용되기 때문에 직렬화 대상에 자식들도 포함될 수 있음! (@OneToMany, @ManyToMany...)
 * -> 성능 문제가 발생할 확률이 농후함
 */
@Getter
public class RequestForMember implements Serializable {
    private String name;
    private String email;
    private String picture;

    @Builder
    public RequestForMember(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
