package com.cloth.wardrobe.entity.clothes;

import com.cloth.wardrobe.entity.community.Comment;
import com.cloth.wardrobe.entity.community.Like;
import com.cloth.wardrobe.entity.community.PostEntity;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.common.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Table(name = "wardrobes")
@Getter
@NoArgsConstructor
public class Wardrobe extends PostEntity {

    // 멤버
    @OneToOne
    @JoinColumn(name = "member_id")
    @Setter
    public Member member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    @Setter
    private Image image;

    private String name;

    private String isPublic;

    @OneToMany(mappedBy = "wardrobe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Cloth> clothes = new ArrayList<>();

    @OneToMany(mappedBy = "wardrobe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Comment> comments = new ArrayList<>();

    @Builder
    public Wardrobe(Member member, Image image, String name, String isPublic) {
        this.member = member;
        this.image = image;
        this.name = name;
        this.isPublic = isPublic;
    }

    public Wardrobe addCloth(Cloth cloth) {
        this.clothes.add(cloth);
        cloth.setWardrobe(this);
        image.setCloth(cloth);
        return this;
    }

    public Wardrobe deleteCloth(Cloth cloth) {
        this.clothes.remove(cloth);
        cloth.setWardrobe(null);
        image.setCloth(null);
        return this;
    }

    public Wardrobe writeComment(Comment comment) {
        this.comments.add(comment);
        comment.setWardrobe(this);
        return this;
    }

    public Wardrobe deleteComment(Comment comment) {
        this.comments.remove(comment);
        comment.setWardrobe(null);
        return this;
    }

    public void update(Image image, String name, String isPublic) {
        this.image = image;
        this.name = name;
        this.isPublic = isPublic;
    }
}
