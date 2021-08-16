package com.cloth.wardrobe.dto.clothes.element;

import lombok.Getter;

@Getter
public class ContentForWardrobes {
    private Long id;
    private String name;
    private int clothesCnt;
    private int likeCnt;
    private String memberName;

    public ContentForWardrobes(Long id, String name, int clothesCnt, int likeCnt, String memberName) {
        this.id = id;
        this.name = name;
        this.clothesCnt = clothesCnt;
        this.likeCnt = likeCnt;
        this.memberName = memberName;
    }
}
