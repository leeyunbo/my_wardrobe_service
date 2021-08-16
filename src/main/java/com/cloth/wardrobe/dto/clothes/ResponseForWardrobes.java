package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.dto.clothes.element.ContentForWardrobes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ResponseForWardrobes {
    private Integer _code;
    private String _message;
    private List<ContentForWardrobes> contents;
}
