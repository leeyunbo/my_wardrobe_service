package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.dto.clothes.element.ContentForWardrobes;
import com.cloth.wardrobe.dto.common.ResponseForError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ResponseForWardrobes extends ResponseForError {
    private List<ContentForWardrobes> contents;
}
