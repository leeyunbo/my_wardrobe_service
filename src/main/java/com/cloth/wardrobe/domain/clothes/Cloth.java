package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Cloth {

    @Id
    @GeneratedValue
    @Column(name = "cloth_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String clothType;

    private LocalDateTime buyingDate;

    private String buying_way;

    private String clothColor;

    private String clothBrand;

    private String imageS3Path;
}
