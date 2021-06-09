package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseRequestDto {
    private Member member;
    private String subject;
    private String content;

    @Builder
    public CommentResponseRequestDto(Member member, String subject, String content) {
        this.member = member;
        this.subject = subject;
        this.content = content;
    }
}
