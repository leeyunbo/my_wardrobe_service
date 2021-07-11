package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.community.Post;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import com.cloth.wardrobe.dto.common.SaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ClothSaveRequestDto extends SaveRequestDto {

    @Setter
    private Member member;
    @Setter
    private Wardrobe wardrobe;

    private List<Image> images = new ArrayList<>();
    private String clothName;
    private String clothType;
    private LocalDateTime buyingDate;
    private String buyingWay;
    private String clothColor;
    private String clothBrand;

    @Builder
    public ClothSaveRequestDto(Member member, Wardrobe wardrobe, List<Image> images, String clothName, String clothType, LocalDateTime buyingDate, String buyingWay, String clothColor, String clothBrand) {
        this.member = member;
        this.wardrobe = wardrobe;
        this.images = images;
        this.clothName = clothName;
        this.clothType = clothType;
        this.buyingDate = buyingDate;
        this.buyingWay = buyingWay;
        this.clothColor = clothColor;
        this.clothBrand = clothBrand;
    }

    @Override
    public Post toEntity() {
        return Cloth.builder()
                .clothName(clothName)
                .clothBrand(clothBrand)
                .clothColor(clothColor)
                .clothType(clothType)
                .buyingWay(buyingWay)
                .buyingDate(buyingDate)
                .images(images)
                .wardrobe(wardrobe)
                .build();
    }


}
