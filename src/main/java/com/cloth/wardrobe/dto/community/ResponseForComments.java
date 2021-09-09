package com.cloth.wardrobe.dto.community;


import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.community.element.ContentForComment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseForComments extends Response {
     List<ContentForComment> contents;
}
