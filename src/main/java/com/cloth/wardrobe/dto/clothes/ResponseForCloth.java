package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.entity.clothes.Cloth;
import com.cloth.wardrobe.dto.common.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseForCloth extends Response {

    private Long id;
    private Integer likeCnt;
    private String memberName;
    private String email;
    private String clothName;
    private String clothType;
    private String buyingDate;
    private String buyingWay;
    private String clothColor;
    private String clothBrand;
    private String imageServerPath;

    @Builder
    public ResponseForCloth(Cloth cloth) {
        this.id = cloth.getId();
        this.memberName = cloth.getMember().getName();
        this.email = cloth.getMember().getEmail();
        this.clothName = cloth.getClothName();
        this.clothType = cloth.getClothType();
        this.buyingDate = cloth.getBuyingDate();
        this.buyingWay = cloth.getBuyingWay();
        this.clothColor = cloth.getClothColor();
        this.clothBrand = cloth.getClothBrand();
        this.likeCnt = cloth.getLikeCnt();
        this.imageServerPath = cloth.getImage().getImageServerPath();
    }
}
