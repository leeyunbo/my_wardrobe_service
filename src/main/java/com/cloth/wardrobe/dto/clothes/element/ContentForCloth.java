package com.cloth.wardrobe.dto.clothes.element;

import com.cloth.wardrobe.domain.common.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentForCloth {
    private Long id;
    private Image image;
    private String buyingDate;
    private String clothBrand;
}
