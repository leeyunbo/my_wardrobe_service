package com.cloth.wardrobe.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class FileSaveRequestDto {
    String filename;
    String directory;
    String filePath;
    MultipartFile uploadFile;

    public FileSaveRequestDto(String filename, String directory, String filePath, MultipartFile multipartFile) {
        this.filename = filename;
        this.directory = directory;
        this.filePath = filePath;
        this.uploadFile = multipartFile;
    }
}
