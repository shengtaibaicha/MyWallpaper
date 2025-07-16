package com.baicha.mywallpaper.controller;

import com.baicha.mywallpaper.model.Respons;
import com.baicha.mywallpaper.service.FilesService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/wallpaper/file")
public class FileController {

    @Autowired
    private FilesService filesService;

    @PostMapping("/upload")
    public Respons upload(@RequestParam("file") MultipartFile file) {
        Respons result = filesService.upload(file);
        return result;
    }

    @GetMapping("/download")
    public void downloadFile(@RequestParam String fileName, HttpServletResponse response) {
        filesService.download(fileName, response);
    }

    @PostMapping("/delete")
    public Respons deleteFile(@RequestParam String fileName) {
        Respons result = filesService.delete(fileName);
        return result;
    }

    @GetMapping("/find/page")
    public Respons findPage(@RequestParam Integer page, @RequestParam Integer size) {
        Respons result = filesService.findPage(page, size);
        return result;
    }
}
