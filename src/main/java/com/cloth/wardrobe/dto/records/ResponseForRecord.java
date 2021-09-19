package com.cloth.wardrobe.dto.records;

import com.cloth.wardrobe.entity.clothes.Record;
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
    private String imageServerPath;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer likeCnt;

    @Builder
    public ResponseForRecord(Record record) {
        id = record.getId();
        clothId = record.getCloth().getId();
        clothName = record.getCloth().getClothName();
        content = record.getContent();
        createdDate = record.getCreatedDate();
        modifiedDate = record.getModifiedDate();
        likeCnt = record.getLikeCnt();
        imageServerPath = record.getImage().getImageServerPath();
    }
}
