package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.community.Post;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.dto.common.SaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ClothSaveRequestDto extends SaveRequestDto {

    @Setter
    private Member member;
    private Wardrobe wardrobe;
    private String clothType;
    private LocalDateTime buyingDate;
    private String buyingWay;
    private String clothColor;
    private String clothBrand;

    @Builder
    public ClothSaveRequestDto(Member member, Wardrobe wardrobe, String clothType, LocalDateTime buyingDate, String buyingWay, String clothColor, String clothBrand) {
        this.member = member;
        this.wardrobe = wardrobe;
        this.clothType = clothType;
        this.buyingDate = buyingDate;
        this.buyingWay = buyingWay;
        this.clothColor = clothColor;
        this.clothBrand = clothBrand;
    }

    @Override
    public Post toEntity() {
        return Cloth.builder()
                .clothBrand(clothBrand)
                .clothColor(clothColor)
                .clothType(clothType)
                .buyingWay(buyingWay)
                .buyingDate(buyingDate)
                .wardrobe(wardrobe)
                .build();
    }


}
