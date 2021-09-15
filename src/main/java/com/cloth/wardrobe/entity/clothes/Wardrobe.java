package com.cloth.wardrobe.entity.clothes;

import com.cloth.wardrobe.entity.community.PostEntity;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.common.Image;
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
@DiscriminatorValue("Wardrobe")
public class Wardrobe extends PostEntity {

    private String name;

    private String isPublic;

    @OneToMany(mappedBy = "wardrobe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Cloth> clothes = new ArrayList<>();

    @Builder
    public Wardrobe(Member member, Image image, String name, String isPublic) {
        super(member, image);
        this.name = name;
        this.isPublic = isPublic;
    }

    public Wardrobe addCloth(Cloth cloth) {
        this.clothes.add(cloth);
        cloth.setWardrobe(this);
        return this;
    }

    public Wardrobe deleteCloth(Cloth cloth) {
        this.clothes.remove(cloth);
        cloth.setWardrobe(null);
        return this;
    }

    public void update(Image image, String name, String isPublic) {
        super.setImage(image);
        this.name = name;
        this.isPublic = isPublic;
    }
}
