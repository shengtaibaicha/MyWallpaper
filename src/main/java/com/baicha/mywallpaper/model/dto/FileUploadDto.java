package com.baicha.mywallpaper.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadDto {
    private MultipartFile file;
    private Integer tagId;
}
