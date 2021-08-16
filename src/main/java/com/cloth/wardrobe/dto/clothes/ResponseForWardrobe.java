package com.cloth.wardrobe.dto.clothes;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class ResponseForWardrobe {
    private Integer _code;
    private String _message;
    private Long id;
    private Member member;
    private Image image;
    private List<Cloth> clothes;
    private List<Comment> comments;
    private List<Like> likes;
    private String name;
    private String isPublic;
    private int likeCnt;
    @Setter private boolean isLikeUser;

    @Builder
    public ResponseForWardrobe(Wardrobe wardrobe) {
        this.id = wardrobe.getId();
        this.member = wardrobe.getMember();
        this.image = wardrobe.getImage();
        this.clothes = wardrobe.getClothes();
        this.name = wardrobe.getName();
        this.isPublic = wardrobe.getIsPublic();
        this.likeCnt = wardrobe.getLikeCnt();
        this.comments = wardrobe.getComments();
        this.likes = wardrobe.getLikes();
    }


}
