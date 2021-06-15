package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WardrobeUpdateRequestDto {

    private Image image;
    private String name;
    private String isPublic;

    @Builder
    public WardrobeUpdateRequestDto(Image image, String name, String isPublic) {
        this.image = image;
        this.name = name;
        this.isPublic = isPublic;
    }
}
