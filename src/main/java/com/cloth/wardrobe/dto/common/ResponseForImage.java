package com.cloth.wardrobe.dto.common;


import com.cloth.wardrobe.domain.common.Image;
import lombok.Getter;

@Getter
public class ResponseForImage {

    private String fileName;
    private String imageLocalPath;
    private String imageServerPath;

    public ResponseForImage(Image image) {
        fileName = image.getFileName();
        imageServerPath = image.getImageServerPath();
        imageLocalPath = image.getImageLocalPath();
    }
}
