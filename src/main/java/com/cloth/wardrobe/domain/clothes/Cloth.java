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

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    public String clothName;

    private String clothType;

    private LocalDateTime buyingDate;

    private String buyingWay;

    private String clothColor;

    private String clothBrand;

    private int likeCnt;

    @Builder
    public Cloth(Wardrobe wardrobe, List<Image> images, String clothName, String clothType, LocalDateTime buyingDate, String buyingWay, String clothColor, String clothBrand) {
        this.wardrobe = wardrobe;
        this.images = images;
        this.clothName = clothName;
        this.clothType = clothType;
        this.buyingDate = buyingDate;
        this.buyingWay = buyingWay;
        this.clothColor = clothColor;
        this.clothBrand = clothBrand;
    }

    @Override
    public Post changeLikeCnt(Like like, MethodType type) {
        if(type.equals(MethodType.ADD)) {
            this.likeCnt++;
            this.likes.add(like);
            like.setCloth(this);
        }
        else if(type.equals(MethodType.DELETE)) {
            this.likeCnt--;
            this.likes.remove(like);
            like.setCloth(null);
        }
        return this;
    }

    public Cloth addImage(Image image) {
        images.add(image);
        image.setCloth(this);
        return this;
    }
}
