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

@Getter
@NoArgsConstructor
public class RecordGetResponseDto {
    private Member member;
    private List<Image> images;
    private String subject;
    private String content;

    @Builder
    public RecordGetResponseDto(Record record) {
        member = record.getMember();
        images = record.getImages();
        subject = record.getSubject();
        content = record.getContent();
    }
}
