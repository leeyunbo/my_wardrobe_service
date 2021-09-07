package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.common.ResponseForImage;
import com.cloth.wardrobe.dto.records.ResponseForRecord;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ResponseForCloth extends Response {

    private Long id;
    private Integer likeCnt;
    private String clothName;
    private String clothType;
    private String buyingDate;
    private String buyingWay;
    private String clothColor;
    private String clothBrand;
    private String imageServerPath;

    @Builder
    public ResponseForCloth(Cloth cloth) {
        this.id = cloth.getId();
        this.clothName = cloth.getClothName();
        this.clothType = cloth.getClothType();
        this.buyingDate = cloth.getBuyingDate();
        this.buyingWay = cloth.getBuyingWay();
        this.clothColor = cloth.getClothColor();
        this.clothBrand = cloth.getClothBrand();
        this.likeCnt = cloth.getLikeCnt();
        this.imageServerPath = cloth.getImage().getImageServerPath();
    }
}
