package com.cloth.wardrobe.dto.records;

import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.s3.Image;
import com.cloth.wardrobe.dto.common.ResponseForImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
public class ResponseForRecord {
    private Long id;
    private Long clothId;
    private String clothName;
    private ResponseForImage image;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int likeCnt;

    @Builder
    public ResponseForRecord(Record record) {
        id = record.getId();
        clothId = record.getCloth().getId();
        clothName = record.getCloth().getClothName();
        content = record.getContent();
        createdDate = record.getCreatedDate();
        modifiedDate = record.getModifiedDate();
        likeCnt = record.getLikeCnt();
        image = new ResponseForImage(record.getImage());
    }
}
