package com.cloth.wardrobe.dto.common;

import com.cloth.wardrobe.domain.community.Post;

public abstract class SaveRequestDto {

    public abstract Post toEntity();
}
