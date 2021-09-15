package com.cloth.wardrobe.entity.common;

import com.cloth.wardrobe.entity.BaseTimeEntity;
import com.cloth.wardrobe.entity.clothes.Cloth;
import com.cloth.wardrobe.entity.clothes.Record;
import com.cloth.wardrobe.entity.clothes.Wardrobe;
import com.cloth.wardrobe.entity.community.PostEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.*;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "images")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Image extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String imageLocalPath;

    private String imageServerPath;

    private String fileName;

    @OneToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Builder
    public Image(String imageLocalPath, String imageServerPath, String fileName) {
        this.imageLocalPath = imageLocalPath;
        this.imageServerPath = imageServerPath;
        this.fileName = fileName;
    }

    public Image fileUpload(MultipartFile file, String email) throws IOException {
        this.fileName = UUID.randomUUID() + "_" + email + "_" + file.getOriginalFilename();
        this.imageLocalPath = "/Users/iyunbog/Downloads/Study/wardrobe_image/" + fileName;
        this.imageServerPath = "/image/" + fileName;

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(imageLocalPath));
        stream.write(file.getBytes());
        stream.close();
        return this;
    }
}
