package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.entity.clothes.Wardrobe;
import com.cloth.wardrobe.dto.common.Response;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseForWardrobe extends Response {
    private Long id;
    private Integer likeCnt;
    private String memberName;
    private String email;
    private String name;
    private String isPublic;
    private String imageServerPath;

    @Builder
    public ResponseForWardrobe(Wardrobe wardrobe) {
        this.id = wardrobe.getId();
        this.memberName = wardrobe.getMember().getName();
        this.email = wardrobe.getMember().getEmail();
        this.name = wardrobe.getName();
        this.isPublic = wardrobe.getIsPublic();
        this.likeCnt = wardrobe.getLikeCnt();
        this.imageServerPath = wardrobe.getImage().getImageServerPath();
    }
}
