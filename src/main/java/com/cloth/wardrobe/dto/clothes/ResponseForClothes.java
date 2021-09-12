package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.dto.clothes.element.ContentForCloth;
import com.cloth.wardrobe.dto.common.PaginatedResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseForClothes extends PaginatedResponse {
    private List<ContentForCloth> contents;
}
