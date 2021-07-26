package com.cloth.wardrobe.domain.s3;

import com.cloth.wardrobe.domain.BaseTimeEntity;
import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Record;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.dto.clothes.WardrobeSaveRequestDto;
import com.cloth.wardrobe.dto.common.FileSaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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

    private String imagePath;


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
    public Image(String imagePath) {
        this.imagePath = imagePath;
    }

    public Image fileUpload(FileSaveRequestDto fileSaveRequestDto) throws IOException {
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fileSaveRequestDto.getFilePath())));
        stream.write(fileSaveRequestDto.getUploadFile().getBytes());
        stream.close();
        this.imagePath = fileSaveRequestDto.getFilePath();

        return this;
    }
}
