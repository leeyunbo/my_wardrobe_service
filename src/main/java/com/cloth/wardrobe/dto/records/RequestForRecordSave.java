package com.cloth.wardrobe.dto.records;

import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.common.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RequestForRecordSave {

    @Setter private Member member;
    @Setter private Image image;
    @NotNull private String content;

    @Builder
    public RequestForRecordSave(Image image, String content) {
        this.image = image;
        this.content = content;
    }

    public Record toEntity() {
        return Record.builder()
                .member(member)
                .image(image)
                .content(content)
                .build();
    }
}
