package com.cloth.wardrobe.dto.community;

import com.cloth.wardrobe.entity.community.Comment;
import com.cloth.wardrobe.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RequestForCommentSave {

    private String content;

    @Builder
    public RequestForCommentSave(String content, String email) {
        this.content = content;
    }

    public Comment toEntity(Member member) {
        return Comment.builder()
                .member(member)
                .content(content)
                .build();
    }
}
