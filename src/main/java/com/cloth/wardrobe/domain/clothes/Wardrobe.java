package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.BaseTimeEntity;
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 이미지
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    // 옷
    @OneToMany(mappedBy = "wardrobe")
    private List<Cloth> clothes = new ArrayList<>();

    private String name;

    private boolean isPublic;

    private int likeCnt;

    @Builder
    public Wardrobe(Member member, Image image, String name, boolean isPublic, int likeCnt) {
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
        clothes.add(cloth);
        return this;
    }

    /**
     * Wardrobe 이름 변경
     * @param name
     */
    public Wardrobe changeName(String name) {
        this.name = name;
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
     * 이미지 변경
     * @param image
     * @return
     */
    public Wardrobe changeImage(Image image) {
        this.image = image;
        return this;
    }
}
