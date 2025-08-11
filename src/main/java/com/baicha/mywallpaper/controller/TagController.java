package com.baicha.mywallpaper.controller;

import com.baicha.mywallpaper.model.Respons;
import com.baicha.mywallpaper.model.dto.TagDto;
import com.baicha.mywallpaper.service.TagsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallpaper/file/tag")
public class TagController {

    @Autowired
    private TagsService tagsService;

    // 获取所有tag
    @GetMapping("/tags")
    @Operation(summary = "获取分类信息标签")
    public Respons getTags() {
        Respons result = tagsService.getTags();
        return result;
    }

    @PostMapping("/find/page")
    @Operation(summary = "根据分类标签分页获取图片")
    public Respons findPage(@RequestBody TagDto tag) {
        Respons result = tagsService.getByTagId(tag.getPage(), tag.getSize(), tag.getTagId());
        return result;
    }
}
