package com.cloth.wardrobe.domain.s3;

import com.cloth.wardrobe.domain.BaseTimeEntity;
import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.*;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "images")
@Getter
@NoArgsConstructor
public class Image extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String imageLocalPath;

    private String imageServerPath;

    private String fileName;

    @Setter
    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cloth_id")
    private Cloth cloth;

    @Builder
    public Image(String imageLocalPath, String imageServerPath, String fileName) {
        this.imageLocalPath = imageLocalPath;
        this.imageServerPath = imageServerPath;
        this.fileName = fileName;
    }

    public Image fileUpload(MultipartFile file, String email) throws IOException {
        StringBuilder realPathOfFile = new StringBuilder();
        this.fileName = UUID.randomUUID() + "_" + email + "_" + file.getOriginalFilename();
        this.imageLocalPath = "/Users/iyunbog/Downloads/Study/wardrobe_image/" + fileName;
        this.imageServerPath = "/image/" + fileName;

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(imageLocalPath)));
        stream.write(file.getBytes());
        stream.close();
        return this;
    }
}
