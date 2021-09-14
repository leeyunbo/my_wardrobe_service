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

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clothes")
@Getter
@NoArgsConstructor
public class Cloth extends PostEntity {

    @Setter
    @ManyToOne
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Like> likes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    public String clothName;

    private String clothType;

    private String buyingDate;

    private String buyingWay;

    private String clothColor;

    private String clothBrand;

    private int likeCnt;

    @Builder
    public Cloth(Member member, Wardrobe wardrobe, Image image, String clothName, String clothType, String buyingDate, String buyingWay, String clothColor, String clothBrand) {
        this.member = member;
        this.wardrobe = wardrobe;
        this.image = image;
        this.clothName = clothName;
        this.clothType = clothType;
        this.buyingDate = buyingDate;
        this.buyingWay = buyingWay;
        this.clothColor = clothColor;
        this.clothBrand = clothBrand;
    }

    @Override
    public PostEntity changeLikeCnt(Like like, MethodType type) {
        switch (type) {
            case ADD:
                this.likeCnt++;
                this.likes.add(like);
                like.setCloth(this);
                break;
            case DELETE:
                this.likeCnt--;
                this.likes.remove(like);
                like.setCloth(null);
                break;
        }

        return this;
    }

    public Cloth addRecord(Record record) {
        records.add(record);
        record.setCloth(this);
        return this;
    }

    public Cloth deleteRecord(Record record) {
        records.remove(record);
        record.setCloth(null);
        return this;
    }

    /**
     * 옷장 코멘트 작성
     * @param comment
     * @return
     */
    public Cloth writeComment(Comment comment) {
        this.comments.add(comment);
        comment.setCloth(this);
        return this;
    }

    /**
     * 옷장 코멘트 제거
     */
    public Cloth deleteComment(Comment comment) {
        this.comments.remove(comment);
        comment.setCloth(null);
        return this;
    }
}
