package com.cloth.wardrobe.dto.wardrobe;

import com.cloth.wardrobe.dto.wardrobe.element.ContentForWardrobe;
import com.cloth.wardrobe.dto.common.PaginatedResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ResponseForWardrobes extends PaginatedResponse {
    private List<ContentForWardrobe> contents;
}
