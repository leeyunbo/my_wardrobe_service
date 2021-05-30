package com.cloth.wardrobe.domain.community;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String subejct;

    private String content;

    private LocalDateTime registDate;

    private LocalDateTime modifyDate;
}
