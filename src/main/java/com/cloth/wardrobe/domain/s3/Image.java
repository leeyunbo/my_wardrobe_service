package com.cloth.wardrobe.domain.s3;

import com.cloth.wardrobe.domain.BaseTimeEntity;
import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@Getter
@NoArgsConstructor
public class Image extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String imageS3Path;


    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cloth_id")
    private Cloth cloth;

    @Builder
    public Image(String imageS3Path) {
        this.imageS3Path = imageS3Path;
    }
}
