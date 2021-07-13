package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class ClothSaveRequestDto {

    private List<Image> images;
    private String clothName;
    private String clothType;
    private String buyingDate;
    private String buyingWay;
    private String clothColor;
    private String clothBrand;

    @Builder
    public ClothSaveRequestDto(List<Image> images, String clothName, String clothType, String buyingDate, String buyingWay, String clothColor, String clothBrand) {
        this.images = images;
        this.clothName = clothName;
        this.clothType = clothType;
        this.buyingDate = buyingDate;
        this.buyingWay = buyingWay;
        this.clothColor = clothColor;
        this.clothBrand = clothBrand;
    }

    public Cloth toEntity() {
        return Cloth.builder()
                .clothName(clothName)
                .clothBrand(clothBrand)
                .clothColor(clothColor)
                .clothType(clothType)
                .buyingWay(buyingWay)
                .buyingDate(buyingDate)
                .images(images)
                .build();
    }
}
