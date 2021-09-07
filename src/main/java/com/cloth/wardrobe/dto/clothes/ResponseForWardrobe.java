package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.common.ResponseForImage;
import com.cloth.wardrobe.dto.community.ResponseForComment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseForWardrobe extends Response {
    private Long id;
    private Integer likeCnt;
    private String memberName;
    private String email;
    private String name;
    private String isPublic;
    private String imageServerPath;

    @Builder
    public ResponseForWardrobe(Wardrobe wardrobe) {
        this.id = wardrobe.getId();
        this.memberName = wardrobe.getMember().getName();
        this.email = wardrobe.getMember().getEmail();
        this.name = wardrobe.getName();
        this.isPublic = wardrobe.getIsPublic();
        this.likeCnt = wardrobe.getLikeCnt();
        this.imageServerPath = wardrobe.getImage().getImageServerPath();
    }


}
