package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WardrobeSaveRequestDto {

    private String name;
    private Member member;
    private int likeCnt;
    private boolean isPublic;

    @Builder
    public WardrobeSaveRequestDto(String name, Member member, Boolean isPublic) {
        this.name = name;
        this.member = member;
        this.isPublic = isPublic;
        this.likeCnt = 0;
    }

    public Wardrobe toEntity() {
        return Wardrobe.builder()
                .name(name)
                .member(member)
                .isPublic(isPublic)
                .likeCnt(likeCnt)
                .build();
    }

}
