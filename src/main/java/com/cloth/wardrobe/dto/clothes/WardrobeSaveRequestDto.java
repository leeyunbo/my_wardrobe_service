package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WardrobeSaveRequestDto {

    private String name;
    private Member member;
    private String image;
    private int likeCnt;
    private String isPublic;

    @Builder
    public WardrobeSaveRequestDto(String name, Member member, String image, String isPublic) {
        this.name = name;
        this.member = member;
        this.image = image;
        this.isPublic = isPublic;
        this.likeCnt = 0;
    }

    public Wardrobe toEntity(Image image, Member member) {
        return Wardrobe.builder()
                .name(name)
                .member(member)
                .isPublic(isPublic)
                .likeCnt(likeCnt)
                .image(image)
                .build();
    }

}
