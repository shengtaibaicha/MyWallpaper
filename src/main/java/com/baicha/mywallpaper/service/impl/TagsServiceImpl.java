package com.baicha.mywallpaper.service.impl;

import com.baicha.mywallpaper.entity.Files;
import com.baicha.mywallpaper.entity.Tagandfile;
import com.baicha.mywallpaper.mapper.FilesMapper;
import com.baicha.mywallpaper.mapper.TagandfileMapper;
import com.baicha.mywallpaper.model.Respons;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baicha.mywallpaper.entity.Tags;
import com.baicha.mywallpaper.service.TagsService;
import com.baicha.mywallpaper.mapper.TagsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 12793
* @description 针对表【tags】的数据库操作Service实现
* @createDate 2025-07-18 21:59:23
*/
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags>
    implements TagsService{

    @Autowired
    private TagsMapper tagsMapper;

    @Autowired
    private TagandfileMapper tagandfileMapper;

    @Override
    public Respons getTags() {
        List<Tags> tags = tagsMapper.selectList(null);
        return Respons.ok(tags);
    }

    @Override
    public Respons getByTagId(Integer page, Integer size, Integer tagId) {
        Page<Files> tagsPage = new Page<>(page, size);
        IPage<Files> filesIPage = tagandfileMapper.selectFilesPageByTag(tagsPage, tagId);
        return Respons.ok(filesIPage);
    }
}




