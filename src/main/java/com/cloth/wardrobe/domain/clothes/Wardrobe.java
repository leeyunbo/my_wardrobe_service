package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wardrobes")
@Getter
@NoArgsConstructor
public class Wardrobe {

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

    private int clothCount;

    private boolean isPublic;

    private int likeCnt;
}
