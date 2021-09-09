package com.cloth.wardrobe.dto.community;


import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.community.element.ContentForComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseForComments extends Response {
     List<ContentForComment> contents;

     public ResponseForComments(List<ContentForComment> contents) {
          this.contents = contents;
     }
}
