package com.cloth.wardrobe.dto.community;


import com.cloth.wardrobe.dto.common.PaginatedResponse;
import com.cloth.wardrobe.dto.community.element.ContentForComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseForComments extends PaginatedResponse {
     List<ContentForComment> contents;
}
