package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class WardrobeSaveRequestDto {

    @NotNull @NotBlank private String name;
    @Setter private Member member;
    @Setter private Image image;
    private int likeCnt;
    @NotBlank private String isPublic;

    @Builder
    public WardrobeSaveRequestDto(String name, Member member, Image image, String isPublic) {
        this.name = name;
        this.member = member;
        this.image = image;
        this.isPublic = isPublic;
        this.likeCnt = 0;
    }

    public Wardrobe toEntity() {
        return Wardrobe.builder()
                .name(name)
                .member(member)
                .isPublic(isPublic)
                .likeCnt(likeCnt)
                .image(image)
                .build();
    }
}
