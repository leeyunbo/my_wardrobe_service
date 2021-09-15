package com.cloth.wardrobe.dto.clothes.element;

import com.cloth.wardrobe.entity.clothes.Cloth;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentForCloth {
    private Long id;
    private String clothName;
    private String clothType;
    private String imageServerPath;
    private String buyingDate;
    private String clothBrand;

    public ContentForCloth(Cloth cloth) {
        this.id = cloth.getId();
        this.clothName = cloth.getClothName();
        this.clothType = cloth.getClothType();
        this.imageServerPath = cloth.getImage().getImageServerPath();
        this.buyingDate = cloth.getBuyingDate();
        this.clothBrand = cloth.getClothBrand();
    }
}
