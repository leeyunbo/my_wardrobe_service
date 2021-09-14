package com.cloth.wardrobe.dto.community.element;

import com.cloth.wardrobe.entity.community.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContentForComment {
    Long id;
    String memberName;
    String email;
    String content;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    public ContentForComment(Comment comment) {
        this.id = comment.getId();
        this.memberName = comment.getMember().getName();
        this.email = comment.getMember().getEmail();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
