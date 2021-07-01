package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.domain.community.Post;
import com.cloth.wardrobe.domain.s3.Image;
import lombok.Builder;
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
public class Cloth extends Post {

    @Id
    @GeneratedValue
    @Column(name = "cloth_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    private String clothType;

    private LocalDateTime buyingDate;

    private String buying_way;

    private String clothColor;

    private String clothBrand;

    @Builder
    public Cloth(Wardrobe wardrobe, String clothType, LocalDateTime buyingDate, String buying_way, String clothColor, String clothBrand) {
        this.wardrobe = wardrobe;
        this.clothType = clothType;
        this.buyingDate = buyingDate;
        this.buying_way = buying_way;
        this.clothColor = clothColor;
        this.clothBrand = clothBrand;
    }

    @Override
    public Post changeLikeCnt(Like like, MethodType type) {
        return null;
    }
}
