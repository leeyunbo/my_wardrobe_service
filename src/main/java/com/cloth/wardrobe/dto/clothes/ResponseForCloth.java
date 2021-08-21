package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import com.cloth.wardrobe.dto.common.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseForCloth extends Response {

    private List<Like> likes;
    private List<Record> records;
    private Image image;
    private Member member;
    private Long id;
    private String clothName;
    private String clothType;
    private String buyingDate;
    private String buyingWay;
    private String clothColor;
    private String clothBrand;
    private int likeCnt;
    @Setter
    private boolean isLikeUser;

    @Builder
    public ResponseForCloth(Cloth cloth) {
        this.id = cloth.getId();
        this.image = cloth.getImage();
        this.records = cloth.getRecords();
        this.clothName = cloth.getClothName();
        this.clothType = cloth.getClothType();
        this.buyingDate = cloth.getBuyingDate();
        this.buyingWay = cloth.getBuyingWay();
        this.clothColor = cloth.getClothColor();
        this.clothBrand = cloth.getClothBrand();
        this.likes = cloth.getLikes();
        this.likeCnt = cloth.getLikeCnt();
        this.member = cloth.getMember();
    }
}
