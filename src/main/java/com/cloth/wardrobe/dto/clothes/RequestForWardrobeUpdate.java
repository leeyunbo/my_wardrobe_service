package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.entity.common.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestForWardrobeUpdate {

    private Image image;
    private String name;
    private String isPublic;
    private String email;

    @Builder
    public RequestForWardrobeUpdate(Image image, String name, String isPublic, String email) {
        this.image = image;
        this.name = name;
        this.isPublic = isPublic;
        this.email = email;
    }
}
