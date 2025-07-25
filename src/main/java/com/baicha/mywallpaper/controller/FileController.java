package com.baicha.mywallpaper.controller;

import com.baicha.mywallpaper.model.Respons;
import com.baicha.mywallpaper.model.dto.FileUploadDto;
import com.baicha.mywallpaper.service.FilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/wallpaper/file")
public class FileController {

    @Autowired
    private FilesService filesService;

    @PostMapping("/upload")
    @Operation(summary = "文件上传", description = "接收文件")
    public Respons upload(FileUploadDto fileUploadDto) {
        Respons result;
        try {
            result = filesService.upload(fileUploadDto.getFile(), fileUploadDto.getTagId());
        } catch (IOException e) {
            log.error(e.getMessage());
            result = Respons.error("上传失败！");
        }
        return result;
    }

    @GetMapping("/download")
    @Operation(summary = "文件下载", description = "接收文件名进行下载")
    public void downloadFile(@RequestParam String fileName, HttpServletResponse response) {
        filesService.download(fileName, response);
    }

    @PostMapping("/delete")
    @Operation(summary = "文件删除", description = "接收文件名进行删除")
    public Respons deleteFile(@Parameter(name = "fileName", description = "文件名") @RequestParam String fileName) {
        Respons result = filesService.delete(fileName);
        return result;
    }

    @GetMapping("/find/page")
    @Operation(summary = "分页查询图片", description = "接收分页参数进行查询")
    public Respons findPage(@Parameter(name = "page", description = "当前页码") @RequestParam Integer page,
                            @Parameter(name = "size", description = "每页数据量") @RequestParam Integer size) {
        Respons result = filesService.findPage(page, size);
        return result;
    }

    @GetMapping("/find/name")
    @Operation(summary = "根据文件名模糊分页查询", description = "接收分页参数和文件名进行查询")
    public Respons findByNamePage(@Parameter(name = "page", description = "当前页码") @RequestParam Integer page,
                                  @Parameter(name = "size", description = "每页数据量") @RequestParam Integer size,
                                  @Parameter(name = "name", description = "文件名") @RequestParam String name) {
        Respons result = filesService.findByNamePage(page, size, name);
        return result;
    }
}
