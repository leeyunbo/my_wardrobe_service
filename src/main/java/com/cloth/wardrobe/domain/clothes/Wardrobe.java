package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Wardrobe {

    @Id
    @GeneratedValue
    @Column(name = "wardrobe_id")
    private Long id;

    private String imageS3Path;

    private String name;

    private int clothCount;

    private boolean isPublic;

    private int likeCnt;

    //멤버
    // 옷장
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
