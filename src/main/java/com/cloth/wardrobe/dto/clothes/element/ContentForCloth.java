package com.cloth.wardrobe.dto.clothes.element;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.common.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentForCloth {
    private Long id;
    private String imageServerPath;
    private String buyingDate;
    private String clothBrand;

    public ContentForCloth(Cloth cloth) {
        this.id = cloth.getId();
        this.imageServerPath = cloth.getImage().getImageServerPath();
        this.buyingDate = cloth.getBuyingDate();
        this.clothBrand = cloth.getClothBrand();
    }
}
