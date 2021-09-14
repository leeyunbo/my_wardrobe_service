package com.cloth.wardrobe.dto.community;

import com.cloth.wardrobe.entity.community.Like;
import com.cloth.wardrobe.dto.common.Response;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseForLike extends Response {

    private Boolean isLike;
    private LocalDateTime clickedDate;

    public ResponseForLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public ResponseForLike(Like like, Boolean isLike) {
        this.clickedDate = like.getModifiedDate();
        this.isLike = isLike;
    }
}
