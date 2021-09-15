package com.cloth.wardrobe.entity.clothes;

import com.cloth.wardrobe.entity.community.PostEntity;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.common.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clothes")
@Getter
@NoArgsConstructor
public class Cloth extends PostEntity {

    @Setter
    @ManyToOne
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @OneToMany(mappedBy = "cloth", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Record> records = new ArrayList<>();

    public String clothName;

    private String clothType;

    private String buyingDate;

    private String buyingWay;

    private String clothColor;

    private String clothBrand;

    @Builder
    public Cloth(Member member, Wardrobe wardrobe, Image image, String clothName, String clothType, String buyingDate, String buyingWay, String clothColor, String clothBrand) {
        super(member, image);
        this.wardrobe = wardrobe;
        this.clothName = clothName;
        this.clothType = clothType;
        this.buyingDate = buyingDate;
        this.buyingWay = buyingWay;
        this.clothColor = clothColor;
        this.clothBrand = clothBrand;
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
