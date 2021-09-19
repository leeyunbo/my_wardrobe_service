package com.cloth.wardrobe.dto.records;

import com.cloth.wardrobe.entity.clothes.Record;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.common.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RequestForRecordSave {

    private Image image;
    private String content;
    private String email;

    @Builder
    public RequestForRecordSave(Image image, String content, String email) {
        this.image = image;
        this.content = content;
        this.email = email;
    }

    public Record toEntity(Member member, Image image) {
        return Record.builder()
                .member(member)
                .image(image)
                .content(content)
                .build();
    }
}
