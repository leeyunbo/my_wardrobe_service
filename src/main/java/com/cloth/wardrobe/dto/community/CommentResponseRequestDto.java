package com.cloth.wardrobe.dto.community;

import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseRequestDto {
    private Long id;
    private Member member;
    private String content;

    @Builder
    public CommentResponseRequestDto(Comment comment) {
        this.id = comment.getId();
        this.member = comment.getMember();
        this.content = comment.getContent();
    }
}
