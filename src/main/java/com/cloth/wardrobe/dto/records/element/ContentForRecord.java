package com.cloth.wardrobe.dto.records.element;

import com.cloth.wardrobe.domain.clothes.Record;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ContentForRecord {
    Long id;
    String content;
    String memberName;
    String email;
    String imageServerPath;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    public ContentForRecord(Record record) {
        this.id = record.getId();
        this.content = record.getContent();
        this.memberName = record.getMember().getName();
        this.email = record.getMember().getEmail();
        this.imageServerPath = record.getImage().getImageServerPath();
        this.createdDate = record.getCreatedDate();
        this.modifiedDate = record.getModifiedDate();
    }
}
