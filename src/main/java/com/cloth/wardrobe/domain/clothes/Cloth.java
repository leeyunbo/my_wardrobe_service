package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.BaseTimeEntity;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clothes")
@Getter
@NoArgsConstructor
public class Cloth extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "cloth_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    private String clothType;

    private LocalDateTime buyingDate;

    private String buying_way;

    private String clothColor;

    private String clothBrand;

    //@OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name = "image_id")
    //private List<Image> imagePath = new ArrayList<>();
}
