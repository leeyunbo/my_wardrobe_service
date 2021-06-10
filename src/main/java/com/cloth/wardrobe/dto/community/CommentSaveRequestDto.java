package com.cloth.wardrobe.dto.community;

import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentSaveRequestDto {

    private Member member;
    private String subject;
    private String content;

    @Builder
    public CommentSaveRequestDto(Member member, String subject, String content) {
        this.member = member;
        this.subject = subject;
        this.content = content;
    }

    public Comment toEntity() {
        return Comment.builder()
                .member(member)
                .subject(subject)
                .content(content)
                .build();
    }
}
