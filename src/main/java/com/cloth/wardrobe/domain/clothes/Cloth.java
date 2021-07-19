package com.cloth.wardrobe.domain.clothes;

import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.domain.community.Post;
import com.cloth.wardrobe.domain.member.Member;
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
    @ManyToOne
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Like> likes = new ArrayList<>();

    public String clothName;

    private String clothType;

    private String buyingDate;

    private String buyingWay;

    private String clothColor;

    private String clothBrand;

    private int likeCnt;

    @Builder
    public Cloth(Member member, Wardrobe wardrobe, List<Image> images, String clothName, String clothType, String buyingDate, String buyingWay, String clothColor, String clothBrand) {
        this.member = member;
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

    public Cloth addImage(Image image) {
        images.add(image);
        image.setCloth(this);
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
}
