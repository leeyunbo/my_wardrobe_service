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

    public Wardrobe toEntity() {
        return Wardrobe.builder()
                .name(name)
                .isPublic(isPublic)
                .likeCnt(likeCnt)
                .build();
    }
}
