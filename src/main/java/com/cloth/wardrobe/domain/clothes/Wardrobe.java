package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.BaseTimeEntity;
import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wardrobes")
@Getter
@NoArgsConstructor
public class Wardrobe extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "wardrobe_id")
    private Long id;

    // 멤버
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 이미지
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    // 옷장
    @OneToMany(mappedBy = "wardrobe", cascade = CascadeType.ALL)
    private List<Cloth> clothes = new ArrayList<>();

    @OneToMany(mappedBy = "wardrobe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    private String name;

    private String isPublic;

    private int likeCnt;

    /* 옷장 내 옷 카운트 필드 추가 예정 */
    // private int clothCnt;


    @Builder
    public Wardrobe(Member member, Image image, String name, String isPublic, int likeCnt) {
        this.member = member;
        this.image = image;
        this.name = name;
        this.isPublic = isPublic;
        this.likeCnt = likeCnt;
    }

    /**
     * 옷장에 옷 추가
     * @param cloth
     * @return
     */
    public Wardrobe addCloth(Cloth cloth) {
        this.clothes.add(cloth);
        cloth.setWardrobe(this);
        return this;
    }

    /**
     * 옷장 코멘트 작성
     * @param comment
     * @return
     */
    public Wardrobe writeComment(Comment comment) {
        this.comments.add(comment);
        comment.setWardrobe(this);
        return this;
    }

    /**
     * 옷장 코멘트 제거
     */
    public Wardrobe deleteComment(Comment comment) {
        this.comments.remove(comment);
        comment.setWardrobe(null);
        return this;
    }


    /**
     * 좋아요수 증가
     */
    public Wardrobe addLikeCnt() {
        this.likeCnt++;
        return this;
    }

    /**
     * 좋아요수 감소
     */
    public Wardrobe delLikeCnt() {
        this.likeCnt--;
        return this;
    }


    /**
     * 업데이트 화면을 통한 옷장 정보 업데이트
     */
    public void update(Image image, String name, String isPublic) {
        this.image = image;
        this.name = name;
        this.isPublic = isPublic;
    }
}
