package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestForWardrobeUpdate {

    private Image image;
    private String name;
    private String isPublic;

    @Builder
    public RequestForWardrobeUpdate(Image image, String name, String isPublic) {
        this.image = image;
        this.name = name;
        this.isPublic = isPublic;
    }
}
