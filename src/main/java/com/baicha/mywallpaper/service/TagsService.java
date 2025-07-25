package com.baicha.mywallpaper.service;

import com.baicha.mywallpaper.entity.Tags;
import com.baicha.mywallpaper.model.Respons;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 12793
* @description 针对表【tags】的数据库操作Service
* @createDate 2025-07-18 21:59:23
*/
public interface TagsService extends IService<Tags> {

    Respons getTags();

    Respons getByTagId(Integer page, Integer size, Integer tagId);

}
