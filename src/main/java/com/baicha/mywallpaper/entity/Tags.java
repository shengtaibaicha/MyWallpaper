package com.baicha.mywallpaper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName tags
 */
@TableName(value ="tags")
@Data
public class Tags {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer tagId;

    /**
     * 
     */
    private String tagName;
}