package com.cloth.wardrobe.dto.records.element;

import com.cloth.wardrobe.entity.clothes.Record;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ContentForRecord {
    Long id;
    Long clothId;
    Integer likeCnt;
    String content;
    String clothName;
    String memberName;
    String email;
    String imageServerPath;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    public ContentForRecord(Record record) {
        this.id = record.getId();
        this.clothId = record.getId();
        this.likeCnt = record.getLikeCnt();
        this.content = record.getContent();
        this.clothName = record.getCloth().getClothName();
        this.memberName = record.getMember().getName();
        this.email = record.getMember().getEmail();
        this.imageServerPath = record.getImage().getImageServerPath();
        this.createdDate = record.getCreatedDate();
        this.modifiedDate = record.getModifiedDate();
    }
}
