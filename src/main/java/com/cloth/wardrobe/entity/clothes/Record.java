package com.cloth.wardrobe.entity.clothes;

import com.cloth.wardrobe.entity.community.Comment;
import com.cloth.wardrobe.entity.community.Like;
import com.cloth.wardrobe.entity.community.PostEntity;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.common.Image;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "records", indexes = {@Index(columnList = "id")})
public class Record extends PostEntity {

    @Setter
    @ManyToOne
    @JoinColumn(name = "cloth_id")
    private Cloth cloth;

    private String content;

    @Builder
    public Record(Member member, Image image, Cloth cloth, String content) {
        super(member, image);
        this.cloth = cloth;
        this.content = content;
    }
}
