package com.baicha.mywallpaper.service;

import com.baicha.mywallpaper.entity.Files;
import com.baicha.mywallpaper.model.Respons;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author 12793
* @description 针对表【files】的数据库操作Service
* @createDate 2025-07-15 14:59:27
*/
public interface FilesService extends IService<Files> {

    Respons upload(MultipartFile file, Integer tagId) throws IOException;

    void download(String fileName, HttpServletResponse response);

    Respons delete(String fileName);

    Respons findPage(Integer page, Integer size);

    Respons findByNamePage(Integer page, Integer size, String name);
}
