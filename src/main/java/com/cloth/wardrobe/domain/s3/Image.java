package com.cloth.wardrobe.domain.s3;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@Getter @Setter
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String imageS3Path;

    private LocalDateTime registDate;
}
