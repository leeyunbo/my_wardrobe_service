package com.cloth.wardrobe.dto.community;

import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseForComment {
    private Long id;
    private String nameOfWriter;
    private String emailOfWriter;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public ResponseForComment(Comment comment) {
        this.id = comment.getId();
        this.nameOfWriter = comment.getMember().getName();
        this.emailOfWriter = comment.getMember().getEmail();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
