package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.dto.clothes.element.ContentForWardrobes;
import com.cloth.wardrobe.dto.common.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ResponseForWardrobes extends Response {
    private List<ContentForWardrobes> contents;
}
