package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class ClothSaveRequestDto {

    @Setter private Member member;
    @Setter private Image image;
    @NotBlank private String clothName;
    @NotBlank private String clothType;
    private String buyingDate;
    private String buyingWay;
    @NotBlank private String clothColor;
    private String clothBrand;

    @Builder
    public ClothSaveRequestDto(Image image, String clothName, String clothType, String buyingDate, String buyingWay, String clothColor, String clothBrand) {
        this.image = image;
        this.clothName = clothName;
        this.clothType = clothType;
        this.buyingDate = buyingDate;
        this.buyingWay = buyingWay;
        this.clothColor = clothColor;
        this.clothBrand = clothBrand;
    }

    public Cloth toEntity() {
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
