package com.cloth.wardrobe.dto.records;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@NoArgsConstructor
public class RecordSaveRequestDto {
    private List<Image> images;
    private String subject;
    private String content;

    @Builder
    public RecordSaveRequestDto(List<Image> images, String subject, String content) {
        this.images = images;
        this.subject = subject;
        this.content = content;
    }

    public Record toEntity() {
        return Record.builder()
                .images(images)
                .subject(subject)
                .content(content)
                .build();
    }
}
