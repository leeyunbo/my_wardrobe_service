package com.cloth.wardrobe.dto.records;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@NoArgsConstructor
public class ResponseForRecord {
    private String memberName;
    private String clothName;
    private List<Image> images;
    private String subject;
    private String content;
    private int likeCnt;

    @Builder
    public ResponseForRecord(Record record) {
        memberName = record.getMember().getName();
        clothName = record.getCloth().getClothName();
        images = record.getImages();
        subject = record.getSubject();
        content = record.getContent();
        likeCnt = record.getLikeCnt();
    }
}
