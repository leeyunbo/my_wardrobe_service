package com.cloth.wardrobe.dto.community;

import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseForComment {
    private Long id;
    private Member member;
    private String content;

    @Builder
    public ResponseForComment(Comment comment) {
        this.id = comment.getId();
        this.member = comment.getMember();
        this.content = comment.getContent();
    }
}
