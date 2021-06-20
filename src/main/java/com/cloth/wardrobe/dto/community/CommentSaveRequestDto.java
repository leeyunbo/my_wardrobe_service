package com.cloth.wardrobe.dto.community;

import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    @Setter
    private Member member;
    private String content;

    @Builder
    public CommentSaveRequestDto(String content) {
        this.content = content;
    }

    public Comment toEntity() {
        return Comment.builder()
                .member(member)
                .content(content)
                .build();
    }
}
