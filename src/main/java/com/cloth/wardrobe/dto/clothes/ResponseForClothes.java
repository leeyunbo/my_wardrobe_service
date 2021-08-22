package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.dto.clothes.element.ContentForCloth;
import com.cloth.wardrobe.dto.common.Response;
import lombok.Data;

import java.util.List;

@Data
public class ResponseForClothes extends Response {
    private List<ContentForCloth> contents;
}
