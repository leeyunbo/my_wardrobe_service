package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.entity.clothes.Wardrobe;
import com.cloth.wardrobe.entity.common.Image;
import com.cloth.wardrobe.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestForWardrobeSave {

    private String name;
    private int likeCnt;
    private String isPublic;

    @Builder
    public RequestForWardrobeSave(String name, String isPublic) {
        this.name = name;
        this.isPublic = isPublic;
        this.likeCnt = 0;
    }

    public Wardrobe toEntity(Member member, Image image) {
        return Wardrobe.builder()
                .name(name)
                .isPublic(isPublic)
                .member(member)
                .image(image)
                .build();
    }
}
