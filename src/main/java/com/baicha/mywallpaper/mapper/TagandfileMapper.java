package com.baicha.mywallpaper.mapper;

import com.baicha.mywallpaper.entity.Files;
import com.baicha.mywallpaper.entity.Tagandfile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author 12793
* @description 针对表【tagAndFile】的数据库操作Mapper
* @createDate 2025-07-19 11:16:42
* @Entity com.baicha.mywallpaper.entity.Tagandfile
*/
@Mapper
public interface TagandfileMapper extends BaseMapper<Tagandfile> {


    @Select("SELECT * FROM files WHERE file_id IN (SELECT file_id FROM tagAndFile WHERE tag_id = #{tagId}) AND status = '已审核'")
    IPage<Files> selectFilesPageByTag(Page<?> page, @Param("tagId") Integer tagId);
}




