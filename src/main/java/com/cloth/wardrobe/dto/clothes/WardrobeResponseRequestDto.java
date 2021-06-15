package com.cloth.wardrobe.dto.clothes;


import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
public class WardrobeResponseRequestDto {
    private Long id;
    private Member member;
    private Image image;
    private List<Cloth> clothes;
    private String name;
    private String isPublic;
    private int likeCnt;

    @Builder
    public WardrobeResponseRequestDto(Wardrobe wardrobe) {
        this.id = wardrobe.getId();;
        this.member = wardrobe.getMember();
        this.image = wardrobe.getImage();
        this.clothes = wardrobe.getClothes();
        this.name = wardrobe.getName();
        this.isPublic = wardrobe.getIsPublic();
        this.likeCnt = wardrobe.getLikeCnt();
    }


}
