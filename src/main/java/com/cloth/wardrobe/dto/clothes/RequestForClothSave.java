package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.entity.clothes.Cloth;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.common.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RequestForClothSave {

    @Setter private Image image;
    private String clothName;
    private String clothType;
    private String buyingDate;
    private String buyingWay;
    private String clothColor;
    private String clothBrand;

    @Builder
    public RequestForClothSave(Image image, String clothName, String clothType, String buyingDate, String buyingWay, String clothColor, String clothBrand) {
        this.image = image;
        this.clothName = clothName;
        this.clothType = clothType;
        this.buyingDate = buyingDate;
        this.buyingWay = buyingWay;
        this.clothColor = clothColor;
        this.clothBrand = clothBrand;
    }

    public Cloth toEntity(Member member) {
        return Cloth.builder()
                .member(member)
                .clothName(clothName)
                .clothBrand(clothBrand)
                .clothColor(clothColor)
                .clothType(clothType)
                .buyingWay(buyingWay)
                .buyingDate(buyingDate)
                .image(image)
                .build();
    }
}
