package com.cloth.wardrobe.dto.clothes.element;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentForWardrobe {
    private Long id;
    private String name;
    private Integer clothesCnt;
    private Integer likeCnt;
    private String memberName;
}
