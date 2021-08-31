package com.cloth.wardrobe.dto.records;

import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class RequestForRecordSave {

    @Setter private Member member;
    @Setter private Image image;
    @NotBlank private String subject;
    @NotNull private String content;

    @Builder
    public RequestForRecordSave(Image image, String subject, String content) {
        this.image = image;
        this.subject = subject;
        this.content = content;
    }

    public Record toEntity() {
        return Record.builder()
                .member(member)
                .image(image)
                .subject(subject)
                .content(content)
                .build();
    }
}
