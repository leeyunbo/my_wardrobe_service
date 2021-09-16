package com.cloth.wardrobe.entity.community;


import com.cloth.wardrobe.entity.BaseTimeEntity;
import com.cloth.wardrobe.entity.clothes.Cloth;
import com.cloth.wardrobe.entity.clothes.MethodType;
import com.cloth.wardrobe.entity.clothes.Record;
import com.cloth.wardrobe.entity.clothes.Wardrobe;
import com.cloth.wardrobe.entity.common.Image;
import com.cloth.wardrobe.entity.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    private int likeCnt = 0;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private final List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private final List<Comment> comments = new ArrayList<>();

    public PostEntity(Member member, Image image) {
        this.member = member;
        this.image = image;
    }

    public PostEntity changeLikeCnt(Like like, MethodType type) {
        if(type.equals(MethodType.ADD)) {
            this.likeCnt+=1;
            this.likes.add(like);
            like.setPost(this);
        }
        else if(type.equals(MethodType.DELETE)) {
            this.likeCnt-=1;
            this.likes.remove(like);
            like.setPost(null);
        }
        return this;
    }

    public PostEntity writeComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
        return this;
    }

    public PostEntity deleteComment(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
        return this;
    }
}